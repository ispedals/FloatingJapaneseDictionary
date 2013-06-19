package pedals.is.floatingjapanesedictionary.deinflector;

public class DeInflectorRuleEntry {

	public final String from;
	public final String to;
	public final int type;
	public final int reason;

	public DeInflectorRuleEntry(String f, String t, int ty, int r) {

		from = f;
		to = t;
		type = ty;
		reason = r;
	}

}
