package gems.io.mime;

interface ExtensionDetector<T> {

	String detect(T path);

}
