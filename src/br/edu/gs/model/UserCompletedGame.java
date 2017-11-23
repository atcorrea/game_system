package br.edu.gs.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the tb_user_completed_games database table.
 * 
 */
@Entity
@Table(name="tb_user_completed_games")
@NamedQuery(name="UserCompletedGame.findAll", query="SELECT u FROM UserCompletedGame u")
public class UserCompletedGame implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private UserCompletedGamePK id;

	public UserCompletedGame() {
	}
	
	public UserCompletedGame(long idGame, long idUser) {
		this.id = new UserCompletedGamePK();
		this.id.setIdGame(idGame);
		this.id.setIdUser(idUser);
	}

	public UserCompletedGamePK getId() {
		return this.id;
	}

	public void setId(UserCompletedGamePK id) {
		this.id = id;
	}

}