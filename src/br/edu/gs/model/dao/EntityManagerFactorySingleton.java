package br.edu.gs.model.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * 
 * @author Andre Torres
 * Singleton para evitar ficar criando v�rias f�bricas de Entity Manager
 */

class EntityManagerFactorySingleton {
	
	private static EntityManagerFactory emf;
	
	private EntityManagerFactorySingleton(){
		
	}
	
	public static EntityManagerFactory getInstance(){
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("game");
		}
			return emf;			
	}
}
