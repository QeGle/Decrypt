import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Utils {

	/**
	 * массив с буквами русского алфавита в верхнем регистре
	 */
	static final List<Character> alphabetRus = new ArrayList<>();

	/**
	 * массив с буквами английского алфавита в верхнем регистре
	 */
	static final List<Character> alphabetEng = new ArrayList<>();

	/**
	 * массив с табличными частотами встречания букв в руском алфавите
	 */
	static final Map<String, Double> tableFrequency = new HashMap<>();

	/**
	 * массив с частотами встречания делителей
	 */
	static final Map<Integer, Integer> dividersFrequency = new HashMap<>();

	static {
		//инициализируем алфавиты
		for (char c = 'А'; c <= 'Я'; c++) if (c != 'Ё') alphabetRus.add(c);
		for (char c = 'A'; c <= 'Z'; c++) alphabetEng.add(c);
		//Инициализируем табличные частоты
		setTableFrequency();
	}

	static public void main(String[] args) {
		String str = "1,5";
		int n = Integer.parseInt(str);
		dividers(15);
		dividers(15);
		dividers(15);
		dividers(15);
		dividers(16);
		dividers(16);
		System.out.println(dividersFrequency);

	}

	/**
	 * Вычисляет делители для всех чисел в массиве ArrayList<Integer>.
	 * <br>Передает каждое число в метод {@link Utils#dividers(Integer n)}.
	 *
	 * @param input массив с числами, для которых вычисляются делители.
	 */
	static void dividersAll(ArrayList<Integer> input) {
		input.forEach(Utils::dividers);
	}

	/**
	 * Вычисляет все делители числа и заносит результаты в {@link Utils#dividersFrequency}.
	 *
	 * @param n число, для которого вычисляются делители.
	 */
	static void dividers(Integer n) {
		//System.out.print("\n"+n+ " = ");
		if (!dividersFrequency.containsKey(n)) {
			for (int i = 1; i <= n; i++) {
				if (((n % i) == 0) && (i != 1)) {
					dividersFrequency.put(i, 0);
				}
			}
		}

		for (int i = 1; i <= n; i++) {
			if (((n % i) == 0) && (i != 1)) {
				int value = dividersFrequency.get(i);
				dividersFrequency.put(i, value + 1);
				//System.out.print(i + ", ");
			}
		}
		//System.out.println(dividersFrequency);
	}

	/**
	 * Устанавливает табличные значения частоты встречания букв в тексте в {@link Utils#tableFrequency}.
	 */
	private static void setTableFrequency() {
		tableFrequency.put("О", 0.090);
		tableFrequency.put("Е", 0.072);
		tableFrequency.put("А", 0.062);
		tableFrequency.put("И", 0.062);
		tableFrequency.put("Т", 0.053);
		tableFrequency.put("Н", 0.053);
		tableFrequency.put("М", 0.045);
		tableFrequency.put("Р", 0.040);
		tableFrequency.put("В", 0.038);
		tableFrequency.put("Л", 0.035);
		tableFrequency.put("К", 0.028);
		tableFrequency.put("М", 0.026);
		tableFrequency.put("Д", 0.025);
		tableFrequency.put("П", 0.023);
		tableFrequency.put("У", 0.021);
		tableFrequency.put("Я", 0.018);
		tableFrequency.put("Ы", 0.016);
		tableFrequency.put("З", 0.016);
		tableFrequency.put("Ъ", 0.014);
		tableFrequency.put("Ь", 0.014);
		tableFrequency.put("Б", 0.014);
		tableFrequency.put("Г", 0.013);
		tableFrequency.put("Ч", 0.013);
		tableFrequency.put("Й", 0.010);
		tableFrequency.put("Х", 0.009);
		tableFrequency.put("Ж", 0.007);
		tableFrequency.put("Ю", 0.006);
		tableFrequency.put("Ш", 0.006);
		tableFrequency.put("Ц", 0.004);
		tableFrequency.put("Щ", 0.003);
		tableFrequency.put("Э", 0.003);
		tableFrequency.put("Ф", 0.002);
	}

	/**
	 * Проверяет, является ли символ буквой.
	 *
	 * @param c символ, отправляемый на проверку.
	 * @return true, если символ - буква. Иначе - false.
	 */
	private static boolean isLetter(char c) {
		return alphabetEng.contains(c) || alphabetRus.contains(c);
	}

	// --Commented out by Inspection START (21.11.2016 18:21):
//	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
//		Map<K, V> result = new LinkedHashMap<>();
//		Stream<Map.Entry<K, V>> st = map.entrySet().stream();
//
//		st.sorted(Comparator.comparing(e -> e.getValue()))
//				.forEach(e -> result.put(e.getKey(), e.getValue()));
//
//		return result;
//	}
// --Commented out by Inspection STOP (21.11.2016 18:21)

	/**
	 * Проверяет принадлежности буквы к английскому алфавиту.
	 *
	 * @param c символ, отправляемый на проверку.
	 * @return true, если символ принадлежит к английскому алфавиту. Иначе - false.
	 */
	static boolean isEnglish(char c) {
		return alphabetEng.contains(c); //Проверяем только диапазон строчных букв
	}

	/**
	 * Проверяет, состоит ли строка из букв английского алфавита.
	 *
	 * @param string строка, отправляемая на проверку.
	 * @return true, если в строке все буквы принадлежат к английскому алфавиту. Иначе - false.
	 */
	static boolean isEnglish(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!isEnglish(string.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Проверяет принадлежности буквы к русскому алфавиту.
	 *
	 * @param c символ, отправляемый на проверку.
	 * @return true, если символ принадлежит к русскому алфавиту. Иначе - false.
	 */
	static boolean isRussian(char c) {
		return alphabetRus.contains(c); //Проверяем только диапазон строчных букв
	}

	/**
	 * Проверяет, состоит ли строка из букв русского алфавита.
	 *
	 * @param string строка, отправляемая на проверку.
	 * @return true, если в строке все буквы принадлежат к русскому алфавиту. Иначе - false.
	 */
	static boolean isRussian(String string) {
		for (int i = 0; i < string.length(); i++) {
			if (!isRussian(string.charAt(i)))
				return false;
		}
		return true;
	}

	/**
	 * Вычисляет наибольший общий делитель для двух чисел.
	 *
	 * @param a первое число.
	 * @param b второе число.
	 * @return наибольший общий делитель для a и b.
	 */
	static int gcd(Integer a, Integer b) {
		while (b > 0) {

			int temp = b;
			b = a % b; // % is remainder
			a = temp;
		}
		return a;
	}

	/**
	 * Вычисляет наибольший общий делитель для массива чисел.
	 *
	 * @param input -  массив с числами, для которых вычисляется наибольший общий делитель.
	 * @return наибольший общий делитель для массива чисел.
	 */
	static int gcd(ArrayList<Integer> input) {
		int result = input.get(0);
		for (int i = 1; i < input.size(); i++) result = gcd(result, input.get(i));
		return result;
	}

	/**
	 * Удаляет из массива чисел все простые числа.
	 *
	 * @param input массив с числами, из которого удаляются все простые числа.
	 * @return массив без простых чисел.
	 */
	static ArrayList<Integer> antiPrime(ArrayList<Integer> input) {
		for (int i = 0; i < input.size(); i++) {
			if (isPrime(input.get(i)))
				input.remove(i);
		}
		return input;
	}

	/**
	 * Проверяет, является ли число простым
	 *
	 * @param N число, для которого производится проверка
	 * @return true, если число простое. Иначе - false.
	 */
	static boolean isPrime(int N) {
		if (N < 1) return false;
		for (int i = 2; i * i <= N; i++)
			if (N % i == 0) return false;
		return true;
	}

	/**
	 * Вычисляет максимальное число в массиве
	 *
	 * @param list массив с числами
	 * @return максимальное число из массива
	 */
	static Integer maxIndex(ArrayList<Integer> list) {

		int maxIndex = 0;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(maxIndex) < list.get(i))
				maxIndex = i;
		}
		return list.get(maxIndex);
	}

	/**
	 * Выдаёт из Map<Integer, Double> единственный ключ.
	 *
	 * @param hashMap массив ключей и значений
	 * @return единственный ключ, находящийся в массиве
	 */
	static int key(Map<Integer, Double> hashMap) {
		Integer key = 3;
		for (Integer i : hashMap.keySet()) {
			double newValue = hashMap.get(i);
			key = i;
			return key;
		}
		return key;
	}

	/**
	 * Выдаёт из Map<Integer, Double> ключ с максимальным значением
	 *
	 * @param hashMap массив ключей и значений
	 * @return ключ, при котором значение максимальное
	 */
	static int keyWithMaxValue(Map<Integer, Double> hashMap) {
		Double value = 0.0;
		Integer key = 0;
		for (Integer i : hashMap.keySet()) {
			Double newValue = hashMap.get(i);
			if (newValue > value) {
				key = i;
				value = newValue;
			}

		}
		return key;
	}

	/**
	 * Убирает из строки все символы кроме букв.
	 *
	 * @param msg строка, для которой производится преобразование
	 * @return строка, состоящая только из букв
	 */
	static StringBuilder transform(String msg) {
		StringBuilder msgSB = new StringBuilder("");
		for (int i = 0; i < msg.length(); i++) { //оставляем только буквы
			if (isLetter(msg.toUpperCase().charAt(i)))
				msgSB.append(msg.toUpperCase().charAt(i)); //приводим весь текст к нижнему регистру
			if (msg.toUpperCase().charAt(i) == 'Ё')    //заменяем ё и Ё на е
				msgSB.append("Е");
		}
		return msgSB;
	}

	/**
	 * Находит расстояние между индексами в массиве.
	 *
	 * @param input массив индексов
	 * @return массив расстояний
	 */
	static ArrayList<Integer> getLengthBetweenIndices(ArrayList<Integer> input) {
		ArrayList<Integer> output = new ArrayList<>();

		//-1 - чтобы не выйти за пределы массива input
		//Ведь последний его член мы и так зацепляем алгоритмом
		for (int i = 0; i < input.size() - 1; i++) {
			output.add(i, input.get(i + 1) - input.get(i));
		}
		return output;
	}

	/**
	 * Производит дешифровку сообщения, зашифрованного методом Vigenere.
	 *
	 * @param input зашифрованное сообщение
	 * @param key   длинна ключа, которым было зашифровано сообщение
	 * @return дешифрованное сообщение
	 */
	static StringBuilder decoder(StringBuilder input, int key) {
		ArrayList<StringBuilder> messageMix = new ArrayList<>();
		StringBuilder output;

		for (int i = 0; i < key; i++) {
			StringBuilder string = new StringBuilder();
			for (int j = i; j < input.length(); j += key) {
				string.append(input.charAt(j));
			}
			messageMix.add(i, Utils.transform(Caesar.hack(string.toString())));
		}
		output = construct(messageMix);
		return output;
	}

	/**
	 * Соединяет побуквенно строки расшифрованного сообщения.
	 *
	 * @param input массив строк расшифрованного сообщения
	 * @return дешифрованное сообщение
	 */
	static StringBuilder construct(ArrayList<StringBuilder> input) {
		int key = input.size();
		StringBuilder output = new StringBuilder();

		for (int i = 0; i < input.get(0).length(); i++) {
			for (int j = 0; j < key; j++) {
				if (input.get(j).length() > i)
					output.append(input.get(j).charAt(i));
			}
		}
		return output;
	}

	/**
	 * Возвращает ключ у которго значение максимально из {@link Utils#dividersFrequency}.
	 *
	 * @return ключ
	 */
	static int maxKey() {
		int key = 0;
		int frequency = 0;
		for (int i = 0; i < dividersFrequency.size(); i++) {
			if (dividersFrequency.containsKey(i)) {
				if (dividersFrequency.get(i) > frequency) {
					key = i;
					frequency = dividersFrequency.get(i);
				} else if (dividersFrequency.get(i) == frequency)
					key = Math.max(i, key);
			}
		}
		return key;
	}
}
