package br.edu.gs.model.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.Game;
import br.edu.gs.utils.EntityManagerFactorySingleton;
import br.edu.gs.viewModel.GameView;

/**
 * 
 * @author Andr� Torres Corr�a
 * Classe com l�gica de acesso ao banco de dados, tabelas de Game.
 */
public class GameDAO implements IDal<Game> {

	/**
	 * Limite de resultados em uma consulta.
	 */
	private final int QUERY_LIMIT = 1000;

	@Override
	public Game insert(Game newGame) {
		
		try {
			EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
			em.getTransaction().begin();
			em.persist(newGame);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel inserir o jogo!");
			return null;
		}

		return newGame;
	}

	@Override
	public Game edit(Game object) {
		return null;
	}
	
	/**
	 * M�todo utilizado na view para encontrar um jogo no banco atrav�s de seu nome, faz a diferencia��o pelo console,
	 * pois podem haver dois games com o mesmo nome para v�rios consoles (jogo multiplataforma);
	 * @param gameName = Nome do Game a ser encontrado.
	 * @param idPlataform = C�digo da Plataforma.
	 * @return Objeto Game
	 */
	public GameView getGameFromName(String gameName, long idPlataform){
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		em.getTransaction().begin();
		TypedQuery<GameView> qry = em.createQuery("select g from GameView g where g.nmGame = :n and g.idPlataform = :p", GameView.class);
		qry.setParameter("n", gameName.toUpperCase());
		qry.setParameter("p", idPlataform);

		GameView game = qry.getSingleResult();
		em.close();
		
		return game;
	}

	public List<GameView> getAll() {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameView> qry = em.createQuery("select g from GameView g order by g.nmPlataform, g.nmGame", GameView.class);

		List<GameView> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.close();

		return games;
	}
	
	public List<GameView> getAll(String parameter, GameSearchMode searchBy) {
		
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();		
		String query = "select g from GameView g where g.";
		Integer numParameter = null;
		
		switch (searchBy) {
		case Name:
			query += "nmGame like ?1";
			break;
		case Developer:
			query += "nmDeveloper like ?1";
			break;
		case Genre:
			query += "nmGenre like ?1";
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
		
		query += " order by g.nmPlataform, g.nmGame";
		em.getTransaction().begin();
		TypedQuery<GameView> qry = em.createQuery(
				query, GameView.class);
		
		if (numParameter == null){
			qry.setParameter(1, "%" + parameter.toUpperCase() + "%");
		}
		else{
			qry.setParameter(2, numParameter);
		}
		
		List<GameView> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.close();

		return games;
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
			System.out.println("N�o foi poss�vel excluir o Game!");
			return null;
		}

		return game;
	}

}
