package MusicPiece;

import java.util.Vector;

import Lyrics.*;


public class MusicPiece {
	Vector<MusicElement> elements;
	
	public MusicPiece(Lyrics l){
		elements = new Vector<MusicElement>();
		Vector<Phrase> phrases = l.getPhrases();
		for(int i=0; i<phrases.size(); i++){
			Vector<Syllable> s = phrases.get(i).getSyllables();
			adaptMusicElements(s);
		}
	}
	
	//Añade las sílabas al nuevo modelo además de las posibles pausas 
	void adaptMusicElements(Vector<Syllable> s){
		String texto;
		for(int j=0; j<s.size(); j++){
			texto = s.get(j).getText();
			//vemos si es una vocal tónica
			boolean tonica = texto.matches("[A-Z]*"); 
			SyllableM silaba = new SyllableM(texto, tonica);
			elements.add(silaba);
			char ending = s.get(j).getEnding();
			if(ending == ' ' || ending == ',' || ending == '.' || ending == '?' || ending == '!')
				elements.add(new Pause());
		}
	}
	
	//TODO eliminar depurar
	public void depurar(){
		System.out.println("\nEn MusicPiece. Size: "+elements.size());
		System.out.println("text \ttónica");
		for(int i = 0; i<elements.size(); i++){
			elements.get(i).depurar();
		}
	}
}
