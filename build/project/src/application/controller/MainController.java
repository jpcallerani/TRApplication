package application.controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;

import com.jfoenix.controls.JFXDrawer;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;

import application.util.Install;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

@Controller
@Transactional
public class MainController implements Initializable {

	@FXML
	public static AnchorPane mainPage;

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
	private Label lblUsername;

	@FXML
	private JFXHamburger hamMenu;

	@FXML
	private JFXDrawer drawMenu;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.loadMenu();

		this.lblUsername.setText("Connected as: " + Install.username + "@" + Install.tns);


	}

	private void loadMenu() {
		VBox box = null;
		try {
			box = FXMLLoader.load(getClass().getResource("/gui/menuBar.fxml"));
		} catch (IOException e1) {
			new Alert(AlertType.ERROR, e1.getMessage(), ButtonType.OK).showAndWait();
		}
		drawMenu.setSidePane(box);
		HamburgerBackArrowBasicTransition transition = new HamburgerBackArrowBasicTransition(hamMenu);
		transition.setRate(-1);
		hamMenu.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
			transition.setRate(transition.getRate() * -1);
			transition.play();

			if (drawMenu.isOpened()) {
				drawMenu.close();
			} else {
				drawMenu.open();
			}
		});
	}
}
