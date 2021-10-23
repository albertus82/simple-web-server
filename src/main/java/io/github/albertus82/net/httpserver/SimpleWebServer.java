package io.github.albertus82.net.httpserver;

import io.github.albertus82.net.httpserver.config.HttpServerDefaultConfig;

public class SimpleWebServer {

	private LightweightHttpServer server;

	public static void main(final String... args) {
		new SimpleWebServer(Integer.parseInt(args[0]), args[1]).server.start(false);
	}

	public SimpleWebServer(final int port, final String basePath) {
		if (port < 1 || port > 0xFFFF) {
			throw new IllegalArgumentException("Port out of range: " + port);
		}
		if (basePath == null) {
			throw new NullPointerException("Invalid base path: " + basePath);
		}
		server = new LightweightHttpServer(new HttpServerDefaultConfig() {
			@Override
			public HttpPathHandler[] getHandlers() {
				return new HttpPathHandler[] { new FilesHandler(this, basePath, "/") };
			}

			@Override
			public int getPort() {
				return port;
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
	}

	LightweightHttpServer getServer() {
		return server;
	}

}
