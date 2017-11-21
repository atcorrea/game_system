package br.edu.gs.model.managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.GameComment;
import br.edu.gs.model.User;
import br.edu.gs.model.dao.CommentDAO;
import br.edu.gs.model.dao.GameDAO;
import br.edu.gs.model.dao.GameGradeDAO;
import br.edu.gs.utils.ContextManager;
import br.edu.gs.utils.NumberUtils;
import br.edu.gs.viewModel.CommentView;
import br.edu.gs.viewModel.GameView;

/**
 * 
 * @author André Torres Ações para manipulação de Games Auxilia a view de busca
 *         e a de visualização de Game
 */
@ManagedBean(name = "gameMB")
@ViewScoped
public class GameMB {

	private GameView game = new GameView();

	private GameComment comment = new GameComment();

	private String txtGrade;
	private Double gameAvgGrade;

	private List<GameView> games = new ArrayList<GameView>();

	private List<CommentView> comments = new ArrayList<CommentView>();

	private boolean alreadyCommentedbyUser;
	private boolean alreadyGradedbyUser;

	private GameDAO gameDAO = new GameDAO();
	private ContextManager context = new ContextManager();

	// ======================== Métodos Públicos
	// ===========================================================//
	/**
	 * Action: antes de buscar o nome, verifica se o campo de busca foi
	 * preenchido para realizar o filtro e, caso não tenha sido preenchido,
	 * retorna todos os jogos (até um limite pré-estabelecido na DAO).
	 */
	public void search() throws IOException {

		if (game.getNmGame().isEmpty()) {
			searchAllGames();
		}

		else {
			searchFiltered(game.getNmGame());
		}
	}

	/**
	 * Método para inicializar uma página de visualização de game, baseia-se em
	 * parametros na url, por isso, inclui uma validação para verificar se este
	 * parâmetro está vazio ou não.
	 */
	public String startGamePage() throws IOException {

		// Verifica se a URL possui os parâmetros necessários
		boolean hasQuery = context.hasQueryStringParameter("g") && context.hasQueryStringParameter("p");

		if (!hasQuery) {
			context.redirect("searchGames.xhtml");
		} else {
			getGameData();
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
			comment = new GameComment();
			context.reloadPage(2);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar o comentário, tente novamente mais tarde.");
		}

		return "";
	}

	public String gradeGame() {

		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		GameGradeDAO dao = new GameGradeDAO();

		try {
			dao.insert(getUserGrade(), game.getIdGame(), user.getIdUser());
			context.newMessage("Sua nota para " + game.getNmGame() + " foi atualizada!");
			txtGrade = null;
			context.reloadPage(3);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar nota para o game, tente novamente mais tarde.");
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

	/**
	 * Recupera Todas as informações referente a um game
	 */
	private void getGameData() {
		CommentDAO comDAO = new CommentDAO();
		GameGradeDAO ggDAO = new GameGradeDAO();
		User user = (User) context.getFromSession("user");

		// Recupera Ficha completa do game:
		game = gameDAO.getGameFromName(game.getNmGame(), game.getIdPlataform());
		gameAvgGrade = NumberUtils.round((ggDAO.getAverageGrade(game.getIdGame())), 2);

		// Recupera comentarios do game carregado:
		setComments(comDAO.getAllGameComments(game.getIdGame()));

		// Verifica se usuário já comentou e deu nota para este game:
		alreadyCommentedbyUser = comDAO.userAlreadyCommented(game.getIdGame(), user.getIdUser());
		alreadyGradedbyUser = ggDAO.userAlreadyGraded(game.getIdGame(), user.getIdUser());
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

	public boolean isAlreadyGradedbyUser() {
		return alreadyGradedbyUser;
	}

	public void setTxtGrade(String txtGrade) {
		this.txtGrade = txtGrade;
	}

	public String getTxtGrade() {
		return txtGrade;
	}
	
	public Double getUserGrade(){
		
		String n = this.txtGrade;
		
		if (n.contains(",")) {
			n = txtGrade.replace(",", ".");
		}
		return Double.parseDouble(n);
	}

 	public Double getGameAvgGrade() {
		return gameAvgGrade;
	}

	public void setGameAvgGrade(Double gameAvgGrade) {
		this.gameAvgGrade = gameAvgGrade;
	}

}