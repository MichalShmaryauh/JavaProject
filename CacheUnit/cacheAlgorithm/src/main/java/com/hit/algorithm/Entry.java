package com.hit.algorithm;

import java.util.Objects;

/**
 * Helper generic class which help to implement the algorithm by priority queue.
 * this class represents an entry in the queue with key & value and compare the
 * entries by their values.
 * 
 * @author ъош
 *
 * @param <K>
 * @param <V>
 */

public class Entry<K, V extends Comparable<V>> implements Comparable<Entry<K, V>> {

	K key;
	V value;

	public Entry(K key, V value) {
		this.key = key;
		this.value = value;
	}

	public K getKey() {
		return key;
	}

	public void setKey(K key) {
		this.key = key;
	}

	public V getValue() {
		return value;
	}

	public void setValue(V value) {
		this.value = value;
	}

	/**
	 * compare by value.
	 */
	@Override
	public int compareTo(Entry<K, V> o) {
		return this.value.compareTo(o.value);
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 97 * hash + Objects.hashCode(this.key);
		hash = 97 * hash + Objects.hashCode(this.value);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final Entry<?, ?> other = (Entry<?, ?>) obj;
		if (!Objects.equals(this.key, other.key)) {
			return false;
		}
		if (!Objects.equals(this.value, other.value)) {
			return false;
		}
		return true;
	}
}
