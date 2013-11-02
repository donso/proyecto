import Lyrics.Lyrics;
import MusicPiece.MusicPiece;


public class main {
	

    public static void main (String [ ] args)
    {
        //Usar diccionarios para procesar la letra y obtener las sílabas?

        String s = "la CA-sa es a-ma-RI-lla.";

        //Leer una letra
        Lyrics l =  new Lyrics(s);
        System.out.println ("\nexaminar");
        l.examinar();

        //Transformar Lyric a MusicPiece
        //MusicPiece m = new MusicPiece(l);
    } 
}
