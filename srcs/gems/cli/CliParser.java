package gems.cli;

/**
 * Parses given command line arguments according to given command
 * line options and recognizes actuators and resting arguments.
 *
 * @author <a href="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public interface CliParser {

	/**
	 * Parses given command line arguments according to given command
	 * line options and recognizes actuators and resting arguments.
	 *
	 * @param args command line arguments as typed by the user.
	 * @param options command line options as expected by the application.
	 *
	 * @return recognized actuators and resting arguments holding object.
	 */
	CliActuators parse(String[] args, CliOptions options);
}
