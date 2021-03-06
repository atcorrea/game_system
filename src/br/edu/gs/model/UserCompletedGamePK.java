package br.edu.gs.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the tb_user_completed_games database table.
 * 
 */
@Embeddable
public class UserCompletedGamePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_USER", insertable=false, updatable=false)
	private long idUser;

	@Column(name="ID_GAME", insertable=false, updatable=false)
	private long idGame;

	public UserCompletedGamePK() {
	}
	public long getIdUser() {
		return this.idUser;
	}
	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}
	public long getIdGame() {
		return this.idGame;
	}
	public void setIdGame(long idGame) {
		this.idGame = idGame;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof UserCompletedGamePK)) {
			return false;
		}
		UserCompletedGamePK castOther = (UserCompletedGamePK)other;
		return 
			(this.idUser == castOther.idUser)
			&& (this.idGame == castOther.idGame);
	}
	
	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idGame ^ (this.idGame >>> 32)));
		hash = hash * prime + ((int) (this.idUser ^ (this.idUser >>> 32)));
		
		return hash;
	}
}