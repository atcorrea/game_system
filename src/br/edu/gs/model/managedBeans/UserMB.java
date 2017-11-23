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
 * @author Andr� Torres A��es para Manipular Usu�rios
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
		System.out.println("Inserindo usu�rio...");
		try {
			dao.insert(user);
			cm.newMessage("Usu�rio Inserido com sucesso");
			cm.redirect("../login.xhtml");
		}
		
		catch (ConstraintViolationException e){
			cm.newMessage("nome de usu�rio j� existe!");
		}
		
		catch (Exception e) {
			cm.newMessage("Ocorreu um erro ao inserir o usu�rio, tente novamente mais tarde.");
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
			cm.addToSession("user", loggedUser);
			cm.redirect("games/searchGames.xhtml");
		}

		else {
			cm.newMessage("Este usu�rio n�o existe ou a senha digitada � inv�lida");
		}
	}

	public String loadUserPage() {

		// Verifica se a URL possui os par�metros necess�rios
		boolean hasQuery = context.hasQueryStringParameter("user");

		if (!hasQuery) {
			context.redirect("searchGames.xhtml");
		} else {
			loadUserData();
		}
		return "";
	}

	// -------------------------M�todos
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
