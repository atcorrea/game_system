package br.edu.gs.model.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.model.User;

public class UserDAL implements IDal<User>{

	private EntityManager em; 
	
	public UserDAL() {
		//Apenas uma instância da fábrica para todas as DALs.
		em = EntityManagerFactorySingleton.getInstance().createEntityManager();
	}
	
	@Override
	public User insert(User newUser) {
		
		try {
			em.getTransaction().begin();
			em.persist( newUser );
			em.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Não foi possível inserir o usuário!");
			return null;
		}	
		
		return newUser;
	}

	@Override
	public User edit(User object) {
		return null;
	}

	@Override
	public List<User> getAll() {
		return null;
	}

	@Override
	public User delete(User user) {
		
		try {
			em.getTransaction().begin();
			em.remove( user );
			em.getTransaction().commit();
			
		} catch (Exception e) {
			System.out.println("Não foi possível excluir o usuário!");
			return null;
		}	
		
		return user;
	}
	
	public boolean authenticate(User object){
		em.getTransaction().begin();
		TypedQuery<User> tq = em.createQuery(
					"select u from User u where u.nmUser = :nm and u.vlPassw = :pw", User.class);
		tq.setParameter("nm", object.getNmUser());
		tq.setParameter("pw", object.getVlPassw());
		
		List<User> us = tq.getResultList();
		
		if( us.size() == 1){
			return true;
		}
		
		return false;
	}	

}
