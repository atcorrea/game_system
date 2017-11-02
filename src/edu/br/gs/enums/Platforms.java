package edu.br.gs.enums;
/**
 * Autor: André Torres;
 * Criação: 02/11/2017;
 * Descrição: Lista de Plataformas disponíveis na base de dados
 */
public enum Platforms {
	Nintendo64(1), SuperNintendo(2), GameBoy(3), GameBoyAdvance(4), NintendoDS(5), Nintendo3DS(6), VirtualBoy(7), SegaMasterSystem(8), SegaMegaDrive(9), SegaSaturn(10), SegaDreamCast(11), Playstation(12), Playstation2(13), Playstation3(14), Playstation4(15), Xbox(16), Xbox360(17), XboxONE(18), PSP(19), PSVITA(20), PC(21), ATARI2600(22), ATARI5200(23), ATARI7800(24), ATARIJaguar(25), ATARILynx(26), MSX(27), _3DO(28), PhillipsCDI(29), NintendoGamecube(30), NintendoWii(31);
	
	private final int value;
	
	Platforms(int value){
		this.value = value;
	}
	
	public int getValue(){
		return this.value;
	}
}
