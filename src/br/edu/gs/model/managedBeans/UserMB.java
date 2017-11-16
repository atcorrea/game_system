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
 * @author André Torres Ações para Manipular Usuários
 */
@ManagedBean(name="UserMB")
@SessionScoped
public class UserMB {

	private User user = new User();
	
	@ViewScoped
	private UserDAO dao = new UserDAO();

	public String newUser() {

		System.out.println("Inserindo usuário...");
		try {
			dao.insert(user);
			System.out.println("Cadastrado com sucesso!");

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	/**
	 * Autenticação de usuário, recebe nome de usuário e senha e 
	 * busca no banco para ver se encotra alguma informação.
	 * Caso encontre, o objeto do usuário é armazenado em sessão.
	 */
	public void authenticate() {

		User loggedUser = dao.authenticate(user);

		try {

			if (loggedUser != null) {
				user = loggedUser;
				FacesContext.getCurrentInstance().getExternalContext().redirect("games/searchGames.xhtml");
			}

			else {
				System.out.println("usuário inválido");
				FacesMessage msg = new FacesMessage("Este usuário não existe ou a senha digitada é inválida");
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
