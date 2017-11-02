package edu.br.gs.model;

/**
 * Autor: Andr� Torres; Cria��o: 02/11/2017; Descri��o: Classe representando a
 * entidade usu�rio
 */
public class User {
	private String deEmail;
	private String vlPasswd;
	private String nmUser;

	public String getDeEmail() {
		return deEmail;
	}

	public void setDeEmail(String deEmail) {
		// Verifica validade do email.
		if (deEmail.length() > 250 || !deEmail.matches("\\w+(?:\\.|-)?@\\w+(?:\\.)\\w+")) {

			throw new IllegalArgumentException("Nome de usu�rio inv�lido!");

		} else {

			this.deEmail = deEmail;
		}
	}

	public String getVlPasswd() {
		return vlPasswd;
	}

	public void setVlPasswd(String vlPasswd) {

		// Verifica se senha possui entre 8 e 30 caracteres e se possui um
		// n�mero.
		if ((vlPasswd.length() > 30 || vlPasswd.length() < 8) || !vlPasswd.matches("(?:\\w+(?:\\.|-)*){1,4}@(?:\\w+(?:\\.|-)*){1,4}")) {

			throw new IllegalArgumentException("Nome de usu�rio inv�lido!");

		} else {

			this.vlPasswd = vlPasswd;
		}
	}

	public String getNmUser() {
		return nmUser;
	}

	public void setNmUser(String nmUser) {

		if (nmUser.length() > 70 || nmUser.length() < 4) {

			throw new IllegalArgumentException("Nome de usu�rio inv�lido!");

		} else {

			this.nmUser = nmUser;

		}

	}
}
