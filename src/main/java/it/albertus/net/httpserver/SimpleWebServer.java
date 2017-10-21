package it.albertus.net.httpserver;

import it.albertus.net.httpserver.AbstractHttpHandler;
import it.albertus.net.httpserver.FilesHandler;
import it.albertus.net.httpserver.LightweightHttpServer;
import it.albertus.net.httpserver.config.HttpServerDefaultConfig;
import it.albertus.net.httpserver.config.IHttpServerConfig;

public class SimpleWebServer {

	private SimpleWebServer() {
		throw new IllegalAccessError();
	}

	public static void main(final String... args) {
		final IHttpServerConfig configuration = new HttpServerDefaultConfig() {
			@Override
			public AbstractHttpHandler[] getHandlers() {
				return new AbstractHttpHandler[] { new FilesHandler(this, args[1], "/") };
			}

			@Override
			public int getPort() {
				return Integer.parseInt(args[0]);
			}
		};

		final LightweightHttpServer server = new LightweightHttpServer(configuration);
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
		server.start(false);
	}

}
