package br.edu.gs.viewModel;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;


/**
 * Classe com todos os dados do game, utilizada para exibir em listas, não para inserir.
 * Utilizada apenas em VIEW.
 * 
 */
@Entity
@Table(name="VW_GAMEFULL")
@NamedQuery(name="GameView.findAll", query="SELECT v FROM GameView v")
public class GameView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DE_DESCRIPTION")
	private String deDescription;

	@Id
	@Column(name="ID_GAME")
	private long idGame;

	@Column(name="ID_PLATAFORM")
	private long idPlataform;

	@Column(name="NM_DEVELOPER")
	private String nmDeveloper;

	@Column(name="NM_GAME")
	private String nmGame;

	@Column(name="NM_GENRE")
	private String nmGenre;

	@Column(name="NM_PLATAFORM")
	private String nmPlataform;

	@Column(name="VL_AVG_SCORE")
	private BigDecimal vlAvgScore;

	public GameView() {
	}

	public String getDeDescription() {
		return this.deDescription;
	}

	public void setDeDescription(String deDescription) {
		this.deDescription = deDescription;
	}

	public long getIdGame() {
		return this.idGame;
	}

	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public long getIdPlataform() {
		return this.idPlataform;
	}

	public void setIdPlataform(long idPlataform) {
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

	public String getNmPlataform() {
		return this.nmPlataform;
	}

	public void setNmPlataform(String nmPlataform) {
		this.nmPlataform = nmPlataform;
	}

	public BigDecimal getVlAvgScore() {
		return this.vlAvgScore;
	}

	public void setVlAvgScore(BigDecimal vlAvgScore) {
		this.vlAvgScore = vlAvgScore;
	}

}