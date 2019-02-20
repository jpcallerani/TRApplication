package application.util;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

import animatefx.animation.FadeIn;
import animatefx.animation.FadeInDown;
import animatefx.animation.FadeInLeft;
import application.main.Launch;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 * 
 * @author Jp-PC
 *
 */

public class Install {

	public static String username;
	public static String password;
	public static String tns;
	public static String cod_sistema = "";
	public static String url;
	public static String driver = "oracle.jdbc.driver.OracleDriver";
	public static Stage mainStage;
	public static AnchorPane mainRoot;
	public static String fileDefine;
	public static String filePackage;
	public static String fileToBeExecutedFrom;
	public static String fileToBeExecutedTo;

	private static double xOffset = 0;
	private static double yOffset = 0;

	/**
	 * põe a tela em status de load;
	 * 
	 * @param parent
	 */
	public static void loadStatus(AnchorPane parent) {
		parent.setCursor(Cursor.WAIT);
		for (int i = 0; i < parent.getChildren().size(); i++) {
			parent.getChildren().get(i).setDisable(true);
		}
	}

	/**
	 * tira a tela do status de load;
	 * 
	 * @param parent
	 */
	public static void unLoadStatus(AnchorPane parent) {
		parent.setCursor(Cursor.DEFAULT);
		for (int i = 0; i < parent.getChildren().size(); i++) {
			parent.getChildren().get(i).setDisable(false);
		}
	}

	/**
	 * Balança o componente
	 * 
	 * @param node
	 * @return
	 */
	public static TranslateTransition shakeTransition(Node node) {
		TranslateTransition shake_transition = new TranslateTransition(Duration.millis(50), node);
		shake_transition.setFromX(-10f);
		shake_transition.setByX(10f);
		shake_transition.setCycleCount(2);
		shake_transition.setAutoReverse(true);
		return shake_transition;
	}

	/**
	 * Faz piscar o componente.
	 * 
	 * @param node
	 * @return
	 */
	public static Timeline blinkTranstition(Node node) {
		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.05), evt -> node.setVisible(false)),
				new KeyFrame(Duration.seconds(0.1), evt -> node.setVisible(true)));
		timeline.setCycleCount(Animation.INDEFINITE);
		return timeline;
	}

	/**
	 * Função hover do botão home das páginas de filtro
	 * 
	 * @param node
	 */
	public static void homeIn(Node node) {
		String img = Install.class.getResource("/resources/images/back.png").toExternalForm();
		Image image = new Image(img);
		((ImageView) node).setImage(image);
		new FadeIn(node).play();
	}

	/**
	 * Função hover do botão home das páginas de filtro
	 * 
	 * @param node
	 */
	public static void homeOut(Node node) {
		String img = Install.class.getResource("/resources/images/home.png").toExternalForm();
		Image image = new Image(img);
		((ImageView) node).setImage(image);
		new FadeIn(node).play();
	}

	/**
	 * Função para carregar telas
	 * 
	 * @param nameFXML
	 */
	public static void loadScene(String fXML) {

		AnchorPane newRoot = null;
		try {
			newRoot = FXMLLoader.load(Install.class.getResource(fXML));
		} catch (IOException ex) {

		}

		Scene lastScene = Install.mainStage.getScene();
		lastScene = new Scene(newRoot);
		Install.mainStage.setScene(lastScene);

		if (fXML.contains("Main")) {
			new FadeInDown(newRoot).play();
		} else {
			new FadeInLeft(newRoot).play();
		}
		// MainController.root = newRoot;
	}

	/**
	 * Faz o stage ser arrastado.
	 * 
	 * @param node
	 */
	public static void makeStageDrageable(Node node) {
		node.setOnMousePressed(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				xOffset = event.getSceneX();
				yOffset = event.getSceneY();
			}
		});
		node.setOnMouseDragged(new EventHandler<MouseEvent>() {
			@Override
			public void handle(MouseEvent event) {
				Launch.stage.setX(event.getScreenX() - xOffset);
				Launch.stage.setY(event.getScreenY() - yOffset);
				Launch.stage.setOpacity(0.7f);
			}
		});
		node.setOnDragDone((e) -> {
			Launch.stage.setOpacity(1.0f);
		});
		node.setOnMouseReleased((e) -> {
			Launch.stage.setOpacity(1.0f);
		});
	}

	/**
	 * Função para copiar um arquivo.
	 * 
	 * @param in  Arquivo de entrada.
	 * @param out Arquivo de saída.
	 * @throws IOException
	 * @throws             java.io.IOException
	 */
	@SuppressWarnings("resource")
	public static boolean copyFile(String in, String out) throws IOException {
		boolean fileCopy = false;

		FileChannel inChannel = null;
		FileChannel outChannel;

		inChannel = new FileInputStream(in).getChannel();
		outChannel = new FileOutputStream(out).getChannel();

		inChannel.transferTo(0, inChannel.size(), outChannel);

		if (inChannel != null) {
			inChannel.close();
		}

		if (outChannel != null) {
			outChannel.close();
		}

		fileCopy = true;
		return fileCopy;
	}


	/**
	 * Procura o arquivo no diretório selecionado
	 * 
	 * @param folder
	 * @param file
	 * @throws IOException
	 */
	@SuppressWarnings("rawtypes")
	public static boolean findFile(String folder, String pfile) {
		boolean fileFound = false;
		pfile = pfile.substring(pfile.lastIndexOf("\\") + 1);
		try {
			String[] extensions = { "sql" };
			boolean recursive = true;

			Collection files = FileUtils.listFiles(new File(folder), extensions, recursive);

			for (Iterator iterator = files.iterator(); iterator.hasNext();) {
				File file = (File) iterator.next();
				if (pfile.equalsIgnoreCase(file.getName())) {
					Install.fileToBeExecutedFrom = file.getAbsolutePath();
					fileFound = true;
					break;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileFound;
	}

	/**
	 * Cria bat com settings iniciais antes de chamar executavel do produto.
	 * 
	 * @param extractionpoint
	 * @param execname
	 * @throws IOException
	 */
	public static void createWindowsStartInstallBat(String createpoint) throws IOException {
		File v_file_startinstall = new File(createpoint, "startinstall.bat");
		BufferedWriter v_bufferedwriter_startinstall = null;

		try {
			if (!v_file_startinstall.exists()) {
				v_file_startinstall.createNewFile();
			}
			v_bufferedwriter_startinstall = new BufferedWriter(new FileWriter(v_file_startinstall));

			v_bufferedwriter_startinstall.write("echo off");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("cls");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("echo Starting...");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("echo ==============================================");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("echo.");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("set nls_lang=AMERICAN_AMERICA.WE8ISO8859P1");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("echo on");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("CALL Instala_win.bat");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.close();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				v_bufferedwriter_startinstall.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Cria o arquivo caracterset.sql para adequação do character set da base
	 * 
	 * @param extractionpoint
	 * @throws IOException
	 */
	public static void createCharacterSetScripts(String createpoint) throws IOException {
		File v_file_startinstall = new File(createpoint, "caracterset.sql");
		BufferedWriter v_bufferedwriter_startinstall = null;

		try {
			if (!v_file_startinstall.exists()) {
				v_file_startinstall.createNewFile();
			}
			v_bufferedwriter_startinstall = new BufferedWriter(new FileWriter(v_file_startinstall));

			v_bufferedwriter_startinstall.write("declare");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("--");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("v_s_CharacterSet VARCHAR2(160);");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("--");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("begin");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  --");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  select VALUE");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("    into v_s_CharacterSet");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("    from NLS_DATABASE_PARAMETERS");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("   where PARAMETER = 'NLS_CHARACTERSET';");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  --");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  if v_s_CharacterSet in ('UTF8', 'AL32UTF8') then");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("    --");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("    execute immediate 'alter session set nls_length_semantics=char';");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("    --");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  end if;");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("  --");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("end;");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.write("/");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();

			v_bufferedwriter_startinstall.close();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				v_bufferedwriter_startinstall.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Popula combo com cod_Sistema
	 */
	public static ObservableList<String> listCodSistema() {
		List<String> choices = new ArrayList<>();
		choices.add("0");
		choices.add("2");
		choices.add("3");
		choices.add("6");
		choices.add("9");
		choices.add("10");
		choices.add("11");
		choices.add("21");
		choices.add("27");
		choices.add("28");
		choices.add("500");
		choices.add("1000");

		ObservableList<String> options = FXCollections.observableArrayList(choices);

		return options;
	}

	/**
	 * Cria ordem instalação
	 * 
	 * @param createpoint
	 * @param content
	 * @throws IOException
	 */
	public static void createOrdemInstall(String createpoint, StringBuilder content) throws IOException {
		StringBuilder stringBuilderNovo = new StringBuilder();
		File v_file_startinstall = new File(createpoint, "ordem_instalacao_win.sql");
		BufferedWriter v_bufferedwriter_startinstall = null;

		try {
			if (!v_file_startinstall.exists()) {
				v_file_startinstall.createNewFile();
			}
			v_bufferedwriter_startinstall = new BufferedWriter(new FileWriter(v_file_startinstall));

			stringBuilderNovo.append("@\".\\define.sql\" \n");
			stringBuilderNovo.append(content);
			v_bufferedwriter_startinstall.write(stringBuilderNovo.toString());
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();

			v_bufferedwriter_startinstall.close();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				v_bufferedwriter_startinstall.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Cria Instala_win.bat
	 * 
	 * @param createpoint
	 * @throws IOException
	 */
	public static void createWinInstall(String createpoint) throws IOException {
		File v_file_startinstall = new File(createpoint, "Instala_win.bat");
		BufferedWriter v_bufferedwriter_startinstall = null;

		try {
			if (!v_file_startinstall.exists()) {
				v_file_startinstall.createNewFile();
			}
			v_bufferedwriter_startinstall = new BufferedWriter(new FileWriter(v_file_startinstall));

			v_bufferedwriter_startinstall.write("sqlplus /nolog @ordem_instalacao_win.sql");
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.close();
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				v_bufferedwriter_startinstall.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * Deleta pasta selecionada
	 * 
	 * @param path
	 * @throws IOException
	 */
	public static void clearFolder(String pathname) throws IOException {
		FileUtils.cleanDirectory(new File(pathname));
	}

	/**
	 * 
	 * @param file
	 * @throws IOException
	 */
	public static StringBuilder readFile(String file) throws IOException {
		StringBuilder fileContent = new StringBuilder();
		LineIterator it = FileUtils.lineIterator(new File(file), "UTF-8");
		try {
			while (it.hasNext()) {
				fileContent.append(it.nextLine().toUpperCase() + "\n");
			}
		} finally {
			LineIterator.closeQuietly(it);
		}
		return fileContent;
	}

	/**
	 * Rewrite the file removing oracle variable VERSAO_ATUAL_*
	 * 
	 * @param createpoint
	 * @param content
	 * @throws IOException
	 */
	public static boolean reWriteFile(String createpoint, StringBuilder content) throws IOException {
		boolean fileCreated = false;
		File v_file_startinstall = new File(createpoint);
		BufferedWriter v_bufferedwriter_startinstall = null;

		try {
			if (!v_file_startinstall.exists()) {
				v_file_startinstall.createNewFile();
			}
			v_bufferedwriter_startinstall = new BufferedWriter(new FileWriter(v_file_startinstall));

			content = new StringBuilder(
					content.toString().replaceAll("&&VERSAO_ATUAL\\w*?.*?\\'", "EXECUTED_BY_SYSTEM'"));
			v_bufferedwriter_startinstall.write(content.toString());
			v_bufferedwriter_startinstall.newLine();
			v_bufferedwriter_startinstall.newLine();

			v_bufferedwriter_startinstall.close();

			fileCreated = true;
		} catch (IOException e) {
			throw e;
		} finally {
			try {
				v_bufferedwriter_startinstall.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return fileCreated;
	}
}
