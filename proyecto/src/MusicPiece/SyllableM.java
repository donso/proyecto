package MusicPiece;

public class SyllableM extends MusicElement{
	int note;
	String text;
	boolean tonica;
	
	SyllableM(String texto, boolean tonic){
		text = texto;
		tonica = tonic;
	}
	
	//TODO eliminar depurar
	void depurar(){
		System.out.println(text+" \t"+tonica);
	}
}
