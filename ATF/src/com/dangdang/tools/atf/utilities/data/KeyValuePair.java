package com.dangdang.tools.atf.utilities.data;

public class KeyValuePair<K,V> {
private K key;
private V value;

/**
 * @param key
 * @param value
 */
public KeyValuePair(K key, V value) {
	super();
	this.key = key;
	this.value = value;
}
/**
 * 
 */
public KeyValuePair() {
	super();
}
/**
 * @return the key
 */
public K getKey() {
	return key;
}
/**
 * @param key the key to set
 */
public void setKey(K key) {
	this.key = key;
}
/**
 * @return the value
 */
public V getValue() {
	return value;
}
/**
 * @param value the value to set
 */
public void setValue(V value) {
	this.value = value;
}
}
