package MusicPiece;

public class SyllableM extends MusicElement{
	int note;
	String text;
	boolean tonic;
	
	public SyllableM(String texto, boolean tonica){
		text = texto;
		tonic = tonica;
	}
	
	public boolean getTonic(){
		return tonic;
	}
	
	public String getText(){
		return text;
	}

    public boolean equals(Object obj) {
        if (obj instanceof SyllableM)
            return text.equals(((SyllableM)obj).getText()) && tonic==((SyllableM) obj).getTonic();
        else
            return false;
    }
    
	//TODO eliminar depurar
	public void depurar(){
//		System.err.println(text+" \t"+tonic);
		System.err.print(text+" ");
	}
}
