package gems.storage;

import gems.Identifiable;

public final class MemoryStorageFactory<K, V extends Identifiable<K>> implements StorageFactory<K, V> {

	@Override public Storage<K, V> getStorage() {
		return new MemoryStorage<K, V>();
	}

}
