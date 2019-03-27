package application.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextArea;

import application.entity.Erro;
import application.util.Install;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.control.ProgressBar;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

@Controller
public class taskErroController implements Initializable {

	private static final Image img = new Image(("/resources/images/checked.png"));
	private static final String copyTo = "execute/cm_log_scripts/";

	@FXML
	private AnchorPane rootPane;

	@FXML
	private AnchorPane shadowPane;

	@FXML
	private HBox hboxFind;

	@FXML
	private HBox hboxExec;

	@FXML
	private ProgressBar progressFind;

	@FXML
	private ImageView imgFind;

	@FXML
	private ProgressBar progressExec;

	@FXML
	private ImageView imgExec;

	@FXML
	private JFXTextArea txLog;

	@FXML
	private JFXButton btnClose;

	private Erro erro;

	private Task<?> taskFindFile;

	private Task<?> taskExecDatabase;

	private Task<?> taskWinInstall;

	StringBuilder executeResult;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {

		Platform.runLater(() -> {
			Install.fileToBeExecutedFrom = null;
			Install.fileToBeExecutedTo = null;
			if (!new File(copyTo).exists()) {
				new File(copyTo).mkdirs();
			}
			this.findFile();
		});

		btnClose.setOnMouseClicked(e -> {
			// close the dialog.
			Node source = (Node) e.getSource();
			Stage stage = (Stage) source.getScene().getWindow();
			stage.close();
		});
	}

	/**
	 * Procura o arquivo no diretório selecionado
	 * 
	 * @param folder
	 * @param file
	 * @throws IOException
	 */
	public void findFile() {
		taskFindFile = new Task<Object>() {
			boolean validFile = false;

			@Override
			protected Integer call() throws Exception {
				// desabilita componentes
				btnClose.setDisable(true);
				shadowPane.setCursor(Cursor.WAIT);
				hboxExec.setDisable(true);
				txLog.setCursor(Cursor.WAIT);

				// valida se o arquivo foi encontrado no caminho informado.
				validFile = Install.findFile(Install.filePackage, erro.getObjeto());

				if (validFile) {
					/**
					 * Se achou o arquivo, chama o método para executalo no banco.
					 */
					Platform.runLater(() -> {
						execDatabase();
					});
				} else {
					txLog.appendText("File \"" + erro.getObjeto() + "\" not found in " + Install.filePackage);
				}
				return null;
			}

			@Override
			protected void succeeded() {
				// habilita ou desabilita componentes
				progressFind.progressProperty().unbind();
				progressFind.setProgress(0);
				hboxFind.setDisable(true);
				if (validFile) {
					Install.fileToBeExecutedTo = copyTo + new File(Install.fileToBeExecutedFrom).getName();
					imgFind.setImage(img);
					hboxExec.setDisable(false);
					btnClose.setDisable(true);
					shadowPane.setCursor(Cursor.WAIT);
					txLog.setCursor(Cursor.WAIT);
				} else {
					hboxExec.setDisable(true);
					btnClose.setDisable(false);
					shadowPane.setCursor(Cursor.DEFAULT);
					txLog.setCursor(Cursor.DEFAULT);
				}

			}
		};

		progressFind.progressProperty().bind(taskFindFile.progressProperty());

		taskFindFile.setOnFailed(e -> {
			progressFind.progressProperty().unbind();
			progressFind.setProgress(0);
			shadowPane.setCursor(Cursor.DEFAULT);
			txLog.setCursor(Cursor.DEFAULT);
			hboxExec.setDisable(false);
			hboxFind.setDisable(false);
			btnClose.setDisable(false);
		});

		Thread th = new Thread(taskFindFile);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * Run the script on database.
	 */
	private void execDatabase() {

		taskExecDatabase = new Task<Object>() {
			@Override
			protected Integer call() throws Exception {
				try {
					List<String> variableErrors = findVariableError();
					if (variableErrors.size() > 0) {
						for (String string : variableErrors) {
							txLog.appendText("Variable " + string + " not found on define.sql!\n");
						}
						txLog.appendText("Check the files and try again!\n");
						txLog.appendText("File to be Install path: \"" + Install.fileToBeExecutedFrom + "\"\n");
						txLog.appendText("Define.sql path: \"" + Install.fileDefine + "\"\n");
						progressExec.progressProperty().unbind();
						progressExec.setProgress(0);
						shadowPane.setCursor(Cursor.DEFAULT);
						txLog.setCursor(Cursor.DEFAULT);
						hboxExec.setDisable(false);
						hboxFind.setDisable(false);
						btnClose.setDisable(false);
					} else {
						if (Install.reWriteFile(new File(Install.fileToBeExecutedTo).getAbsolutePath(), 
						    Install.readFile(new File(Install.fileToBeExecutedFrom).getAbsolutePath()))) {
							//FileUtils.forceDelete(new File(Install.fileToBeExecutedFrom));
							Install.copyFile(Install.fileDefine, copyTo + "define.sql");
							Install.createCharacterSetScripts(copyTo);
							Install.createOrdemInstall(copyTo, writeOrdemInstall());
							Install.createWinInstall(copyTo);
							Install.createWindowsStartInstallBat(copyTo);
							runExecWindows();
						}
					}

				} catch (IOException e) {
					txLog.appendText(e.getMessage());
				}
				return null;
			}

			@Override
			protected void succeeded() {

			}
		};

		taskExecDatabase.setOnFailed(e -> {
			shadowPane.setCursor(Cursor.DEFAULT);
			txLog.setCursor(Cursor.DEFAULT);
			hboxExec.setDisable(false);
			hboxFind.setDisable(false);
			btnClose.setDisable(false);
		});

		progressExec.progressProperty().bind(taskExecDatabase.progressProperty());

		Thread th = new Thread(taskExecDatabase);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * 
	 */
	public void runExecWindows() {
		taskWinInstall = new Task<Integer>() {
			@Override
			protected Integer call() throws Exception {
				try {
					executeResult = new StringBuilder();
					btnClose.setDisable(true);
					hboxFind.setDisable(true);
					shadowPane.setCursor(Cursor.WAIT);
					txLog.setCursor(Cursor.WAIT);
					ProcessBuilder pb = new ProcessBuilder(new File(copyTo).getAbsoluteFile() + "\\Instala_win.bat");
					pb = pb.directory(new File(copyTo).getAbsoluteFile());
					Process down = pb.start();
					BufferedReader reader = new BufferedReader(new InputStreamReader(down.getInputStream()));
					String line;
					while ((line = reader.readLine()) != null) {
						// System.out.println(line);
						executeResult.append(line + "\n"); // Has no effect
					}
					down.waitFor();
				} catch (IOException e) {
					txLog.appendText(e.getMessage() + "\n");
					return 1;
				}
				return 0;
			}

			@Override
			protected void succeeded() {
				txLog.appendText(executeResult.toString());
				progressExec.progressProperty().unbind();
				progressExec.setProgress(0);
				imgExec.setImage(img);
				btnClose.setDisable(false);
				shadowPane.setCursor(Cursor.DEFAULT);
				txLog.setCursor(Cursor.DEFAULT);
				hboxExec.setDisable(false);
				hboxFind.setDisable(false);
				try {
					Install.clearFolder(copyTo);
				} catch (IOException e) {
					txLog.appendText(e.getMessage() + "\n");
				}
			}
		};

		progressExec.progressProperty().bind(taskWinInstall.progressProperty());

		taskWinInstall.setOnFailed(e -> {
			try {
				Install.clearFolder(copyTo);
			} catch (IOException e1) {
				txLog.appendText(e1.getMessage() + "\n");
			}
		});
		new Thread(taskWinInstall).start();
	}

	/**
	 * Retorna lista de variável não encontrada no define.
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<String> findVariableError() throws IOException {
		List<String> erroVariavel = new ArrayList<>();
		List<String> scriptVariavel = new ArrayList<>();
		StringBuilder defineVariable = new StringBuilder();
		// find by oracle variables
		Pattern p = Pattern.compile("\\&&(.*?)\\s");
		// read the files to check incompatible variables
		StringBuilder fileContent = Install.readFile(Install.fileToBeExecutedFrom);
		// replace all non character minus &&
		fileContent = new StringBuilder(fileContent.toString().replaceAll("[^\\w^&]", " "));
		// find variables
		Matcher m1 = p.matcher(fileContent.toString());
		while (m1.find()) {
			erroVariavel.add(m1.group());
		}
		// find by all define variables
		p = Pattern.compile("\\DEFINE( .*?)\\s");
		StringBuilder fileDefine = Install.readFile(Install.fileDefine);
		fileDefine = new StringBuilder(fileDefine.toString().replaceAll("[^\\w^&]", " "));
		m1 = p.matcher(fileDefine.toString());
		while (m1.find()) {
			defineVariable.append("&&" + m1.group().toString().replaceAll("DEFINE ", "") + "\n");
		}
		for (String variavel : erroVariavel) {
			if (!defineVariable.toString().contains(variavel)) {
				if (!variavel.contains("&&VERSAO_ATUAL")) {
					scriptVariavel.add(variavel);
				}
			}
		}
		return scriptVariavel;
	}

	/**
	 * Escrevendo ordem instalação
	 * 
	 * @return
	 */
	private StringBuilder writeOrdemInstall() {
		StringBuilder ordemInstall = new StringBuilder();
		ordemInstall.append("define COD_SISTEMA_INSTALACAO = " + Install.cod_sistema + "");
		ordemInstall.append("\n");
		ordemInstall.append("Conn " + Install.username + "/" + Install.password + "@" + Install.tns + ";");
		ordemInstall.append("\n");
		ordemInstall.append("prompt ==========================================");
		ordemInstall.append("\n");
		ordemInstall.append("prompt Carregando caracterset.sql");
		ordemInstall.append("\n");
		ordemInstall.append("prompt ==========================================");
		ordemInstall.append("\n");
		ordemInstall.append("@@\"caracterset.sql\"");
		ordemInstall.append("\n\n");
		ordemInstall.append("@@\"" + new File(Install.fileToBeExecutedTo).getName() + "\"");
		ordemInstall.append("\n\n");
		ordemInstall.append("exit;");
		return ordemInstall;
	}

	/**
	 * @return the erro
	 */
	public Erro getErro() {
		return erro;
	}

	/**
	 * @param erro the erro to set
	 */
	public void setErro(Erro erro) {
		this.erro = erro;
	}
}
