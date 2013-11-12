package Lyrics;

import java.util.List;

import org.modelcc.IModel;
import org.modelcc.Suffix;


@Suffix("\\.")

public class Phrase implements IModel{
	List<Syllable> syllables;

	//Devuelve sílabas de la frase
	public List<Syllable> getSyllables(){
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
