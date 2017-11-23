package br.edu.gs.model.managedBeans;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;

import org.hibernate.exception.ConstraintViolationException;

import br.edu.gs.model.User;
import br.edu.gs.model.dao.GameDAO;
import br.edu.gs.model.dao.UserDAO;
import br.edu.gs.utils.ContextManager;
import br.edu.gs.viewModel.GameView;

/**
 * 
 * @author André Torres Ações para Manipular Usuários
 */
@ManagedBean(name = "UserMB")
@ViewScoped
public class UserMB implements Serializable {

	private User user = new User();
	private UserDAO dao = new UserDAO();
	private List<GameView> completedGames = new ArrayList<GameView>();

	private ContextManager context = new ContextManager();

	public String newUser() {

		ContextManager cm = new ContextManager();
		System.out.println("Inserindo usuário...");
		try {
			dao.insert(user);
			cm.newMessage("Usuário Inserido com sucesso");
			cm.redirect("../login.xhtml");
		}
		
		catch (ConstraintViolationException e){
			cm.newMessage("nome de usuário já existe!");
		}
		
		catch (Exception e) {
			cm.newMessage("Ocorreu um erro ao inserir o usuário, tente novamente mais tarde.");
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
			cm.addToSession("user", loggedUser);
			cm.redirect("games/searchGames.xhtml");
		}

		else {
			cm.newMessage("Este usuário não existe ou a senha digitada é inválida");
		}
	}

	public String loadUserPage() {

		// Verifica se a URL possui os parâmetros necessários
		boolean hasQuery = context.hasQueryStringParameter("user");

		if (!hasQuery) {
			context.redirect("searchGames.xhtml");
		} else {
			loadUserData();
		}
		return "";
	}

	// -------------------------Métodos
	// privados------------------------------------------------------//
	private void loadUserData() {

		String parameter = user.getNmUser();
		user = dao.getUserFromName(parameter);

		// Carrega Lista de Jogos completados
		setCompletedGames(new GameDAO().getCompletedGames(user.getIdUser()));
	}

	// -------------------------Propridades------------------------------------------------------//
	public User getUser() {
		return user;
	}

	public void setUser(User u) {
		this.user = u;
	}

	public List<GameView> getCompletedGames() {
		return completedGames;
	}

	public void setCompletedGames(List<GameView> completedGames) {
		this.completedGames = completedGames;
	}

}
