package br.edu.gs.utils;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
/**
 * 
 * @author Andre Torres
 * Singleton para evitar ficar criando v�rias f�bricas de Entity Manager
 */

public class EntityManagerFactorySingleton {
	
	//Precisei adicionar um limite de conex�es, pois o uso cont�nuo acabava gerando erros.
	private static EntityManagerFactory emf;
	
	private EntityManagerFactorySingleton(){
		
	}
	
	public static EntityManagerFactory getInstance(){
		
		
		if (emf == null){
			emf = Persistence.createEntityManagerFactory("gamemysql");
		}	
			return emf;			
	}
	
}
