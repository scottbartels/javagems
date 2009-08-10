package gems.io.mime.mimetype;

/**
 * An extension detector gets a filename extension from givne context information.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 * @param <T> type of context.
 */
interface ExtensionDetector<T> {

	/**
	 * Analyses given context and tries to determine a filename extension.
	 *
	 * @param context analyzed context.
	 *
	 * @return filename extension detected from a given context.
	 */
	String detect(T context);

}
