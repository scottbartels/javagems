package gems;

@Experimental public interface StorageFactory<K, V extends Identifiable<K>> {

	Storage<K, V> getStorage();

}
