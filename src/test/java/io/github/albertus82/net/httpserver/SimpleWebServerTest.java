package io.github.albertus82.net.httpserver;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Test;

import picocli.CommandLine.ExitCode;
import picocli.CommandLine.ParameterException;

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
		Assert.assertEquals(ExitCode.USAGE, SimpleWebServer.call("8080x", ""));
	}

	@Test
	public void testOk() throws InterruptedException, TimeoutException, IOException {
		server = new SimpleWebServer(new Random().nextInt(65535 - 1024) + 1024, Paths.get("."), false).getServer();
		startServer();
		Assert.assertTrue(true);
	}

	@Test
	public void testWrongPort() throws InterruptedException, IOException {
		Assert.assertThrows(ParameterException.class, () -> server = new SimpleWebServer(new Random().nextInt(65535) + 65536, Paths.get("."), false).getServer());
	}

	@Test
	public void testNegativePort() throws InterruptedException, IOException {
		Assert.assertThrows(ParameterException.class, () -> server = new SimpleWebServer(-1 * (new Random().nextInt(65536)), Paths.get("."), false).getServer());
	}

	@Test
	public void testWrongPath() throws InterruptedException, IOException {
		Assert.assertThrows(ParameterException.class, () -> server = new SimpleWebServer(new Random().nextInt(65535 - 1024) + 1024, null, false).getServer());
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
