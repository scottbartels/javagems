package option;

/**
 * A credit bank providing loans to clients. In this case 'ordinary'
 * means that it uses a straightforward interface desing returning
 * business object of loan directly, which can cause troubles when
 * client code is not robust enough.  
 */
public interface OrdinaryCreditBank {

	/**
	 * Limit for maximal credit.
	 */
	int LIMIT = 10000;

	/**
	 * An example of interface implementation.
	 */
	OrdinaryCreditBank EXAMPLE = new OrdinaryCreditBank() {

		/**
		 * {@inheritDoc}
		 *
		 * @throws IllegalArgumentException if {@code amount} is negative.
		 */
		@Override public Loan getLoan(final int amount) {
			if (amount < 0) {
				throw new IllegalArgumentException(String.valueOf(amount));
			}
			if (amount <= LIMIT) {
				return new Loan(amount >> 1);
			}
			return null;
		}

	};

	/**
	 * Returns a new loan or {@code null} if a request is withheld.
	 *
	 * @param amount requested amount.
	 *
	 * @return  a new loan or {@code null} if a request is withheld.
	 */
	Loan getLoan(int amount);

}
