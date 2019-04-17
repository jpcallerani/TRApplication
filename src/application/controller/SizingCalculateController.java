package application.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import animatefx.animation.BounceInDown;
import application.entity.DaSizingEstimado;
import application.entity.DaSizingQuestionario;
import application.service.DaSizingEstimadoService;
import application.service.DaSizingQuestionarioService;
import application.util.BeanUtil;
import application.util.Install;
import gui.enumeration.enumTelas;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Accordion;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

public class SizingCalculateController implements Initializable {

	private DaSizingQuestionarioService DaSizingQuestionarioService;
	private DaSizingEstimadoService DaSizingEstimadoService;

	@FXML
	private AnchorPane frmSizingCalculate;

	@FXML
	private Pane filtroSizingCalculate;

	@FXML
	private ImageView btnHome;

	@FXML
	private Accordion accorSizing;

	@FXML
	private TitledPane tpNucleoComum;

	@FXML
	private AnchorPane anchorQuestionario;

	@FXML
	private HBox hboxExportador;

	@FXML
	private Label lbl01;

	@FXML
	private JFXTextField tx01yesno;

	@FXML
	private JFXTextField tx01Inicial;

	@FXML
	private JFXTextField tx01Percentual;

	@FXML
	private JFXTextField tx01Mensal;

	@FXML
	private HBox hboxImportador;

	@FXML
	private Label lbl02;

	@FXML
	private JFXTextField tx02yesno;

	@FXML
	private JFXTextField tx02Inicial;

	@FXML
	private JFXTextField tx02Percentual;

	@FXML
	private JFXTextField tx02Mensal;

	@FXML
	private HBox hboxClienteInter;

	@FXML
	private Label lbl34;

	@FXML
	private JFXTextField tx34yesno;

	@FXML
	private JFXTextField tx34Inicial;

	@FXML
	private JFXTextField tx34Percentual;

	@FXML
	private JFXTextField tx34Mensal;

	@FXML
	private HBox hboxFabricante;

	@FXML
	private Label lbl03;

	@FXML
	private JFXTextField tx03yesno;

	@FXML
	private JFXTextField tx03Inicial;

	@FXML
	private JFXTextField tx03Percentual;

	@FXML
	private JFXTextField tx03Mensal;

	@FXML
	private HBox hboxParceiro;

	@FXML
	private Label lbl04;

	@FXML
	private JFXTextField tx04yesno;

	@FXML
	private JFXTextField tx04Inicial;

	@FXML
	private JFXTextField tx04Percentual;

	@FXML
	private JFXTextField tx04Mensal;

	@FXML
	private HBox hboxItensInter;

	@FXML
	private Label lbl05;

	@FXML
	private JFXTextField tx05yesno;

	@FXML
	private JFXTextField tx05Inicial;

	@FXML
	private JFXTextField tx05Percentual;

	@FXML
	private JFXTextField tx05Mensal;

	@FXML
	private HBox hboxItensNac;

	@FXML
	private Label lbl20;

	@FXML
	private JFXTextField tx20yesno;

	@FXML
	private JFXTextField tx20Inicial;

	@FXML
	private JFXTextField tx20Percentual;

	@FXML
	private JFXTextField tx20Mensal;

	@FXML
	private HBox hboxDespesa;

	@FXML
	private Label lbl06;

	@FXML
	private JFXTextField tx06yesno;

	@FXML
	private JFXTextField tx06Inicial;

	@FXML
	private JFXTextField tx06Percentual;

	@FXML
	private JFXTextField tx06Mensal;

	@FXML
	private Label lbl07;

	@FXML
	private JFXTextField tx07yesno;

	@FXML
	private JFXTextField tx07Inicial;

	@FXML
	private JFXTextField tx07Percentual;

	@FXML
	private JFXTextField tx07Mensal;

	@FXML
	private Label lbl08;

	@FXML
	private JFXTextField tx08yesno;

	@FXML
	private JFXTextField tx08Inicial;

	@FXML
	private JFXTextField tx08Percentual;

	@FXML
	private JFXTextField tx08Mensal;

	@FXML
	private Label lbl09;

	@FXML
	private JFXTextField tx09yesno;

	@FXML
	private JFXTextField tx09Inicial;

	@FXML
	private JFXTextField tx09Percentual;

	@FXML
	private JFXTextField tx09Mensal;

	@FXML
	private Label lbl10;

	@FXML
	private JFXTextField tx10yesno;

	@FXML
	private JFXTextField tx10Inicial;

	@FXML
	private JFXTextField tx10Percentual;

	@FXML
	private JFXTextField tx10Mensal;

	@FXML
	private Label lbl11;

	@FXML
	private JFXTextField tx11yesno;

	@FXML
	private JFXTextField tx11Inicial;

	@FXML
	private JFXTextField tx11Percentual;

	@FXML
	private JFXTextField tx11Mensal;

	@FXML
	private Label lbl12;

	@FXML
	private JFXTextField tx12yesno;

	@FXML
	private JFXTextField tx12Inicial;

	@FXML
	private JFXTextField tx12Percentual;

	@FXML
	private JFXTextField tx12Mensal;

	@FXML
	private Label lbl13;

	@FXML
	private JFXTextField tx13yesno;

	@FXML
	private JFXTextField tx13Inicial;

	@FXML
	private JFXTextField tx13Percentual;

	@FXML
	private JFXTextField tx13Mensal;

	@FXML
	private Label lbl14;

	@FXML
	private JFXTextField tx14yesno;

	@FXML
	private JFXTextField tx14Inicial;

	@FXML
	private JFXTextField tx14Percentual;

	@FXML
	private JFXTextField tx14Mensal;

	@FXML
	private Label lbl15;

	@FXML
	private JFXTextField tx15yesno;

	@FXML
	private JFXTextField tx15Inicial;

	@FXML
	private JFXTextField tx15Percentual;

	@FXML
	private JFXTextField tx15Mensal;

	@FXML
	private Label lbl16;

	@FXML
	private JFXTextField tx16yesno;

	@FXML
	private JFXTextField tx16Inicial;

	@FXML
	private JFXTextField tx16Percentual;

	@FXML
	private JFXTextField tx16Mensal;

	@FXML
	private Label lbl17;

	@FXML
	private JFXTextField tx17yesno;

	@FXML
	private JFXTextField tx17Inicial;

	@FXML
	private JFXTextField tx17Percentual;

	@FXML
	private JFXTextField tx17Mensal;

	@FXML
	private Label lbl18;

	@FXML
	private JFXTextField tx18yesno;

	@FXML
	private JFXTextField tx18Inicial;

	@FXML
	private JFXTextField tx18Percentual;

	@FXML
	private JFXTextField tx18Mensal;

	@FXML
	private Label lbl19;

	@FXML
	private JFXTextField tx19yesno;

	@FXML
	private JFXTextField tx19Inicial;

	@FXML
	private JFXTextField tx19Percentual;

	@FXML
	private JFXTextField tx19Mensal;

	@FXML
	private Label lbl21;

	@FXML
	private JFXTextField tx21yesno;

	@FXML
	private JFXTextField tx21Inicial;

	@FXML
	private JFXTextField tx21Percentual;

	@FXML
	private JFXTextField tx21Mensal;

	@FXML
	private Label lbl22;

	@FXML
	private JFXTextField tx22yesno;

	@FXML
	private JFXTextField tx22Inicial;

	@FXML
	private JFXTextField tx22Percentual;

	@FXML
	private JFXTextField tx22Mensal;

	@FXML
	private Label lbl23;

	@FXML
	private JFXTextField tx23yesno;

	@FXML
	private JFXTextField tx23Inicial;

	@FXML
	private JFXTextField tx23Percentual;

	@FXML
	private JFXTextField tx23Mensal;

	@FXML
	private Label lbl24;

	@FXML
	private JFXTextField tx24yesno;

	@FXML
	private JFXTextField tx24Inicial;

	@FXML
	private JFXTextField tx24Percentual;

	@FXML
	private JFXTextField tx24Mensal;

	@FXML
	private Label lbl25;

	@FXML
	private JFXTextField tx25yesno;

	@FXML
	private JFXTextField tx25Inicial;

	@FXML
	private JFXTextField tx25Percentual;

	@FXML
	private JFXTextField tx25Mensal;

	@FXML
	private Label lbl26;

	@FXML
	private JFXTextField tx26yesno;

	@FXML
	private JFXTextField tx26Inicial;

	@FXML
	private JFXTextField tx26Percentual;

	@FXML
	private JFXTextField tx26Mensal;

	@FXML
	private Label lbl27;

	@FXML
	private JFXTextField tx27yesno;

	@FXML
	private JFXTextField tx27Inicial;

	@FXML
	private JFXTextField tx27Percentual;

	@FXML
	private JFXTextField tx27Mensal;

	@FXML
	private Label lbl28;

	@FXML
	private JFXTextField tx28yesno;

	@FXML
	private JFXTextField tx28Inicial;

	@FXML
	private JFXTextField tx28Percentual;

	@FXML
	private JFXTextField tx28Mensal;

	@FXML
	private Label lbl29;

	@FXML
	private JFXTextField tx29yesno;

	@FXML
	private JFXTextField tx29Inicial;

	@FXML
	private JFXTextField tx29Percentual;

	@FXML
	private JFXTextField tx29Mensal;

	@FXML
	private Label lbl30;

	@FXML
	private JFXTextField tx30yesno;

	@FXML
	private JFXTextField tx30Inicial;

	@FXML
	private JFXTextField tx30Percentual;

	@FXML
	private JFXTextField tx30Mensal;

	@FXML
	private Label lbl31;

	@FXML
	private JFXTextField tx31yesno;

	@FXML
	private JFXTextField tx31Inicial;

	@FXML
	private JFXTextField tx31Percentual;

	@FXML
	private JFXTextField tx31Mensal;

	@FXML
	private Label lbl32;

	@FXML
	private JFXTextField tx32yesno;

	@FXML
	private JFXTextField tx32Inicial;

	@FXML
	private JFXTextField tx32Percentual;

	@FXML
	private JFXTextField tx32Mensal;

	@FXML
	private Label lbl33;

	@FXML
	private JFXTextField tx33yesno;

	@FXML
	private JFXTextField tx33Inicial;

	@FXML
	private JFXTextField tx33Percentual;

	@FXML
	private JFXTextField tx33Mensal;

	@FXML
	private Pane paneExecSizing;

	@FXML
	private JFXButton btnExecSizing;

	private Task<?> task = null;

	private File pathWorkBookFile;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// Load bean on memory
		this.DaSizingQuestionarioService = BeanUtil.getBean(DaSizingQuestionarioService.class);
		this.DaSizingEstimadoService = BeanUtil.getBean(DaSizingEstimadoService.class);

		// set accordion expanded
		this.accorSizing.setExpandedPane(tpNucleoComum);

		/**
		 * Run sizing calculate
		 */
		this.btnExecSizing.setOnMouseClicked(e -> {
			boolean valid = true;
			// valid if there are null values;
			for (Node node : hboxExportador.getChildren()) {
				if (node instanceof TextField) {
					if (((TextField) node).getText().equals("")) {
						new Alert(AlertType.ERROR, "There are null fields. -> NÚCLEO COMUM", ButtonType.OK)
								.showAndWait();
						((TextField) node).requestFocus();
						valid = false;
						break;
					} else {

					}
				}
			}
			if (valid) {
				// open filechooser to save excel file;
				Stage stage = (Stage) frmSizingCalculate.getScene().getWindow();
				FileChooser fileChooser = new FileChooser();
				fileChooser.setTitle("Select a path to save a file!");
				fileChooser.getExtensionFilters().addAll(new ExtensionFilter("excel files (*.xlsx)", "*.xlsx"));
				pathWorkBookFile = fileChooser.showSaveDialog(stage);
				if (pathWorkBookFile == null) {
					new Alert(AlertType.ERROR, "File path cannot be null", ButtonType.OK).showAndWait();
					return;
				}
				// find all question from database;
				try {
					List<DaSizingQuestionario> awnsers = this.DaSizingQuestionarioService.findAll();
					// set value from first question
					for (DaSizingQuestionario daSizingQuestionario : awnsers) {
						if (daSizingQuestionario.getId() == Integer.parseInt(lbl01.getText())) {
							// set value from first question
							daSizingQuestionario.setRespostaSimNao(tx01yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx01Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx01Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx01Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl02.getText())) {
							// set value from second question
							daSizingQuestionario.setRespostaSimNao(tx02yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx02Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx02Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx02Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl34.getText())) {
							// set value from third question
							daSizingQuestionario.setRespostaSimNao(tx34yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx34Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx34Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx34Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl03.getText())) {
							// set value from fourth question
							daSizingQuestionario.setRespostaSimNao(tx03yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx03Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx03Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx03Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl04.getText())) {
							// set value from fifth question
							daSizingQuestionario.setRespostaSimNao(tx04yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx04Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx04Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx04Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl05.getText())) {
							// set value from sixth question
							daSizingQuestionario.setRespostaSimNao(tx05yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx05Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx05Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx05Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl20.getText())) {
							// set value from seventh question
							daSizingQuestionario.setRespostaSimNao(tx20yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx20Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx20Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx20Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl06.getText())) {
							// set value from eighth question
							daSizingQuestionario.setRespostaSimNao(tx06yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx06Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx06Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx06Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl07.getText())) {
							// set value from ninth question
							daSizingQuestionario.setRespostaSimNao(tx07yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx07Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx07Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx07Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl08.getText())) {
							// set value from tenth question
							daSizingQuestionario.setRespostaSimNao(tx08yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx08Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx08Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx08Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl09.getText())) {
							// set value from eleventh question
							daSizingQuestionario.setRespostaSimNao(tx09yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx09Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx09Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx09Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl10.getText())) {
							// set value from twelfth question
							daSizingQuestionario.setRespostaSimNao(tx10yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx10Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx10Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx10Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl11.getText())) {
							// set value from twelfth question
							daSizingQuestionario.setRespostaSimNao(tx11yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx11Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx11Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx11Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl12.getText())) {
							// set value from thirteenth question
							daSizingQuestionario.setRespostaSimNao(tx12yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx12Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx12Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx12Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl13.getText())) {
							// set value from fourteenth question
							daSizingQuestionario.setRespostaSimNao(tx13yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx13Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx13Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx13Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl14.getText())) {
							// set value from fifteenth question
							daSizingQuestionario.setRespostaSimNao(tx14yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx14Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx14Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx14Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl15.getText())) {
							// set value from sixteenth question
							daSizingQuestionario.setRespostaSimNao(tx15yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx15Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx15Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx15Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl16.getText())) {
							// set value from seventeenth question
							daSizingQuestionario.setRespostaSimNao(tx16yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx16Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx16Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx16Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl17.getText())) {
							// set value from eighteenth question
							daSizingQuestionario.setRespostaSimNao(tx17yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx17Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx17Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx17Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl18.getText())) {
							// set value from nineteenth question
							daSizingQuestionario.setRespostaSimNao(tx18yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx18Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx18Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx18Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl19.getText())) {
							// set value from twentieth question
							daSizingQuestionario.setRespostaSimNao(tx19yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx19Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx19Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx19Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl20.getText())) {
							// set value from twentieth second question
							daSizingQuestionario.setRespostaSimNao(tx20yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx20Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx20Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx20Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl21.getText())) {
							// set value from twentieth third question
							daSizingQuestionario.setRespostaSimNao(tx21yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx21Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx21Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx21Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl22.getText())) {
							// set value from twentieth fourth question
							daSizingQuestionario.setRespostaSimNao(tx22yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx22Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx22Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx22Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl23.getText())) {
							// set value from twentieth fifth question
							daSizingQuestionario.setRespostaSimNao(tx23yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx23Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx23Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx23Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl24.getText())) {
							// set value from twentieth sixth question
							daSizingQuestionario.setRespostaSimNao(tx24yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx24Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx24Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx24Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl25.getText())) {
							// set value from twentieth seventh question
							daSizingQuestionario.setRespostaSimNao(tx25yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx25Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx25Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx25Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl26.getText())) {
							// set value from twentieth eighth question
							daSizingQuestionario.setRespostaSimNao(tx26yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx26Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx26Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx26Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl27.getText())) {
							// set value from twentieth nineth question
							daSizingQuestionario.setRespostaSimNao(tx27yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx27Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx27Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx27Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl28.getText())) {
							// set value from thirtieth question
							daSizingQuestionario.setRespostaSimNao(tx28yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx28Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx28Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx28Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl29.getText())) {
							// set value from thirtieth first question
							daSizingQuestionario.setRespostaSimNao(tx29yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx29Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx29Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx29Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl30.getText())) {
							// set value from thirtieth second question
							daSizingQuestionario.setRespostaSimNao(tx31yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx30Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx30Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx30Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl31.getText())) {
							// set value from thirtieth third question
							daSizingQuestionario.setRespostaSimNao(tx31yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx31Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx31Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx31Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl32.getText())) {
							// set value from thirtieth fourth question
							daSizingQuestionario.setRespostaSimNao(tx32yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx32Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx32Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx32Mensal.getText()));
							// ------------------------------------------------------------------------
						} else if (daSizingQuestionario.getId() == Integer.parseInt(lbl33.getText())) {
							// set value from thirtieth fifth question
							daSizingQuestionario.setRespostaSimNao(tx33yesno.getText().toUpperCase().charAt(0));
							daSizingQuestionario.setQtdeInicial(new BigDecimal(tx33Inicial.getText()));
							daSizingQuestionario.setPercCrescimentoMensal(new BigDecimal(tx33Percentual.getText()));
							daSizingQuestionario.setQtdeMensal(new BigDecimal(tx33Mensal.getText()));
							// ------------------------------------------------------------------------
						}
					}
					this.saveAll(awnsers);
				} catch (Exception e2) {
					e2.printStackTrace();
					new Alert(AlertType.ERROR, "you are not connected on DA_USER -> " + e2.getMessage(), ButtonType.OK)
							.showAndWait();
				}
			}
		});

		/**
		 * Volta para página principal;
		 */
		this.btnHome.setOnMouseClicked(e -> {
			Install.loadScene(enumTelas.MAIN_PAGE.getUrl());
			new BounceInDown(Install.mainRoot).play();
		});

		/**
		 * efeito no botão home;
		 */
		this.btnHome.setOnMouseEntered(e -> {
			Install.homeIn(this.btnHome);
		});

		/**
		 * efeito no botão home;
		 */
		this.btnHome.setOnMouseExited(e -> {
			Install.homeOut(this.btnHome);
		});
	}

	/**
	 * Method to save the awnser into database;
	 */
	private void saveAll(List<DaSizingQuestionario> awnsers) {
		task = new Task<Object>() {
			@Override
			protected String call() throws Exception {
				Install.loadStatus(frmSizingCalculate);
				String result = DaSizingQuestionarioService.saveAll(awnsers);
				DaSizingQuestionarioService.sizingCalculate();
				List<DaSizingEstimado> estimate = DaSizingEstimadoService.findAll();
				createSizingWorkbook(estimate);
				return result;
			}

			/**
			 * 
			 */
			@Override
			protected void succeeded() {
				Install.unLoadStatus(frmSizingCalculate);
				String result = (String) task.getValue();
				if (result.equalsIgnoreCase("")) {
					new Alert(AlertType.INFORMATION, "Sizing was calculated successfully! -> " + pathWorkBookFile,
							ButtonType.OK).showAndWait();
				} else {
					new Alert(AlertType.ERROR, result, ButtonType.OK).showAndWait();
				}
			}
		};
		
		/**
		 * 
		 */
		task.setOnFailed(e -> {
			Install.unLoadStatus(frmSizingCalculate);
			Throwable exception = e.getSource().getException();
			if (exception != null) {
				exception.printStackTrace();
				new Alert(AlertType.ERROR, exception.getMessage(), ButtonType.OK).showAndWait();
			}
		});
		Thread th = new Thread(task);
		th.setDaemon(true);
		th.start();
	}

	/**
	 * 
	 * @param p_sizing_estimado
	 * @return
	 * @throws IOException
	 */
	public String createSizingWorkbook(final List<DaSizingEstimado> p_sizing_estimado) throws IOException {
		// Create a Workbook
		Workbook workbook = new XSSFWorkbook();
		// Create a Sheet
		Sheet firstSheet = workbook.createSheet("Sizing");
		FileOutputStream fileOutputStream = null;
		try {
			// Create a Font for styling header cells
			Font headerFont = workbook.createFont();
			headerFont.setBold(true);
			headerFont.setFontHeightInPoints((short) 12);
			headerFont.setColor(IndexedColors.WHITE.getIndex());

			// Create a CellStyle with the font
			CellStyle cellStyle = workbook.createCellStyle();
			cellStyle.setFont(headerFont);
			cellStyle.setFillForegroundColor(IndexedColors.ORANGE.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

			Row headerRow = firstSheet.createRow(3);

			Cell cell1 = headerRow.createCell(3);
			cell1.setCellStyle(cellStyle);
			cell1.setCellValue("USUARIO");
			firstSheet.autoSizeColumn(3);
			Cell cell2 = headerRow.createCell(4);
			cell2.setCellStyle(cellStyle);
			cell2.setCellValue("KB_INICIAL");
			firstSheet.autoSizeColumn(4);
			Cell cell3 = headerRow.createCell(5);
			cell3.setCellStyle(cellStyle);
			cell3.setCellValue("KB_MENSAL");
			firstSheet.autoSizeColumn(5);
			Cell cell4 = headerRow.createCell(6);
			cell4.setCellStyle(cellStyle);
			cell4.setCellValue("KB_ANUAL");
			firstSheet.autoSizeColumn(6);
			Cell cell5 = headerRow.createCell(7);
			cell5.setCellStyle(cellStyle);
			cell5.setCellValue("MB_INICIAL");
			firstSheet.autoSizeColumn(7);
			Cell cell6 = headerRow.createCell(8);
			cell6.setCellStyle(cellStyle);
			cell6.setCellValue("MB_MENSAL");
			firstSheet.autoSizeColumn(8);
			Cell cell7 = headerRow.createCell(9);
			cell7.setCellStyle(cellStyle);
			cell7.setCellValue("MB_ANUAL");
			firstSheet.autoSizeColumn(9);
			Cell cell8 = headerRow.createCell(10);
			cell8.setCellStyle(cellStyle);
			cell8.setCellValue("GB_INICIAL");
			firstSheet.autoSizeColumn(10);
			Cell cell9 = headerRow.createCell(11);
			cell9.setCellStyle(cellStyle);
			cell9.setCellValue("GB_MENSAL");
			firstSheet.autoSizeColumn(11);
			Cell cell10 = headerRow.createCell(12);
			cell10.setCellStyle(cellStyle);
			cell10.setCellValue("GB_ANUAL");
			firstSheet.autoSizeColumn(12);
			//
			cellStyle = workbook.createCellStyle();
			cellStyle.setFillForegroundColor((short) 9);
			// cellStyle.setFillPattern((short)1);
			// cellStyle.setAlignment((short)2);
			cellStyle.setBottomBorderColor((short) 52);
			// cellStyle.setBorderBottom((short)1);
			// cellStyle.setBorderTop((short)1);
			cellStyle.setTopBorderColor((short) 52);
			Font font = (Font) workbook.createFont();
			font.setFontHeightInPoints((short) 11);
			font.setFontName("Calibri");
			font.setColor((short) 8);
			cellStyle.setFont(font);
			int i = 4;
			for (final DaSizingEstimado sizing : p_sizing_estimado) {
				Row row1 = firstSheet.createRow(i);
				Cell cell_resp_3 = row1.createCell(3);
				// cell_resp_3.setCellType(cell_resp_3.LAST_COLUMN_NUMBER);
				cell_resp_3.setCellStyle(cellStyle);
				cell_resp_3.setCellValue(sizing.getUsuario());
				firstSheet.autoSizeColumn(3);
				Cell cell_resp_4 = row1.createCell(4);
				// cell_resp_4.setCellType(0);
				cell_resp_4.setCellStyle(cellStyle);
				cell_resp_4.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbInicial())));
				firstSheet.autoSizeColumn(4);
				Cell cell_resp_5 = row1.createCell(5);
				// cell_resp_5.setCellType(0);
				cell_resp_5.setCellStyle(cellStyle);
				cell_resp_5.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbMensal())));
				firstSheet.autoSizeColumn(5);
				Cell cell_resp_6 = row1.createCell(6);
				// cell_resp_6.setCellType(0);
				cell_resp_6.setCellStyle(cellStyle);
				cell_resp_6.setCellValue(Double.parseDouble(String.valueOf(sizing.getKbAnual())));
				firstSheet.autoSizeColumn(6);
				Cell cell_resp_7 = row1.createCell(7);
				// cell_resp_7.setCellType(0);
				cell_resp_7.setCellStyle(cellStyle);
				cell_resp_7.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbInicial())));
				firstSheet.autoSizeColumn(7);
				Cell cell_resp_8 = row1.createCell(8);
				// cell_resp_8.setCellType(0);
				cell_resp_8.setCellStyle(cellStyle);
				cell_resp_8.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbMensal())));
				firstSheet.autoSizeColumn(8);
				Cell cell_resp_9 = row1.createCell(9);
				// cell_resp_9.setCellType(0);
				cell_resp_9.setCellStyle(cellStyle);
				cell_resp_9.setCellValue(Double.parseDouble(String.valueOf(sizing.getMbAnual())));
				firstSheet.autoSizeColumn(9);
				Cell cell_resp_10 = row1.createCell(10);
				// cell_resp_10.setCellType(0);
				cell_resp_10.setCellStyle(cellStyle);
				cell_resp_10.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbInicial())));
				firstSheet.autoSizeColumn(10);
				Cell cell_resp_11 = row1.createCell(11);
				// cell_resp_11.setCellType(0);
				cell_resp_11.setCellStyle(cellStyle);
				cell_resp_11.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbMensal())));
				firstSheet.autoSizeColumn(11);
				Cell cell_resp_12 = row1.createCell(12);
				// cell_resp_12.setCellType(0);
				cell_resp_12.setCellStyle(cellStyle);
				cell_resp_12.setCellValue(Double.parseDouble(String.valueOf(sizing.getGbAnual())));
				firstSheet.autoSizeColumn(12);
				++i;
			}
			CellStyle cellStyle2 = workbook.createCellStyle();
			cellStyle2.setFillForegroundColor((short) 9);
			// cellStyle2.setFillPattern((short)1);
			// cellStyle2.setAlignment((short)2);
			cellStyle2.setBottomBorderColor((short) 52);
			// cellStyle2.setBorderBottom((short)1);
			// cellStyle2.setBorderTop((short)1);
			cellStyle2.setTopBorderColor((short) 52);
			final Font font2 = (Font) workbook.createFont();
			font2.setFontHeightInPoints((short) 11);
			font2.setFontName("Calibri");
			font2.setColor((short) 8);
			font2.setBold(true);
			cellStyle2.setFont(font2);
			Row total = firstSheet.createRow(10);
			Cell cell_total = total.createCell(3);
			cell_total.setCellStyle(cellStyle2);
			cell_total.setCellValue("TOTAL");
			firstSheet.autoSizeColumn(3);
			Cell cell_kb_inicial = total.createCell(4);
			cell_kb_inicial.setCellStyle(cellStyle2);
			// cell_kb_inicial.setCellType(2);
			cell_kb_inicial.setCellFormula("SUM(E5:E10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_kb_mensal = total.createCell(5);
			cell_kb_mensal.setCellStyle(cellStyle2);
			// cell_kb_mensal.setCellType(2);
			cell_kb_mensal.setCellFormula("SUM(F5:F10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_kb_anual = total.createCell(6);
			cell_kb_anual.setCellStyle(cellStyle2);
			// cell_kb_anual.setCellType(2);
			cell_kb_anual.setCellFormula("SUM(G5:G10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_mb_inicial = total.createCell(7);
			cell_mb_inicial.setCellStyle(cellStyle2);
			// cell_mb_inicial.setCellType(2);
			cell_mb_inicial.setCellFormula("SUM(H5:H10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_mb_mensal = total.createCell(8);
			cell_mb_mensal.setCellStyle(cellStyle2);
			// cell_mb_mensal.setCellType(2);
			cell_mb_mensal.setCellFormula("SUM(I5:I10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_mb_anual = total.createCell(9);
			cell_mb_anual.setCellStyle(cellStyle2);
			// cell_mb_anual.setCellType(2);
			cell_mb_anual.setCellFormula("SUM(J5:J10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_gb_inicial = total.createCell(10);
			cell_gb_inicial.setCellStyle(cellStyle2);
			// cell_gb_inicial.setCellType(2);
			cell_gb_inicial.setCellFormula("SUM(K5:K10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_gb_mensal = total.createCell(11);
			cell_gb_mensal.setCellStyle(cellStyle2);
			// cell_gb_mensal.setCellType(2);
			cell_gb_mensal.setCellFormula("SUM(L5:L10)");
			firstSheet.autoSizeColumn(3);
			Cell cell_gb_anual = total.createCell(12);
			cell_gb_anual.setCellStyle(cellStyle2);
			// cell_gb_anual.setCellType(2);
			cell_gb_anual.setCellFormula("SUM(M5:M10)");
			firstSheet.autoSizeColumn(3);
			// open filechooser to save excel file;
			fileOutputStream = new FileOutputStream(pathWorkBookFile);
			workbook.write((OutputStream) fileOutputStream);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
		} catch (Exception e) {
			return "Erro ao exportar arquivo" + e.getMessage();
		} finally {
			fileOutputStream.flush();
			fileOutputStream.close();
			workbook.close();
		}
		return "";
	}
}
