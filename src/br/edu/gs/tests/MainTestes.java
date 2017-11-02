package br.edu.gs.tests;

import br.edu.gs.model.User;
import br.edu.gs.model.dal.UserDAL;

public class MainTestes {
	public static void main(String[] args) {
		User u = new User();
		
		u.setNmUser("sophie");
		u.setDeEmail("sophie@email.com");
		u.setVlPassw("aaa1234");
		
		System.out.println("Usuario Criado");
		
		UserDAL dal = new UserDAL();
		
		dal.insert(u);
		
		System.out.println("Usuario inserido!");
	}
}
