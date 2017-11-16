package br.edu.gs.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.model.GameComment;
import br.edu.gs.model.GameGrade;

public class CommentDAO implements IDal<GameComment> {

	@Override
	public GameComment insert(GameComment gc) {		
		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(gc);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir o comentário!");
			return null;
		}

		return gc;
	}

	@Override
	public GameComment edit(GameComment gc) {
		return null;
	}
	
	public List<GameComment> getGameComments(long idGame) {
		String query = "select g from GameComment g where g.idGame = :g";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameComment> tq = em.createQuery(query, GameComment.class);
		tq.setParameter("g", idGame);
		List<GameComment> commentList = tq.getResultList();
		em.close();

		return commentList;
	}

	@Override
	public GameComment delete(GameComment gc) {
		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.remove(gc);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir o comentário!");
			return null;
		}

		return gc;
	}

}
