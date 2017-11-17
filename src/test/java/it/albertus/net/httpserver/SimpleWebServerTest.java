package it.albertus.net.httpserver;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.junit.After;
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

	@Test
	public void testOk() throws InterruptedException {
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
		catch (final IllegalArgumentException e) {
			Assert.assertNotNull(e);
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

	private static void startServer() throws InterruptedException {
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
	}

}
