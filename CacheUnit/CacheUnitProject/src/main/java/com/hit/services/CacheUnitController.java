package com.hit.services;

import com.hit.dm.DataModel;

/**
 * 
 * @author ъош
 *
 * This class get a request and move it to the service treatment.
 *
 * @param <T>
 */


public class CacheUnitController<T> {
	
	private CacheUnitService<T> service;

	public CacheUnitController(){
		service = new CacheUnitService<T>();
	}
	
	public boolean update(DataModel<T>[] dataModels){
		return service.update(dataModels);
	}
	
	public boolean delete(DataModel<T>[] dataModels){
		return service.delete(dataModels);
	}
	
	public DataModel<T>[] get(DataModel<T>[] dataModels){
		return service.get(dataModels);
	}
	
	public Boolean updateDiskByCache(){
		return service.updateDiskByCache();
	}
	
	public String showStatistics(){
		return service.showStatistics();
	}
	
}
