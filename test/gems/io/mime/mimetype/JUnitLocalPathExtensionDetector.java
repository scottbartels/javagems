package gems.io.mime.mimetype;

import org.junit.Assert;
import org.junit.Test;

/**
 * Unit tests for {@code LocalPathExtensionDetector} class.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public final class JUnitLocalPathExtensionDetector {

	/**
	 * Checks whether {@code null} path separator is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullPathSeparatorIsForbidden() {
		new LocalPathExtensionDetector(null, ".");
	}

	/**
	 * Checks whether empty path separator is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void emptyPathSeparatorIsForbidden() {
		new LocalPathExtensionDetector("", ".");
	}

	/**
	 * Checks whether {@code null} extension separator is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullExtensionSeparatorIsForbidden() {
		new LocalPathExtensionDetector("/", null);
	}

	/**
	 * Checks whether empty extension separator is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void emptyExtensionSeparatorIsForbidden() {
		new LocalPathExtensionDetector("/", "");
	}

	/**
	 * Checks whether {@code null} context is forbidden.
	 */
	@Test(expected = IllegalArgumentException.class) public void nullContextIsForbidden() {
		new LocalPathExtensionDetector("/", ".").detect(null);
	}

	/**
	 * Checks extension detection for various paths using obvious separators.
	 */
	@Test public void testVariousPahts() {
		final ExtensionDetector<String> fixture = new LocalPathExtensionDetector("/", ".");
		Assert.assertEquals("", fixture.detect(""));
		Assert.assertEquals("", fixture.detect("/"));
		Assert.assertEquals("txt", fixture.detect("file.txt"));
		Assert.assertEquals("Makefile", fixture.detect("Makefile"));
		Assert.assertEquals("Makefile", fixture.detect("Linux.Makefile"));
		Assert.assertEquals("txt", fixture.detect("/file.txt"));
		Assert.assertEquals("txt", fixture.detect("/directory/file.txt"));
		Assert.assertEquals("gz", fixture.detect("archive.tar.gz"));
		Assert.assertEquals("svn", fixture.detect("/working/copy/.svn"));
		Assert.assertEquals("", fixture.detect("/working/copy/.svn/"));
	}

	/**
	 * Checks behavior for various path separators.
	 */
	@Test public void testVarioiusPathSeparators() {
		final String path = "a/b\\c::d";
		Assert.assertEquals("b\\c::d", new LocalPathExtensionDetector("/", ".").detect(path));
		Assert.assertEquals("c::d", new LocalPathExtensionDetector("\\", ".").detect(path));
		Assert.assertEquals("d", new LocalPathExtensionDetector(":", ".").detect(path));
		Assert.assertEquals("a/b\\c::d", new LocalPathExtensionDetector(";", ".").detect(path));
		Assert.assertEquals(":d", new LocalPathExtensionDetector("c:", ".").detect(path));
		Assert.assertEquals("", new LocalPathExtensionDetector(path, ".").detect(path));
	}

	/**
	 * Checks behavior for various extension separators.
	 */
	@Test public void testVariousExtensionSeparators() {
		final String path = "a/b\\c::d";
		Assert.assertEquals("a/b\\c::d", new LocalPathExtensionDetector("@", ".").detect(path));
		Assert.assertEquals("d", new LocalPathExtensionDetector("@", ":").detect(path));
		Assert.assertEquals("c::d", new LocalPathExtensionDetector("@", "\\").detect(path));
		Assert.assertEquals("b\\c::d", new LocalPathExtensionDetector("@", "/").detect(path));
		Assert.assertEquals(":d", new LocalPathExtensionDetector("@", "c:").detect(path));
		Assert.assertEquals("", new LocalPathExtensionDetector("@", path).detect(path));
	}

}
