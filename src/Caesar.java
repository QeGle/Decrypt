import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Caesar {
	private static int key;


	/**
	 * Возвращает ключ, с помощью которого был зашифрован текст.
	 *
	 * @return ключ.
	 */
	static int getKey() {
		return key;
	}

	public static void main(String[] args) {
		System.out.println(Utils.transform("АААAAAzzzяяя"));
		System.out.print(encrypt("АААAAA", -2));
		System.out.println(encrypt("zzzяяя", 2));
		System.out.print(decrypt("ЮЮЮYYY", -2));
		System.out.println(decrypt("BBBБББ", 2));
		System.out.println(hack("РЬАЬА ШЮЙАП ОЩОСО ЫЕЦШТ ЩНРУЯ УЩЙГЦ ЯЩОРЫ ЙГТУА УЧЯЪЬ АЮНАТ УРЬЕШ ОЦЪОЩ\n" +
				"КЕЦШЫ ОТОЪШ ЬЮЬЩУ ЧЦЕУЮ АУЧЦХ РБЕЦА ЛАООТ ЯШОНЪ БХЙШО ХОРЙР ОУАБЫ ЙЩЙЧЯ\n" +
				"ЪЙЕЬШ ЯАЮОЖ ЫЙЧЕУ ЮАБГР ОАЦЩШ ОЮОЭБ ХЦШОЦ ЯАУШО УАШЩМ ШРУЫЫ ЙЧЯЬШ ЪОЩКЕ\n" +
				"ЦШЬЫЯ ЭОЯУА ЯНЬАЕ УЮЫЬС ЬСЫУР ОЪОЫЬ РУЫЦУ ЪПУЩЬ ЧЮБШЦ ЭЬЯЪЬ АЮЦЬС ЬЫКШЦ\n" +
				"ЭЮЦПЩ ЦФОМА ЯНЯЩУ РОРЦТ ЦЖКВО ШУЩЙР ЦТЦЖК ТЙЪШЦ ЛАЬРУ ЮЫЬЯО ЪОШЬЮ ЬЩУРО\n" +
				"ТУРЬЕ ШООГЫ УАХОЕ УЪАЙТ ЮОХЫЦ ЖКЪУЫ НЛАЬО ТЯШОН ЯРЦАО ШЬЮЬЩ УРОАО ГЬТЦА\n" +
				"ЯЮУТК ПУЩЬС ЬТЫНР ЯНСЦЮ ЩНЫТО ЪЦЮЬХ ЭУЮУР ЦАОЦЖ ЩУЧВУ УЫЬЯЦ АЪУЕО ЪЦХРУ\n" +
				"ЫНРХТ ЙГОМЗ ЦГЮЙД ОЮУЧЯ РЦАОР ТЮБСЭ ОНДЭУ ЮУСЫБ ЩЯНХО ЮОЪЭБ ЦШЮЦЕ ЦАЭЬЪ\n" +
				"ЬСЦАУ ЦЯАУШ ОМНШЩ МШРУЫ ЫЙЪЯЬ ШЬЪХО ПЦЫАЬ РОЫАЮ НЭЦДУ ЧЫОСЬ ЩЬРУЪ ЬУЧШО\n" +
				"ЮАЬЫЫ ЙЧЖЩУ ЪОРЮБ ШУТУЮ УРНЫЫ ЙЧЪУЕ ХОЭЩО ШОЩЦТ УРЬЕШ ОЦЪОЩ КЕЦШЦ ХОШЮЙ\n" +
				"ЩЯНРУ ЯУЩЙЧ ПОЩОС ОЫЕЦШ"));
	}

	/**
	 * Возвращает расшифрованное сообщение.
	 *
	 * @param msg сообщение для расшифровки.
	 * @param key ключ для шифрования.
	 * @return расшифрованное сообщение.
	 */
	static String decrypt(String msg, int key) {
		return encrypt(msg, -key);
	}

	/**
	 * Возвращает зашифрованное сообщение.
	 *
	 * @param msg сообщение для зашифровки.
	 * @param key ключ для шифрования.
	 * @return зашифрованное сообщение.
	 */
	static String encrypt(String msg, int key) {
		StringBuilder M; //сообщение
		StringBuilder C = new StringBuilder(""); //зашифрованное сообщение
		M = Utils.transform(msg);
		for (int i = 0; i < M.length(); i++) {
			C.append(cryptCH(M.charAt(i), key));
		}
		for (int i = 6; i < C.length(); i = i + 7) {
			C.insert(i, " ");
		}
		if (C.length() > 49)
			for (int i = 49; i < C.length(); i = i + 50) {
				C.insert(i, "\n");
			}

		return C.toString();
	}

	/**
	 * Возвращает взломанное сообщение.
	 *
	 * @param msg сообщение для взлома.
	 * @return взломанное сообщение.
	 */
	static String hack(String msg) {
		msg = Utils.transform(msg).toString();

		Map<Integer, Double> result = new HashMap<>();
		for (int j = 0; j < 32; j++) {
			Map<Character, Double> count = new HashMap<>();
			double res = 0;
			for (int i = 0; i < msg.length(); i++) {
				count.put(msg.charAt(i), (double) 0);
			}

			for (int i = 0; i < msg.length(); i++) {
				Double value;
				value = count.get(msg.charAt(i));
				count.put(msg.charAt(i), value + 1 / (double) msg.length());
			}

			for (Character alphabetRu : Utils.alphabetRus) {
				double inText;
				if (count.containsKey(alphabetRu)) {
					inText = count.get(alphabetRu);
				} else
					continue;
				double inTable;
				if (Utils.tableFrequency.containsKey(alphabetRu.toString()))
					inTable = Utils.tableFrequency.get(alphabetRu.toString());
				else
					continue;
				res += (inText - inTable) * (inText - inTable);
			}
			result.put(j, res);
			msg = encrypt(msg, 1);
		}


		while (result.size() != 1) {
			int i = Utils.keyWithMaxValue(result);
			result.remove(i);
		}

		//результат остается один. Вытаскиваем ключ.
		key = Utils.key(result);
		return encrypt(msg, key);
	}

	/**
	 * Возвращает зашифрованный символ.
	 *
	 * @param c символ для шифрования.
	 * @param m ключ для фшфрования символа
	 * @return зашифрованный символ.
	 */
	private static char cryptCH(char c, int m) {
		int key = 0;
		List<Character> alphabet = new ArrayList<>('1');
		if (Utils.isRussian(c)) {
			key = m % Utils.alphabetRus.size();
			alphabet = Utils.alphabetRus;

		} else if (Utils.isEnglish(c)) {
			key = m % Utils.alphabetEng.size();
			alphabet = Utils.alphabetEng;
		}

		if (alphabet.indexOf(c) + key > alphabet.size() - 1)
			c = alphabet.get(alphabet.indexOf(c) + key - alphabet.size());
		else if (alphabet.indexOf(c) + key < 0)
			c = alphabet.get(alphabet.indexOf(c) + key + alphabet.size());
		else
			c = alphabet.get(alphabet.indexOf(c) + key);

		return c;
	}

}
