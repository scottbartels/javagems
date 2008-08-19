package gems.logging;

/**
 * Encapsulates information about a logging record creator.
 * 
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class CreatorInfo {

	private static final String EMPTY_STRING = "";

	/**
	 * A name of this package.
	 */
	private static final String CURRENT_PACKAGE = CreatorInfo.class.getPackage().getName();

    /**
     * A creator's class name.
     */
    private final String className;

    /**
     * A creator's method name.
     */
    private final String methodName;

    /**
     * A creator's line number.
     */
    private final int lineNumber;

    /**
     * Creates a new creator info object.
     */
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

    /**
     * Returns a creator class name or an empty string if unknown. This method never returns {@code null}.
     *
     * @return a creator class name or an empty string if unknown.
     */
	public String getClassName() {
		return className;
	}

    /**
     * Returns a creator method name or an empty string if unknown. This method never returns {@code null}.
     *
     * @return a creator method name or an empty string if unknown.
     */
    public String getMethodName() {
		return methodName;
	}

    /**
     * Returns a creator line number or zero if inknown.
     *
     * @return a creator line number or zero if inknown.
     */
    public int getLineNumber() {
		return lineNumber;
	}

	/**
	 * Tries to find a caller producing the logging record by stack trace analysis.
	 *
	 * @return a caller producing the logging record.
	 */
	@SuppressWarnings({"ThrowableInstanceNeverThrown"})
    private static StackTraceElement findCaller() {
		for (final StackTraceElement element : (new Throwable()).getStackTrace()) {
			if (!element.getClassName().startsWith(CURRENT_PACKAGE)) {
				return element;
			}
		}
		return null;
	}


}
