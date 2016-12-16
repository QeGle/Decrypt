import java.util.*;

class Vigenere {

	private static String keyString;

	/**
	 * Возвращает ключ, с помощью которого был зашифрован текст.
	 *
	 * @return ключ.
	 */
	static String getKeyString() {
		return keyString;
	}

	/**
	 * Устанавливает ключ, с помощью которого был зашифрован текст.
	 *
	 * @param message   часть зашифрованного сообщения.
	 * @param deMessage часть дешифрованного сообщения.
	 * @param key       длина ключа.
	 */
	private static void setKeyString(String message, String deMessage, int key) {
		if (message.length() > key || deMessage.length() > key) {
			message = message.substring(0, key);
			System.out.println(message);
			deMessage = deMessage.substring(0, key);
			System.out.println(deMessage);
		}
		StringBuilder keyString = new StringBuilder();
		int ch;
		for (int i = 0; i < key; i++) {
			ch = Utils.alphabetRus.indexOf(message.charAt(i)) - Utils.alphabetRus.indexOf(deMessage.charAt(i));
			if (ch < 0)
				ch = Utils.alphabetRus.size() + ch;
			keyString.append(Utils.alphabetRus.get(ch));
		}
		System.out.println(keyString);
		Vigenere.keyString = keyString.toString();
	}

	public static void main(String[] args) {
		System.out.println(Vigenere.hack(
				"йуцкйцукйцукйцукйцукйцукцу"));
	}

	/**
	 * Возвращает зашифрованное сообщение.
	 *
	 * @param msg сообщение для зашифровки.
	 * @param key ключ для шифрования.
	 * @return зашифрованное сообщение.
	 */
	static String encrypt(String msg, String key) {
		StringBuilder M; //сообщение
		StringBuilder C = new StringBuilder(""); //зашифрованное сообщение
		StringBuilder keySB = new StringBuilder(key.toUpperCase());
		M = Utils.transform(msg);

		//подгоняем длину ключа к длине сообщения
		while (keySB.length() < M.length()) {
			keySB.append(key.toUpperCase());
		}
		if (keySB.length() > M.length())
			keySB.delete(M.length(), keySB.length());

		for (int i = 0; i < M.length(); i++) {
			int numMsg = Utils.alphabetRus.indexOf(M.charAt(i));
			int numKey = Utils.alphabetRus.indexOf(keySB.charAt(i));

			//на случай, если буквы не русского алфавита
			//просочиться смогут
			if (numKey < 0)
				numKey = 0;
			int sum = (numKey + numMsg) % Utils.alphabetRus.size();
			C.append(Utils.alphabetRus.get(sum));
		}
		return Caesar.encrypt(C.toString(), 0);
	}

	/**
	 * Возвращает расшифрованное сообщение.
	 *
	 * @param msg сообщение для расшифровки.
	 * @param key ключ для шифрования.
	 * @return расшифрованное сообщение.
	 */
	static String decrypt(String msg, String key) {
		StringBuilder keySB = new StringBuilder("");
		for (int i = 0; i < key.length(); i++) {
			char ch = Utils.alphabetRus.get
					(32 - Utils.alphabetRus.indexOf
							(key.toUpperCase().charAt(i)));
			keySB.append(ch);
		}
		return encrypt(msg, keySB.toString());

	}

	/**
	 * Возвращает взломанное сообщение.
	 *
	 * @param msg сообщение для взлома.
	 * @return взломанное сообщение.
	 */
	static String hack(String msg) {

		StringBuilder message = new StringBuilder(Utils.transform(msg));
		StringBuilder deMessage = new StringBuilder();

		int symbolsInGroups = 3;
		int numberOfGroups = message.length() / (symbolsInGroups * 3);

		Utils.dividersAll(getDistanceBetweenGroups(message, symbolsInGroups, numberOfGroups));
		System.out.println(Utils.dividersFrequency);
		Utils.dividersAll(getDistanceBetweenGroups(message, symbolsInGroups, numberOfGroups));
		System.out.println(Utils.dividersFrequency);
		Utils.dividersAll(getDistanceBetweenGroups(message, symbolsInGroups, numberOfGroups));
		System.out.println(Utils.dividersFrequency);

		int key = Utils.maxKey();
		System.out.println(key);
		if (key==0)
			key=1;
		if ((key == 2
				|| key == 3)
				&& (Math.abs(Utils.dividersFrequency.get(2) - Utils.dividersFrequency.get(3)) <= 10))
			key = 6;

		if (key % 3 == 0)
			while (Utils.dividersFrequency.get(key) - Utils.dividersFrequency.get(key * key) <= Utils.dividersFrequency.get(key) / 5) {
				key *= key;
			}

		if (key % 2 == 0)
			while (Utils.dividersFrequency.get(key) - Utils.dividersFrequency.get(key + key) <= Utils.dividersFrequency.get(key) / 5) {
				key += key;
			}


		System.out.println(key);
		deMessage.append(Caesar.encrypt(Utils.decoder(message, key).toString(), 0));
		Utils.dividersFrequency.clear();
		setKeyString(message.substring(0, key), Utils.transform(deMessage.toString()).substring(0, key), key);
		return deMessage.toString();
	}

	/**
	 * Возвращает массив расстояний между группами символов в сообщении.
	 *
	 * @param inputMsg       сообщение, в котором производится поиск.
	 * @param symbolsInGroup кол-во символов в группе.
	 * @param groupCount     кол-во групп.
	 * @return массив расстояний между группами символов.
	 */
	static ArrayList<Integer> getDistanceBetweenGroups(StringBuilder inputMsg, int symbolsInGroup, int groupCount) {
		StringBuilder copy = new StringBuilder();
		//list of N-grams
		ArrayList<String> nGrams = new ArrayList<>();
		//list of indexes in cipher for all N-grams
		Map<String, ArrayList<Integer>> indexForNGrams = new HashMap<>();
		Map<Integer, ArrayList<Integer>> indexForNGramsID = new HashMap<>();
		//
		ArrayList<Integer> nGramsId;
		//writing C N-grams in input cipher
		for (int i = 0; i < groupCount; i++) {
			copy.setLength(0);
			copy.append(inputMsg);
			if (symbolsInGroup * i > 0) copy.delete(0, symbolsInGroup * i);
			nGrams.add(copy.delete(symbolsInGroup, copy.length()).toString());
		}
		//writing all indexes for all N-grams
		int tmpIndex;
		for (int i = 0; i < groupCount; i++) {
			ArrayList<Integer> internalIndex = new ArrayList<>();
			indexForNGramsID.put(groupCount, null);
			for (int j = 0; j < inputMsg.length(); j++) {
				tmpIndex = j;
				copy.setLength(0);
				copy.append(inputMsg);
				copy.delete(0, j);
				if (symbolsInGroup <= copy.length()) {
					if (nGrams.get(i).equals(copy.delete(symbolsInGroup, copy.length()).toString())) {
						internalIndex.add(tmpIndex);
						indexForNGrams.put(nGrams.get(i), internalIndex);
					}
				}
			}
		}
		System.out.println(nGrams);
		System.out.println(indexForNGrams + "\n");
		//getting distance btw indexes for all values in map
		ArrayList<Integer> distanceOfNGrams = new ArrayList<>();
		List<Map.Entry<String, ArrayList<Integer>>> list = new LinkedList<>(indexForNGrams.entrySet());
		for (Map.Entry<String, ArrayList<Integer>> entry : list) {
			nGramsId = entry.getValue();
			if (nGramsId.size() > 1) {
				//System.out.println(nGramsId);
				for (int t = 0; t < nGramsId.size() - 1; t++) {
					distanceOfNGrams.add(Math.abs(nGramsId.get(t) - nGramsId.get(t + 1)));
				}
			}
			nGramsId.clear();
		}
		System.out.println(distanceOfNGrams);
		return distanceOfNGrams;
	}
}
