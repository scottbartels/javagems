package gems.caching;

import gems.Identifiable;

final class MemoryStorageFactory<K, V extends Identifiable<K>> implements StorageFactory<K, V> {

	@Override public Storage<K, V> getStorage() {
		return new MemoryStorage<K, V>();
	}

}
