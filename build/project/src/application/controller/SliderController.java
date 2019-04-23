package application.controller;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;

import application.main.Launch;
import application.util.DatabaseConnection;
import application.util.Install;
import gui.enumeration.enumTelas;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/**
 * 
 * @author Jp-PC
 *
 */
@Controller
public class SliderController implements Initializable {

	@FXML
	private VBox vBox;

	@FXML
	private VBox firstSubVBox;

	@FXML
	private HBox hboxOperations;

	@FXML
	private Pane paneOperations;

	@FXML
	private JFXButton firstMenu;

	@FXML
	private ImageView imgOperations;

	@FXML
	private VBox firstSubMenuList;

	@FXML
	private Pane paneCmLogScripts;

	@FXML
	private JFXButton btnCmLogScripts;

	@FXML
	private HBox hboxAbout;

	@FXML
	private Pane paneSizing;

	@FXML
	private JFXButton btnSizingCalculate;

	@FXML
	private HBox hboxAbout1;

	@FXML
	private Pane paneCompare;

	@FXML
	private JFXButton btnCompare;

	@FXML
	private HBox hboxExt1;

	@FXML
	private Pane paneAbout;

	@FXML
	private JFXButton btnAbout;

	@FXML
	private ImageView imgAbout;

	@FXML
	private HBox hboxExt;

	@FXML
	private Pane paneExit;

	@FXML
	private JFXButton btnExit;

	@FXML
	private ImageView imgExit;

	Map<VBox, VBox> map = new HashMap<VBox, VBox>();

	/**
	 * 
	 */
	public void initialize(URL location, ResourceBundle resources) {
		// Adiciona os menus dinamicamentes
		addMenusToMap();
		setComponentsSize();

		// Carrega os menus ao abrir da tela
		firstMenu.setOnAction(new EventHandler<ActionEvent>() {
			public void handle(ActionEvent event) {
				toolsSlider(firstSubVBox, firstSubMenuList);
				removeOtherMenus(firstSubVBox);
			}

		});

		// Cria transitions para mexer os ícones do menu
		TranslateTransition rotationOperations = Install.shakeTransition(imgOperations);
		TranslateTransition rotationAbout = Install.shakeTransition(imgAbout);
		TranslateTransition rotationExit = Install.shakeTransition(imgExit);

		// OPERATION MENU
		firstMenu.setOnMouseEntered(e -> {
			rotationOperations.play();
			paneOperations.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			firstMenu.setStyle("-fx-text-fill: #f2740b;");
		});
		// OPERATION MENU
		firstMenu.setOnMouseExited(e -> {
			rotationOperations.pause();
			paneOperations.setStyle("-fx-background-color: none");
			firstMenu.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});
		// CMLOGSCRIPTS MENU
		btnCmLogScripts.setOnMouseEntered(e -> {
			paneCmLogScripts.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			btnCmLogScripts.setStyle("-fx-text-fill: #f2740b;");
		});
		// CMLOGSCRIPTS MENU
		btnCmLogScripts.setOnMouseExited(e -> {
			paneCmLogScripts.setStyle("-fx-background-color: none");
			btnCmLogScripts.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});
		// CMLOGSCRIPTS MENU
		btnCmLogScripts.setOnMouseClicked(e -> {
			Install.loadScene(enumTelas.FRM_CM_LOG_SCRIPTS.getUrl());
		});
		// SIZING CALCULATE MENU
		btnSizingCalculate.setOnMouseEntered(e -> {
			paneSizing.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			btnSizingCalculate.setStyle("-fx-text-fill: #f2740b;");
		});
		// SIZING CALCULATE MENU
		btnSizingCalculate.setOnMouseExited(e -> {
			paneSizing.setStyle("-fx-background-color: none");
			btnSizingCalculate.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});
		// SIZING CALCULATE MENU
		btnSizingCalculate.setOnMouseClicked(e -> {
			Install.loadScene(enumTelas.FRM_SIZING_CALCULATE.getUrl());
		});
		// COMPARE MENU
		btnCompare.setOnMouseExited(e -> {
			paneCompare.setStyle("-fx-background-color: none");
			btnCompare.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});
		// COMPARE MENU
		btnCompare.setOnMouseEntered(e -> {
			paneCompare.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			btnCompare.setStyle("-fx-text-fill: #f2740b;");
		});
		// COMPARE MENU
		btnCompare.setOnMouseClicked(e -> {
			Install.loadScene(enumTelas.FRM_COMPARE.getUrl());
		});
		// ABOUT MENU
		btnAbout.setOnMouseEntered(e -> {
			rotationAbout.play();
			paneAbout.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			btnAbout.setStyle("-fx-text-fill: #f2740b;");
		});
		// ABOUT MENU
		btnAbout.setOnMouseExited(e -> {
			rotationAbout.pause();
			paneAbout.setStyle("-fx-background-color: none");
			btnAbout.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});

		btnExit.setOnMouseEntered(e -> {
			rotationExit.play();
			paneExit.setStyle("-fx-background-color: linear-gradient(#f2740b, #7c471c);");
			btnExit.setStyle("-fx-text-fill: #f2740b;");
		});
		// EXIT MENU
		btnExit.setOnMouseExited(e -> {
			rotationExit.pause();
			paneExit.setStyle("-fx-background-color: none");
			btnExit.setStyle("-fx-background-color:  #FFFFFF;" + "-fx-text-fill: #403a35;");
		});
		// EXIT MENU
		btnAbout.setOnMouseClicked(e -> {

		});
		// EXIT MENU
		btnExit.setOnMouseClicked(e -> {
			DatabaseConnection con = new DatabaseConnection();
			con.Connect();
			con.dropViewCmLogScripts();
			Timeline timeline = new Timeline();
			KeyFrame key = new KeyFrame(Duration.millis(500),
					new KeyValue(Install.mainStage.getScene().getRoot().opacityProperty(), 0));
			timeline.getKeyFrames().add(key);
			timeline.setOnFinished((ae) -> System.exit(1));
			timeline.play();
		});

	}

	/**
	 * Set stage size as per screen resolution
	 */
	private void setComponentsSize() {
		vBox.prefHeightProperty().bind(Launch.stage.heightProperty());
		vBox.setPrefWidth(200);
	}

	/**
	 * Add Menus to map
	 */
	public void addMenusToMap() {
		addMenusToMapImpl();
	}

	private void addMenusToMapImpl() {

		map.put(firstSubVBox, firstSubMenuList);
		/*
		 * map.put(secondSubVBox, secondSubMenuList);
		 * map.put(thirdSubVBox,thirdSubMenuList); map.put(fourthSubVBox,
		 * fourthSubMenuList); map.put(fifthSubVBox, fifthSubMenuList);
		 */

		/**
		 * Remove the components from VBox on load of stage
		 */
		for (Map.Entry<VBox, VBox> entry : map.entrySet()) {
			entry.getKey().getChildren().remove(entry.getValue());
		}
	}

	/**
	 * Menu slider
	 * 
	 * @param menu
	 * @param subMenu
	 */
	public void toolsSlider(VBox menu, VBox subMenu) {
		toolsSliderImpl(menu, subMenu);
	}

	private void toolsSliderImpl(VBox menu, VBox subMenu) {
		if (menu.getChildren().contains(subMenu)) {
			final FadeTransition transition = new FadeTransition(Duration.millis(500), menu);
			transition.setFromValue(0.5);
			transition.setToValue(1);
			transition.setInterpolator(Interpolator.EASE_IN);
			menu.getChildren().remove(subMenu);
			transition.play();
		} else {
			final FadeTransition transition = new FadeTransition(Duration.millis(500), menu);
			transition.setFromValue(0.5);
			transition.setToValue(1);
			transition.setInterpolator(Interpolator.EASE_IN);
			menu.getChildren().add(subMenu);
			transition.play();
		}
	}

	/**
	 * Remove other menus
	 * 
	 * @param menu
	 */
	public void removeOtherMenus(VBox menu) {
		removeOtherMenusImpl(menu);
	}

	/**
	 * 
	 * @param menu
	 */
	private void removeOtherMenusImpl(VBox menu) {
		for (Map.Entry<VBox, VBox> entry : map.entrySet()) {
			if (!entry.getKey().equals(menu))
				entry.getKey().getChildren().remove(entry.getValue());
		}
	}
}