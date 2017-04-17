package br.ufpe.nti.util;


public class CalcularAngulo {
	
	public static double calcular(int hora, int minutos){
		if (hora > 12){
			hora -= 12; 
		}
		
		double angulo = Math.abs(30*hora - 11*minutos/2);
		
		if (angulo > 180.0){
			angulo = Math.abs(360 - angulo);
		}
		
		return angulo;
	}
}
