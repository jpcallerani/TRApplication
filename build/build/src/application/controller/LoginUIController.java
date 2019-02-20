package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.FadeIn;
import application.config.PersistenceJPAConfig;
import application.util.DatabaseConnection;
import application.util.Install;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceDialog;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.stage.WindowEvent;

@Controller
public class LoginUIController implements Initializable {

	private ApplicationContext ctx;

	@FXML
	private AnchorPane apLogin;

	@FXML
	private JFXTextField txUsuario;

	@FXML
	private JFXPasswordField txPassword;

	@FXML
	private JFXTextField txTNS;

	@FXML
	private JFXButton btnConectar;

	@FXML
	private JFXButton btnCancelar;

	/**
	 * Método de inicialização do controller;
	 */
	@Override
	public void initialize(URL url, ResourceBundle rb) {
		Install.makeStageDrageable(apLogin);
		btnConectar.setOnMouseClicked(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				// Executa o médoto de login clicando botão;
				login();
			}
		});
		txTNS.setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				// executa o método de login apertando ENTER;
				if (event.getCode() == KeyCode.ENTER) {
					login();
				}
			}
		});
	}

	/**
	 * Método para login na aplicação
	 */
	public void login() {
		if (txUsuario.getText().equalsIgnoreCase("")) {
			txUsuario.requestFocus();
			new Alert(AlertType.ERROR, "The username is required!", ButtonType.OK).showAndWait();
			return;
		}
		if (txPassword.getText().equalsIgnoreCase("")) {
			txPassword.requestFocus();
			new Alert(AlertType.ERROR, "The password is required!", ButtonType.OK).showAndWait();
			return;
		}
		if (txTNS.getText().equalsIgnoreCase("")) {
			new Alert(AlertType.ERROR, "The TNS is required!", ButtonType.OK).showAndWait();
			return;
		}
		Task<?> task = new Task<Object>() {
			@Override
			protected Integer call() throws Exception {
				Install.loadStatus(apLogin);
				return new DatabaseConnection().validaConexao(txUsuario.getText(), txPassword.getText(),
						txTNS.getText());
			}

			@Override
			protected void succeeded() {
				Integer codigo = Integer.parseInt(getValue().toString());
				if (codigo == 1) {
					try {
						Parent parent = FXMLLoader.load(getClass().getResource("/gui/frmMain.fxml"));
						Scene scene = new Scene(parent);
						Stage stage = new Stage();
						stage.setTitle(txUsuario.getText() + "@" + txTNS.getText() + " - Thomson Reuters Application");
						stage.setScene(scene);
						Stage stage1 = (Stage) btnConectar.getScene().getWindow();
						ctx = new AnnotationConfigApplicationContext(PersistenceJPAConfig.class);
						ctx.getAutowireCapableBeanFactory().autowireBean(this);
						stage1.close();
						Install.mainStage = stage;
						Install.mainRoot = (AnchorPane) stage.getScene().getRoot();
						stage.setResizable(false);
						stage.addEventHandler(WindowEvent.WINDOW_SHOWING, new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent window) {
								while (!returnCodSistema()) {
								}
							}
						});
						stage.addEventHandler(WindowEvent.WINDOW_CLOSE_REQUEST, new EventHandler<WindowEvent>() {
							@Override
							public void handle(WindowEvent window) {
								DatabaseConnection con = new DatabaseConnection();
								con.Connect();
								con.dropViewCmLogScripts();
							}
						});
						stage.show();

						new FadeIn(parent).play();

					} catch (IOException ex) {
						ex.printStackTrace();
					}
				} else {
					new Alert(AlertType.ERROR, "Connection not found: " + txUsuario.getText() + "@" + txTNS.getText()
							+ ".\n" + " Check username, password and TNS.", ButtonType.OK).showAndWait();
					txUsuario.setText("");
					txPassword.setText("");
					txTNS.setText("");
					txUsuario.requestFocus();
				}
				Install.unLoadStatus(apLogin);
			}
		};
		task.setOnFailed(e -> {
			Throwable exception = e.getSource().getException();
			if (exception != null) {
				new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK).showAndWait();
			}
		});
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	@FXML
	private void close_app(MouseEvent event) {
		System.exit(0);
	}

	/**
	 * Guardo o código sistema selecionado pelo usuário;
	 * 
	 * @return
	 */
	private boolean returnCodSistema() {
		boolean choise = false;
		ChoiceDialog<String> dialog = new ChoiceDialog<>("", Install.listCodSistema());
		dialog.setTitle("Attention");
		dialog.setHeaderText(null);
		dialog.setContentText("Choose the \"COD_SISTEMA\" where you're connected!! ");
		dialog.initStyle(StageStyle.UTILITY);
		// Traditional way to get the response value.
		Optional<String> result = dialog.showAndWait();
		if (result.isPresent()) {
			Install.cod_sistema = result.get();
			choise = true;
		}
		return choise;
	}
}
