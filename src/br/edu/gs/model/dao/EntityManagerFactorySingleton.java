package br.edu.gs.model.dao;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * 
 * @author Andre Torres
 * Singleton para evitar ficar criando várias fábricas de Entity Manager
 */

class EntityManagerFactorySingleton {
	
	//Precisei adicionar um limite de conexões, pois o uso contínuo acabava gerando erros.
	private static final int CONECTION_LIMIT = 15;
	private static EntityManagerFactory emf;
	private static int useCount = 0;
	
	private EntityManagerFactorySingleton(){
		
	}
	
	public static EntityManagerFactory getInstance(){
		
		if (useCount == CONECTION_LIMIT) {
			emf = null;
		}
		
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("game");
		}
		
			useCount++;
			return emf;			
	}
}
