package br.edu.gs.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


/**
 * The persistent class for the TB_GAME_GRADES database table.
 * 
 */
@Entity
@Table(name="TB_GAME_GRADES")
@NamedQuery(name="GameGrade.findAll", query="SELECT t FROM GameGrade t")
public class GameGrade implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private GameGradePK id;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DT_GRADE")
	private Date dtGrade;

	@Column(name="VL_GRADE")
	private Double vlGrade;

	public GameGrade() {
	}

	public GameGradePK getId() {
		return this.id;
	}

	public void setId(GameGradePK id) {
		this.id = id;
	}

	public Date getDtGrade() {
		return this.dtGrade;
	}

	public void setDtGrade(Date dtGrade) {
		this.dtGrade = dtGrade;
	}

	public Double getVlGrade() {
		return this.vlGrade;
	}

	public void setVlGrade(Double vlGrade) {
		this.vlGrade = vlGrade;
	}

}