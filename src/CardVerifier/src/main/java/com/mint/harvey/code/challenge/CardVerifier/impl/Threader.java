package com.mint.harvey.code.challenge.CardVerifier.impl;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
/**
 * @Authur Harvey Imama
 * Starts up the thread pool and determines the number of threads to use for application
 */
public class Threader {

	private static ExecutorService executor;
	private final static int NUMBER_OF_THREADS = 10;

	private Threader() {
	}

	public static ExecutorService startThreader() {
		if (executor == null)
			executor = Executors.newFixedThreadPool(NUMBER_OF_THREADS);

		return executor;
	}

}
