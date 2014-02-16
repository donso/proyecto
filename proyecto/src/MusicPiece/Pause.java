package MusicPiece;

public class Pause extends MusicElement{

	char ending;
	
	public Pause(char end){
		ending = end;
	}
	
	public Pause(Pause ele) {
		// TODO Auto-generated constructor stub
		duration = ele.duration;
	}

	public String getText(){
		return "<pausa>";
	}

	public char getEnding(){
		return ending;
	}

	//TODO eliminar depurar
	public void depurar(){
		System.err.print("<pausa>"+duration+" ");
	}
}