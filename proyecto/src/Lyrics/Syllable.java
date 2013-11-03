package Lyrics;

public class Syllable {
	String text;
	boolean ending;
	
	//Texto de la sílaba
	//b = true si es sílaba al final de palabra, false en otro caso
	public Syllable(String s, boolean b){
		text = s;
		ending = b;
	}

	public String getText(){
		return text;
	}

	public boolean getEnding(){
		return ending;
	}

	public void depurar(){
		System.out.println(text+" fin "+ending);
	}
}
