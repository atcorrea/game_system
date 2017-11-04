package br.edu.gs.model.managedBeans;

import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

import br.edu.gs.model.Game;
import br.edu.gs.model.dao.GameDAO;
/**
 * 
 * @author André Torres
 * Ações para manipulação de Games
 */
@ManagedBean
@SessionScoped
public class GameMB {

	private Game game = new Game();
	private GameDAO dao = new GameDAO();
	private List<Game> games = new ArrayList<Game>();
	 
	public String searchGames(){
		
		try {
			games = dao.getAll();
		} catch (Exception e) {
			return "Error";
		}
		
		return "Success";
	}
	
	/*
	 * Implementar Pesquisa com filtros!
	 * public String searchFiltered(){}
	 */
	
	public List<Game> getGames() {
		return games;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

}
