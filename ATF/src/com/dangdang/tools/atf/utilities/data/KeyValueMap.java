package com.dangdang.tools.atf.utilities.data;

import java.util.*;

public class KeyValueMap<K, V> {
	private List<KeyValuePair<K, V>> data = new ArrayList<KeyValuePair<K, V>>();
	public List<K> keySet = new ArrayList<K>();
	public List<V> values = new ArrayList<V>();

	// keySet
	public List<KeyValuePair<K, V>> getData() {
		return data;
	}

	public void setAll(List<KeyValuePair<K, V>> data) {
		this.data = data;
		this.keySet.clear();
		this.values.clear();
		for (KeyValuePair<K, V> kvp : data) {
			this.keySet.add(kvp.getKey());
			this.values.add(kvp.getValue());
		}
	}

	public void set(K key, V value) {
		this.keySet.add(key);
		this.values.add(value);
		this.data.add(new KeyValuePair<K, V>(key, value));
	}

	public ArrayList<V> get(K key) {
		ArrayList<V> list = new ArrayList<V>();
		for (KeyValuePair<K, V> kvp : this.data) {
			if (kvp.getKey().equals(key)) {
				list.add(kvp.getValue());
			}
		}
		return list;
	}

	public Iterator<KeyValuePair<K, V>> entrySet() {
		return this.data.iterator();
	}
}
