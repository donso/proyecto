import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserException;
import org.modelcc.parser.ParserFactory;

import Lyrics.Lyrics;
import MusicPiece.MusicPiece;
import MusicPiece.SyllableM;
import MusicPieceWriter.MusicPieceWriter;


public class Pruebas {
	

    public static void main (String[] args)
    {
        //Usar diccionarios para procesar la letra y obtener las sílabas?

    	String s = "TEN-go_UN trac-TOR a-ma-RI-llo.";
    	
        System.out.println("Frases originales:\n"+s);
        
		try {
			Model model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);

	        //Leer una letra
	        Lyrics l;
			l = parser.parse(s);
	       // l.depurar();

	        //Transformar Lyric a MusicPiece
	        MusicPiece m = new MusicPiece(l);
//	        m.depurar();
	        
	        //Generar ritmo
		    MusicPieceWriter mpw = new MusicPieceWriter(m, 2);
			SyllableM s1 = new SyllableM("TEN", true);
			SyllableM s2 = new SyllableM("llo", false);

		    mpw.generateRhythm(s1, s2);
//		    regenerar con distinto ritmo
		    mpw.generateRhythm(s1, s2);
		    
		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    } 
}
