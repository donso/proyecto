package Lyrics;

public class Syllable {
	String text;
	char ending;
	
	//TODO eliminar contructor
	public Syllable(String texto, char end){
		text = texto;
		ending = end;
	}

	public String getText(){
		return text;
	}

	public char getEnding(){
		return ending;
	}

	//TODO eliminar depurar
	public void depurar(){
		System.out.println(text+"  \t"+ending);
	}
}
