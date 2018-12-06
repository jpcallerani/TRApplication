package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

public class CmLogScriptsController implements Initializable {

	DropShadow shadow = new DropShadow();

	final String IDLE_BUTTON_STYLE = "-fx-effect: dropshadow(three-pass-box, rgba(0,0,0,0.8), 10, 0, 0, 0);";
	final String HOVERED_BUTTON_STYLE = "-fx-background-color: -fx-shadow-highlight-color, -fx-outer-border, -fx-inner-border, -fx-body-color;";

	@FXML
	private AnchorPane frmCmLogScripts;

	@FXML
	private BorderPane bpCmLogScripts;

	@FXML
	private ImageView btnNovo;

	@FXML
	private ImageView btnEdit;

	@FXML
	private ImageView btnSave;

	@FXML
	private ImageView btnDelete;

	@FXML
	private ImageView btnSearch;

	@FXML
	private ImageView btnHome;

	@FXML
	private Pane paneCentroCmLogScripts;

	@FXML
	private TextField tfTeste;

	@FXML
	private GridPane pnMenu;

	@FXML
	void add(MouseEvent event) {
		this.tfTeste.setText("CLIQUEI");
	}

	@FXML
	void delete(MouseEvent event) {

	}

	@FXML
	void edit(MouseEvent event) {

	}

	@FXML
	void home(MouseEvent event) {

	}

	@FXML
	void save(MouseEvent event) {

	}

	@FXML
	void search(MouseEvent event) {

	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		btnNovo.addEventHandler(MouseEvent.MOUSE_EXITED, new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent e) {
				tfTeste.setText("");
			}
		});
	}

}
