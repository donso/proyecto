package Lyrics;

import java.util.List;

import org.modelcc.IModel;
import org.modelcc.Separator;


public class Lyrics implements IModel{
	@Separator("[ \n\t]*")
	List<Phrase> phrases;	
	
	public List<Phrase> getPhrases() {
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
