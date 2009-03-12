package gems;

@Experimental public interface Storage<K, V extends Identifiable<K>> {

	void put(V value);

	V get(K key);

	void remove(K key);
	
}
