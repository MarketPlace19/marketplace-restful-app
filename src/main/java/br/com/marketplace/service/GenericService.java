package br.com.marketplace.service;

import java.util.List;

public interface GenericService<T> {
	
	List<T> findAll();
	
	T findOne(long id);
	
	void add(T t);
	
	void update(T t);
	
	void deleteById(long id);
	
}
