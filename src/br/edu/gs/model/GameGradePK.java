package br.edu.gs.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the TB_GAME_GRADES database table.
 * 
 */
@Embeddable
public class GameGradePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="ID_GAME", insertable=false, updatable=false)
	private long idGame;

	@Column(name="ID_USER", insertable=false, updatable=false)
	private long idUser;

	public GameGradePK(long idGame, long idUser) {
		this.idGame = idGame;
		this.idUser = idUser;
	}
	
	public GameGradePK(){
	
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

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof GameGradePK)) {
			return false;
		}
		GameGradePK castOther = (GameGradePK)other;
		return 
			(this.idGame == castOther.idGame)
			&& (this.idUser == castOther.idUser);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.idGame ^ (this.idGame >>> 32)));
		hash = hash * prime + ((int) (this.idUser ^ (this.idUser >>> 32)));
		
		return hash;
	}
}