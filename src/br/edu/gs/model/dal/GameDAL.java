package br.edu.gs.model.dal;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.Game;

public class GameDAL implements IDal<Game> {

	private final int QUERY_LIMIT = 150;

	@Override
	public Game insert(Game newGame) {
		
		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(newGame);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível inserir o jogo!");
			return null;
		}

		return newGame;
	}

	@Override
	public Game edit(Game object) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Game> getAll() {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<Game> qry = em.createQuery("select g from Game g", Game.class);

		List<Game> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.close();

		return games;
	}
	
	public List<Game> getAll(String parameter, GameSearchMode searchBy) {
		
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();		
		String query = "select g from Game g where g.";
		Integer numParameter = null;
		
		switch (searchBy) {
		case Name:
			query += "nmGame like ?1";
			parameter = transformToLike(parameter);
			break;
		case Developer:
			query += "nmDeveloper like ?1";
			parameter = transformToLike(parameter);
			break;
		case Genre:
			query += "nmGenre like ?1";
			parameter = transformToLike(parameter);
			break;
		case Grade:
			query += "vlAvgScore = ?2";
			numParameter = Integer.parseInt(parameter);
			break;
		case Platform:
			query += "idPlataform = ?2";
			numParameter = Integer.parseInt(parameter);
			break;
		}

		em.getTransaction().begin();
		TypedQuery<Game> qry = em.createQuery(
				query, Game.class);
		
		if (numParameter == null){
			qry.setParameter(1, parameter.toUpperCase());
		}
		else{
			qry.setParameter(2, numParameter);
		}
		
		List<Game> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.close();

		return games;
	}
	
	private String transformToLike(String s){
		return "%" + s + "%";
	}

	@Override
	public Game delete(Game game) {

		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.remove(game);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("Não foi possível excluir o Game!");
			return null;
		}

		return game;
	}

}
