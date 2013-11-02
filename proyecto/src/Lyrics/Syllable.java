package Lyrics;

public class Syllable {
	String text;
	boolean ending;
	
	public Syllable(String s, boolean b){
		text = s;
		ending = b;
	}
	
	public void examinar(){
		System.out.println("S\t"+text+" fin "+ending);
	}

}
