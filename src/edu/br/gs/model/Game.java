package edu.br.gs.model;

import edu.br.gs.enums.Platforms;

/**
 * Autor: André Torres;
 * Criação: 02/11/2017;
 * Descrição: Classe representando a entidade jogo
 */
public class Game {
	
	private Platforms idPlataform;
	private String NmGame;
	private String DeDescription;
	private float VlAvgScore;
	private String NmDeveloper;
	private String NmGenre;

	public String getNmGame() {
		return NmGame;
	}

	public void setNmGame(String nmGame) {
		if (nmGame.length() > 70 || nmGame.length() < 2) {
			throw new IllegalArgumentException("Nome do Jogo inválido!");
		} else {
			NmGame = nmGame;
		}
	}

	public Platforms getIdPlataform() {
		return idPlataform;
	}

	public void setIdPlataform(Platforms idPlataform) {
		this.idPlataform = idPlataform;
	}

	public String getDeDescription() {
		return DeDescription;
	}

	public void setDeDescription(String deDescription) {
		if (deDescription.length() > 250) {
			throw new IllegalArgumentException("Descrição muito longa!");
		} else {
			DeDescription = deDescription;
		}
	}

	public float getVlAvgScore() {
		return VlAvgScore;
	}

	public String getNmDeveloper() {
		return NmDeveloper;
	}

	public void setNmDeveloper(String nmDeveloper) {
		if (nmDeveloper.length() > 30 || nmDeveloper.length() < 2) {
			throw new IllegalArgumentException("Desenvolvedor Inválido!");
		} else {
			NmDeveloper = nmDeveloper;
		}
	}

	public String getNmGenre() {
		return NmGenre;
	}

	public void setNmGenre(String nmGenre) {
		if (nmGenre.length() > 30 || nmGenre.length() < 2) {
			throw new IllegalArgumentException("Gênero Inválido!");
		} else {
			NmGenre = nmGenre;
		}
	}

}
