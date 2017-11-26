package br.edu.gs.model.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.Game;
import br.edu.gs.model.UserCompletedGame;
import br.edu.gs.utils.EntityManagerFactorySingleton;
import br.edu.gs.viewModel.GameView;

/**
 * 
 * @author Andr� Torres Corr�a Classe com l�gica de acesso ao banco de dados,
 *         tabelas de Game.
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
	 * M�todo utilizado na view para encontrar um jogo no banco atrav�s de seu
	 * nome, faz a diferencia��o pelo console, pois podem haver dois games com o
	 * mesmo nome para v�rios consoles (jogo multiplataforma);
	 * 
	 * @param gameName
	 *            = Nome do Game a ser encontrado.
	 * @param idPlataform
	 *            = C�digo da Plataforma.
	 * @return Objeto Game
	 */
	public GameView getGameFromName(String gameName, long idPlataform) {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameView> qry = em.createQuery("select g from GameView g where g.nmGame = :n and g.idPlataform = :p",
				GameView.class);
		qry.setParameter("n", gameName.toUpperCase());
		qry.setParameter("p", idPlataform);

		GameView game = qry.getSingleResult();
		em.getTransaction().commit();
		em.close();

		return game;
	}

	public List<GameView> getAll() {
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		TypedQuery<GameView> qry = em.createQuery("select g from GameView g order by g.nmPlataform, g.nmGame",
				GameView.class);

		List<GameView> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.getTransaction().commit();
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
		TypedQuery<GameView> qry = em.createQuery(query, GameView.class);

		if (numParameter == null) {
			qry.setParameter(1, "%" + parameter.toUpperCase() + "%");
		} else {
			qry.setParameter(2, numParameter);
		}

		List<GameView> games = qry.setMaxResults(QUERY_LIMIT).getResultList();
		em.getTransaction().commit();
		em.close();

		return games;
	}

	@Override
	public Game delete(Game game) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		
		try {
			
			em.getTransaction().begin();
			em.remove(game);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel excluir o Game!");
			em.getTransaction().rollback();
			em.close();
			return null;
			
		}

		return game;
	}

	/**
	 * Adiciona jogo � lista de jogos completados do usu�rio
	 * 
	 * @param idGame
	 * @param IdUser
	 */
	public UserCompletedGame addCompletedGame(long idGame, long idUser) {
		UserCompletedGame cg = new UserCompletedGame(idGame, idUser);

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		try {

			em.getTransaction().begin();
			em.persist(cg);
			em.getTransaction().commit();			

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel inserir o jogo completado!");
			em.getTransaction().rollback();
			return null;
		} 
		finally{
			em.close();
		}

		return cg;
	}

	/**
	 * Remove jogo da lista de jogos completados do usu�rio
	 * 
	 * @param idGame
	 * @param IdUser
	 */
	public UserCompletedGame removeCompletedGame(long idGame, long idUser) {
		UserCompletedGame cg = new UserCompletedGame(idGame, idUser);
		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();
		try {
			em.getTransaction().begin();
			Object c = em.merge(cg);
			em.remove(c);
			em.getTransaction().commit();
			em.close();

		} catch (Exception e) {
			System.out.println("N�o foi poss�vel remover o jogo completado!" + e.getMessage());
			em.getTransaction().rollback();
			em.close();
			return null;
		}

		return cg;
	}

	/**
	 * Recupera Lista de Jogos completados pelo usu�rio
	 * 
	 * @param idUser
	 */
	public List<GameView> getCompletedGames(long idUser) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		Query qry = em.createQuery("select g.id.idGame from UserCompletedGame g where g.id.idUser = :id");
		qry.setParameter("id", idUser);

		List<Integer> games = new ArrayList<Integer>(qry.getResultList());

		List<GameView> completedGames = new ArrayList<GameView>();

		if (games.size() != 0) {
			qry = em.createQuery("select gv from GameView gv where gv.idGame in :list");
			qry.setParameter("list", games);
			completedGames = qry.getResultList();
		}
		em.getTransaction().commit();
		em.close();

		return completedGames;
	}

	public boolean userAlreadyCompleted(long idGame, long idUser) {

		EntityManager em = EntityManagerFactorySingleton.getInstance().createEntityManager();

		em.getTransaction().begin();
		Query qry = em
				.createQuery("select g.id.idGame from UserCompletedGame g where g.id.idUser = :u and g.id.idGame = :g");
		qry.setParameter("u", idUser);
		qry.setParameter("g", idGame);

		boolean isCompleted = true;

		try {
			qry.getSingleResult();
			em.getTransaction().commit();
			
		} catch (NoResultException e) {
			em.getTransaction().rollback();
			em.close();
			return false;
		}
		
		em.close();
		return isCompleted;
	}
}
