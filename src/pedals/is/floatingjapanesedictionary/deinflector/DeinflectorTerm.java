package pedals.is.floatingjapanesedictionary.deinflector;

public class DeinflectorTerm {

	public String word;
	public int type;
	public String reason;

	public DeinflectorTerm() {

	}

	public DeinflectorTerm(String w, int t, String r) {

		word = w;
		type = t;
		reason = r;
	}

}
