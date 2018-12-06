package application.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

public class MainController implements Initializable {

	@FXML
	private AnchorPane mainPage;

	@FXML
	private MenuBar menuPrincipal;

	@FXML
	private MenuItem itemMenuSair;

	@FXML
	private MenuItem menuItemCmLogScripts;

	@FXML
	private ImageView background;

	@FXML
	private ImageView btnCmLogScripts;

	@FXML
	void exitProgram(ActionEvent event) {
		System.exit(0);
	}

	@FXML
	void btnCmLogScriptsMouseEntered(MouseEvent event) {
		btnCmLogScripts.setImage(new Image("resources/btnCmLogScriptsShadow.png"));
	}

	@FXML
	void btnCmLogScriptsMouseExited(MouseEvent event) {
		btnCmLogScripts.setImage(new Image("resources/btnCmLogScripts.png"));
	}

	@FXML
	void frmCmLogScripts(MouseEvent event) {
		try {
			AnchorPane frmCmLogScripts = (AnchorPane) FXMLLoader.load(getClass().getResource("/gui/frmCmLogScripts.fxml"));
			mainPage.getChildren().setAll(frmCmLogScripts);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}
}
