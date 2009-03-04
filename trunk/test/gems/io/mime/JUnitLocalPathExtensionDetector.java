package gems.io.mime;

import org.junit.Test;
import org.junit.Assert;

public final class JUnitLocalPathExtensionDetector {

	private final ExtensionDetector<String> fixture = new LocalPathExtensionDetector("/", "."); 

	@Test(expected = IllegalArgumentException.class) public void nullPathSeparatorIsForbidden() {
		new LocalPathExtensionDetector(null, ".");
	}

	@Test(expected = IllegalArgumentException.class) public void emptyPathSeparatorIsForbidden() {
		new LocalPathExtensionDetector("", ".");
	}

	@Test(expected = IllegalArgumentException.class) public void nullExtensionSeparatorIsForbidden() {
		new LocalPathExtensionDetector("/", null);
	}

	@Test(expected = IllegalArgumentException.class) public void emptyExtensionSeparatorIsForbidden() {
		new LocalPathExtensionDetector("/", "");
	}

	@Test(expected = IllegalArgumentException.class) public void nullPathIsForbidden() {
		fixture.detect(null);
	}

	@Test public void testVariousPahts() {
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

	@Test public void testVarioiusPathSeparators() {
		final String path = "a/b\\c::d";
		Assert.assertEquals("b\\c::d", new LocalPathExtensionDetector("/", ".").detect(path));
		Assert.assertEquals("c::d", new LocalPathExtensionDetector("\\", ".").detect(path));
		Assert.assertEquals("d", new LocalPathExtensionDetector(":", ".").detect(path));
		Assert.assertEquals("a/b\\c::d", new LocalPathExtensionDetector(";", ".").detect(path));
		Assert.assertEquals(":d", new LocalPathExtensionDetector("c:", ".").detect(path));
	}
	
}
