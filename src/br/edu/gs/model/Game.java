/*
 * Autor: André Torres;
 * Criação: 02/11/2017;
 */
package br.edu.gs.model;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * @author André Torres Classe representando a entidade jogo (gerada pelo JPA)
 */
@Entity
@Table(name = "TB_GAME")
@NamedQuery(name = "Game.findAll", query = "SELECT t FROM Game t")
public class Game implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_GAME")
	private long idGame;

	@Column(name = "DE_DESCRIPTION")
	private String deDescription;

	@Column(name = "ID_PLATAFORM")
	private int idPlataform;

	@Column(name = "NM_DEVELOPER")
	private String nmDeveloper;

	@Column(name = "NM_GAME")
	private String nmGame;

	@Column(name = "NM_GENRE")
	private String nmGenre;

	@Column(name = "VL_AVG_SCORE")
	private float vlAvgScore;

	public Game() {
	}

	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public long getIdGame() {
		return this.idGame;
	}

	public String getDeDescription() {
		return this.deDescription;
	}

	public void setDeDescription(String deDescription) {
		this.deDescription = deDescription;
	}

	public int getIdPlataform() {
		return this.idPlataform;
	}

	public void setIdPlataform(int idPlataform) {
		this.idPlataform = idPlataform;
	}

	public String getNmDeveloper() {
		return this.nmDeveloper;
	}

	public void setNmDeveloper(String nmDeveloper) {
		this.nmDeveloper = nmDeveloper;
	}

	public String getNmGame() {
		return this.nmGame;
	}

	public void setNmGame(String nmGame) {
		this.nmGame = nmGame;
	}

	public String getNmGenre() {
		return this.nmGenre;
	}

	public void setNmGenre(String nmGenre) {
		this.nmGenre = nmGenre;
	}

	public float getVlAvgScore() {
		return this.vlAvgScore;
	}

	public void setVlAvgScore(float vlAvgScore) {
		this.vlAvgScore = vlAvgScore;
	}

}