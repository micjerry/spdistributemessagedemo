package com.sculler.distmessage.mq;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MessageSender {
	@Autowired
	@Qualifier("dstRabbitTemplate")
	private AmqpTemplate rabbitTemplate;
	
	@Value("${sculler.rabbitmq.queue}")
	String queueName;
	
	public void send(TestStudent student, String exchange, String routingkey) {
		rabbitTemplate.convertAndSend(exchange, routingkey, student);
	}
	
	public void directSend(TestStudent student) {
		rabbitTemplate.convertAndSend(queueName, student);
	}
}
