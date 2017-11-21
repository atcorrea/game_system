package br.edu.gs.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * 
 * @author Andre
 * Utilitario para tratamento de n�meros.
 */
public class NumberUtils {
	
	/**
	 * Arredonda n�meros para cima
	 * @param value = Valor a ser arredondado (Tipo Double)
	 * @param places = N�mero de Casas (Tipo Int)
	 * @return Retorna o n�mero arredondado
	 */
	public static Double round(Double value, int places){
		if (places < 0) throw new IllegalArgumentException();

	    BigDecimal bd = new BigDecimal(value);
	    bd = bd.setScale(places, RoundingMode.HALF_UP);
	    return bd.doubleValue();
	}
	
}
