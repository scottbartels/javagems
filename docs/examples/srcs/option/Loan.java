package option;

/**
 * A loan. It simply holds amount of loaned money.
 */
public final class Loan {

	/**
	 * Amount of loan.
	 */
	private final int amount;

	/**
	 * Creates a new loan with a given amount loaned of money.
	 *
	 * @param amount amount of loaned money.
	 */
	public Loan(final int amount) {
		this.amount = amount;
	}

	/**
	 * Returns amount of loaned money.
	 *
	 * @return amount of loaned money.
	 */
	public int getAmount() {
		return amount;
	}
	
}
