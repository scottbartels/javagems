package option;

import gems.Option;

/**
 * A credit bank providing loans to clients. In this case 'careful'
 * means that it uses Option wrapper for returned business object
 * of loan and enforces client code to be robust enough to handle
 * all possible scenarios.
 */
public interface CarefulCreditBank {

	/**
	 * Limit for maximal credit.
	 */
	int LIMIT = 10000;

	/**
	 * An example of interface implementation.
	 */
	CarefulCreditBank EXAMPLE = new CarefulCreditBank() {

		/**
		 * {@inheritDoc}
		 *
		 * @throws IllegalArgumentException if {@code amount} is negative.
		 */
		@Override public Option<Loan> getLoan(final int amount) {
			if (amount < 0) {
				throw new IllegalArgumentException(String.valueOf(amount));
			}
			if (amount <= LIMIT) {
				return new Option(new Loan(amount >> 1));
			}
			return new Option<Loan>(null);
		}

	};

	/**
	 * Returns a new loan. Returned option is empty if a request was witheld.
	 *
	 * @param amount requested amount.
	 *
	 * @return  a new loan.
	 */
	Option<Loan> getLoan(int amount);

}
