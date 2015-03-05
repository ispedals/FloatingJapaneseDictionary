package pedals.is.floatingjapanesedictionary.test;

import java.util.ArrayList;

import pedals.is.floatingjapanesedictionary.deinflector.DeInflector;
import pedals.is.floatingjapanesedictionary.deinflector.DeinflectorTerm;
import junit.framework.TestCase;

public class DeinflectorTest extends TestCase {


	/**
	 * @param name
	 */
	public DeinflectorTest(String name) {
		super(name);
	}
	
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
	    super.setUp();
	 }

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
	    super.tearDown();
	}

	/**
	 * Test something
	 */
	public final void testDeinflect() {
		ArrayList<DeinflectorTerm> words = DeInflector.deInflect("来ます");        
		assertTrue("Kanji deinflected", words.get(0).word.equals("来る"));
		words = DeInflector.deInflect("きます");
		assertTrue("Hiragana deinflected", words.get(0).word.equals("くる"));
	}

}
