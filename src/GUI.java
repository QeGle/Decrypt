import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class GUI extends Application {

	private final TextField keyTextField = new TextField();
	private final TextArea inputTextField = new TextArea();
	private char cipherType = 'c';
	private TextArea outputTextField = new TextArea();
	private Label outputTextLabel = new Label("Зашифрованное сообщение:");
	private ToggleButton toggleButtonCaesar = new ToggleButton("Цезарь");
	private ToggleButton toggleButtonVigenere = new ToggleButton("Виженер");
	private Button encryptButton = new Button("Зашифровать");
	private Button decryptButton = new Button("Расшифровать");
	private Button hackButton = new Button("Взломать");
	private Label inputTextLabel = new Label("Введите текст сообщения:");
	private Button clearEncryptButton = new Button("О\nч\nи\nс\nт\nи\nт\nь");
	private Button clearDecryptButton = new Button("О\nч\nи\nс\nт\nи\nт\nь");

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		toggleButtonCaesar.setOnAction(this::toggleCaesar);
		toggleButtonVigenere.setOnAction(this::toggleVigenere);
		toggleButtonCaesar.setSelected(true);

		VBox vBox = new VBox(10);
		vBox.setPadding(new Insets(10, 10, 10, 10));
		vBox.setAlignment(Pos.CENTER);
		Scene scene = new Scene(vBox, 500, 600);

		HBox taskButtonHBox = new HBox(10);
		taskButtonHBox.setAlignment(Pos.CENTER);

		Label keyTextLabel = new Label("Введите ключ:");

		keyTextField.setPrefWidth(210);

		HBox inputHBox = new HBox(10);
		inputHBox.setAlignment(Pos.CENTER);

		HBox textInputHBox = new HBox(10);
		textInputHBox.setAlignment(Pos.CENTER_LEFT);

		inputTextField.setPrefHeight(300);
		inputTextField.setPrefWidth(445);
		inputTextField.setPrefColumnCount(5);

		clearDecryptButton.setPrefHeight(300);
		clearDecryptButton.setPrefWidth(30);
		clearEncryptButton.setPrefHeight(300);
		clearEncryptButton.setPrefWidth(30);

		HBox textOutputHBox = new HBox(10);
		textOutputHBox.setAlignment(Pos.CENTER_LEFT);

		outputTextField.setPrefHeight(300);
		outputTextField.setPrefWidth(445);
		outputTextField.setPrefColumnCount(5);
		outputTextField.setEditable(false);

		encryptButton.setOnAction(this::encrypt);
		decryptButton.setOnAction(this::decrypt);
		hackButton.setOnAction(this::hack);

		clearDecryptButton.setOnAction(actionEvent -> outputTextField.clear());
		clearEncryptButton.setOnAction(actionEvent -> inputTextField.clear());

		taskButtonHBox.getChildren().addAll(toggleButtonCaesar, toggleButtonVigenere, encryptButton, decryptButton, hackButton);
		inputHBox.getChildren().addAll(clearEncryptButton, keyTextLabel, keyTextField);
		textInputHBox.getChildren().addAll(inputTextField, clearEncryptButton);
		textOutputHBox.getChildren().addAll(outputTextField, clearDecryptButton);
		vBox.getChildren().addAll(taskButtonHBox, inputHBox, inputTextLabel, textInputHBox, outputTextLabel, textOutputHBox);

		primaryStage.setTitle("Цезарь");
		primaryStage.setScene(scene);
		primaryStage.show();
	}

	/**
	 * При нажатии кнопки {@link GUI#toggleButtonCaesar} фиксирует её в нажатом положении и записывает в {@link GUI#cipherType} 'c'
	 * @param e ActionEvent parameters
	 */
	private void toggleCaesar(ActionEvent e){
		cipherType = 'c';
		toggleButtonVigenere.setSelected(false);
	}

	/**
	 * При нажатии кнопки {@link GUI#toggleButtonVigenere} фиксирует её в нажатом положении и записывает в {@link GUI#cipherType} 'м'
	 * @param e ActionEvent parameters
	 */
	private void toggleVigenere(ActionEvent e){
		cipherType = 'v';
		toggleButtonCaesar.setSelected(false);
	}
	/**
	 * Производит шифрование строки, полученной из {@link GUI#inputTextField}, при помощи ключа из {@link GUI#keyTextField}.
	 * B выводит ее в {@link GUI#outputTextField}
	 * @param e ActionEvent parameters
	 */
	private void encrypt(ActionEvent e) {
		if (!mIsEmpty()) {
			if (!inputFieldIsEmpty()) {

				if (cipherType == 'c' && mIsInt()) {
					outputTextField.setText(Caesar.encrypt(inputTextField.getText(),
							Integer.parseInt(keyTextField.getText())));
					outputTextLabel.setText("Зашифрованное сообщение:");

				}
				if (cipherType == 'v' && mIsWord()) {
					if (Utils.isRussian(Utils.transform(inputTextField.getText()).toString())) {
						outputTextField.setText(Vigenere.encrypt(inputTextField.getText(),
								keyTextField.getText().toUpperCase()));
						outputTextLabel.setText("Зашифрованное сообщение:");
					} else alertTypeText("Текст для зашифровки не на русском");
				}

			}
		}
	}

	/**
	 * Производит дешифрование строки, полученной из {@link GUI#inputTextField}, при помощи ключа из {@link GUI#keyTextField}.
	 * B выводит ее в {@link GUI#outputTextField}
	 * @param e ActionEvent parameters
	 */
	private void hack(ActionEvent e) {
		if (!inputFieldIsEmpty()) {
			if (Utils.isRussian(Utils.transform(inputTextField.getText()).toString())) {

				if (cipherType == 'c') {
					outputTextField.setText(Caesar.hack(inputTextField.getText()));
					outputTextLabel.setText("Взломанное сообщение:");
					keyTextField.setText(Caesar.getKey() + "");
				}

				if (cipherType == 'v') {
					outputTextField.setText(Vigenere.hack(inputTextField.getText()));
					outputTextLabel.setText("Взломанное сообщение:");
					keyTextField.setText(Vigenere.getKeyString());
				}

			} else alertTypeText("Текст для взлома не на русском");
		}
	}

	/**
	 * Производит взлом строки, полученной из {@link GUI#inputTextField}
	 * и выводит ее в {@link GUI#outputTextField}, а полученное значение ключа в {@link GUI#keyTextField}.
	 * @param e ActionEvent parameters
	 */
	private void decrypt(ActionEvent e) {
		if (!mIsEmpty()) {
			if (!inputFieldIsEmpty()) {
				if (cipherType == 'c' && mIsInt()) {
					outputTextField.setText(Caesar.decrypt(inputTextField.getText(),
							Integer.parseInt(keyTextField.getText())));
					outputTextLabel.setText("Расшифрованное сообщение:");
				}
				if (cipherType == 'v' && mIsWord()) {
					if (Utils.isRussian(Utils.transform(inputTextField.getText()).toString())) {
						outputTextField.setText(Vigenere.decrypt(inputTextField.getText(),
								keyTextField.getText().toUpperCase()));
						outputTextLabel.setText("Расшифрованное сообщение:");
					} else alertTypeText("Текст для расшифровки не на русском");
				}
			}
		}
	}

	/**
	 * Проверяет, пустое ли поле ввода сообщения или нет.
	 * В случае если оно пустое - выдает предупреждение.
	 *
	 * @return true, если поле ввода сообщения пустое. Иначе false.
	 */
	private boolean inputFieldIsEmpty() {
		if (inputTextField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.WARNING);
			alert.setTitle("Error!!");
			alert.setHeaderText(null);
			alert.setContentText("Поле ввода пустое");
			alert.showAndWait();
			return true;
		}
		return false;
	}

	/**
	 * Проверяет, пустое ли поле ввода ключа или нет.
	 *
	 * @return true, если поле ввода ключа пустое. Иначе false.
	 */
	private boolean mIsEmpty() {
		if (keyTextField.getText().isEmpty()) {
			Alert alert = new Alert(Alert.AlertType.ERROR);
			alert.setTitle("Error!!");
			alert.setHeaderText(null);
			alert.setContentText("Вы не ввели ключ");
			alert.showAndWait();
			return true;
		}
		return false;
	}

	/**
	 * Проверяет, что введено в поле ввода.
	 *
	 * @return если в поле ввода слово, состоящее из букв русского алфавита - true. Иначе false.
	 */
	private boolean mIsWord() {
		String str = keyTextField.getText().toUpperCase();
		for (int i = 0; i < keyTextField.getText().length(); i++) {
			if (!Utils.alphabetRus.contains(str.charAt(i))) {
				alertTypeText("Вы ввели неправильный ключ для выбранного шифра");
				return false;
			}
		}
		return true;
	}

	/**
	 * Проверяет, что введено в поле ввода.
	 *
	 * @return если в поле ввода число - true. Иначе false.
	 */
	private boolean mIsInt() {
		String str = keyTextField.getText();
		try {
			Integer.parseInt(str);
		} catch (Exception e) {
			alertTypeText("Вы ввели неправильный ключ для выбранного шифра");
			return false;
		}
		return true;
	}

	/**
	 * Выводит на экран предупреждение с заданным текстом.
	 *
	 * @param s текст, который необходимо вывести в предупреждении.
	 */
	private void alertTypeText(String s) {
		Alert alert = new Alert(Alert.AlertType.ERROR);
		alert.setTitle("Error!!");
		alert.setHeaderText(null);
		alert.setContentText(s);
		alert.showAndWait();
	}

}
