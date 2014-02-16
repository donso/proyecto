package Lyrics;

import org.modelcc.IModel;
import org.modelcc.Pattern;
import org.modelcc.Value;

@Pattern(regExp="[a-zA-ZÃ¡Ã©Ã­Ã³ÃºÃ�Ã‰Ã�Ã“Ãš_]+")

public class Text implements IModel {

	@Value
	String text;

	public String toString(){
		return text;
	}
}

