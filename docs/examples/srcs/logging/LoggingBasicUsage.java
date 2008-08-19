package logging;

import gems.logging.Logger;
import gems.logging.LoggingEntryPoint;
import gems.logging.LoggingFacility;
import gems.logging.LoggingSeverity;
import gems.logging.LoggingTag;
import gems.logging.formatters.PlainLoggingRecordFormatter;
import gems.logging.handlers.PrintStreamLoggingHandler;
import gems.logging.loggers.SynchronousLogger;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class LoggingBasicUsage {

	public static void main(final String[] args) throws Exception {
		final Logger logger = new SynchronousLogger();
		logger.addHandler(new PrintStreamLoggingHandler(System.out, new PlainLoggingRecordFormatter()));
		final LoggingEntryPoint lep = new LoggingEntryPoint(logger);

		lep.log("Simple message.");
		lep.log("Simple message with severity.", LoggingSeverity.WARNING);
		lep.log("Simple message with facility.", LoggingFacility.getFacility("SECURITY"));
		lep.log("Facility and severity together.", LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO);
		lep.log("The most complex case.",
				new LoggingTag(LoggingFacility.getFacility("PERFORMANCE"), LoggingSeverity.INFO),
				new LoggingTag(LoggingFacility.getFacility("SECURITY"), LoggingSeverity.WARNING),
				new LoggingTag(LoggingFacility.NULL_FACILITY, LoggingSeverity.CRITICAL));

		lep.log(new RuntimeException("Ooops.", new RuntimeException("Cause.")), LoggingSeverity.FATAL);
	}

}
