package it.albertus.httpserver;

public class SimpleWebServer {

	private SimpleWebServer() {
		throw new IllegalAccessError();
	}

	public static void main(final String... args) {
		final IHttpServerConfiguration httpServerConfiguration = new DefaultHttpServerConfiguration() {
			@Override
			public AbstractHttpHandler[] getHandlers() {
				return new AbstractHttpHandler[] { new FilesHandler(args[1], "/") };
			}

			@Override
			public int getPort() {
				return Integer.parseInt(args[0]);
			}
		};

		final LightweightHttpServer server = new LightweightHttpServer(httpServerConfiguration);
		Runtime.getRuntime().addShutdownHook(new Thread(server::stop));
		server.start(false);
	}

}
