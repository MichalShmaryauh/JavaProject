package com.hit.dao;

import java.io.IOException;

/**
 * An interface to manage communication with files. manages the action with the
 * disk entities.
 * 
 * @author ъош
 *
 * @param <ID>
 * @param <T>
 */

public interface IDao<ID extends java.io.Serializable, T> {

	/**
	 * Deletes a given entity.
	 * 
	 * @param entity
	 * @throws IllegalArgumentException
	 */
	public void delete(T entity) throws IllegalArgumentException;

	/**
	 * Saves a given entity.
	 * 
	 * @param entity
	 * @throws IOException 
	 */
	public void save(T entity) throws IOException;

	/**
	 * Retrieves an entity by its id.
	 * 
	 * @param entity
	 * @throws IOException 
	 */
	public T find(ID id) throws IllegalArgumentException, IOException;

}
