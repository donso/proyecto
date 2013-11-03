package Lyrics;

import java.util.Vector;

public class Lyrics {
	Vector<Phrase> p = new Vector<Phrase>(10);
	
	
//	Los puntos separan frases.
//	Los guiones separan sílabas.
//	Los espacios separan palabras.
	public Lyrics(String s){
		String[] frases = s.split("\\.");
		for(int i = 0;i <frases.length ;i++){
			p.add(new Phrase(frases[i]));
		}
	}
	
	//Devuelve número de frases
	public int size(){
		return p.size();
	}

	//Devuelve sílabas de una frase
	public Vector<Syllable> getSyllables(int i){
		return p.get(i).getSyllables();
	}
	
	
	public void depurar(){
		System.out.println("En Lyrics");
		for(int i = 0;i <p.size() ;i++){
			p.get(i).depurar();
		}		
	}

}
