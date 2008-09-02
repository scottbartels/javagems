package logging;

import gems.logging.Logger;
import gems.logging.LoggingEntryPoint;
import gems.logging.LoggingFacility;
import gems.logging.LoggingHandler;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import gems.logging.handlers.PrintStreamLoggingHandler;
import gems.logging.loggers.SynchronousLogger;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingBasicUsage {

	/**
	 * A logging entry point.
	 */
	private static final LoggingEntryPoint LEP;

	static {
		/*
		 * At first, a logger is created. This is the real entry point to the
		 * logging subsystem, a LoggingEntryPoint is only a facade providing
		 * an easier access to the Logger interface and logging records creation.
		 */
		final Logger logger = new SynchronousLogger();
		/*
		 * At third, at least one handler should be provided to ensure
		 * the output from the logging subsystem.
		 */
		final LoggingHandler handler = new PrintStreamLoggingHandler(System.out);
		/*
		 * Putting logger and handler together.
		 */
		logger.addHandler(handler);
		/*
		 * Finally, the logger is encapsulated into the logging entry point.
		 */
		LEP = new LoggingEntryPoint(logger);
	}

	/**
	 * An example entry point. This is very basic example of logging usage.
	 *
	 * @param args command line arguments, completelly ignored.
	 */
	public static void main(final String[] args) {
		/*
		 * The easiest way how to log something is to put it as a single
		 * argument to the log() method. Please note that an arbitrary
		 * object can be use here, not only a string. Even more, 'null'
		 * is allowed here.
		 */
		LEP.log(null);
		LEP.log(" Hello, world! ");
		LEP.log(new Object());

		/*
		 * A pretty obvious use case is to assign a logging severity to a logged message.
		 */
		LEP.log("Something is wrong.", LoggingSeverity.WARNING);

		/*
		 * If there is a need to distinguish among several kinds of logging messages,
		 * for instance "messages related to performance" and "messages related to security",
		 * the logging facility may be assigned to logging records explicitly.
		 */
		LEP.log("Bad boys are here.", LoggingFacility.getFacility("SECURITY"));
		LEP.log("Load is too high.", LoggingFacility.getFacility("PERFORMANCE"));

		/*
		 * Of course, facility and severity can be assigned to a logging message at one go.
		 */
		LEP.log("Performance information.", LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO);

		/*
		 * This is the most complex use case: one logging message has assigned more
		 * facilities, each of them with a different severity. A facility-severity
		 * in encapsulated in LoggingTag object.
		 */
		LEP.log("Too many connections.",
				new LoggingTag(LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO),
				new LoggingTag(LoggingFacility.getFacility("SECURITY"), LoggingSeverity.WARNING));

		/*
		 * Finally, a special approach is used for exceptions logging.
		 * A stack trace is generated, including chainded causes.
		 */
		LEP.log(new RuntimeException("Ooops.", new RuntimeException("I'm the cause.")), LoggingSeverity.FATAL);
	}

}
