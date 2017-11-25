package br.edu.gs.model.dao;

import java.util.ArrayList;

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

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		try {		
			em.getTransaction().begin();
			em.persist(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir a nota!");
			em.getTransaction().rollback();
			return null;
		}

		return gg;
	}

	public GameGrade insert(Double vlGrade, long idGame, long idUser) {

		GameGrade gg = new GameGrade();
		gg.setId(new GameGradePK(idGame, idUser));
		gg.setVlGrade(vlGrade);

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		try {			
			em.getTransaction().begin();
			em.persist(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir a nota!");
			em.getTransaction().rollback();
			return null;
		}

		return gg;
	}

	@Override
	public GameGrade edit(GameGrade grade) {
		String query = "update GameGrade g set g.vlGrade = :vl where g.id.idGame = :g and g.id.idUser = :u";
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		Query qry = em.createQuery(query);
		qry.setParameter("vl", grade.getVlGrade());
		qry.setParameter("g", grade.getId().getIdGame());
		qry.setParameter("u", grade.getId().getIdUser());
		
		try {
			qry.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			grade = null;
			em.getTransaction().rollback();
		}					
		em.close();

		return grade;
	}

	public GameGrade getUserGameGrade(long idGame, long idUser) {

		String query = "select g from GameGrade g where g.id.idGame = :g and g.id.idUser = :u";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameGrade> tq = em.createQuery(query, GameGrade.class);
		tq.setParameter("g", idGame);
		tq.setParameter("u", idUser);

		GameGrade userGrade = tq.getSingleResult();
		em.getTransaction().commit();
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
		em.getTransaction().commit();
		em.close();
		return avgGrade;

	}

	@Override
	public GameGrade delete(GameGrade gg) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			em.remove(gg);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir a nota!");
			em.getTransaction().rollback();
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
			em.getTransaction().rollback();
			em.close();
			return false;
		}
		em.getTransaction().commit();
		em.close();
		return true;
	}

}
