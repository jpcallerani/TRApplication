package application.controller;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.FadeIn;
import application.entity.Erro;
import application.util.Install;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class ErroController implements Initializable {

	@FXML
	private AnchorPane shadowPane;

	@FXML
	private AnchorPane rootPane;

	@FXML
	private JFXTextField txUsuario;

	@FXML
	private JFXTextField txVersao;

	@FXML
	private JFXTextField txTipo;

	@FXML
	private JFXTextField txObjeto;

	@FXML
	private JFXTextField txData;

	@FXML
	private JFXTextArea txErro;

	@FXML
	private JFXTextField txRM;

	@FXML
	private JFXButton btnExec;

	@FXML
	private JFXButton btnExit;

	private Erro selectedErro;

	@FXML
	private JFXButton btnFile;

	Task<?> taskFindFile;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/**
		 * fecha o modal do erro
		 */
		this.btnExit.setOnMouseClicked(e -> {
			// close the dialog.
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		});

		/**
		 * Executa o script selecionado na base;
		 */
		this.btnExec.setOnMouseClicked(e -> {
			if (Install.alertYesAndNo("Are you sure that you want execute \n" + this.selectedErro.getObjeto() + "\n"
					+ "in " + Install.username + "@" + Install.tns + "?")) {
				this.openTaskErro();
			}
		});

		/**
		 * Abre o arquivo do erro para analisar;
		 */
		this.btnFile.setOnMouseClicked(e -> {
			openErroFile();
		});

		Platform.runLater(() -> {
			this.fieldsPopulate();
		});
	}

	public void openErroFile() {
		taskFindFile = new Task<Object>() {

			@Override
			protected Boolean call() throws Exception {
				// desabilita componentes
				rootPane.setCursor(Cursor.WAIT);
				btnExec.setDisable(true);
				btnExit.setDisable(true);
				btnFile.setDisable(true);

				// Busca arquivo para mostrar na tela.
				try {
					rootPane.setCursor(Cursor.WAIT);
					Install.loadStatus(rootPane);
					Install.findFile(Install.filePackage, selectedErro.getObjeto());
					if (Install.fileToBeExecutedFrom != null) {
						if (System.getProperty("os.name").toLowerCase().contains("windows")) {
							Process run = Runtime.getRuntime()
									.exec("rundll32 url.dll,FileProtocolHandler " + Install.fileToBeExecutedFrom);
							run.waitFor();
						} else {
							Desktop.getDesktop().edit(new File(Install.fileToBeExecutedFrom));
						}
						return true;
					} else {
						return false;
					}
				} catch (Exception e1) {
					return false;
				}
			}

			@Override
			protected void succeeded() {
				// habilita ou desabilita componentes
				rootPane.setCursor(Cursor.WAIT);
				Install.unLoadStatus(rootPane);
				Install.fileToBeExecutedFrom = null;
				if ((boolean) taskFindFile.getValue()) {
					Install.fileToBeExecutedFrom = null;
				} else {
					Install.fileToBeExecutedFrom = null;
					new Alert(AlertType.ERROR, selectedErro.getObjeto() + " File not found!.", ButtonType.OK)
							.showAndWait();
				}
			}
		};

		taskFindFile.setOnFailed(e -> {
			Install.fileToBeExecutedFrom = null;
			rootPane.setCursor(Cursor.WAIT);
			Install.unLoadStatus(rootPane);
			new Alert(AlertType.ERROR, selectedErro.getObjeto() + " File not found!.", ButtonType.OK).showAndWait();
		});

		Thread th = new Thread(taskFindFile);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * @param teste the teste to set
	 */
	public void setSelectedErro(Erro selectedErro) {
		this.selectedErro = selectedErro;
	}

	/**
	 * Popula os campos com parametros que veio do controlador anterior.
	 */
	private void fieldsPopulate() {
		this.txUsuario.setText(this.selectedErro.getUsuario());
		this.txVersao.setText(this.selectedErro.getVersao());
		this.txData.setText(new SimpleDateFormat("dd/MM/yyyyy").format(this.selectedErro.getData()));
		this.txTipo.setText(this.selectedErro.getTipo());
		this.txRM.setText(this.selectedErro.getRm());
		this.txObjeto.setText(this.selectedErro.getObjeto());
		this.txErro.setText(this.selectedErro.getErro());
	}

	/*
	 * Abre o dialog do erro selecionado.
	 */
	public void openTaskErro() {
		// check the table's selected item and get selected item
		try {
			FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/dialogExecuteErro.fxml"));
			Parent parent = fxmlLoader.load();
			taskErroController taskController = fxmlLoader.<taskErroController>getController();
			taskController.setErro(selectedErro);

			Scene scene = new Scene(parent);
			Stage stage = new Stage();
			scene.setFill(Color.TRANSPARENT); // Fill our scene with nothing
			stage.initStyle(StageStyle.TRANSPARENT); // Important one!
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.setScene(scene);

			new FadeIn(parent).play();

			stage.showAndWait();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
