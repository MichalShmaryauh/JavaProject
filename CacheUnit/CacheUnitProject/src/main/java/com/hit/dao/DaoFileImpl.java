package com.hit.dao;

import com.hit.dm.DataModel;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * Concrete class inherit from IDao.
 * 
 * @author ъош
 *
 * @param <T>
 */
public class DaoFileImpl<T> implements IDao<Long, DataModel<T>> {

	private String filePath;
	private int capacity;
	private Gson gson = new Gson();

	public DaoFileImpl(String filePath) {
		this.capacity = 10;
		this.filePath = filePath;
		initFile();
	}

	public DaoFileImpl(String filePath, int capacity) {
		this.capacity = capacity;
		this.filePath = filePath;
		initFile();
	}

	@Override
	public void delete(DataModel<T> entity) throws IllegalArgumentException {
		if (entity == null || entity.getDataModelId() == null) {
			throw new IllegalArgumentException();
		}
		try {
			ArrayList<DataModel<T>> entities = readFile();
			if (entities.remove(entity)) {
				writeFile(entities);
			}
		} catch (IOException e) {
			System.out.println("Failed to contact with file!");
		}

	}

	@Override
	public void save(DataModel<T> entity) throws IOException {
		if (entity == null || entity.getDataModelId() == null) {
			return;
		}
		ArrayList<DataModel<T>> entities = readFile();
		if (entities.size() < capacity) {
			entities.add(entity);
			writeFile(entities);
		} else {
			System.out.println("Disk is full!!");
		}

	}

	@Override
	public DataModel<T> find(Long id) throws IllegalArgumentException, IOException {
		if (id == null) {
			throw new IllegalArgumentException();
		}
		ArrayList<DataModel<T>> entities = readFile();
		for (DataModel<T> entity : entities) {
			if (entity.getDataModelId().equals(id)) {
				return entity;
			}
		}
		return null;
	}

	/**
	 * private method to handle reading file.
	 * 
	 * @return
	 * @throws IOException
	 */
	private ArrayList<DataModel<T>> readFile() throws IOException {
		FileReader fileReader = new FileReader(filePath);
		ArrayList<DataModel<T>> entities = gson.fromJson(fileReader, new TypeToken<ArrayList<DataModel<T>>>() {
		}.getType());
		fileReader.close();
		return entities;
	}

	/**
	 * private method to handle writing to file.
	 * 
	 * @param entities
	 * @throws IOException
	 */
	private void writeFile(ArrayList<DataModel<T>> entities) throws IOException {
		FileWriter fileWriter = new FileWriter(filePath);
		gson.toJson(entities, fileWriter);
		fileWriter.close();
	}

	/**
	 * private method which writes an empty array to file at initializing time.
	 */
	private void writeEmptyArray() {
		FileWriter fileWriter;
		try {
			fileWriter = new FileWriter(filePath);
			gson.toJson(new Object[0], fileWriter);
			fileWriter.close();
		} catch (IOException e) {
			System.out.println("Failed to contact with file!");
		}

	}
	
	private void initFile(){
		try {
			ArrayList<DataModel<T>> fileContent = readFile();
			if(fileContent.isEmpty()){
				writeEmptyArray();
			}
		} catch (IOException | NullPointerException e) {
			writeEmptyArray();
		}
	}

}
