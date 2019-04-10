//Cópia do cmlogscriptscontroller, alterando a funcionalidade, o que foi alterado
//será comentado
//Por enquanto apenas o botão define foi alterado para ler o xml
package application.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.netbeans.lib.cvsclient.command.CommandAbortedException;
import org.netbeans.lib.cvsclient.command.CommandException;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.springframework.stereotype.Controller;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import com.jfoenix.controls.JFXTreeTableColumn;
import com.jfoenix.controls.JFXTreeTableView;
import com.jfoenix.controls.RecursiveTreeItem;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

import animatefx.animation.BounceInDown;
import application.entity.Objeto;
import application.util.CVSUtil;
import application.util.Install;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import gui.enumeration.enumTelas;
import javafx.application.Platform;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
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

@Controller
public class CompareScriptsController implements Initializable {

	@FXML
	private AnchorPane								frmCompare;

	@FXML
	private Pane											filtroCmLogScripts;

	@FXML
	private JFXTreeTableView<Objeto>	tableCompare;

	@FXML
	private ImageView									btnHome;

	@FXML
	private DatePicker								txDate;

	@FXML
	private JFXTextField							txErro;

	@FXML
	private JFXButton									btnSearch;
	//
	@FXML
	private JFXButton									btnRead;
	//
	@FXML
	private JFXButton									btnClear;

	@FXML
	private FontAwesomeIcon						btnFolder;

	@FXML
	private JFXTextField							txFolder;

	@FXML
	private FontAwesomeIcon						btnDefine;

	@FXML
	private JFXTextField							txDefine;

	@FXML
	private JFXTextField							txCodigo;

	@FXML
	private Tooltip										ttPackage;

	@FXML
	private Tooltip										ttDefine;

	@FXML
	private Label											labelNome;					// username
																												// vira
																												// nome

	@FXML
	private JFXTextField							txNome;

	@FXML
	private ContextMenu								cmDeletaLinha;

	@FXML
	private MenuItem									miTeste;

	//
	@FXML
	private Label											labelTipo;

	@FXML
	private JFXTextField							txTipo;

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

		this.btnDefine.setOnMouseClicked(e -> {
			// ao selecionar o arquivo .xml irá lê-lo
			List<Objeto> objetos = null;
			javafx.scene.Node source = (javafx.scene.Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Select your xml file");
			fileChooser.getExtensionFilters().addAll(new ExtensionFilter("Xml/Excel", "*.xml", "*.xls", "*.xlsx"));
			final File selectedFile = fileChooser.showOpenDialog(stage);
			if (selectedFile != null) {
				this.txDefine.setText(selectedFile.getAbsolutePath());

				//// funcao para ler o xml, está na classe scriptRead
				if (FilenameUtils.getExtension(selectedFile.getAbsolutePath()).equalsIgnoreCase("xml")) {
					objetos = this.lerXML(selectedFile);
				} else {
					objetos = this.LerExcel(selectedFile);
				}
				/////
				JFXTreeTableColumn<Objeto, String> colCodSistema = new JFXTreeTableColumn<>("Sistema");
				colCodSistema.setPrefWidth(100);
				colCodSistema.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Objeto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Objeto, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getCodSistema());
					}
				});

				JFXTreeTableColumn<Objeto, String> colNome = new JFXTreeTableColumn<>("Nome do Objeto");
				colNome.setPrefWidth(350);
				colNome.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Objeto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Objeto, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getNome());
					}
				});

				JFXTreeTableColumn<Objeto, String> colErro = new JFXTreeTableColumn<>("Erro");
				colErro.setPrefWidth(200);
				colErro.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Objeto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Objeto, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getErro());
					}
				});

				JFXTreeTableColumn<Objeto, String> colTipo = new JFXTreeTableColumn<>("Tipo");
				colTipo.setPrefWidth(300);
				colTipo.setCellValueFactory(new Callback<TreeTableColumn.CellDataFeatures<Objeto, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(TreeTableColumn.CellDataFeatures<Objeto, String> param) {
						return new SimpleStringProperty(param.getValue().getValue().getTipo());
					}
				});

				ObservableList<Objeto> obObjetos = FXCollections.observableArrayList();

				for (int i = 0; i < objetos.size(); i++) {
					obObjetos.add(objetos.get(i));
				}

				final TreeItem<Objeto> root = new RecursiveTreeItem<Objeto>(obObjetos, RecursiveTreeObject::getChildren);
				tableCompare.setRoot(root);
				tableCompare.setShowRoot(false);
				tableCompare.getColumns().setAll(colCodSistema,colNome, colErro, colTipo);

			}

		});

		/**
		 * find package folder
		 */
		this.btnFolder.setOnMouseClicked(e -> {
			javafx.scene.Node source = (javafx.scene.Node) e.getSource();
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
		 * abre o dialog com a linha selecionada.
		 */
		this.tableCompare.setOnMouseClicked(e -> {
			if (e.getClickCount() > 1) {
				if (this.tableCompare.getSelectionModel().getSelectedItem() != null) {
					Objeto objeto = this.tableCompare.getSelectionModel().getSelectedItem().getValue();
					this.executeCompare(objeto);
				}
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
	}

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

	// cria uma lista de objetos referentes ao do xml lido
	private List<Objeto> lerXML(File file) {
		Objeto objeto1 = new Objeto();
		List<Objeto> v_list_usuario = new ArrayList<Objeto>();

		try {
			//
			InputStream inputStream = new FileInputStream(file);
			Reader reader = new InputStreamReader(inputStream, "UTF-8");
			InputSource is = new InputSource(reader);
			is.setEncoding("UTF-8");
			//

			// processo para ler
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(is);
			doc.getDocumentElement().normalize();

			// obtem os nodes para le-los, versao para obter o id e nodes para os
			// objetos
			System.out.println("root of xml file" + doc.getDocumentElement().getNodeName());
			NodeList versao = doc.getElementsByTagName("Versao");
			NodeList nodes = doc.getElementsByTagName("Objeto");
			System.out.println("==========================");

			System.out.println("id: " + getValue("id", (Element) versao.item(0)));
			System.out.println();

			// loop para ler todos os objetos
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = (Node) nodes.item(i);

				if (node.getNodeType() == Node.ELEMENT_NODE) {
					Element element = (Element) node;

					objeto1 = new Objeto();

					String nome = element.getElementsByTagName("nome").item(0).getTextContent();
					String codSistema = element.getElementsByTagName("codSistema").item(0).getTextContent();
					String tipo = element.getElementsByTagName("tipo").item(0).getTextContent();
					String erro = element.getElementsByTagName("erro").item(0).getTextContent();
					String codigo = element.getElementsByTagName("codigo").item(0).getTextContent();

					///
					/*
					 * as tags do xml estao todas como BG, a funçao tagCerta corrige-as
					 * com base no codSistema delas
					 */
					String id = tagCerta(codSistema, versao);
					objeto1.setId(id);

					// seta o valor do nome, codsistema,erro e codigo no objeto1 que em
					// seguida
					// é adicionado na lista
					objeto1.setNome(nome);
					objeto1.setCodSistema(codSistema);
					objeto1.setTipo(tipo);
					objeto1.setErro(erro);
					objeto1.setCodigo(codigo);
					//
					v_list_usuario.add(objeto1);

				}
			}

		} catch (Exception e) {
			new Alert(AlertType.ERROR, "XML invalid format!! -> " + e.getMessage(), ButtonType.OK).showAndWait();
		}
		return v_list_usuario;

	}

	// funcao para obter o valor dos nodes
	private String getValue(String tag, Element element) {
		NodeList nodes = element.getElementsByTagName(tag).item(0).getChildNodes();
		Node node = (Node) nodes.item(0);
		return node.getNodeValue();

	}

	// funcao para obter a tag certa
	private String tagCerta(String cod, NodeList version) {
		String id2 = "";
		String id1 = "";

		if (cod.equals("0")) {
			id2 = "tag_" + getValue("id", (Element) version.item(0));
		} else if (cod.equals("2")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "ES");
		} else if (cod.equals("3")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "DB");
		} else if (cod.equals("9")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "IS");
		} else if (cod.equals("10")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "CI");
		} else if (cod.equals("11")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "CE");
		} else if (cod.equals("21")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "BS");
		} else if (cod.equals("500")) {
			id1 = getValue("id", (Element) version.item(0));
			id2 = "tag_" + id1.replace("BG", "IO");
		}

		return id2;
	}

	/**
	 * Executa a comparação da linha selecionada;
	 */
	private void executeCompare(Objeto objeto) {
		Task<?> task = new Task<Object>() {
			@Override
			protected Integer call() throws Exception {
				Install.loadStatus(frmCompare);
				try {
					// Cria arquivo de origem para comparação;
					File compareFolder = new File("Compare");

					// Cria a pasta caso não exista;
					if (!compareFolder.exists()) {
						compareFolder.mkdirs();
					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								try {
									FileUtils.forceDelete(compareFolder);
								} catch (IOException e) {
								}
							}
						});					
					}

					// Cria o arquivo de destino vindo pelo xml ou excel;
					File toFile = new File(compareFolder + "\\" + objeto.getNome() + ".sql");

					// Se o arquivo foi do XML/XLS foi criado com sucesso continua a execução;
					Install.createFile(toFile, objeto.getCodigo());

					// Busca arquivo CVS;
					File fromFile = findFileCVS(objeto);

					// se encontrou o arquivo faz o checkout;
					if (fromFile != null) {

						try {
							File fromFileLocal = checkoutFileFromCVS(fromFile, objeto);
							
							File examDiff = new File("Diff");
							
							 Runtime.getRuntime().exec(examDiff.getAbsolutePath() + "\\ExamDiff.exe  " + fromFileLocal.getAbsolutePath() + " " + toFile.getAbsolutePath());
							
						} catch (Exception e) {
							new Alert(AlertType.ERROR, "Error in CVS checkout -> " + e.getMessage(), ButtonType.OK).showAndWait();
						}

					} else {
						Platform.runLater(new Runnable() {
							@Override
							public void run() {
								new Alert(AlertType.ERROR, objeto.getNome() + " -> Not Found!!", ButtonType.OK).showAndWait();
							}
						});
					}
				} catch (Exception e) {
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							new Alert(AlertType.ERROR, e.getMessage(), ButtonType.OK).showAndWait();
						}
					});
				}

				return null;
			}

			@Override
			protected void succeeded() {
				Install.unLoadStatus(frmCompare);
			}
		};
		task.setOnFailed(e -> {
			Install.unLoadStatus(frmCompare);
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
	 * Método para ler Excel
	 * 
	 * @param arquivo
	 * @return
	 * @throws IOException
	 * @throws EncryptedDocumentException
	 * @throws InvalidFormatException
	 */
	private List<Objeto> LerExcel(File arquivo) {

		List<Objeto> objetos = new ArrayList<>();

		try {
			// Creating a Workbook from an Excel file (.xls or .xlsx)
			Workbook workbook = WorkbookFactory.create(arquivo);

			// Getting the Sheet at index zero
			Sheet sheet = workbook.getSheetAt(0);

			// Create a DataFormatter to format and get each cell's value as String
			DataFormatter dataFormatter = new DataFormatter();

			String cellValue;
			for (Row row : sheet) {
				Objeto obj = new Objeto();
				for (Cell cell : row) {
					if (row.getRowNum() > 0) {
						if (cell.getColumnIndex() > 0) {
							cellValue = dataFormatter.formatCellValue(cell);
							// get cod sistema;
							if (cell.getColumnIndex() == 1) {
								obj.setId(cellValue);
								// get tipo objeto;
							} else if (cell.getColumnIndex() == 2) {
								obj.setTipo(cellValue);
								// get nome objeto;
							} else if (cell.getColumnIndex() == 3) {
								obj.setNome(cellValue);
								// get erro objeto;
							} else if (cell.getColumnIndex() == 4) {
								obj.setErro(cellValue);
								// get código objeto;
							} else if (cell.getColumnIndex() == 5) {
								obj.setCodigo(cellValue);
							}
						}
					} else {
						if (cell.getColumnIndex() == 1) {
							cellValue = dataFormatter.formatCellValue(cell);
							if (!cellValue.toUpperCase().contains("OWNER")) {
								throw new Exception();
							}
						}
					}
				}
				objetos.add(obj);
			}
		} catch (Exception e) {
			new Alert(AlertType.ERROR, "Excel invalid format!! -> " + e.getMessage(), ButtonType.OK).showAndWait();
		}
		return objetos;
	}

	/**
	 * Busca o ,v dentro do repositório do CVS;
	 * 
	 * @return
	 */
	private File findFileCVS(Objeto objeto) {
		File arquivoEncontrado = null;
		String repositorio = "";
		try {

			repositorio = "W:\\" + Install.returnRepoFromCodSistema(objeto.getCodSistema()) + "\\" + Install.returnModuloFromCodSistema(objeto.getCodSistema());

			// Busca o arquivo na pasta PLSQL do CVS
			arquivoEncontrado = Install.findFile(repositorio + "\\PLSQL", objeto.getNome() + ".sql,v");

			// se não achar busca na pasta de Integrações
			if (arquivoEncontrado == null) {
				arquivoEncontrado = Install.findFile(repositorio + "\\Integracoes", objeto.getNome() + ".sql,v");
			}

			// se não achar busca na pasta de Interfaces
			if (arquivoEncontrado == null) {
				arquivoEncontrado = Install.findFile(repositorio + "\\Interfaces", objeto.getNome() + ".sql,v");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return arquivoEncontrado;
	}

	/**
	 * 
	 * @param arquivo
	 * @throws Exception
	 * @throws CommandException
	 * @throws CommandAbortedException
	 */
	private File checkoutFileFromCVS(File arquivo, Objeto objeto) throws CommandAbortedException, CommandException, Exception {

		File arquivoEncontrado = null;

		// Monta o CVSRoot;
		String cvsRoot = ":pserver:jopaulo@cvs01.desenv.cps.sfw.com.br:/export01/cvs/" + Install.returnRepoFromCodSistema(objeto.getCodSistema());

		// Cria pasta onde serão feitos os checkouts;
		File temp = new File("temp");
		if (!temp.exists()) {
			temp.mkdirs();
		}

		//
		CVSUtil cvs = new CVSUtil(cvsRoot, temp.getAbsolutePath(), "SOFTWAYSA2014");

		// monta arquivo para ser feito o checkout;
		String arquivoCheckout = arquivo.getAbsolutePath().substring(arquivo.getAbsolutePath().indexOf(Install.returnModuloFromCodSistema(objeto.getCodSistema())), arquivo.getAbsolutePath().length() - 2);
		cvs.checkOut(arquivoCheckout, objeto.getId());

		// busca se o arquivo foi baixado com sucesso;
		arquivoEncontrado = Install.findFile(temp.getAbsolutePath(), objeto.getNome().toLowerCase() + ".sql");
		
		// Caminho do arquivo destino
		File arquivoDestino = new File("compare\\" + objeto.getNome().toLowerCase() + "_VB.sql");

		// Copia a pasta do checkout para pasta de compare;
		Install.copyFile(arquivoEncontrado.getAbsolutePath(), arquivoDestino.getAbsolutePath());

		// limpa a pasta de checkout;
		try {
			FileUtils.forceDelete(temp);
		} catch (Exception e) {
		}

		return arquivoDestino;
	}
}
