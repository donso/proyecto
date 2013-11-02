package Lyrics;

import java.util.Vector;

public class Lyrics {
	Vector<Phrase> p = new Vector<Phrase>(10);
	
	public Lyrics(String s){
		String[] frases = s.split("\\.");
		System.out.print("i");
		for(int i = 0;i <frases.length ;i++){
			System.out.print("\nd "+frases[i]);
			p.addElement(new Phrase(frases[i]));
		}
	}
	
	public void examinar(){
		for(int i = 0;i <p.size() ;i++){
			p.get(i).examinar();
		}		
	}
}
