package br.edu.gs.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * Objeto representado a entidade plataforma
 * 
 */
@Entity
@Table(name="TB_PLATAFORM")
@NamedQuery(name="Plataform.findAll", query="SELECT t FROM Plataform t")
public class Plataform implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name="ID_PLATAFORM")
	private long idPlataform;

	@Column(name="DE_DESCRIPTION")
	private String deDescription;

	@Column(name="NM_PLATAFORM")
	private String nmPlataform;

	public Plataform() {
	}

	public long getIdPlataform() {
		return this.idPlataform;
	}

	public void setIdPlataform(long idPlataform) {
		this.idPlataform = idPlataform;
	}

	public String getDeDescription() {
		return this.deDescription;
	}

	public void setDeDescription(String deDescription) {
		this.deDescription = deDescription;
	}

	public String getNmPlataform() {
		return this.nmPlataform;
	}

	public void setNmPlataform(String nmPlataform) {
		this.nmPlataform = nmPlataform;
	}

}