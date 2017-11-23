package br.edu.gs.model.dao;

/** 
 * @author Andr� Torres
 * Interface para DALs
 * @param <T>
 */
public interface IDal<T> {
	
	T insert(T object);
	T edit(T object);
	T delete(T object);
	
}
