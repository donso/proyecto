package Lyrics;

import org.modelcc.IModel;
import org.modelcc.Optional;
import org.modelcc.types.NonQuotedStringModel;

public class Syllable implements IModel{
	NonQuotedStringModel text;
	@Optional Ending ending;
	
	public NonQuotedStringModel getText(){
		return text;
	}

	public Ending getEnding(){
		return ending;
	}

	//TODO eliminar depurar
	public void depurar(){
		System.out.println(text+"  \t"+ending);
	}
}
