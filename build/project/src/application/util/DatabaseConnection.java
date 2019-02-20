package application.util;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.jdbc.OracleDriver;

public class DatabaseConnection {

	private Connection con = null;

	/**
	 *
	 * @return
	 * @throws SQLException
	 */
	public Connection Connect() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties v_properties_prop;

			v_properties_prop = new Properties();
			v_properties_prop.put("user", Install.username);
			v_properties_prop.put("password", Install.password);

			con = DriverManager.getConnection(Install.url, v_properties_prop);
		} catch (ClassNotFoundException e) {
		} catch (SQLException e) {
		}
		return con;
	}

	/**
	 * 
	 * @param username
	 * @param password
	 * @param tns
	 * @return
	 */
	public Integer validaConexao(String username, String password, String tns) {
		Integer retorno = 0;
		try {
			String url = getURL(tns);

			Class.forName("oracle.jdbc.driver.OracleDriver");
			Properties v_properties_prop;

			v_properties_prop = new Properties();
			v_properties_prop.put("user", username);
			v_properties_prop.put("password", password);

			DriverManager.registerDriver(new OracleDriver());

			con = DriverManager.getConnection(url, v_properties_prop);

			Install.username = username;
			Install.password = password;
			Install.tns = tns;
			Install.url = url;

			this.createViewCmLogScripts();

			con.close();
			retorno = 1;
		} catch (Exception e) {
		}
		return retorno;
	}

	/**
	 * 
	 * @param tns
	 * @return
	 */
	public String getURL(String tns) {
		try {
			String v_s_host = "";
			String v_s_tnsping;
			Process p = Runtime.getRuntime().exec("tnsping " + tns);
			InputStream stdoutStream = new BufferedInputStream(p.getInputStream());

			StringBuilder buffer = new StringBuilder();
			for (;;) {
				int c = stdoutStream.read();
				if (c == -1) {
					break;
				}
				buffer.append((char) c);
			}
			v_s_tnsping = buffer.toString().toUpperCase();

			stdoutStream.close();

			v_s_tnsping = v_s_tnsping.replaceAll(" ", "");

			if (v_s_tnsping.contains("(DESCRIPTION")) {
				v_s_host = v_s_tnsping.substring(v_s_tnsping.indexOf("(DESCRIPTION"), v_s_tnsping.indexOf("OK"));
			}

			return v_s_host = "jdbc:oracle:thin:@" + v_s_host;

		} catch (Exception e) {
			return "";
		}
	}

	/**
	 * 
	 */
	public void createViewCmLogScripts() {
		try {
			this.Connect();
			Statement statement = con.createStatement();
			String code = "create or replace view  V_CM_LOG_SCRIPTS as select rownum as id, usuario as usuario, versao as versao, tipo as tipo, objeto as objeto, erro as erro, data as data, rm as rm from cm_log_scripts";
			statement.executeUpdate(code);
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 */
	public void dropViewCmLogScripts() {
		try {
			this.Connect();
			Statement statement = con.createStatement();
			String code = "drop view V_CM_LOG_SCRIPTS";
			statement.executeUpdate(code);
		} catch (Exception e) {

		}
	}
}
