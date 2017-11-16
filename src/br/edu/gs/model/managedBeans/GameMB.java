package br.edu.gs.model.managedBeans;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.GameComment;
import br.edu.gs.model.GameView;
import br.edu.gs.model.dao.CommentDAO;
import br.edu.gs.model.dao.GameDAO;

/**
 * 
 * @author André Torres 
 * Ações para manipulação de Games
 * Auxilia a view de busca e a de visualização de Game
 */
@ManagedBean(name="gameMB")
@SessionScoped
public class GameMB {
	
	private GameView game = new GameView();
	
	@ViewScoped
	private GameDAO dao = new GameDAO();
	
	private List<GameView> games = new ArrayList<GameView>();
	private List<GameComment> comments = new ArrayList<GameComment>();
	
	/**
	 * Action: antes de buscar o nome, verifica se o campo de busca foi preenchido para realizar o filtro e,
	 * caso não tenha sido preenchido, retorna todos os jogos (até um limite de 1000).
	 */
	public void search() throws IOException {

		if (game.getNmGame().isEmpty()) {
			searchAllGames();
		}

		else {
			searchFiltered(game.getNmGame());
		}

		FacesContext.getCurrentInstance().getExternalContext().redirect("searchGames.xhtml");
	}
	
	/**
	 * 
	 * @param nmGame = Nome do Jogo
	 * Método para busca de jogos no banco baseando-se no nome.
	 */
	private String searchFiltered(String nmGame) {

		games = dao.getAll(nmGame, GameSearchMode.Name);
		return "";
	}
	
	
	/**
	 * Método para retornar todos os games(sem filtros). 
	 */
	private String searchAllGames() {
		games = dao.getAll();
		return "";
	}
	
	/** 
	 * Método para inicializar uma página de visualização de game, baseia-se em parametros na url,
	 * por isso, inclui uma validação para verificar se este parâmetro está vazio ou não.
	 */
	public String startGamePage() throws IOException {		
		
		//Verifica se a URL possui os parâmetros necessários
		boolean hasQuery = !(game.getNmGame() == null || game.getIdPlataform() == 0); 
		
		if(!hasQuery){
			FacesContext.getCurrentInstance().getExternalContext().redirect("searchGames.xhtml");
		}
		else{
			//Recupera Ficha completa do Jogo:
			game = dao.getGameFromName(game.getNmGame(),game.getIdPlataform());
			//Recupera comentarios do jogo carregado:
			System.out.println("Carregando comentarios para " + game.getIdGame());
			setComments(new CommentDAO().getGameComments(game.getIdGame()));
		}		
		return "";
	}

//======================== Propriedades ===========================================================//
	
	public List<GameView> getGames() {
		return games;
	}

	public GameView getGame() {
		return game;
	}

	public void setGame(GameView game) {
		this.game = game;
	}

	public List<GameComment> getComments() {
		return comments;
	}

	public void setComments(List<GameComment> comments) {
		this.comments = comments;
	}

}
