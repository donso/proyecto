package Lyrics;

import org.modelcc.IModel;
import org.modelcc.Optional;

public class Syllable implements IModel{
	Text text;
	@Optional Ending ending;
	
	public Text getText(){
		return text;
	}

	public Ending getEnding(){
		return ending;
	}

	//TODO eliminar depurar
	public void depurar(){
		System.out.print("\n"+text);
		if(ending!=null)
			System.out.print(/*text+*/"  \t"+ending.getText());
	}
}
