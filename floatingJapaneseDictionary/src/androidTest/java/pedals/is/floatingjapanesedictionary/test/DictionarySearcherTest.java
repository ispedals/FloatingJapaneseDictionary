package pedals.is.floatingjapanesedictionary.test;

import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntries;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionaryEntry;
import pedals.is.floatingjapanesedictionary.dictionarysearcher.DictionarySearcher;
import android.test.AndroidTestCase;

public class DictionarySearcherTest extends AndroidTestCase {

	public DictionarySearcherTest() {
		super();
	}
	
	public final void testFindKanjiWord() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "軍車");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("軍車"));
		assertTrue(entry.toString().contains("ぐんしゃ"));
	}
	
	public final void testFindKanjiWordwithKana() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "ぐんしゃ");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("軍車"));
		assertTrue(entry.toString().contains("ぐんしゃ"));
	}
	
	public final void testFindHiraganaWord() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "ちゃんばら");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("ちゃんばら"));
	}
	
	public final void testkatakanaToHiragana() {
		assertTrue(DictionarySearcher.katakanaToHiragana("ナツ").equals("なつ"));
	}
	
	public final void testkatakanaToHiraganaWithLongVowel() {
		assertTrue(DictionarySearcher.katakanaToHiragana("ドーナツ").equals("どーなつ"));
	}
	
	public final void testkatakanaToHiraganaWithNoKatakkana() {
		assertTrue(DictionarySearcher.katakanaToHiragana("軍車").equals("軍車"));
		assertTrue(DictionarySearcher.katakanaToHiragana("ぐんしゃ").equals("ぐんしゃ"));
		assertTrue(DictionarySearcher.katakanaToHiragana("ぐんしゃー").equals("ぐんしゃー"));
	}
	
	public final void testFindKatakanaWord() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "ドーナツ");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().contains("ドーナツ"));
	}
	
	public final void testFindKanjiInflectedWord() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "踞った");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("踞る"));
		assertTrue(entry.toString().contains("(past)"));
	}
	
	public final void testFindKanaInflectedWord() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "うずくまった");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("踞る"));
		assertTrue(entry.toString().contains("(past)"));
	}
	
	public final void testFindWordwithTrailingCharacters() {
		DictionaryEntries entries = DictionarySearcher.findWord(getContext(), "踞るドーナツ");
		assertFalse(entries.isEmpty());
		DictionaryEntry entry = entries.get(0);
		assertTrue(entry.toString().startsWith("踞る"));
	}
	
	public final void testIsHiragna() {
			assertTrue(DictionarySearcher.isHiragana("ぐんしゃ"));
			assertTrue(DictionarySearcher.isHiragana("ぐんしゃー"));
			assertTrue(DictionarySearcher.isHiragana("うずくまった"));
			assertFalse(DictionarySearcher.isHiragana("踞る"));
			assertFalse(DictionarySearcher.isHiragana("ドーナツ"));
	}

}
