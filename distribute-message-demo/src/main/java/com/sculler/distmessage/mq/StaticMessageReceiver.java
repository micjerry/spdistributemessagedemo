package com.sculler.distmessage.mq;

import org.springframework.stereotype.Component;

@Component
public class StaticMessageReceiver {
	/*(@RabbitListener(bindings = @QueueBinding(
		        value = @Queue(value = "${sculler.rabbitmq.queue}", durable = "false"),
		        exchange = @Exchange(value = "${sculler.rabbitmq.exchange}", ignoreDeclarationExceptions = "true"),
		        key = "${sculler.rabbitmq.routingkey}")
		  )
	public void recievedMessage(TestStudent student) {
		System.out.println("static receive message from queue");
		System.out.println(student);
	}*/
	
}
