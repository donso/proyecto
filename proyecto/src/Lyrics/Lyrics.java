package Lyrics;

import java.util.Vector;

public class Lyrics {
	Vector<Phrase> phrases;
	
	//TODO eliminar constructor
	public Lyrics(String s){
		phrases = new Vector<Phrase>();
		String[] frases = s.split("\\.");
		for(int i = 0;i <frases.length ;i++){
			phrases.add(new Phrase(frases[i]));
		}
	}
	
	public Vector<Phrase> getPhrases() {
	    return phrases;
	}
	
	//TODO eliminar depurar
	public void depurar(){
		System.out.println("\nEn Lyrics ");
		System.out.println("Número de frases "+phrases.size());
		for(int i = 0; i<phrases.size(); i++){
			phrases.get(i).depurar();
		}		
	}

}
