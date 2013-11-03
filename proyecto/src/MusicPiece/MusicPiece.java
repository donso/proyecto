package MusicPiece;

import java.util.Vector;

import Lyrics.*;


public class MusicPiece {
	Vector<MusicElement> elements = new Vector<MusicElement>(10);
	
	public MusicPiece(Lyrics l){
		for(int i=0; i<l.size(); i++){
			Vector<Syllable> s = l.getSyllables(i);
			adaptMusicElements(s);
			//System.out.println("\nconst musicPiece "+s.size());
			//for(int j=0; j<s.size(); j++){
			//	s.get(j).examinar();				
			//}
		}
	}
	
	//Añade las sílabas al nuevo modelo además de las posibles pausas entre sílabas de distintas palabras 
	//que se pueden juntar.
	//¿Hay pausas entre palabras? 
	void adaptMusicElements(Vector<Syllable> s){
		String texto;
		System.out.println("\nEn MusicPiece");
		for(int j=0; j<s.size(); j++){
			texto = s.get(j).getText();
			System.out.println(texto+" size "+elements.size());
			SyllableM silaba = new SyllableM(texto);
			elements.add(silaba);
			if(s.get(j).getEnding() && j+1!=s.size()){
				String t1=s.get(j+1).getText();
				if(texto.matches(".*[aeiou]$") && t1.matches("^[aeiou].*")){ //pausa opcional
					elements.add(new Pause());
				}
			}
		}
	}

}
