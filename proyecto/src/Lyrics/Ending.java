package Lyrics;

import org.modelcc.IModel;
import org.modelcc.Pattern;
import org.modelcc.Value;

@Pattern(regExp="_|-| ")

public class Ending implements IModel{
	@Value char text;
	
	public char getText(){
		return text;
	}

}
