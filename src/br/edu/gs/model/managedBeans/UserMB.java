package br.edu.gs.model.managedBeans;

import java.io.IOException;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;

import br.edu.gs.model.User;
import br.edu.gs.model.dao.UserDAO;

/**
 * 
 * @author Andr� Torres A��es para Manipular Usu�rios
 */
@ManagedBean(name="UserMB")
@SessionScoped
public class UserMB {

	private User user = new User();
	
	@ViewScoped
	private UserDAO dao = new UserDAO();

	public String newUser() {

		System.out.println("Inserindo usu�rio...");
		try {
			dao.insert(user);
			System.out.println("Cadastrado com sucesso!");

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	/**
	 * Autentica��o de usu�rio, recebe nome de usu�rio e senha e 
	 * busca no banco para ver se encotra alguma informa��o.
	 * Caso encontre, o objeto do usu�rio � armazenado em sess�o.
	 */
	public void authenticate() {

		User loggedUser = dao.authenticate(user);

		try {

			if (loggedUser != null) {
				user = loggedUser;
				FacesContext.getCurrentInstance().getExternalContext().redirect("games/searchGames.xhtml");
			}

			else {
				System.out.println("usu�rio inv�lido");
				FacesMessage msg = new FacesMessage("Este usu�rio n�o existe ou a senha digitada � inv�lida");
				FacesContext.getCurrentInstance().addMessage(null, msg);
				user = new User();
			}

		} catch (IOException e) {
			e.printStackTrace();
			user = new User();
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

}
