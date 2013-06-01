package pedals.is.floatingjapanesedictionary;

import java.util.ArrayList;
import java.util.HashMap;

public class DeInflector {
	
	private static final ArrayList<String> reasons = new ArrayList<String>();
	private static final ArrayList<DeInflectorRuleGroup> rules = new ArrayList<DeInflectorRuleGroup>();
	
	static {
		reasons.add("polite past negative");
		reasons.add("polite negative");
		reasons.add("polite volitional");
		reasons.add("-chau");
		reasons.add("-sugiru");
		reasons.add("-nasai");
		reasons.add("polite past");
		reasons.add("-tara");
		reasons.add("-tari");
		reasons.add("causative");
		reasons.add("potential or passive");
		reasons.add("-sou");
		reasons.add("-tai");
		reasons.add("polite");
		reasons.add("past");
		reasons.add("negative");
		reasons.add("passive");
		reasons.add("-ba");
		reasons.add("volitional");
		reasons.add("potential");
		reasons.add("passive or causative");
		reasons.add("-te");
		reasons.add("-zu");
		reasons.add("imperative");
		reasons.add("masu stem");
		reasons.add("adv");
		reasons.add("noun");
		reasons.add("imperative negative");
		
		String stringRules = 	"くありませんでした	い	1152	0\n" +
		"いませんでした	う	640	0\n" +
		"きませんでした	く	640	0\n" +
		"きませんでした	くる	2176	0\n" +
		"ぎませんでした	ぐ	640	0\n" +
		"しませんでした	す	640	0\n" +
		"しませんでした	する	4224	0\n" +
		"ちませんでした	つ	640	0\n" +
		"にませんでした	ぬ	640	0\n" +
		"びませんでした	ぶ	640	0\n" +
		"みませんでした	む	640	0\n" +
		"りませんでした	る	640	0\n" +
		"くありません	い	1152	1\n" +
		"ませんでした	る	2432	0\n" +
		"いましょう	う	640	2\n" +
		"きましょう	く	640	2\n" +
		"きましょう	くる	2176	2\n" +
		"ぎましょう	ぐ	640	2\n" +
		"しましょう	す	640	2\n" +
		"しましょう	する	4224	2\n" +
		"ちましょう	つ	640	2\n" +
		"にましょう	ぬ	640	2\n" +
		"びましょう	ぶ	640	2\n" +
		"みましょう	む	640	2\n" +
		"りましょう	る	640	2\n" +
		"いじゃう	ぐ	514	3\n" +
		"いすぎる	う	513	4\n" +
		"いちゃう	く	514	3\n" +
		"いなさい	う	640	5\n" +
		"いました	う	640	6\n" +
		"いません	う	640	1\n" +
		"かったら	い	1152	7\n" +
		"かったり	い	1152	8\n" +
		"きすぎる	く	513	4\n" +
		"きすぎる	くる	2049	4\n" +
		"ぎすぎる	ぐ	513	4\n" +
		"きちゃう	くる	2050	3\n" +
		"きなさい	く	640	5\n" +
		"きなさい	くる	2176	5\n" +
		"ぎなさい	ぐ	640	5\n" +
		"きました	く	640	6\n" +
		"きました	くる	2176	6\n" +
		"ぎました	ぐ	640	6\n" +
		"きません	く	640	1\n" +
		"きません	くる	2176	1\n" +
		"ぎません	ぐ	640	1\n" +
		"こさせる	くる	2049	9\n" +
		"こられる	くる	2049	10\n" +
		"しすぎる	す	513	4\n" +
		"しすぎる	する	4097	4\n" +
		"しちゃう	す	514	3\n" +
		"しちゃう	する	4098	3\n" +
		"しなさい	す	640	5\n" +
		"しなさい	する	4224	5\n" +
		"しました	す	640	6\n" +
		"しました	する	4224	6\n" +
		"しません	す	640	1\n" +
		"しません	する	4224	1\n" +
		"ちすぎる	つ	513	4\n" +
		"ちなさい	つ	640	5\n" +
		"ちました	つ	640	6\n" +
		"ちません	つ	640	1\n" +
		"っちゃう	う	514	3\n" +
		"っちゃう	く	514	3\n" +
		"っちゃう	つ	514	3\n" +
		"っちゃう	る	514	3\n" +
		"にすぎる	ぬ	513	4\n" +
		"になさい	ぬ	640	5\n" +
		"にました	ぬ	640	6\n" +
		"にません	ぬ	640	1\n" +
		"びすぎる	ぶ	513	4\n" +
		"びなさい	ぶ	640	5\n" +
		"びました	ぶ	640	6\n" +
		"びません	ぶ	640	1\n" +
		"ましょう	る	2432	2\n" +
		"みすぎる	む	513	4\n" +
		"みなさい	む	640	5\n" +
		"みました	む	640	6\n" +
		"みません	む	640	1\n" +
		"りすぎる	る	513	4\n" +
		"りなさい	る	640	5\n" +
		"りました	る	640	6\n" +
		"りません	る	640	1\n" +
		"んじゃう	ぬ	514	3\n" +
		"んじゃう	ぶ	514	3\n" +
		"んじゃう	む	514	3\n" +
		"いそう	う	640	11\n" +
		"いたい	う	516	12\n" +
		"いたら	く	640	7\n" +
		"いだら	ぐ	640	7\n" +
		"いたり	く	640	8\n" +
		"いだり	ぐ	640	8\n" +
		"います	う	640	13\n" +
		"かせる	く	513	9\n" +
		"がせる	ぐ	513	9\n" +
		"かった	い	1152	14\n" +
		"かない	く	516	15\n" +
		"がない	ぐ	516	15\n" +
		"かれる	く	513	16\n" +
		"がれる	ぐ	513	16\n" +
		"きそう	く	640	11\n" +
		"きそう	くる	2176	11\n" +
		"ぎそう	ぐ	640	11\n" +
		"きたい	く	516	12\n" +
		"きたい	くる	2052	12\n" +
		"ぎたい	ぐ	516	12\n" +
		"きたら	くる	2176	7\n" +
		"きたり	くる	2176	8\n" +
		"きます	く	640	13\n" +
		"きます	くる	2176	13\n" +
		"ぎます	ぐ	640	13\n" +
		"くない	い	1028	15\n" +
		"ければ	い	1152	17\n" +
		"こない	くる	2052	15\n" +
		"こよう	くる	2176	18\n" +
		"これる	くる	2049	19\n" +
		"させる	する	4097	9\n" +
		"させる	る	2305	9\n" +
		"さない	す	516	15\n" +
		"される	す	513	20\n" +
		"される	する	4097	16\n" +
		"しそう	す	640	11\n" +
		"しそう	する	4224	11\n" +
		"したい	す	516	12\n" +
		"したい	する	4100	12\n" +
		"したら	す	640	7\n" +
		"したら	する	4224	7\n" +
		"したり	す	640	8\n" +
		"したり	する	4224	8\n" +
		"しない	する	4100	15\n" +
		"します	す	640	13\n" +
		"します	する	4224	13\n" +
		"しよう	する	4224	18\n" +
		"すぎる	い	1025	4\n" +
		"すぎる	る	2305	4\n" +
		"たせる	つ	513	9\n" +
		"たない	つ	516	15\n" +
		"たれる	つ	513	16\n" +
		"ちそう	つ	640	11\n" +
		"ちたい	つ	516	12\n" +
		"ちます	つ	640	13\n" +
		"ちゃう	る	2306	3\n" +
		"ったら	う	640	7\n" +
		"ったら	つ	640	7\n" +
		"ったら	る	640	7\n" +
		"ったり	う	640	8\n" +
		"ったり	つ	640	8\n" +
		"ったり	る	640	8\n" +
		"なさい	る	2432	5\n" +
		"なせる	ぬ	513	9\n" +
		"なない	ぬ	516	15\n" +
		"なれる	ぬ	513	16\n" +
		"にそう	ぬ	640	11\n" +
		"にたい	ぬ	516	12\n" +
		"にます	ぬ	640	13\n" +
		"ばせる	ぶ	513	9\n" +
		"ばない	ぶ	516	15\n" +
		"ばれる	ぶ	513	16\n" +
		"びそう	ぶ	640	11\n" +
		"びたい	ぶ	516	12\n" +
		"びます	ぶ	640	13\n" +
		"ました	る	2432	6\n" +
		"ませる	む	513	9\n" +
		"ません	る	2432	1\n" +
		"まない	む	516	15\n" +
		"まれる	む	513	16\n" +
		"みそう	む	640	11\n" +
		"みたい	む	516	12\n" +
		"みます	む	640	13\n" +
		"らせる	る	513	9\n" +
		"らない	る	516	15\n" +
		"られる	る	2817	10\n" +
		"りそう	る	640	11\n" +
		"りたい	る	516	12\n" +
		"ります	る	640	13\n" +
		"わせる	う	513	9\n" +
		"わない	う	516	15\n" +
		"われる	う	513	16\n" +
		"んだら	ぬ	640	7\n" +
		"んだら	ぶ	640	7\n" +
		"んだら	む	640	7\n" +
		"んだり	ぬ	640	8\n" +
		"んだり	ぶ	640	8\n" +
		"んだり	む	640	8\n" +
		"いた	く	640	14\n" +
		"いだ	ぐ	640	14\n" +
		"いて	く	640	21\n" +
		"いで	ぐ	640	21\n" +
		"えば	う	640	17\n" +
		"える	う	513	19\n" +
		"おう	う	640	18\n" +
		"かず	く	640	22\n" +
		"がず	ぐ	640	22\n" +
		"きた	くる	2176	14\n" +
		"きて	くる	2176	21\n" +
		"くて	い	1152	21\n" +
		"けば	く	640	17\n" +
		"げば	ぐ	640	17\n" +
		"ける	く	513	19\n" +
		"げる	ぐ	513	19\n" +
		"こい	くる	2176	23\n" +
		"こう	く	640	18\n" +
		"ごう	ぐ	640	18\n" +
		"こず	くる	2176	22\n" +
		"さず	す	640	22\n" +
		"した	す	640	14\n" +
		"した	する	4224	14\n" +
		"して	す	640	21\n" +
		"して	する	4224	21\n" +
		"しろ	する	4224	23\n" +
		"せず	する	4224	22\n" +
		"せば	す	640	17\n" +
		"せよ	する	4224	23\n" +
		"せる	す	513	19\n" +
		"そう	い	1152	11\n" +
		"そう	す	640	18\n" +
		"そう	る	2432	11\n" +
		"たい	る	2308	12\n" +
		"たず	つ	640	22\n" +
		"たら	る	2432	7\n" +
		"たり	る	2432	8\n" +
		"った	う	640	14\n" +
		"った	く	640	14\n" +
		"った	つ	640	14\n" +
		"った	る	640	14\n" +
		"って	う	640	21\n" +
		"って	く	640	21\n" +
		"って	つ	640	21\n" +
		"って	る	640	21\n" +
		"てば	つ	640	17\n" +
		"てる	つ	513	19\n" +
		"とう	つ	640	18\n" +
		"ない	る	2308	15\n" +
		"なず	ぬ	640	22\n" +
		"ねば	ぬ	640	17\n" +
		"ねる	ぬ	513	19\n" +
		"のう	ぬ	640	18\n" +
		"ばず	ぶ	640	22\n" +
		"べば	ぶ	640	17\n" +
		"べる	ぶ	513	19\n" +
		"ぼう	ぶ	640	18\n" +
		"ます	る	2432	13\n" +
		"まず	む	640	22\n" +
		"めば	む	640	17\n" +
		"める	む	513	19\n" +
		"もう	む	640	18\n" +
		"よう	る	2432	18\n" +
		"らず	る	640	22\n" +
		"れば	る	7040	17\n" +
		"れる	る	2817	19\n" +
		"ろう	る	640	18\n" +
		"わず	う	640	22\n" +
		"んだ	ぬ	640	14\n" +
		"んだ	ぶ	640	14\n" +
		"んだ	む	640	14\n" +
		"んで	ぬ	640	21\n" +
		"んで	ぶ	640	21\n" +
		"んで	む	640	21\n" +
		"い	いる	384	24\n" +
		"い	う	640	24\n" +
		"い	る	2176	23\n" +
		"え	う	640	23\n" +
		"え	える	384	24\n" +
		"き	きる	384	24\n" +
		"き	く	640	24\n" +
		"ぎ	ぎる	384	24\n" +
		"ぎ	ぐ	640	24\n" +
		"く	い	1152	25\n" +
		"け	く	640	23\n" +
		"け	ける	384	24\n" +
		"げ	ぐ	640	23\n" +
		"げ	げる	384	24\n" +
		"さ	い	1152	26\n" +
		"し	す	640	24\n" +
		"じ	じる	384	24\n" +
		"ず	る	2432	22\n" +
		"せ	す	640	23\n" +
		"せ	せる	384	24\n" +
		"ぜ	ぜる	384	24\n" +
		"た	る	2432	14\n" +
		"ち	ちる	384	24\n" +
		"ち	つ	640	24\n" +
		"て	つ	640	23\n" +
		"て	てる	384	24\n" +
		"て	る	2432	21\n" +
		"で	でる	384	24\n" +
		"な		7040	27\n" +
		"に	にる	384	24\n" +
		"に	ぬ	640	24\n" +
		"ね	ぬ	640	23\n" +
		"ね	ねる	384	24\n" +
		"ひ	ひる	384	24\n" +
		"び	びる	384	24\n" +
		"び	ぶ	640	24\n" +
		"へ	へる	384	24\n" +
		"べ	ぶ	640	23\n" +
		"べ	べる	384	24\n" +
		"み	みる	384	24\n" +
		"み	む	640	24\n" +
		"め	む	640	23\n" +
		"め	める	384	24\n" +
		"よ	る	384	23\n" +
		"り	りる	384	24\n" +
		"り	る	640	24\n" +
		"れ	る	640	23\n" +
		"れ	れる	384	24\n" +
		"ろ	る	384	23\n";
		
		DeInflectorRuleGroup ruleGroup = new DeInflectorRuleGroup();
		ruleGroup.fromLen = -1;
		
		for(String line: stringRules.split("\n")) {
			String[] lineFields = line.split("\t");
			DeInflectorRuleEntry entry = new DeInflectorRuleEntry(lineFields[0], lineFields[1],  Integer.parseInt(lineFields[2]),  Integer.parseInt(lineFields[3]));
			if (ruleGroup.fromLen != entry.from.length()) {
				ruleGroup = new DeInflectorRuleGroup();
				ruleGroup.fromLen = entry.from.length();
				rules.add(ruleGroup);
			}
			ruleGroup.add(entry);
		}
	}
	
	public static ArrayList<DeinflectorTerm> deInflect(final String inflectedWord){
		final HashMap<String, Integer> have = new HashMap<String, Integer>();
		have.put(inflectedWord, 0);
	
		final ArrayList<DeinflectorTerm> r = new ArrayList<DeinflectorTerm>();
		r.add(new DeinflectorTerm(inflectedWord, 0xFF, ""));
		
		int i = 0;
		do {
			
			final String word = r.get(i).word;
			final int wordLen = word.length();
			final int type = r.get(i).type;
	
			for (int j = 0; j < rules.size(); ++j) {
				final DeInflectorRuleGroup ruleGroup = rules.get(j);
				
				if (ruleGroup.fromLen <= wordLen) {
					final String end = word.substring(word.length()-ruleGroup.fromLen);
					
					for (int k = 0; k < ruleGroup.size(); ++k) {
						final DeInflectorRuleEntry rule = ruleGroup.get(k);
						
						if (((type & rule.type) > 0) && (end.equals(rule.from))) {
							final String newWord = word.substring(0, word.length() - rule.from.length()) + rule.to;
							
							if (newWord.length() <= 1) {
								continue;
							}
							
							DeinflectorTerm o = new DeinflectorTerm();
							
							if (have.containsKey(newWord)) {
								o = r.get(have.get(newWord));
								o.type |= (rule.type >> 8);
								continue;
							}
							
							have.put(newWord, r.size());
							
							if (r.get(i).reason.length() > 0){
								o.reason = reasons.get(rule.reason) + "&" + r.get(i).reason;
							}
							else{
								o.reason = reasons.get(rule.reason);
							}
							
							o.type = rule.type >> 8;
							o.word = newWord;
							r.add(o);
						}
					}
				}
			}
		} while (++i < r.size());
	
		return r;
	}

}
