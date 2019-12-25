package com.sculler.distmessage.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.sculler.distmessage.mq.DynamicReceiverFactory;
import com.sculler.distmessage.mq.MessageSender;
import com.sculler.distmessage.mq.StudentHandler;
import com.sculler.distmessage.mq.TestStudent;

@RestController
public class DistMessageController {
	@Autowired
	private MessageSender msgSender;
	
	@Autowired
	private DynamicReceiverFactory factory;
	
	@Value("${sculler.rabbitmq.exchange}")
	String exchange;
	
	@Value("${sculler.rabbitmq.routingkey}")
	String routingkey;
	
	@Value("${sculler.rabbitmq.topicexchange}")
	String topicexchange;
	
	@RequestMapping(value = "/static/send", method = RequestMethod.GET)
	public String staticSend() {
		TestStudent student = new TestStudent("Tom", "2");
		msgSender.directSend(student);
		return "OK";
	}
	
	@RequestMapping(value = "/dynamic/send", method = RequestMethod.GET)
	public String dynamicSend() {
		TestStudent student = new TestStudent("Jerry", "3");
		msgSender.send(student, topicexchange, routingkey);
		return "OK";
	}
	
	@RequestMapping(value = "/dynamic/createconsumer", method = RequestMethod.GET)
	public String createConsumer() {
		factory.addConsumer("aklstest", topicexchange, routingkey, new StudentHandler());
		return "OK";
	}
	
	
}
