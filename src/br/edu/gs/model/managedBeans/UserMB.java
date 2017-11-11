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

	public String newUser() {

		try {
			dao.insert(u);

		} catch (Exception e) {
			return "";
		}

		return "";
	}

	public String authenticate() {

		boolean userOK =  dao.authenticate(u);
		
		if(userOK){
			return "register-user.xhtml";
		}
		
		else{
			FacesMessage msg = new FacesMessage("Este usu�rio n�o existe ou a senha digitada � inv�lida");
			FacesContext.getCurrentInstance().addMessage( null, msg);
			return "";
		}
	}

	public User getUser() {
		return u;
	}

	public void setUser(User u) {
		this.u = u;
	}

}
