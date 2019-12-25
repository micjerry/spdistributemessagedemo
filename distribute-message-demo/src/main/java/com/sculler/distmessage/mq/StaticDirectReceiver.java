package com.sculler.distmessage.mq;

import java.util.Date;
import java.util.concurrent.CountDownLatch;

public class StaticDirectReceiver {
	private CountDownLatch countDownLatch = new CountDownLatch(1);

	public void processMessage(TestStudent student) {
		System.out.println(new Date() + "receive message is:" + student);
		countDownLatch.countDown();
	}

	public CountDownLatch getCountDownLatch() {
		return countDownLatch;
	}
}

