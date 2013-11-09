package Lyrics;

import java.util.Vector;

public class Phrase {
	Vector<Syllable> syllables;
	
	//TODO eliminar constructor
	public Phrase(String s){
		syllables = new Vector<Syllable>();
		String[] silabas = s.split(" ");
		int indice = 0;

		silabas = s.split("-|_|,| |¿|\\?|!|¡");
		int i = 0;
		char ending ;
		for(;i<silabas.length-1 ;i++){
			indice = s.indexOf(silabas[i], indice);
			ending = s.charAt( indice + silabas[i].length());
			syllables.addElement(new Syllable(silabas[i], ending)); 
			indice += silabas[i].length();
		}
		ending = '.';
		syllables.addElement(new Syllable(silabas[i], ending)); 
	}
	

	//Devuelve sílabas de la frase
	public Vector<Syllable> getSyllables(){
		return syllables;
	}

	
	//TODO eliminar depurar
	public void depurar(){
		System.out.println("Número de sílabas de la frase: "+syllables.size());
		System.out.println("text \tending");
		for(int i = 0;i <syllables.size() ;i++)
			syllables.get(i).depurar();
	}	
}
