package br.edu.gs.utils;

import java.io.IOException;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;

import org.primefaces.context.RequestContext;

/**
 * 
 * @author Andre Torres Classe que encapsula gerenciamento de contexto. Manipula
 *         sessão, redirecionamento, etc.
 */
public class ContextManager {

	private FacesContext fContext;
	private ExternalContext eContext;
	private Map<String, Object> session;
	private Map<String, Object> application;

	public ContextManager() {
		fContext = FacesContext.getCurrentInstance();
		eContext = fContext.getExternalContext();
		session = eContext.getSessionMap();
		application = eContext.getApplicationMap();
	}

	public void redirect(String url) {
		try {
			eContext.redirect(url);

		} catch (IOException e) {

			e.printStackTrace();
		}
	}
	
	public void reloadComponent(String idComponent){
		RequestContext.getCurrentInstance().update(idComponent);
	}

	
	public boolean keepInSession(String key, Object value) {
		try {
			session.put(key, value);

		} catch (Exception e) {

			return false;
		}

		return true;
	}

	public Object getFromSession(String key) {
		try {
			return session.get(key);
		} catch (Exception e) {
			System.out.println(key + "não foi registrada na session!");
			return null;
		}
	}

	public boolean removeFromSession(String key) {
		try {
			session.remove(key);

		} catch (Exception e) {

			return false;
		}

		return true;
	}

	public boolean clearSession() {

		try {
			eContext.invalidateSession();
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public boolean addToApplication(String key, Object value) {
		try {
			application.put(key, value);

		} catch (Exception e) {

			return false;
		}

		return true;
	}

	public Object getFromApplication(String key) {
		try {
			return application.get(key);
		} catch (Exception e) {
			System.out.println(key + "não foi registrada na aplication!");
			return null;
		}
	}

	public boolean removeFromApplication(String key) {
		try {
			application.remove(key);

		} catch (Exception e) {

			return false;
		}

		return true;
	}

	public boolean hasQueryStringParameter(String key) {
		Map<String, String> queryString = eContext.getRequestParameterMap();
		return queryString.containsKey(key);
	}

	public void newMessage(String msg) {
		FacesMessage m = new FacesMessage(msg);
		fContext.addMessage(null, m);
	}
}
