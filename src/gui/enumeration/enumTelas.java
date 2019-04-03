package gui.enumeration;

public enum enumTelas {

	 MAIN_PAGE("/gui/frmMain.fxml"),
	 FRM_CM_LOG_SCRIPTS("/gui/frmCmLogScripts.fxml"),
	 FRM_SIZING_CALCULATE("/gui/frmSizingCalculate.fxml"),
	 FRM_DATABASE_COMPARISON("/gui/frmSizingCalculate.fxml"),
	 LOGIN("/gui/Login.fxml"),
	 MENU_BAR("/gui/menuBar.fxml"),
	 COMPARE("/gui/frmCompare.fxml");
	
	
	private String url; 	 

	private enumTelas(String url) {
		this.url = url;
	}

	public String getUrl() {
		return url;
	}

}
