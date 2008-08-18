package gems.logging;

/**
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CreatorInfo {

	private static final String EMPTY_STRING = "";

	/**
	 * A name of this package.
	 */
	private static final String CURRENT_PACKAGE = LoggingRecord.class.getPackage().getName();

	private final String className;

	private final String methodName;

	private final int lineNumber;
	
	CreatorInfo() {
		final StackTraceElement creator = findCaller();
		if (creator != null) {
			className = creator.getClassName() != null ? creator.getClassName() : EMPTY_STRING;
			methodName = creator.getMethodName() != null ? creator.getMethodName() : EMPTY_STRING;
			lineNumber = creator.getLineNumber() > 0 ? creator.getLineNumber() : 0;
		} else {
			className = EMPTY_STRING;
			methodName = EMPTY_STRING;
			lineNumber = 0;
		}
	}


	public String getClassName() {
		return className;
	}

	public String getMethodName() {
		return methodName;
	}

	public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * Tries to find a caller producing the logging record by stack trace analysis.
	 *
	 * @return a caller producing the logging record.
	 */
	private static StackTraceElement findCaller() {
		for (final StackTraceElement element : (new Throwable()).getStackTrace()) {
			if (!element.getClassName().startsWith(CURRENT_PACKAGE)) {
				return element;
			}
		}
		return null;
	}


}
