package br.edu.gs.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.gs.model.GameGrade;
import br.edu.gs.model.GameGradePK;
import br.edu.gs.utils.EntityManagerFactorySingleton;

public class GameGradeDAO implements IDal<GameGrade> {

	@Override
	public GameGrade insert(GameGrade gg) {

		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir a nota!");
			return null;
		}

		return gg;
	}

	public GameGrade insert(Double vlGrade, long idGame, long idUser) {

		GameGrade gg = new GameGrade();
		gg.setId(new GameGradePK(idGame, idUser));
		gg.setVlGrade(vlGrade);

		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir a nota!");
			return null;
		}

		return gg;
	}

	@Override
	public GameGrade edit(GameGrade object) {
		// TODO Auto-generated method stub
		return null;
	}

	public GameGrade getUserGameGrade(long idGame, long idUser) {

		String query = "select g from GameGrade g where g.id.idGame = :g and gg.id.idUser = :u";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameGrade> tq = em.createQuery(query, GameGrade.class);
		tq.setParameter("g", idGame);
		tq.setParameter("u", idUser);

		GameGrade userGrade = tq.getSingleResult();

		em.close();

		return userGrade;
	}

	
	public Double getAverageGrade(long idGame) {

		String query = "select AVG(g.vlGrade) from GameGrade g where g.id.idGame = :g";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		Query q = em.createQuery(query);
		q.setParameter("g", idGame);

		Double avgGrade = 5.0;

		ArrayList<Double> result = (ArrayList<Double>) q.getResultList();
		Double first = result.get(0);

		if (first != null) {
			avgGrade = first;
		}

		em.close();
		return avgGrade;

	}

	@Override
	public GameGrade delete(GameGrade gg) {

		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.remove(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir a nota!");
			return null;
		}

		return gg;
	}

	public boolean userAlreadyGraded(long idGame, long idUser) {

		String query = "select g from GameGrade g where g.id.idUser = :iu and g.id.idGame = :ig";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<GameGrade> tq = em.createQuery(query, GameGrade.class);
		tq.setParameter("iu", idUser);
		tq.setParameter("ig", idGame);

		try {
			tq.getSingleResult();
		} catch (NoResultException e) {
			em.close();
			return false;
		}

		em.close();
		return true;
	}

}
