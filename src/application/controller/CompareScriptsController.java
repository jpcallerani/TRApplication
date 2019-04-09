//Cópia do cmlogscriptscontroller, alterando a funcionalidade, o que foi alterado
//será comentado
//Por enquanto apenas o botão define foi alterado para ler o xml
package application.controller;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import animatefx.animation.BounceInDown;
import application.entity.Erro;
import application.service.ErroService;
import application.util.Install;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gui.enumeration.enumTelas;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.Tooltip;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeTableColumn;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;
import javafx.util.Callback;

//por hora foi comentado a parte de pesquisar

@Controller
public class CompareScriptsController implements Initializable {

	private ErroService erroService;

	@FXML
	private AnchorPane frmCompare;

	@FXML
	private Pane filtroCmLogScripts;

	@FXML
	private JFXTreeTableView<User> tableCompare;

	@FXML
	private ImageView btnHome;

	@FXML
	private DatePicker txDate;

	@FXML
	private JFXTextField txErro;

	@FXML
	private JFXButton btnSearch;
	//
	@FXML
	private JFXButton btnRead;
	//
	@FXML
	private JFXButton btnClear;

	@FXML
	private FontAwesomeIcon btnFolder;

	@FXML
	private JFXTextField txFolder;

	// alterei a função do botao define para buscar um arquivo xml e le-lo
	@FXML
	private FontAwesomeIcon btnDefine;

	@FXML
	private JFXTextField txDefine;

	@FXML
	private JFXTextField txCodigo;

	@FXML
	private Tooltip ttPackage;

	@FXML
	private Tooltip ttDefine;

	@FXML
	private Label labelNome; // username vira nome

	@FXML
	private JFXTextField txNome;

	private ObservableList<Erro> data;

	private TreeItem<User> root;

	@FXML
	private ContextMenu cmDeletaLinha;

	@FXML
	private MenuItem miTeste;

	//
	@FXML
	private Label labelTipo;

	@FXML
	private JFXTextField txTipo;

	//

	@SuppressWarnings("unchecked")
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		this.tableCompare.addEventHandler(MouseEvent.MOUSE_CLICKED, new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent t) {
				if (t.getButton() == MouseButton.SECONDARY) {
					cmDeletaLinha.show(tableCompare, t.getScreenX(), t.getScreenY());
				}
			}
		});

		/*
		 * this.miTeste.setOnAction(e -> { // TODO Implementar deletar linha
		 * 
		 * Erro erro =
		 * this.tableCompare.getSelectionModel().getSelectedItem().getValue(); if
		 * (Install.
		 * alertYesAndNo("Are you sure that you want to delete the selected error?")) {
		 * this.erroService.deleteErro(erro); new Alert(AlertType.WARNING,
		 * "This function is not implemented!!", ButtonType.OK).showAndWait(); }
		 * 
		 * });
		 */

		/*
		 * botao ao clicar, seleciona-se um arquivo .xml e le-lo, por hora, cria-se uma
		 * lista com os objetos do xml e printa-os no Console
		 * 
		 */
		this.btnDefine.setOnMouseClicked(e -> {
			// ao selecionar o arquivo .xml irá lê-lo
			List<Objeto> users = null;

			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select your xml file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Xml/Excel", "*.xml", "*.xls","*.xlsx"));
			final File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				this.txDefine.setText(selectedFile.getAbsolutePath());
				Install.fileDefine = selectedFile.getAbsolutePath();

				//// funcao para ler o xml, está na classe scriptRead
				users = scriptRead.lerXML(selectedFile);
				/////

				for (int i = 0; i < users.size(); i++) {
					System.out.println("TAG = " + users.get(i).getId());
					System.out.println("nome = " + users.get(i).getNome());
					System.out.println("codSistema = " + users.get(i).getCodSistema());
					System.out.println("tipo = " + users.get(i).getTipo());
					System.out.println("erro = " + users.get(i).getErro());
					System.out.println("codigo = " + users.get(i).getCodigo());
					System.out.println("################################################");
				}

				
				JFXTreeTableColumn<User, String> colNome = new JFXTreeTableColumn<>("Nome do Objeto");
				colNome.setPrefWidth(150);
				colNome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User,String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String>param){
						return param.getValue().getValue().nome;
					}
				});
				
				JFXTreeTableColumn<User, String> colErro = new JFXTreeTableColumn<>("Erro");
				colErro.setPrefWidth(150);
				colErro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User,String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String>param){
						return param.getValue().getValue().erro;
					}
				});
				
				JFXTreeTableColumn<User, String> colTipo = new JFXTreeTableColumn<>("Tipo");
				colTipo.setPrefWidth(150);
				colTipo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<User,String>, ObservableValue<String>>(){
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<User, String>param){
						return param.getValue().getValue().tipo;
					}
				});
				
				ObservableList<User> usuarios = FXCollections.observableArrayList();
				for (int j = 0; j < users.size(); j++) {
					
					usuarios.add(new User(users.get(j).getNome(),users.get(j).getErro(),users.get(j).getTipo()));
					
				}
				
				final TreeItem<User> root = new RecursiveTreeItem<User>(usuarios, RecursiveTreeObject::getChildren);
				tableCompare.setRoot(root);
				tableCompare.setShowRoot(false);
				tableCompare.getColumns().setAll(colNome,colErro,colTipo);
				
				
				
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

		//

		/**
		 * Limpa o filtro
		 */
		this.btnClear.setOnMouseClicked(e -> {
			this.clearFilters();
		});

		/**
		 * Executa consulta
		 */
		/*
		 * this.btnSearch.setOnMouseClicked(e -> { this.findByFilter(); });
		 */
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

		/*
		 * // setando valor para coluna VERSAO JFXTreeTableColumn<Erro, String>
		 * colVersao = new JFXTreeTableColumn<>("VERSÃO"); colVersao.setPrefWidth(170);
		 * colVersao.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String>
		 * param) -> { return new
		 * SimpleStringProperty(param.getValue().getValue().getVersao()); }); // setando
		 * valor para coluna ERRO JFXTreeTableColumn<Erro, String> colErro = new
		 * JFXTreeTableColumn<>("ERRO"); colErro.setPrefWidth(800);
		 * colErro.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String>
		 * param) -> { return new
		 * SimpleStringProperty(param.getValue().getValue().getErro()); }); // setando
		 * valor para coluna OBJETO JFXTreeTableColumn<Erro, String> colObjeto = new
		 * JFXTreeTableColumn<>("OBJETO"); colObjeto.setPrefWidth(400);
		 * colObjeto.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String>
		 * param) -> { return new
		 * SimpleStringProperty(param.getValue().getValue().getObjeto()); }); // setando
		 * valor para coluna ERRO JFXTreeTableColumn<Erro, String> colData = new
		 * JFXTreeTableColumn<>("DATA"); colData.setPrefWidth(100);
		 * colData.setCellValueFactory((TreeTableColumn.CellDataFeatures<Erro, String>
		 * param) -> { return new SimpleStringProperty( new
		 * SimpleDateFormat("dd/MM/yyyy").format(param.getValue().getValue().getData()))
		 * ; });
		 */

		///////////////
		/*
		 * /** abre o dialog com a linha selecionada.
		 */
		/*
		 * this.tableCompare.setOnMouseClicked(e -> { if (e.getClickCount() > 1) {
		 * this.openDialogErro(); } });
		 */
		/**
		 * realiza consulta apertando ENTER
		 */
		/*
		 * this.txErro.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) {
		 * this.findByFilter(); } });
		 */
		/*
		 * /** realiza consulta apertando ENTER
		 */
		/*
		 * this.btnSearch.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) {
		 * this.findByFilter(); } });
		 * 
		 * this.txCodigo.setOnKeyPressed(e -> { if (e.getCode() == KeyCode.ENTER) {
		 * this.findByFilter(); } }); //
		 */

		/*
		 * this.txFolder.setText("Please select packages folder");
		 * this.txDefine.setText("Please select XML file"); //
		 * this.tableCompare.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE
		 * ); this.tableCompare.getColumns().setAll(colVersao, colErro, colObjeto,
		 * colData); // this.erroService = BeanUtil.getBean(ErroService.class); //
		 */
	}

	// Abre o dialog do erro selecionado.

	/*
	 * public void openDialogErro() { if
	 * (!this.txFolder.getText().equals("Please select packages folder") &&
	 * !this.txDefine.getText().equals("Please select define.sql")) { // check the
	 * table's selected item and get selected item if
	 * (this.tableCompare.getSelectionModel().getSelectedItem() != null) { Erro erro
	 * = this.tableCompare.getSelectionModel().getSelectedItem().getValue(); try {
	 * FXMLLoader fxmlLoader = new
	 * FXMLLoader(getClass().getResource("/gui/dialogErro.fxml")); Parent parent =
	 * fxmlLoader.load(); ErroController erroController =
	 * fxmlLoader.<ErroController>getController();
	 * erroController.setSelectedErro(erro);
	 * 
	 * Scene scene = new Scene(parent); Stage stage = new Stage();
	 * scene.setFill(Color.TRANSPARENT); // Fill our scene with nothing
	 * stage.initStyle(StageStyle.TRANSPARENT); // Important one!
	 * stage.initModality(Modality.APPLICATION_MODAL); stage.setScene(scene);
	 * 
	 * new FadeIn(parent).play();
	 * 
	 * stage.showAndWait();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); } } } else { new
	 * Alert(AlertType.ERROR,
	 * "Select a folder containing package content and/or define.sql file!!",
	 * ButtonType.OK).showAndWait(); if
	 * (this.txFolder.getText().equals("Please select packages folder")) {
	 * this.btnFolder.requestFocus(); new Shake(this.btnFolder).play();
	 * Event.fireEvent(btnFolder, new MouseEvent(MouseEvent.MOUSE_CLICKED,
	 * btnFolder.getX(), btnFolder.getY(), btnFolder.getX(), btnFolder.getY(),
	 * MouseButton.PRIMARY, 1, true, true, true, true, true, true, true, true, true,
	 * true, null)); } else { this.btnDefine.requestFocus(); new
	 * Shake(this.btnDefine).play(); Event.fireEvent(btnDefine, new
	 * MouseEvent(MouseEvent.MOUSE_CLICKED, btnDefine.getX(), btnDefine.getY(),
	 * btnDefine.getX(), btnDefine.getY(), MouseButton.PRIMARY, 1, true, true, true,
	 * true, true, true, true, true, true, true, null)); } } }
	 * 
	 * 
	 * /** Executa consulta.
	 * 
	 * 
	 * private void findByFilter() { Task<?> task = new Task<Object>() {
	 * 
	 * @Override protected Integer call() throws Exception {
	 * Install.loadStatus(frmCompare); data =
	 * FXCollections.observableList(erroService.findByFilter(txErro.getText().
	 * toUpperCase(), txDate.getEditor().getText(),
	 * txCodigo.getText().toUpperCase(), txNome.getText())); root = new
	 * RecursiveTreeItem<User>(data, RecursiveTreeObject::getChildren); return null;
	 * }
	 * 
	 * @Override protected void succeeded() { Install.unLoadStatus(frmCompare);
	 * tableCompare.setShowRoot(false); tableCompare.setRoot(root); } };
	 * task.setOnFailed(e -> { Install.unLoadStatus(frmCompare); Throwable exception
	 * = e.getSource().getException(); if (exception != null) { new
	 * Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK).showAndWait();
	 * } }); Thread th = new Thread(task); th.setDaemon(true); th.start(); }
	 */

	/**
	 * clear fields
	 */
	private void clearFilters() {
		this.txDate.getEditor().setText("");
		this.txErro.setText("");
		this.txCodigo.setText("");
		this.txNome.setText("");
		this.txTipo.setText("");
	}

	class User extends RecursiveTreeObject<User> {
		StringProperty nome;
		StringProperty erro;
		StringProperty tipo;

		public User(String nome, String erro, String tipo) {
			this.nome = new SimpleStringProperty(nome);
			this.erro = new SimpleStringProperty(erro);
			this.tipo = new SimpleStringProperty(tipo);
		}

	}

}
