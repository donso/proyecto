import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.modelcc.io.java.JavaModelReader;
import org.modelcc.metamodel.Model;
import org.modelcc.parser.Parser;
import org.modelcc.parser.ParserFactory;

import Lyrics.Lyrics;

public class LyricsTest {

	@Test
	public void unaFraseTest() {
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se DE_e-JEM-plo.");
	        assertEquals(1, l.getPhrases().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void variasFrasesTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se.O-tra.");
	        assertEquals(2, l.getPhrases().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	

	@Test
	public void contarSilabasUnaFraseTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se DE_e-JEM-plo.");
	        assertEquals(8, l.getPhrases().get(0).getSyllables().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void contarSilabasVariasFrasesTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se DE_e-JEM-plo.		O-tra?");
	        assertEquals(8, l.getPhrases().get(0).getSyllables().size());
	        assertEquals(2, l.getPhrases().get(1).getSyllables().size());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	@Test
	public void fraseSinPuntoTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
		}
	}
	

	@Test
	public void phraseEndingTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na FRA-se. Ora?	  a-fd-qw-KO!");
	        assertEquals(".", String.valueOf(l.getPhrases().get(0).getEnding().getText()));
	        assertEquals("?", String.valueOf(l.getPhrases().get(1).getEnding().getText()));
	        assertEquals("!", String.valueOf(l.getPhrases().get(2).getEnding().getText()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Test
	public void syllableEndingTest(){
		Model model;
		try {
			model = JavaModelReader.read(Lyrics.class);
			Parser<Lyrics> parser = ParserFactory.create(model);
	        Lyrics l = parser.parse("U-na AR se.");
	        assertEquals("-", String.valueOf(l.getPhrases().get(0).getSyllables().get(0).getEnding().getText()));
	        assertEquals(" ", String.valueOf(l.getPhrases().get(0).getSyllables().get(1).getEnding().getText()));
	        assertEquals(" ", String.valueOf(l.getPhrases().get(0).getSyllables().get(2).getEnding().getText()));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}


//assertFalse(l.isEmpty());
//assertValid( //la acepta el parser
//assertInvalid //no la acepta el parser
//assertAmbiguityFree //la acepta pero solo con una interpretacion

//fail("Not yet implemented");

