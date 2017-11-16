package br.edu.gs.model.dao;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.model.GameGrade;

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

}
