package br.edu.gs.model.dao;

import java.util.List;

import br.edu.gs.viewModel.GameView;

/** 
 * @author André Torres
 * Interface para DALs
 * @param <T>
 */
public interface IDal<T> {
	
	T insert(T object);
	T edit(T object);
	T delete(T object);
	
}
