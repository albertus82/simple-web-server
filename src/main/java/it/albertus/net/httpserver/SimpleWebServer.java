package it.albertus.net.httpserver;

import it.albertus.net.httpserver.config.HttpServerDefaultConfig;

public class SimpleWebServer {

	private SimpleWebServer() {
		throw new IllegalAccessError();
	}

	public static void main(final String... args) {
		final LightweightHttpServer server = new LightweightHttpServer(new HttpServerDefaultConfig() {
			@Override
			public HttpPathHandler[] getHandlers() {
				return new HttpPathHandler[] { new FilesHandler(this, args[1], "/") };
			}

			@Override
			public int getPort() {
				return Integer.parseInt(args[0]);
			}
		});
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
		server.start(false);
	}

}
