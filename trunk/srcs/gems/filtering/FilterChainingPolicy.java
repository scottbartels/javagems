package gems.filtering;

/**
 * Policies for chaining of filters. A chaining policy defines whether
 * are chained filters combined using logical OR or using logical AND.
 *
 * @author <a hraf="mailto:jozef.babjak@gmail.com">Jozef BABJAK</a>
 */
public enum FilterChainingPolicy {

	/**
	 * All chained filters have to allow a processing. This is useful
	 * when more different conditions have to be satisfied to allow
	 * a processing. In that case, every condition can be implemented
	 * by separate filter. In another words, the {@code SATISFY_ALL}
	 * policy defines a logical AND operator for chained filters.
	 */
	SATISFY_ALL,

	/**
	 * At least one chained filter has to allow a processing. This is
	 * useful when more different conditions can lead to a processing.
	 * In that case, every condition can be implemented by separate
	 * filter. In another words, the {@code SATISFY_ANY} policy defines
	 * a logical OR operator for chained filters.
	 */
	SATISFY_ANY

}
