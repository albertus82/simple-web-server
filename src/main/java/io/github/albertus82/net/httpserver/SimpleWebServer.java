package io.github.albertus82.net.httpserver;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.util.concurrent.Callable;

import io.github.albertus82.net.httpserver.config.HttpServerDefaultConfig;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.ExitCode;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.Parameters;

@Command(name = "serve", sortSynopsis = false, sortOptions = false, mixinStandardHelpOptions = true, versionProvider = VersionProvider.class, description = "Start a simple HTTP server to serve files from a directory.")
public class SimpleWebServer implements Callable<Integer> {

	private LightweightHttpServer server;

	@Parameters(index = "0", arity = "0..1", paramLabel = "<port>", description = "TCP port to listen on (default: ${DEFAULT-VALUE}).", defaultValue = "8080")
	private int port;

	@Parameters(index = "1", arity = "0..1", paramLabel = "<directory>", description = "Directory containing the files to serve (default: ${DEFAULT-VALUE}).", defaultValue = ".")
	private Path basePath;

	@Option(names = { "-L", "--follow-links" }, description = "Follow symbolic links.")
	private boolean followLinks;

	public static void main(final String... args) {
		call(args);
	}

	static int call(final String... args) {
		return new CommandLine(new SimpleWebServer()).setUsageHelpAutoWidth(true).setOptionsCaseInsensitive(true).setCaseInsensitiveEnumValuesAllowed(true).setSubcommandsCaseInsensitive(true).execute(args);
	}

	private SimpleWebServer() {}

	SimpleWebServer(final int port, final Path basePath, final boolean followLinks) throws IOException {
		this.port = port;
		this.basePath = basePath;
		this.followLinks = followLinks;
		call();
	}

	@Override
	public Integer call() throws IOException {
		if (port < 1 || port > 0xFFFF) {
			throw new ParameterException(new CommandLine(this), "Port out of range: " + port);
		}

		if (basePath == null) {
			throw new ParameterException(new CommandLine(this), "Directory must not be null.");
		}
		basePath = basePath.toRealPath(followLinks ? new LinkOption[] { LinkOption.NOFOLLOW_LINKS } : new LinkOption[0]);
		if (!Files.isDirectory(basePath) || !Files.isReadable(basePath)) {
			throw new ParameterException(new CommandLine(this), "Directory does not exist, is not a directory, or is not readable: " + basePath);
		}

		initializeServer();
		server.start(false);
		return ExitCode.OK;
	}

	private void initializeServer() {
		server = new LightweightHttpServer(new HttpServerDefaultConfig() {
			@Override
			public HttpPathHandler[] getHandlers() {
				return new HttpPathHandler[] { new FilesHandler(this, basePath.toString(), "/") };
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
