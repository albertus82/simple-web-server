package it.albertus.httpserver;

import it.albertus.httpserver.AbstractHttpHandler;
import it.albertus.httpserver.DefaultHttpServerConfiguration;
import it.albertus.httpserver.FilesHandler;
import it.albertus.httpserver.IHttpServerConfiguration;
import it.albertus.httpserver.LightweightHttpServer;

public class SimpleWebServer {

	private SimpleWebServer() {
		throw new IllegalAccessError();
	}

	public static void main(final String... args) {
		final IHttpServerConfiguration httpServerConfiguration = new DefaultHttpServerConfiguration() {
			@Override
			public AbstractHttpHandler[] getHandlers() {
				return new AbstractHttpHandler[] { new FilesHandler(args[0], "/") };
			}

			@Override
			public int getPort() {
				return 80;
			}
		};

		final LightweightHttpServer server = new LightweightHttpServer(httpServerConfiguration);
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				server.stop();
			}
		});
		server.start(false);
	}

}
