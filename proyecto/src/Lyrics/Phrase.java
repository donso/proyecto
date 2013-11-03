package Lyrics;

import java.util.Vector;

public class Phrase {
	Vector<Syllable> p = new Vector<Syllable>(10);
	
	public Phrase(String s){
		String[] palabras = s.split(" ");
		String[] silabas;
		for(int i = 0;i <palabras.length ;i++){
			silabas = palabras[i].split("-");
			for(int j = 0;j<silabas.length ;j++)
				p.addElement(new Syllable(silabas[j],j==silabas.length-1)); //texto y última sílaba de palabra
		}
	}
	
	//Devuelve sílabas de la frase
	public Vector<Syllable> getSyllables(){
		return p;
	}

	public void depurar(){
//		System.out.println("phrase in");
		for(int i = 0;i <p.size() ;i++){
//			System.out.print(p.indexOf(i));
			p.get(i).depurar();
		}
	}
	
	
}
