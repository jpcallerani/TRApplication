package application.util;

import java.util.ArrayList;
import java.util.Vector;

public class CVSFileHeader {

	/** Creates a new instance of CVSFileReader */
	public CVSFileHeader() {

	}

	private String filename;
	private String headrevision;
	private String branch;
	private String locks;
	private String accesslist;
	private ArrayList symbolicnames;
	private String totalrevisions;
	private String selectedrevisions;
	public String module;
	private CVSFileDetails[] detalhes;

	/**
	 * 
	 * @return
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * 
	 * @param filename
	 */
	public void setFilename(String filename) {
		this.filename = filename;
	}

	/**
	 * 
	 * @return
	 */
	public String getHeadrevision() {
		return headrevision;
	}

	/**
	 * 
	 * @param headrevision
	 */
	public void setHeadrevision(String headrevision) {
		this.headrevision = headrevision;
	}

	/**
	 * 
	 * @return
	 */
	public String getBranch() {
		return branch;
	}

	/**
	 * 
	 * @param branch
	 */
	public void setBranch(String branch) {
		this.branch = branch;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocks() {
		return locks;
	}

	/**
	 * 
	 * @param locks
	 */
	public void setLocks(String locks) {
		this.locks = locks;
	}

	/**
	 * 
	 * @return
	 */
	public String getAccesslist() {
		return accesslist;
	}

	/**
	 * 
	 * @param accesslist
	 */
	public void setAccesslist(String accesslist) {
		this.accesslist = accesslist;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList getSymbolicnames() {
		return symbolicnames;
	}

	/**
	 * 
	 * @param symbolicnames
	 */
	public void setSymbolicnames(ArrayList symbolicnames) {
		this.symbolicnames = symbolicnames;
	}

	/**
	 * 
	 * @return
	 */
	public String getTotalrevisions() {
		return totalrevisions;
	}

	/**
	 * 
	 * @param totalrevisions
	 */
	public void setTotalrevisions(String totalrevisions) {
		this.totalrevisions = totalrevisions;
	}

	/**
	 * 
	 * @return
	 */
	public String getSelectedrevisions() {
		return selectedrevisions;
	}

	/**
	 * 
	 * @param selectedrevisions
	 */
	public void setSelectedrevisions(String selectedrevisions) {
		this.selectedrevisions = selectedrevisions;
	}

	/**
	 * 
	 * @return
	 */
	public CVSFileDetails[] getDetalhes() {
		return detalhes;
	}

	/**
	 * 
	 * @param detalhes
	 */
	public void setDetalhes(CVSFileDetails[] detalhes) {
		this.detalhes = detalhes;
	}

	/**
	 * 
	 * @param v
	 */
	public void setDetalhesFromVector(Vector v) {
		if (!v.isEmpty()) {
			detalhes = new CVSFileDetails[v.size()];
			for (int i = 0; i < detalhes.length; i++) {
				detalhes[i] = (CVSFileDetails) v.get(i);
			}
		}
	}

	/**
	 * 
	 * @return
	 */
	public String getModule() {
		return module;
	}

	/**
	 * 
	 * @param module
	 */
	public void setModule(String module) {
		this.module = module;
	}

}
