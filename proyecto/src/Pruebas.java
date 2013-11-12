import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ParserFactory;

import Lyrics.Lyrics;
import MusicPiece.MusicPiece;


public class Pruebas {
	

    public static void main (String[] args)
    {
        //Usar diccionarios para procesar la letra y obtener las sílabas?

    	String s = "TEN-go_UN trac-TOR a-ma-RI-llo.QUE_ES LO QUE SE LLE-va_a-HO-ra.";
    	s = "A.";
        System.out.println("Frases originales:\n"+s);
        
		try {
			Model model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
        
	        //Leer una letra
	        Lyrics l;
			l = parser.parse(s);
	        l.depurar();
	        MusicPiece m = new MusicPiece(l);
	        m.depurar();
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

        //Transformar Lyric a MusicPiece
    } 
}
