package br.edu.gs.tests;

import java.util.ArrayList;

import br.edu.gs.enums.GameSearchMode;
import br.edu.gs.model.Game;
import br.edu.gs.model.dal.GameDAL;

public class MainTestes {
	public static void main(String[] args) {
		
		GameDAL gd = new GameDAL();		
		
		ArrayList<Game> list = (ArrayList<Game>) gd.getAll("13",GameSearchMode.Platform);
		System.out.println(list.size());
		
		for (Game game : list) {
			System.out.println(game.getIdGame() + " " + game.getNmGame());
		}
		System.out.println("Concluido.");
	}
}
