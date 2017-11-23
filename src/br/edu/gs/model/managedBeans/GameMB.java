package br.edu.gs.model.managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.GameComment;
import br.edu.gs.model.GameGrade;
import br.edu.gs.model.GameGradePK;
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

	// Variáveis para exibição/ manipulação de componentes
	private String txtGrade;
	private Double gameAvgGrade;
	private GameComment comment = new GameComment();

	// Listas
	private List<GameView> games = new ArrayList<GameView>();
	private List<CommentView> comments = new ArrayList<CommentView>();

	// Verificação para renderizações
	private boolean alreadyCommentedbyUser;
	private boolean alreadyGradedbyUser;
	private boolean alreadyCompletedbyUser;

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

	/**
	 * Cria um novo comentário para o game
	 */
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
			context.reloadPage(2);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar o comentário, tente novamente mais tarde.");
		}

		return "";
	}

	/**
	 * Edita o comentário do usuário no game carregado.
	 */
	public String editComment() {
		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		comment.setIdGame(game.getIdGame());
		comment.setIdUser(user.getIdUser());
		comment.setDtComment(new Date());

		CommentDAO dao = new CommentDAO();

		try {
			dao.edit(comment);
			context.newMessage("Comentário editado com sucesso!");
			context.reloadPage(2);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao editar o comentário, tente novamente mais tarde.");
		}

		return "";
	}

	/**
	 * Exclui o comentário do usuário no game carregado.
	 */
	public String deleteComment(){
		ContextManager context = new ContextManager();
		CommentDAO dao = new CommentDAO();

		try {
			dao.delete(comment);
			context.newMessage("Comentário removido com sucesso!");
			context.reloadPage(2);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao remover o comentário, tente novamente mais tarde.");
		}

		return "";
	}
	
	/**
	 * Dá uma nota de avaliação do usuário para o game
	 */
	public String gradeGame() {

		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		GameGradeDAO dao = new GameGradeDAO();

		try {
			dao.insert(getUserGrade(), game.getIdGame(), user.getIdUser());
			context.newMessage("Sua nota para " + game.getNmGame() + " foi atualizada!");
			context.reloadPage(3);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar nota para o game, tente novamente mais tarde.");
		}

		return "";
	}

	/**
	 * Atualiza nota de avaliação para do usuário para o game.
	 */
	public String editGrade() {
		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		GameGrade grade = new GameGrade();
		grade.setId(new GameGradePK(game.getIdGame(), user.getIdUser()));
		grade.setVlGrade(getUserGrade());
		grade.setDtGrade(new Date());

		GameGradeDAO dao = new GameGradeDAO();

		try {
			dao.edit(grade);
			context.newMessage("Sua nota para " + game.getNmGame() + " foi atualizada!");
			context.reloadPage(3);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao editar nota para o game, tente novamente mais tarde.");
		}

		return "";
	}

	/**
	 * Adiciona game à lista de jogos completados do usuário logado.
	 */
	public String markAsCompleted() {

		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		try {
			gameDAO.addCompletedGame(game.getIdGame(), user.getIdUser());
			context.newMessage("Jogo adicionado a lista de completados");
			context.reloadPage(3);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao adicionar game na lista");
		}

		return "";
	}

	/**
	 * Remove game da lista de completados do usuário logado.
	 */
	public String removeCompleted() {
		ContextManager context = new ContextManager();
		User user = (User) context.getFromSession("user");

		try {
			gameDAO.removeCompletedGame(game.getIdGame(), user.getIdUser());
			context.newMessage("Jogo removido da lista de completados");
			context.reloadPage(3);

		} catch (Exception e) {
			context.newMessage("Ocorreu um erro ao remover game da lista");
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
		gameAvgGrade = NumberUtils.round((ggDAO.getAverageGrade(game.getIdGame())), 1);

		// Recupera todos os comentarios do game carregado:
		setComments(comDAO.getAllGameComments(game.getIdGame()));

		// Verifica se usuário logado já comentou, se já deu nota e se já
		// completou o game:
		alreadyCommentedbyUser = comDAO.userAlreadyCommented(game.getIdGame(), user.getIdUser());
		alreadyGradedbyUser = ggDAO.userAlreadyGraded(game.getIdGame(), user.getIdUser());
		alreadyCompletedbyUser = gameDAO.userAlreadyCompleted(game.getIdGame(), user.getIdUser());

		// Caso o Usuário já tenha comentado, carrega o comentário feito para
		// edição (O Mesmo vale para nota).
		if (alreadyCommentedbyUser) {
			comment = comDAO.getUserGameComment(game.getIdGame(), user.getIdUser());
		}
		if (alreadyGradedbyUser) {
			txtGrade = NumberUtils.round((ggDAO.getUserGameGrade(game.getIdGame(), user.getIdUser())).getVlGrade(), 1)
					.toString();
		}
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

	public Double getUserGrade() {

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

	public boolean isAlreadyCompletedbyUser() {
		return alreadyCompletedbyUser;
	}

	public void setAlreadyCompletedbyUser(boolean alreadyCompletedbyUser) {
		this.alreadyCompletedbyUser = alreadyCompletedbyUser;
	}

}