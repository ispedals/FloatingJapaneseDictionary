package pedals.is.floatingjapanesedictionary.dictionarysearcher;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DictionaryOpenHelper extends SQLiteOpenHelper {

	private static final int DATABASE_VERSION = 1;
	private static final String DICTIONARY_TABLE_NAME = "dict";

	public DictionaryOpenHelper(Context context) {

		super(context, DICTIONARY_TABLE_NAME, null, DATABASE_VERSION);
	}

	@Override
	public void onCreate(SQLiteDatabase db) {

		StringBuilder sb = new StringBuilder();
		sb.append("CREATE TABLE dict (kanji TEXT, kana TEXT, entry TEXT);");
		sb.append("INSERT INTO table VALUES('軍車','ぐんしゃ','(n) tank (military vehicle)');");
		sb.append("INSERT INTO table VALUES('他州','たしゅう','(n) another state/different region');");
		sb.append("INSERT INTO table VALUES('押しこむ','おしこむ','(v5m,vi,vt) to push into/to cram into/to stuff into/to crowd into/to break in/to burglarize');");
		sb.append("INSERT INTO table VALUES('米話','べいわ','(n) American spoken English');");
		sb.append("INSERT INTO table VALUES(NULL,'どーなつ','ドーナツ /(n) doughnut/(P)/');");
		sb.append("INSERT INTO table VALUES('大宮司','だいぐうじ','(n) high priest of a great shrine');");
		sb.append("INSERT INTO table VALUES('成書','せいしょ','(n) book');");
		sb.append("INSERT INTO table VALUES('ちきん南蛮','ちきんなんばん','チキン南蛮 [チキンなんばん] /(n) fried chicken with vinegar and tartar sauce/');");
		sb.append("INSERT INTO table VALUES('一定不変','いっていふへん','(n) invariable/permanent');");
		sb.append("INSERT INTO table VALUES('つむじ風','つむじかぜ','(n) whirlwind');");
		sb.append("INSERT INTO table VALUES('躁病','そうびょう','(n,adj-no) mania');");
		sb.append("INSERT INTO table VALUES(NULL,'おーしゃのーと','オーシャノート /(n) oceanaut/');");
		sb.append("INSERT INTO table VALUES(NULL,'ちゃんばら','(n,abbr) sword fight/sword play');");
		sb.append("INSERT INTO table VALUES(NULL,'たいむれこーだー','タイムレコーダー /(n) time clock/time recorder/');");
		sb.append("INSERT INTO table VALUES(NULL,'めがねべにはぜ','メガネベニハゼ /(n) redface dwarfgoby (Trimma benjamini, species found in the Western Pacific)/ring-eye pygmy-goby/');");
		sb.append("INSERT INTO table VALUES('炊立て','たきたて','(adj-no) freshly cooked');");
		sb.append("INSERT INTO table VALUES('食い掛け','くいかけ','(adj-no) half-eaten');");
		sb.append("INSERT INTO table VALUES('溢れる','こぼれる','(v1,vi,uk) to spill/to fall out of/to overflow/to peek through/to become visible (although normally not)/to escape (of a smile, tear, etc.)/(P)');");
		sb.append("INSERT INTO table VALUES('梯子の段','はしごのだん','(n) rung of a ladder');");
		sb.append("INSERT INTO table VALUES('相好','そうごう','(n) features/appearance');");
		sb.append("INSERT INTO table VALUES('閉会のことば','へいかいのことば','(exp) closing address');");
		sb.append("INSERT INTO table VALUES('不明','ふめい','(adj-na,n) unknown/obscure/indistinct/uncertain/ambiguous/ignorant/lack of wisdom/anonymous/unidentified/(P)');");
		sb.append("INSERT INTO table VALUES('謝謝','しえしえ','謝謝 [シエシエ] /(int) thank you/');");
		sb.append("INSERT INTO table VALUES('丸写し','まるうつし','(n,vs) copying in entirety (verbatim)');");
		sb.append("INSERT INTO table VALUES('下足場','げそくば','(n) area for storing (outdoor) footwear');");
		sb.append("INSERT INTO table VALUES(NULL,'こんしゃすねす','コンシャスネス /(n) consciousness/');");
		sb.append("INSERT INTO table VALUES('一転','いってん','(n-adv) turn/complete change');");
		sb.append("INSERT INTO table VALUES('言い返し','いいかえし','(n) replying/reply');");
		sb.append("INSERT INTO table VALUES('一人天下','ひとりてんか','(n) being the sole master of the situation/reigning supreme/standing unchallenged');");
		sb.append("INSERT INTO table VALUES(NULL,'もざんびーく','モザンビーク /(n) Mozambique/(P)/');");
		sb.append("INSERT INTO table VALUES('多様性','たようせい','(n) diversity/variety');");
		sb.append("INSERT INTO table VALUES('路用','ろよう','(n) travelling expenses/traveling expenses');");
		sb.append("INSERT INTO table VALUES('序詞','じょし','(n) foreword/preface/introduction/prefatory modifying statement (of a waka, etc.)');");
		sb.append("INSERT INTO table VALUES('かみ合せ','かみあわせ','(n) engaging or meshing (of gears)/occlusion (of teeth)');");
		sb.append("INSERT INTO table VALUES('雷名','らいめい','(n) fame/renown/great name');");
		sb.append("INSERT INTO table VALUES(NULL,'べるがもっと','ベルガモット /(n) bergamot (Citrus aurantium ssp. bergamia)/bergamot orange/bergamot (Monarda didyma)/bee balm/Oswego tea/');");
		sb.append("INSERT INTO table VALUES('鈎','はり','(n,ctr,n-suf) needle/pin/hook/stinger/thorn/hand (e.g. clock, etc.)/pointer/staple (for a stapler)/needlework/sewing/malice/counter for stitches/(P)');");
		sb.append("INSERT INTO table VALUES('詰物','つめもの','(n) filling or packing material/stuffing/padding');");
		sb.append("INSERT INTO table VALUES('気宇仙','きゅうせん','(n,uk) multicolorfin rainbowfish (Parajulis poecilepterus, was Halichoeres poecilopterus)');");
		sb.append("INSERT INTO table VALUES('火山灰','かざんばい','(n) volcanic ash');");
		sb.append("INSERT INTO table VALUES('鱝','えい','(n,uk,gikun,oK) ray (fish)/stingray');");
		sb.append("INSERT INTO table VALUES('引き据える','ひきすえる','(v1) to (physically) force someone to sit down');");
		sb.append("INSERT INTO table VALUES(NULL,'ぐりーくらぶ','グリークラブ /(n) glee club/(P)/');");
		sb.append("INSERT INTO table VALUES('捏ねくり回す','こねくりまわす','(v5s) to knead/to turn');");
		sb.append("INSERT INTO table VALUES('踞る','うずくまる','(v5r,vi,uk) to crouch/to squat/to cower/(P)');");
		sb.append("INSERT INTO table VALUES('嘴太海烏','はしぶとうみがらす','(n,uk) thick-billed murre (Uria lomvia)/Brunnich''s guillemot');");
		sb.append("INSERT INTO table VALUES('可変数','かへんすう','(n) variable number');");
		sb.append("INSERT INTO table VALUES(NULL,'ないとがうん','ナイトガウン /(n) nightgown/');");
		sb.append("INSERT INTO table VALUES('結婚祝い','けっこんいわい','(n) wedding present');");
		sb.append("INSERT INTO table VALUES('秋風落莫','しゅうふうらくばく','(n) forlorn and helpless/lonely and desolate');");
		sb.append("INSERT INTO table VALUES('群衆','ぐんじゅ','(n,vs) group (of people)/crowd/horde/throng/mob/multitude/(P)');");
		sb.append("INSERT INTO table VALUES('天津','あまつ','(adj-f,arch) heavenly/imperial');");
		sb.append("INSERT INTO table VALUES('老優','ろうゆう','(n) elderly or veteran actor');");
		sb.append("INSERT INTO table VALUES('動物ういるす','どうぶつういるす','動物ウイルス [どうぶつウイルス] /(n) animal virus/');");
		sb.append("INSERT INTO table VALUES('竈','かま','(n) stove/furnace/kiln');");
		sb.append("INSERT INTO table VALUES('似鯉','にごい','似鯉 [ニゴイ] /(n,uk) Hemibarbus barbus (species of cyprinid)/');");
		sb.append("INSERT INTO table VALUES(NULL,'おーあーるでーびー','オーアールデービー /(n) ORDB/');");
		sb.append("INSERT INTO table VALUES('銀鍍金','ぎんめっき','(n,vs,adj-no) silvering/silver-plating');");
		sb.append("INSERT INTO table VALUES('行燈','あんどう','(n,oK) fixed paper-enclosed lantern/paper-covered wooden stand housing an (oil) lamp');");
		sb.append("INSERT INTO table VALUES('竜の落し子','たつのおとしご','竜の落し子 [タツノオトシゴ] /(n) seahorse/sea horse/');");
		sb.append("INSERT INTO table VALUES('体位','たいい','(n,adj-no) physique/physical standard/posture/sexual position');");
		sb.append("INSERT INTO table VALUES('執','たらし','(n,arch) bow (and arrow; esp. of a noble)');");
		sb.append("INSERT INTO table VALUES('桂男','かつらお','(n,arch) man in the moon');");
		sb.append("INSERT INTO table VALUES('申込書','もうしこみしょ','(n) application form/written application/(P)');");
		sb.append("INSERT INTO table VALUES('食事をとる','しょくじをとる','(exp,v5r) to take a meal/to have a meal/to catch a meal/to get grub/to grub/to break bread/to chow down/to eat dinner/to have a bite/to strap on a feed-bag');");
		sb.append("INSERT INTO table VALUES(NULL,'らいぶらりけーす','ライブラリケース /(n) library case/');");
		sb.append("INSERT INTO table VALUES(NULL,'さんどばす','サンドバス /(n) sand bath/');");
		sb.append("INSERT INTO table VALUES('値段を高く付ける','ねだんをたかくつける','(exp,v1) to put a high price on');");
		sb.append("INSERT INTO table VALUES('書き難い','かきにくい','(adj-i) difficult to write or draw/does not write well (pen, brush, etc.)');");
		sb.append("INSERT INTO table VALUES(NULL,'えらーこーど','エラーコード /(n) error code/');");
		sb.append("INSERT INTO table VALUES('銘文','めいぶん','(n) inscription');");
		sb.append("INSERT INTO table VALUES('光学','こうがく','(n,adj-no) optics');");
		sb.append("INSERT INTO table VALUES('くーろんの法則','くーろんのほうそく','クーロンの法則 [クーロンのほうそく] /(n) Coulomb''s law/');");
		sb.append("INSERT INTO table VALUES('癒し','いやし','(n,adj-no) healing/soothing/therapy/comfort/solace');");
		sb.append("INSERT INTO table VALUES('先込め銃','さきごめじゅう','(n) muzzle loader');");
		sb.append("INSERT INTO table VALUES('軍備縮小','ぐんびしゅくしょう','(n) reduction of armaments/disarmament');");
		sb.append("INSERT INTO table VALUES('配電','はいでん','(n,vs) distribution of electricity');");
		sb.append("INSERT INTO table VALUES('悪手','あくしゅ','(n) poor move');");
		sb.append("INSERT INTO table VALUES('外股','そともも','(n) outer thigh');");
		sb.append("INSERT INTO table VALUES('汚染','おせん','(n,vs) pollution/contamination/(P)');");
		sb.append("INSERT INTO table VALUES('憧憬','しょうけい','(n,vs) longing/aspiration');");
		sb.append("INSERT INTO table VALUES('輔星','ほせい','(n,obsc) Alcor/the Little Horseman star');");
		sb.append("INSERT INTO table VALUES('食器洗い機','しょっきあらいき','(n) dishwasher/dishwashing machine');");
		sb.append("INSERT INTO table VALUES('耳が痛い','みみがいたい','(exp,adj-i) (e.g. of a reprimand) being painfully-true/striking home');");
		sb.append("INSERT INTO table VALUES('肝臓茸','かんぞうたけ','(n,uk) beefsteak fungus (Fistulina hepatica)/beefsteak mushroom');");
		sb.append("INSERT INTO table VALUES(NULL,'ふらっとぱねる','フラットパネル /(n) flat panel (monitor, e.g.)/');");
		sb.append("INSERT INTO table VALUES(NULL,'すたんだーどらいぶらり','スタンダードライブラリ /(n) standard library (programming)/');");
		sb.append("INSERT INTO table VALUES('許すまじ','ゆるすまじ','(exp) to be unforgivable/to never forgive something');");
		sb.append("INSERT INTO table VALUES('南方仏教','なんぽうぶっきょう','(n) Southern Buddhism (as practiced in Sri Lanka and Southeast Asia)');");
		sb.append("INSERT INTO table VALUES('南海偽角平虫','なんかいにせつのひらむし','南海偽角平虫 [ナンカイニセツノヒラムシ] /(n,uk) Persian carpet flatworm (Pseudobiceros bedfordi)/Bedford''s flatworm/');");
		sb.append("INSERT INTO table VALUES(NULL,'べんちぷれす','ベンチプレス /(n) bench press/');");
		sb.append("INSERT INTO table VALUES('外国人持ち株比率','がいこくじんもちかぶひりつ','(n) foreign stock ownership ratio/foreign stockholding ratio');");
		sb.append("INSERT INTO table VALUES('身元調査','みもとちょうさ','(n,col) identity or background check (often to see if someone is a burakumin)');");
		sb.append("INSERT INTO table VALUES(NULL,'おんざまーく','オンザマーク /(n) on the mark/');");
		sb.append("INSERT INTO table VALUES('原子力時代','げんしりょくじだい','(n) atomic age');");
		sb.append("INSERT INTO table VALUES(NULL,'そんな','(adj-pn) such (about the actions of the listener, or about ideas expressed or understood by the listener)/like that/that sort of/(P)');");
		sb.append("INSERT INTO table VALUES('恬','てん','(adj-t,adv-to) nonchalant');");
		sb.append("INSERT INTO table VALUES(NULL,'そーだふろーと','ソーダフロート /(n) soda float/');");
		sb.append("INSERT INTO table VALUES('保守ぱねる','ほしゅぱねる','保守パネル [ほしゅパネル] /(n) maintenance panel/');");
		sb.append("INSERT INTO table VALUES('帯電体','たいでんたい','(n) charged body');");
		db.execSQL(sb.toString());

	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {

	}

}
