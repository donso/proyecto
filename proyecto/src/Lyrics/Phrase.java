package Lyrics;

import java.util.Vector;

public class Phrase {
	Vector<Syllable> p = new Vector<Syllable>(10);
	
	public Phrase(String s){
		String[] palabras = s.split(" ");
		String[] silabas;
//		la ca-sa es a-zul
		for(int i = 0;i <palabras.length ;i++){
			silabas = palabras[i].split("-");
			for(int j = 0;j<silabas.length ;j++)
				p.addElement(new Syllable(silabas[j],j==silabas.length-1));
		}
	}
	
	public void examinar(){
		System.out.println("phrase in");
		for(int i = 0;i <p.size() ;i++){
//			System.out.print(p.indexOf(i));
			p.get(i).examinar();
		}
	}
}
