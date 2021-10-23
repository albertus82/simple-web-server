package io.github.albertus82.net.httpserver;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

public class SimpleWebServerTest {

	private static LightweightHttpServer server;

	@After
	public void after() {
		if (server != null) {
			server.stop(0);
		}
	}

	@AfterClass
	public static void testMainOk() {
		SimpleWebServer.main("8080", "");
		Assert.assertTrue(true);
	}

	@Test
	public void testMainKo() {
		try {
			SimpleWebServer.main("8080x", "");
			Assert.assertTrue(false);
		}
		catch (final NumberFormatException e) {
			Assert.assertNotNull(e);
			e.printStackTrace();
		}
	}

	@Test
	public void testOk() throws InterruptedException, TimeoutException {
		server = new SimpleWebServer(new Random().nextInt(65535 - 1024) + 1024, ".").getServer();
		startServer();
		Assert.assertTrue(true);
	}

	@Test
	public void testWrongPort() throws InterruptedException {
		try {
			server = new SimpleWebServer(new Random().nextInt(65535) + 65536, ".").getServer();
			Assert.assertTrue(false);
		}
		catch (final IllegalArgumentException e1) {
			Assert.assertNotNull(e1);
			e1.printStackTrace();
			try {
				server = new SimpleWebServer(-1 * (new Random().nextInt(65535) + 65536), ".").getServer();
				Assert.assertTrue(false);
			}
			catch (final IllegalArgumentException e2) {
				Assert.assertNotNull(e2);
				e2.printStackTrace();
			}
		}
	}

	@Test
	public void testWrongPath() throws InterruptedException {
		try {
			server = new SimpleWebServer(new Random().nextInt(65535 - 1024) + 1024, null).getServer();
			Assert.assertTrue(false);
		}
		catch (final NullPointerException e) {
			Assert.assertNotNull(e);
		}
	}

	private static void startServer() throws InterruptedException, TimeoutException {
		server.start();
		final int retryPeriod = 100; // ms
		final int timeout = 10000; // ms
		int time = 0;
		do {
			try {
				TimeUnit.MILLISECONDS.sleep(time += retryPeriod);
			}
			catch (final InterruptedException e) {
				Thread.currentThread().interrupt();
				throw e;
			}
		}
		while (!server.isRunning() && time < timeout);
		if (time >= timeout && !server.isRunning()) {
			throw new TimeoutException("Cannot start the server.");
		}
	}

}
