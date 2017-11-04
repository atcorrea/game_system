package br.edu.gs.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.model.User;

public class UserDAO implements IDal<User> {

	public UserDAO() {

	}

	@Override
	public User insert(User newUser) {

		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(newUser);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel inserir o usu�rio!");
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
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.remove(user);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel excluir o usu�rio!");
			return null;
		}

		return user;
	}

	public Boolean authenticate(User object) {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<User> tq = em.createQuery("select u from User u where u.nmUser = :nm and u.vlPassw = :pw",
				User.class);
		tq.setParameter("nm", object.getNmUser());
		tq.setParameter("pw", object.getVlPassw());

		List<User> us = tq.getResultList();
		em.close();

		if (us.size() == 1) {
			return true;
		}

		return false;
	}

}
