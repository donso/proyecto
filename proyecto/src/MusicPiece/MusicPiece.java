package MusicPiece;

import java.util.ArrayList;
import java.util.List;

import Lyrics.SyllableEnding;
import Lyrics.Lyrics;
import Lyrics.Phrase;
import Lyrics.Syllable;


public class MusicPiece {
	List<MusicElement> elements;
	
	public MusicPiece(Lyrics l){
		elements = new ArrayList<MusicElement>();
		List<Phrase> phrases = l.getPhrases();
		for(int i=0; i<phrases.size(); i++){
			List<Syllable> s = phrases.get(i).getSyllables();
			adaptMusicElements(s);
			elements.add(new Pause(phrases.get(i).getEnding().getText()));
		}
	}
//elimniar
	public MusicPiece(ArrayList<MusicElement> copia){
		elements = new ArrayList<MusicElement>(copia);
	}
	
	public List<MusicElement> getElements(){
		return elements;
	}
	
	
	public void setElementDuration(int pos, int dur){
		elements.get(pos).duration=dur;
	}
	
	//AÃ±ade las sÃ­labas al nuevo modelo ademÃ¡s de las posibles pausas 
	void adaptMusicElements(List<Syllable> s){
		String texto;
		for(int j=0; j<s.size(); j++){
			texto = s.get(j).getText().toString();
			//vemos si es una vocal tÃ³nica
			boolean tonica = texto.matches("[A-ZÃ�Ã‰Ã�Ã“Ãš]+"); 
			SyllableM silaba = new SyllableM(texto, tonica);
			elements.add(silaba);
			SyllableEnding e = s.get(j).getEnding();
			if(e != null){
				char ending = e.getText();
				if(ending == ' ' || ending == ',' || ending == '.' || ending == '?' || ending == '!')
					elements.add(new Pause(ending));
			}
		}
	}
	
	//TODO eliminar depurar
	public void depurar(){
		System.err.println("\nEn MusicPiece. Size: "+elements.size());
		System.err.println("text \ttónica");
		for(int i = 0; i<elements.size(); i++){
			elements.get(i).depurar();
		}
	}
}