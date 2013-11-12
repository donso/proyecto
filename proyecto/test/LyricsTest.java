import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.modelcc.test.ModelAssert.assertValid;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

public class LyricsTest {

	@Test
	public void test() {
		List<Integer> l= new ArrayList<Integer>();
		l.add(5);
		l.add(4);
		assertFalse(l.isEmpty());
		//assertValid( //la acepta el parser
		//assertInvalid //no la acepta el parser
		//assertAmbiguityFree //la acepta pero solo con una interpretacion
		
		
		//fail("Not yet implemented");
	}
	
	@Test
	public void comprobarTest(){
		assertEquals(5,2+3);
		
	}
	

}
