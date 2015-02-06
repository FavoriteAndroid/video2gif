package com.bufer;

import it.sauronsoftware.cron4j.Scheduler;

public class Do {

	public static void main(String[] args) throws Throwable {
		
		Scheduler s = new Scheduler();
		s.schedule("* * * * *", new GifGenerator());

		s.start();
		try {
			Thread.sleep(1L * 6000L * 60L * 10L); // 1 hour
		} catch (InterruptedException e) {
			;
		}

		s.stop();
	}
}
