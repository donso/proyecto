import Lyrics.Lyrics;
import MusicPiece.MusicPiece;


public class Pruebas {
	

    public static void main (String[] args)
    {
        //Usar diccionarios para procesar la letra y obtener las sílabas?

    	String s = "TEN-go_UN trac-TOR a-ma-RI-llo.QUE_ES LO QUE SE LLE-va_a-HO-ra.";

        System.out.println("Frases originales:\n"+s);
        //Leer una letra
        Lyrics l =  new Lyrics(s);
        l.depurar();

        //Transformar Lyric a MusicPiece
        MusicPiece m = new MusicPiece(l);
        m.depurar();
    } 
}
