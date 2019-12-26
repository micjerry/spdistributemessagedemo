package com.sculler.distmessage.mq;

import org.springframework.amqp.core.AmqpAdmin;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.adapter.MessageListenerAdapter;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class DynamicReceiverFactory {
	@Autowired
	private AmqpAdmin admin;
	
	@Autowired
	@Qualifier("simpleConnectionFactory")
	private ConnectionFactory connectionFactory;
	
	
	public void addConsumer(String queueName, String exchange, String routingKey, StudentHandler handler) {
		//temp queue
		Queue queue = new Queue(queueName, false, true, true);
		Binding binding = new Binding(queueName, Binding.DestinationType.QUEUE, exchange, routingKey, null);
		admin.declareExchange(new TopicExchange(exchange));;
		admin.declareQueue(queue);
		admin.declareBinding(binding);
		
		SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
		container.setConnectionFactory(connectionFactory);
		container.setQueues(queue);
		
		MessageListenerAdapter adapter = new MessageListenerAdapter(handler, "handleMessagge");
		adapter.setMessageConverter(new Jackson2JsonMessageConverter());
		container.setMessageListener(adapter);
		container.start();
	}

}
