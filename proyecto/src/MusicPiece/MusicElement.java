package MusicPiece;

public abstract class MusicElement {
	double duration = 0;
	
	public double getDuration(){
		return duration;
	}
	
	public void setDuration(double dur){
		duration = dur;
	}

	//TODO eliminar depurar
	public void depurar(){}

	public String getText() {
		// TODO Auto-generated method stub
		return null;
	}
}