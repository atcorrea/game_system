package br.edu.gs.model.managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.view.ViewScoped;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.GameComment;
import br.edu.gs.model.User;
import br.edu.gs.model.dao.CommentDAO;
import br.edu.gs.model.dao.GameDAO;
import br.edu.gs.utils.ContextManager;
import br.edu.gs.viewModel.CommentView;
import br.edu.gs.viewModel.GameView;

/**
 * 
 * @author André Torres Ações para manipulação de Games Auxilia a view de busca
 *         e a de visualização de Game
 */
@ManagedBean(name = "gameMB")
@SessionScoped
public class GameMB {

	@ViewScoped
	private GameView game = new GameView();
	@ViewScoped
	private GameComment comment = new GameComment();
	private List<GameView> games = new ArrayList<GameView>();
	@ViewScoped
	private List<CommentView> comments = new ArrayList<CommentView>();
	@ViewScoped
	private boolean alreadyCommentedbyUser;

	@ViewScoped
	private GameDAO gameDAO = new GameDAO();

	// ======================== Métodos Públicos
	// ===========================================================//
	/**
	 * Action: antes de buscar o nome, verifica se o campo de busca foi
	 * preenchido para realizar o filtro e, caso não tenha sido preenchido,
	 * retorna todos os jogos (até um limite de 1000).
	 */
	public void search() throws IOException {

		if (game.getNmGame().isEmpty()) {
			searchAllGames();
		}

		else {
			searchFiltered(game.getNmGame());
		}

		ContextManager context = new ContextManager();
		context.redirect("searchGames.xhtml");
	}

	/**
	 * Método para inicializar uma página de visualização de game, baseia-se em
	 * parametros na url, por isso, inclui uma validação para verificar se este
	 * parâmetro está vazio ou não.
	 */
	public String startGamePage() throws IOException {

		ContextManager context = new ContextManager();

		// Verifica se a URL possui os parâmetros necessários
		boolean hasQuery = context.hasQueryStringParameter("g") && context.hasQueryStringParameter("p");

		if (!hasQuery) {
			context.redirect("searchGames.xhtml");
		} else {
			CommentDAO comDAO = new CommentDAO();
			User user = (User) context.getFromSession("user");

			// Recupera Ficha completa do game:
			game = gameDAO.getGameFromName(game.getNmGame(), game.getIdPlataform());
			// Recupera comentarios do game carregado:
			setComments(comDAO.getAllGameComments(game.getIdGame()));
			// Verifica se usuário já comentou este game:
			alreadyCommentedbyUser = comDAO.userAlreadyCommented(game.getIdGame(), user.getIdUser());
		}
		return "";
	}

	public String createNewComment() {
		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");
		comment.setIdGame(game.getIdGame());
		comment.setIdUser(user.getIdUser());
		comment.setDtComment(new Date());

		CommentDAO dao = new CommentDAO();

		try {
			dao.insert(comment);
			context.newMessage("Comentário inserido com sucesso!");
			context.reloadComponent("grdComments");
		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar o comentário, tente novamente mais tarde.");
		}

		return "";
	}

	// ======================== Métodos Privados
	// ===========================================================//

	/**
	 * @param nmGame
	 *            = Nome do Jogo Método para busca de jogos no banco baseando-se
	 *            no nome.
	 */
	private String searchFiltered(String nmGame) {

		games = gameDAO.getAll(nmGame, GameSearchMode.Name);
		return "";
	}

	/**
	 * Método para retornar todos os games(sem filtros).
	 */
	private String searchAllGames() {
		games = gameDAO.getAll();
		return "";
	}

	// ======================== Propriedades
	// ===========================================================//

	public List<GameView> getGames() {
		return games;
	}

	public GameView getGame() {
		return game;
	}

	public void setGame(GameView game) {
		this.game = game;
	}

	public List<CommentView> getComments() {
		return comments;
	}

	public void setComments(List<CommentView> comments) {
		this.comments = comments;
	}

	public GameComment getComment() {
		return comment;
	}

	public void setComment(GameComment comment) {
		this.comment = comment;
	}

	public boolean isAlreadyCommentedbyUser() {
		return alreadyCommentedbyUser;
	}
}