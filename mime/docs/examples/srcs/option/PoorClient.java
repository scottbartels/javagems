package option;

import gems.Option;

/**
 * Comparison of naive implementation and Option wrapper usage for an unsuccessful scenario.
 */
public final class PoorClient {

	/**
	 * Just disables an instance creation.
	 */
	private PoorClient() {
		throw new UnsupportedOperationException();
	}

	/**
	 * Application entry point.
	 *
	 * @param args command-line arguments; completelly ignored.
	 */
	public static void main(final String[] args) {

		// Negative scenario for an ordinary bank, i.e. for an interface method returning
		// business object directly can be forgotten very likely:
		final Loan ordinaryLoan = OrdinaryCreditBank.EXAMPLE.getLoan(OrdinaryCreditBank.LIMIT + 1);

		// The following line will throw NullPointerException:
		// System.out.println("From ordinary credit bank I have this amount: " + ordinaryLoan.getAmount());

		// The correct code needs to check 'null' value at first; unfortunately, this cannot be enforced:
		if (ordinaryLoan != null) {
			System.out.println("From ordinary credit bank I have this amount: " + ordinaryLoan.getAmount());
		} else {
			System.out.println("Have nothing from ordinary credit bank.");
		}

		// Positive scenario for an careful bank, i.e. for an interface method returning
		// business object wrapped in Option is not so straightforward: a presence of the
		// business object must be checked before usage:
		final Option<Loan> carefulLoan = CarefulCreditBank.EXAMPLE.getLoan(CarefulCreditBank.LIMIT + 1);

		// The correct usage includes a sanity check and it is enforced by Option wrapper design.
		// Thus it clearly encourages to handle both cases, positive and negative:
		if (carefulLoan.hasValue()) {
			System.out.println("From careful credit bank I have this amount: " + carefulLoan.getValue().getAmount());
		} else {
			System.out.println("Have nothing from careful credit bank.");
		}

	}


}
