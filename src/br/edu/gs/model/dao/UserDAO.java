package br.edu.gs.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import org.hibernate.exception.ConstraintViolationException;

import br.edu.gs.model.User;
import br.edu.gs.utils.EntityManagerFactorySingleton;

public class UserDAO implements IDal<User> {

	public UserDAO() {

	}

	@Override
	public User insert(User newUser) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		try {			
			em.getTransaction().begin();
			em.persist(newUser);
			em.getTransaction().commit();
			em.close();
		} 
		
		catch (Exception e) {
			System.out.println("Não foi possível inserir o usuário!");
			em.getTransaction().rollback();
			return null;
		} 

		return newUser;
	}

	@Override
	public User edit(User object) {
		return null;
	}

	public List<User> getAll() {
		return null;
	}

	@Override
	public User delete(User user) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		try {
			
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir o usuário!");
			em.getTransaction().rollback();
			return null;
		}

		return user;
	}
	
	public User authenticate(User object) {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		TypedQuery<User> tq = em.createQuery("select u from User u where u.nmUser = :nm and u.vlPassw = :pw",
				User.class);
		tq.setParameter("nm", object.getNmUser());
		tq.setParameter("pw", object.getVlPassw());
		
		User us = null;
		try{
			us = tq.getSingleResult();
			em.getTransaction().commit();
		}
		catch (NoResultException e) {
			em.getTransaction().rollback();
		}
		finally {
			em.close();				
		}	
		return us;
	}

	public User getUserFromName(String nmUser){
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		TypedQuery<User> qry = em.createQuery("select u from User u where u.nmUser = :us", User.class);
		qry.setParameter("us", nmUser.toUpperCase());

		User user = qry.getSingleResult();
		em.getTransaction().commit();
		em.close();
		
		return user;
	}
}
