package gems;

@Experimental public interface ObjectProvider<T, K> {

	public ObjectProvider<Object, Object> NULL_PROVIDER = new ObjectProvider<Object, Object>() {

		public Option<Object> provide(final Object key) {
			if (key == null) {
				throw new IllegalArgumentException();
			}
			return new Option<Object>(null);
		}

	};

	Option<T> provide(K key);
	
}
