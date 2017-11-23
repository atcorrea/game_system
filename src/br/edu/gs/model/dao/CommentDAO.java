package br.edu.gs.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.gs.model.GameComment;
import br.edu.gs.utils.EntityManagerFactorySingleton;
import br.edu.gs.viewModel.CommentView;

public class CommentDAO implements IDal<GameComment> {

	@Override
	public GameComment insert(GameComment gc) {
		
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		try {			
			em.getTransaction().begin();
			em.persist(gc);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir o comentário!");
			em.getTransaction().rollback();
			em.close();
			return null;
		}

		return gc;
	}

	@Override
	public GameComment edit(GameComment gc) {
		
		String query = "update CommentView g set g.deComment = :com where g.idGame = :g and g.idUser = :u";
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		Query qry = em.createQuery(query);
		qry.setParameter("com", gc.getDeComment());
		qry.setParameter("g", gc.getIdGame());
		qry.setParameter("u", gc.getIdUser());
		
		try {
			qry.executeUpdate();
			em.getTransaction().commit();
		} catch (Exception e) {
			gc = null;
			em.getTransaction().rollback();
		}					
		em.close();

		return gc;
		
	}
	
	@Override
	public GameComment delete(GameComment gc) {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		try {			
			em.getTransaction().begin();
			Object c = em.merge(gc);
			em.remove(c);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir o comentário!");
			em.getTransaction().rollback();
			em.close();
			return null;
		}

		return gc;
	}
	
	public List<CommentView> getAllGameComments(long idGame) {
		String query = "select g from CommentView g where g.idGame = :id";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<CommentView> tq = em.createQuery(query, CommentView.class);
		tq.setParameter("id", idGame);
		List<CommentView> commentList = tq.getResultList();
		em.getTransaction().commit();
		em.close();

		return commentList;
	}
	
	public List<CommentView> getAllUserComments(long idUser) {
		String query = "select g from CommentView g where g.idUser = :id";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<CommentView> tq = em.createQuery(query, CommentView.class);
		tq.setParameter("id", idUser);
		List<CommentView> commentList = tq.getResultList();
		em.getTransaction().commit();
		em.close();

		return commentList;
	}
	
	public boolean userAlreadyCommented(long idGame, long idUser){
		
		String query = "select g from GameComment g where g.idUser = :iu and g.idGame = :ig";

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		em.getTransaction().begin();
		TypedQuery<GameComment> tq = em.createQuery(query, GameComment.class);
		tq.setParameter("iu", idUser);
		tq.setParameter("ig", idGame);
		
		try {
			tq.getSingleResult();			
		} catch (NoResultException e) {
			em.close();
			return false;
		}
		em.getTransaction().commit();
		em.close();
		return true;
	}

	public GameComment getUserGameComment(long idGame, long idUser){
		
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		String query = "select g from GameComment g where g.idGame = :g and g.idUser = :u";

		em.getTransaction().begin();
		TypedQuery<GameComment> tq = em.createQuery(query, GameComment.class);
		tq.setParameter("g", idGame);
		tq.setParameter("u", idUser);
		
		GameComment userComment = new GameComment();
		
		try {
			userComment = tq.getSingleResult();
			em.getTransaction().commit();			
		} catch (NoResultException e) {			
			System.out.println("Comentário não encontrado!");
			em.getTransaction().rollback();
		}
		em.close();

		return userComment;
	}
}
