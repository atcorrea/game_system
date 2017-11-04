package br.edu.gs.model.managedBeans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import br.edu.gs.model.User;
import br.edu.gs.model.dao.UserDAO;
/**
 * 
 * @author Andr� Torres
 * A��es para Manipular Usu�rios
 */
@ManagedBean
@SessionScoped
public class UserMB {

	private User u = new User();
	private UserDAO dao = new UserDAO();

	public String createUser() {

		try {
			dao.insert(u);
			FacesMessage msg = new FacesMessage("Usu�rio criado com sucesso!");
			FacesContext.getCurrentInstance().addMessage(null, msg);

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	public String authenticate() {

		return dao.authenticate(u).toString();

	}

	public User getUser() {
		return u;
	}

	public void setUser(User u) {
		this.u = u;
	}

}
