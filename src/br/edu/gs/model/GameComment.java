package br.edu.gs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the TB_GAME_COMMENTS database table.
 * 
 */
@Entity
@Table(name = "TB_GAME_COMMENTS")
@NamedQuery(name = "GameComment.findAll", query = "SELECT t FROM GameComment t")
public class GameComment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_COMMENT")
	private long idComment;

	@Column(name = "ID_GAME")
	private long idGame;

	@Column(name = "ID_USER")
	private long idUser;

	@Column(name = "DE_COMMENT")
	private String deComment;

	public long getIdGame() {
		return idGame;
	}

	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public long getIdUser() {
		return idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_COMMENT")
	private Date dtComment;

	public GameComment() {
	}

	public String getDeComment() {
		return this.deComment;
	}

	public void setDeComment(String deComment) {
		this.deComment = deComment;
	}

	public Date getDtComment() {
		return this.dtComment;
	}

	public void setDtComment(Date dtComment) {
		this.dtComment = dtComment;
	}

	public long getIdComment() {
		return this.idComment;
	}

	public void setIdComment(long idComment) {
		this.idComment = idComment;
	}

}