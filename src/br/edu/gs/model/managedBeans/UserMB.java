package br.edu.gs.model.managedBeans;

import javax.faces.bean.ManagedBean;
import javax.faces.view.ViewScoped;

import br.edu.gs.model.User;
import br.edu.gs.model.dao.UserDAO;
import br.edu.gs.utils.ContextManager;

/**
 * 
 * @author André Torres Ações para Manipular Usuários
 */
@ManagedBean(name = "UserMB")
@ViewScoped
public class UserMB {

	private User user = new User();
	private UserDAO dao = new UserDAO();

	public String newUser() {

		ContextManager cm = new ContextManager();
		System.out.println("Inserindo usuário...");
		try {
			dao.insert(user);
			cm.newMessage("Usuário Inserido com sucesso!");

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	/**
	 * Autenticação de usuário, recebe nome de usuário e senha e busca no banco
	 * para ver se encotra alguma informação. Caso encontre, o objeto do usuário
	 * é armazenado em sessão.
	 */
	public void authenticate() {

		ContextManager cm = new ContextManager();
		User loggedUser = dao.authenticate(user);

		if (loggedUser != null) {
			// Salva o usuário logado na sessão
			cm.keepInSession("user", loggedUser);
			cm.redirect("games/searchGames.xhtml");
		}

		else {
			cm.newMessage("Este usuário não existe ou a senha digitada é inválida");
		}
	}

	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

}
