package br.edu.gs.model.dao;

import java.util.List;

/** 
 * @author André Torres
 * Interface para DALs
 * @param <T>
 */
public interface IDal<T> {
	
	T insert(T object);
	T edit(T object);
	List<T> getAll();
	T delete(T object);
	
}
