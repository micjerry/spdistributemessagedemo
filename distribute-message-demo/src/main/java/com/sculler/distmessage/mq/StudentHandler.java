package com.sculler.distmessage.mq;

public class StudentHandler {
	public void handleMessagge(TestStudent student) {
		System.out.println("dynamic receive message");
		System.out.println(student);
	}
}
