package option;

import gems.Option;

/**
 * Comparison of naive implementation and Option wrapper usage for a successful scenario.
 */
public final class HappyClient {

	/**
	 * Application entry point.
	 *
	 * @param args command-line arguments; completelly ignored.
	 */
	public static void main(final String[] args) {

		// Positive scenario for an ordinary bank, i.e. for an interface method returning
		// business object directly is pretty straigtforward: object is returned and used.
		final Loan ordinaryLoan = OrdinaryCreditBank.EXAMPLE.getLoan(OrdinaryCreditBank.LIMIT);
		System.out.println("From ordinary credit bank I have this amount: " + ordinaryLoan.getAmount());

		// Positive scenario for an careful bank, i.e. for an interface method returning
		// business object wrapped in Option is not so straightforward: a presence of the
		// business object must be checked before usage:
		final Option<Loan> carefulLoan = CarefulCreditBank.EXAMPLE.getLoan(CarefulCreditBank.LIMIT);

		// The following line per se will throw IllegalStateException:
		// System.out.println("From careful credit bank I have this amount: " + carefulLoan.getValue().getAmount());

		// This is the correct usage and it is enforced by Option wrapper design:
		if (carefulLoan.hasValue()) {
			// Value presence was already check, so it is OK now to use it.
			System.out.println("From careful credit bank I have this amount: " + carefulLoan.getValue().getAmount());
		}

	}

}
