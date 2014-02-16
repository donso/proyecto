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

    	String s = "TEN-go_un trac-TOR. SE LLE-va_a-HO-ra.";
    	    	
//        System.out.println("Frases originales:\n"+s);
        
		try {
			Model model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);

	        //Leer una letra
	        Lyrics l;
			l = parser.parse(s);
			System.err.println("DEPURANDO LYTICS");
	        l.depurar();

	        //Transformar Lyric a MusicPiece
	        MusicPiece m = new MusicPiece(l);
			System.err.println("DEPURANDO MUSICPIECE");
	        m.depurar();
	        
	        //Generar ritmo
		    MusicPieceWriter mpw = new MusicPieceWriter(m, 2);
			SyllableM s1 = new SyllableM("TEN", true);
			SyllableM s2 = new SyllableM("ra", false);

		    double importanciaProfundidad = 1, ajusteAlCompas = 1, relacionTonicasAtonas=1, importanciaMediaVarianza=1, mediaIdeal=2, varianzaIdeal=1, importanciaespacios = 1;
		    mpw.generateRhythm(s1, s2, importanciaProfundidad, ajusteAlCompas, relacionTonicasAtonas, importanciaMediaVarianza, mediaIdeal, varianzaIdeal,importanciaespacios);
//		    ver traza del programa y ajustar la heuristica
//		    regenerar con distinto ritmo
//			SyllableM s3 = new SyllableM("trac", false);
	//	    mpw.generateRhythm(s1, s3, importanciaProfundidad, ajusteAlCompas, relacionTonicasAtonas, importanciaMediaVarianza, mediaIdeal, varianzaIdeal,importanciaespacios);

		} catch (ParserException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    } 
}