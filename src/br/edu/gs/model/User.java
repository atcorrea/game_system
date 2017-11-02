/*
 * Autor: André Torres; 
 * Criação: 02/11/2017;
 */

package br.edu.gs.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;

/**
 * @author André Torres
 * Descrição: Classe representando a entidade usuário (gerada pelo jpa)
 * 
 */
@Entity
@Table(name = "TB_USER")
@NamedQuery(name = "User.findAll", query = "SELECT t FROM User t")
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "ID_USER")
	private long idUser;

	@Column(name = "DE_EMAIL")
	private String deEmail;

	@Temporal(TemporalType.DATE)
	@Column(name = "DT_REGISTER")
	private Date dtRegister;

	@Column(name = "NM_USER")
	private String nmUser;

	@Column(name = "VL_PASSW")
	private String vlPassw;

	public User() {
		this.dtRegister = new Date();
	}

	public long getIdUser() {
		return this.idUser;
	}

	public void setIdUser(long idUser) {
		this.idUser = idUser;
	}

	public String getDeEmail() {
		return this.deEmail;
	}

	public void setDeEmail(String deEmail) {

		// Verifica validade do email.
		if (deEmail.length() > 250 || !deEmail.matches("\\w+(?:\\.|-)?@\\w+(?:\\.)\\w+")) {

			throw new IllegalArgumentException("Nome de usuário inválido!");

		} else {

			this.deEmail = deEmail;
		}
	}

	public Date getDtRegister() {
		return this.dtRegister;
	}

	public String getNmUser() {
		return this.nmUser;
	}

	public void setNmUser(String nmUser) {

		if (nmUser.length() > 70 || nmUser.length() < 4) {

			throw new IllegalArgumentException("Nome de usuário inválido!");

		} else {

			this.nmUser = nmUser;

		}
	}

	public String getVlPassw() {
		return this.vlPassw;
	}

	public void setVlPassw(String vlPassw) throws IllegalArgumentException{
		// Verifica se senha possui entre 6 e 30 caracteres e se possui um
		// número.
		if ((vlPassw.length() > 30 || vlPassw.length() < 6)
				|| !vlPassw.matches(".*\\d+.*")) {

			throw new IllegalArgumentException("Senha Inválida!");

		} else {

			this.vlPassw = vlPassw;
		}
	}

}