package br.edu.gs.viewModel;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the VW_COMMENT database table.
 * 
 */
@Entity
@Table(name="VW_COMMENT")
@NamedQuery(name="CommentView.findAll", query="SELECT v FROM CommentView v")
public class CommentView implements Serializable {
	private static final long serialVersionUID = 1L;

	@Column(name="DE_COMMENT", insertable=false, updatable=false)
	private String deComment;

	@Temporal(TemporalType.DATE)
	@Column(name="DT_COMMENT", insertable=false, updatable=false)
	private Date dtComment;

	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name="ID_COMMENT", insertable=false, updatable=false)
	private long idComment;

	@Column(name="ID_GAME", insertable=false, updatable=false)
	private long idGame;

	@Column(name="ID_USER", insertable=false, updatable=false)
	private long idUser;

	@Column(name="NM_GAME", updatable=false)
	private String nmGame;

	@Column(name="NM_USER", insertable=false, updatable=false)
	private String nmUser;

	public CommentView() {
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

	public long getIdGame() {
		return this.idGame;
	}

	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getNmGame() {
		return this.nmGame;
	}

	public void setNmGame(String nmGame) {
		this.nmGame = nmGame;
	}

	public String getNmUser() {
		return this.nmUser;
	}

	public void setNmUser(String nmUser) {
		this.nmUser = nmUser;
	}

}