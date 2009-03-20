package gems;

@Experimental public interface Limits<E extends Enum<E>> {

	Limits<?> ZERO_LIMITS = new Limits() {

		public Number getLimit(final Enum e) {
			if (e == null) {
				throw new IllegalArgumentException();
			}
			return 0;
		}

	};

	Number getLimit(E e); // should return ??? for unknown measure

}

