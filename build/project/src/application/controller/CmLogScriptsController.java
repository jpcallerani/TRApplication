package application.controller;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import animatefx.animation.BounceInDown;
import animatefx.animation.FadeIn;
import animatefx.animation.Shake;
import application.entity.Erro;
import application.service.ErroService;
import application.util.BeanUtil;
import application.util.Install;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gui.enumeration.enumTelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

@Controller
public class CmLogScriptsController implements Initializable {

	private ErroService erroService;

	@FXML
	private AnchorPane frmCmLogScripts;

	@FXML
	private Pane filtroCmLogScripts;

	@FXML
	private JFXTreeTableView<Erro> tableCmLogScripts;

	@FXML
	private ImageView btnHome;

	@FXML
	private DatePicker txDate;

	@FXML
	private JFXTextField txErro;

	@FXML
	private JFXButton btnSearch;

	@FXML
	private JFXButton btnClear;

	@FXML
	private FontAwesomeIcon btnFolder;

	@FXML
	private JFXTextField txFolder;

	@FXML
	private FontAwesomeIcon btnDefine;

	@FXML
	private JFXTextField txDefine;

	@FXML
	private JFXTextField txFileName;

	@FXML
	private Tooltip ttPackage;

	@FXML
	private Tooltip ttDefine;

	@FXML
	private Label labelUsername;

	@FXML
	private JFXTextField txUsername;

	private ObservableList<Erro> data;

	private TreeItem<Erro> root;

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		/**
		 * choose define path
		 */
		this.btnDefine.setOnMouseClicked(e -> {
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select define.sql file, it must be filled!!");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("define.sql", "define.sql"));
			final File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				this.txDefine.setText(selectedFile.getAbsolutePath());
				Install.fileDefine = selectedFile.getAbsolutePath();
			}
		});

		/**
		 * find package folder
		 */
		this.btnFolder.setOnMouseClicked(e -> {
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			final DirectoryChooser directoryChooser = new DirectoryChooser();
			directoryChooser.setTitle("Select the package folder!");
			final File selectedDirectory = directoryChooser.showDialog(stage);
			if (selectedDirectory != null) {
				this.txFolder.setText(selectedDirectory.getAbsolutePath());
				Install.filePackage = selectedDirectory.getAbsolutePath();
			}
		});

		/**
		 * Limpa o filtro
		 */
		this.btnClear.setOnMouseClicked(e -> {
			this.clearFilters();
		});

		/**
		 * Executa consulta
		 */
		this.btnSearch.setOnMouseClicked(e -> {
			this.findByFilter();
		});

		/**
		 * Volta para página principal
		 */
		this.btnHome.setOnMouseClicked(e -> {
			Install.loadScene(enumTelas.MAIN_PAGE.getUrl());
			new BounceInDown(Install.mainRoot).play();
			Install.fileDefine = null;
			Install.filePackage = null;
		});

		/**
		 * efeito no botão home
		 */
		this.btnHome.setOnMouseEntered(e -> {
			Install.homeIn(this.btnHome);
		});

		/**
		 * efeito no botão home
		 */
		this.btnHome.setOnMouseExited(e -> {
			Install.homeOut(this.btnHome);
		});

		// setando valor para coluna VERSAO
		JFXTreeTableColumn<Erro, String> colVersao = new JFXTreeTableColumn<>("VERSÃO");
		colVersao.setPrefWidth(170);
		colVersao.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String> param) -> {
			return new SimpleStringProperty(param.getValue().getValue().getVersao());
		});
		// setando valor para coluna ERRO
		JFXTreeTableColumn<Erro, String> colErro = new JFXTreeTableColumn<>("ERRO");
		colErro.setPrefWidth(800);
		colErro.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String> param) -> {
			return new SimpleStringProperty(param.getValue().getValue().getErro());
		});
		// setando valor para coluna OBJETO
		JFXTreeTableColumn<Erro, String> colObjeto = new JFXTreeTableColumn<>("OBJETO");
		colObjeto.setPrefWidth(400);
		colObjeto.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String> param) -> {
			return new SimpleStringProperty(param.getValue().getValue().getObjeto());
		});
		// setando valor para coluna ERRO
		JFXTreeTableColumn<Erro, String> colData = new JFXTreeTableColumn<>("DATA");
		colData.setPrefWidth(100);
		colData.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String> param) -> {
			return new SimpleStringProperty(
					new SimpleDateFormat("dd/MM/yyyy").format(param.getValue().getValue().getData()));
		});

		/**
		 * abre o dialog com a linha selecionada.
		 */
		this.tableCmLogScripts.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1) {
				this.openDialogErro();
			}
		});

		/**
		 * realiza consulta apertando ENTER
		 */
		this.txErro.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				this.findByFilter();
			}
		});

		/**
		 * realiza consulta apertando ENTER
		 */
		this.btnSearch.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				this.findByFilter();
			}
		});

		this.txFileName.setOnKeyPressed(e -> {
			if (e.getCode() == KeyCode.ENTER) {
				this.findByFilter();
			}
		});
		//
		this.txFolder.setText("Please select packages folder");
		this.txDefine.setText("Please select define.sql");
		//
		this.tableCmLogScripts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
		this.tableCmLogScripts.getColumns().setAll(colVersao, colErro, colObjeto, colData);
		//
		this.erroService = BeanUtil.getBean(ErroService.class);
		//
	}

	/*
	 * Abre o dialog do erro selecionado.
	 */
	public void openDialogErro() {
		if (!this.txFolder.getText().equals("Please select packages folder")
				&& !this.txDefine.getText().equals("Please select define.sql")) {
			// check the table's selected item and get selected item
			if (this.tableCmLogScripts.getSelectionModel().getSelectedItem() != null) {
				Erro erro = this.tableCmLogScripts.getSelectionModel().getSelectedItem().getValue();
				try {
					FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/gui/dialogErro.fxml"));
					Parent parent = fxmlLoader.load();
					ErroController erroController = fxmlLoader.<ErroController>getController();
					erroController.setSelectedErro(erro);

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
		} else {
			new Alert(AlertType.ERROR, "Select a folder containing package content and/or define.sql file!!",
					ButtonType.OK).showAndWait();
			if (this.txFolder.getText().equals("Please select packages folder")) {
				this.btnFolder.requestFocus();
				new Shake(this.btnFolder).play();
				Event.fireEvent(btnFolder,
						new MouseEvent(MouseEvent.MOUSE_CLICKED, btnFolder.getX(), btnFolder.getY(), btnFolder.getX(),
								btnFolder.getY(), MouseButton.PRIMARY, 1, true, true, true, true, true, true, true,
								true, true, true, null));
			} else {
				this.btnDefine.requestFocus();
				new Shake(this.btnDefine).play();
				Event.fireEvent(btnDefine,
						new MouseEvent(MouseEvent.MOUSE_CLICKED, btnDefine.getX(), btnDefine.getY(), btnDefine.getX(),
								btnDefine.getY(), MouseButton.PRIMARY, 1, true, true, true, true, true, true, true,
								true, true, true, null));
			}
		}
	}

	/**
	 * Executa consulta.
	 */
	private void findByFilter() {
		Task<?> task = new Task<Object>() {
			@Override
			protected Integer call() throws Exception {
				Install.loadStatus(frmCmLogScripts);
				data = FXCollections.observableList(erroService.findByFilter(txErro.getText().toUpperCase(),
						txDate.getEditor().getText(), txFileName.getText().toUpperCase(), txUsername.getText()));
				root = new RecursiveTreeItem<Erro>(data, RecursiveTreeObject::getChildren);
				return null;
			}

			@Override
			protected void succeeded() {
				Install.unLoadStatus(frmCmLogScripts);
				tableCmLogScripts.setShowRoot(false);
				tableCmLogScripts.setRoot(root);
			}
		};
		task.setOnFailed(e -> {
			Install.unLoadStatus(frmCmLogScripts);
			Throwable exception = e.getSource().getException();
			if (exception != null) {
				new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK).showAndWait();
			}
		});
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * clear fields
	 */
	private void clearFilters() {
		this.txDate.getEditor().setText("");
		this.txErro.setText("");
		this.txFileName.setText("");
		this.txUsername.setText("");
	}
}
