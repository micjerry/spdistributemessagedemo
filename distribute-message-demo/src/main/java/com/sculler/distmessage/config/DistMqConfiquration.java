package com.sculler.distmessage.config;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.sculler.distmessage.mq.StaticDirectReceiver;

@Configuration
public class DistMqConfiquration {
	@Value("${sculler.rabbitmq.queue}")
	String queueName;
	
	@Value("${sculler.rabbitmq.exchange}")
	String exchange;
	
	@Value("${sculler.rabbitmq.routingkey}")
	String routingkey;
	
	@Value("${spring.rabbitmq.host}")
	String address;
	
	@Value("${spring.rabbitmq.username}")
	String username;
	
	@Value("${spring.rabbitmq.password}")
	String password;
	
	@Value("${sculler.rabbitmq.topicexchange}")
	String topicexchange;
	
	@Bean
	Queue queue() {
		return new Queue(queueName, false);
	}
	
	@Bean
	DirectExchange exchange() {
		return new DirectExchange(exchange);
	}
	
	@Bean
	TopicExchange topicExchange() {
		return new TopicExchange(topicexchange);
	}
	
	@Bean
	Binding binding(Queue queue, DirectExchange exchange) {
		return BindingBuilder.bind(queue).to(exchange).withQueueName();
	}
	
	@Bean
	public MessageConverter jsonMessageConverter() {
		return new Jackson2JsonMessageConverter();
	}
	
	@Bean(name = "simpleConnectionFactory")
	ConnectionFactory connectFactory() {
		CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
		connectionFactory.setAddresses(address);
		connectionFactory.setUsername(username);
		connectionFactory.setPassword(password);
		return connectionFactory;
	}
	
	@Bean
	public AmqpAdmin amqpAdmin(ConnectionFactory connectionFactory) {
	    return new RabbitAdmin(connectionFactory);
	}
	
	@Bean(name = "dstRabbitTemplate")
	public AmqpTemplate rabbitTemplate(@Qualifier("simpleConnectionFactory") ConnectionFactory connectionFactory) {
		final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
		rabbitTemplate.setMessageConverter(jsonMessageConverter());
		return rabbitTemplate;
	}
	
	@Bean
	StaticDirectReceiver receiver() {
        return new StaticDirectReceiver();
    }
	
	@Bean
    MessageListenerAdapter listenerAdapter(StaticDirectReceiver receiver) {
		MessageListenerAdapter adapter = new MessageListenerAdapter(receiver, "processMessage");
		adapter.setMessageConverter(new Jackson2JsonMessageConverter());
        return adapter;
    }
	
	@Bean
    SimpleMessageListenerContainer simpleMessageListenerContainer(MessageListenerAdapter listenerAdapter,
                                                                  @Qualifier("simpleConnectionFactory") ConnectionFactory connectionFactory) {
        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
        container.setMessageListener(listenerAdapter);
        container.setConnectionFactory(connectionFactory);
        container.setQueueNames(queueName);
        return container;
    }

}
