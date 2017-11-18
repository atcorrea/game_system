package br.edu.gs.model.managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.edu.gs.model.User;
import br.edu.gs.model.dao.UserDAO;
import br.edu.gs.utils.ContextManager;

/**
 * 
 * @author Andr� Torres A��es para Manipular Usu�rios
 */
@ManagedBean(name = "UserMB")
@ViewScoped
public class UserMB {

	private User user = new User();
	private UserDAO dao = new UserDAO();

	public String newUser() {

		ContextManager cm = new ContextManager();
		System.out.println("Inserindo usu�rio...");
		try {
			dao.insert(user);
			cm.newMessage("Usu�rio Inserido com sucesso!");

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	/**
	 * Autentica��o de usu�rio, recebe nome de usu�rio e senha e busca no banco
	 * para ver se encotra alguma informa��o. Caso encontre, o objeto do usu�rio
	 * � armazenado em sess�o.
	 */
	public void authenticate() {

		ContextManager cm = new ContextManager();
		User loggedUser = dao.authenticate(user);

		if (loggedUser != null) {
			// Salva o usu�rio logado na sess�o
			cm.keepInSession("user", loggedUser);
			cm.redirect("games/searchGames.xhtml");
		}

		else {
			cm.newMessage("Este usu�rio n�o existe ou a senha digitada � inv�lida");
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

}
