package gems.easteregg.shower;

import java.io.File;
import java.util.Comparator;

/**
 * Compares files according their names.
 */
final class FilenamesComparator implements Comparator<File> {

	@Override public int compare(final File x, final File y) {
		return x.getName().compareTo(y.getName());
	}

}
