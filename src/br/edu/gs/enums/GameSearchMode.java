package br.edu.gs.enums;
/**
 * 
 * @author André Torres
 * Lista de Filtros para busca de jogo
 */
public enum GameSearchMode {
	
	Name(1),Developer(2),Genre(3),Grade(4),Platform(5);
	
	private final int value;
	
	GameSearchMode(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
