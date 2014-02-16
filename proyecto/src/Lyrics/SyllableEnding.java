package Lyrics;

import org.modelcc.IModel;
import org.modelcc.Pattern;
import org.modelcc.Value;

@Pattern(regExp="-| ")

public class SyllableEnding implements IModel{
	@Value char text;
	
	//TODO eliminar
	public char getText(){
		return text;
	}

}
