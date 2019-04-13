package application.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import oracle.dbtools.raptor.newscriptrunner.ISQLCommand;
import oracle.dbtools.raptor.newscriptrunner.ScriptExecutor;
import oracle.dbtools.raptor.newscriptrunner.ScriptParser;
import oracle.dbtools.raptor.newscriptrunner.ScriptRunner;
import oracle.dbtools.raptor.newscriptrunner.ScriptRunnerContext;
import oracle.jdbc.OracleDriver;
import oracle.jdbc.OraclePreparedStatement;

public class DatabaseConnection {

	private Connection con = null;
	private OraclePreparedStatement _statement_st;

	/**
	 *
	 * @return
	 * @throws ClassNotFoundException
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
		} catch (SQLException | ClassNotFoundException e) {
			e.printStackTrace();
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

			this.con = DriverManager.getConnection(url, v_properties_prop);

			Install.username = username;
			Install.password = password;
			Install.tns = tns;
			Install.url = url;

			this.createViewCmLogScripts();

			this.con.close();
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
			Statement statement = this.con.createStatement();
			String code = "create or replace view  V_CM_LOG_SCRIPTS as select rownum as id, usuario as usuario, versao as versao, tipo as tipo, objeto as objeto, erro as erro, data as data, rm as rm from cm_log_scripts";
			statement.executeUpdate(code);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 */
	public void dropViewCmLogScripts() {
		try {
			this.Connect();
			Statement statement = this.con.createStatement();
			String code = "drop view V_CM_LOG_SCRIPTS";
			statement.executeUpdate(code);
		} catch (Exception e) {

		}
	}

	/**
	 * 
	 * @param Query a ser executada
	 * @return Resultset do select executado
	 * @throws SQLException
	 */
	public ResultSet Query(String query) throws SQLException {
		ResultSet v_resultset_result;

		this._statement_st = (OraclePreparedStatement) this.con.prepareStatement(query);
		v_resultset_result = this._statement_st.executeQuery();
		return v_resultset_result;
	}

	/**
	 * 
	 * @param username
	 * @return
	 */
	public boolean returnCodFromDatabase(String username) {
		boolean resposta = true;
		try {
			ResultSet rs = this.Query("select cod_sistema from sfw_cm_schema where s_schema_owner = upper('" + username
					+ "') and rownum = 1");
			while (rs.next()) {
				Install.cod_sistema = rs.getString("cod_sistema");
			}
			if (Install.cod_sistema == null || Install.cod_sistema.equalsIgnoreCase("")) {
				if (username.toUpperCase().contains("RF")) {
					Install.cod_sistema = "6";
				} else {
					resposta = false;
				}
			}
		} catch (Exception e) {
			resposta = false;
		}
		return resposta;
	}

	/**
	 * Function to run a script into database;
	 * 
	 * @param script
	 * @throws SQLException
	 * @throws IOException
	 */ // "@" + script.getAbsolutePath()
	public String runOracleScriptWithLogs(File script) throws SQLException, IOException {
		Connection conn = DriverManager.getConnection(Install.url, Install.username, Install.password);

		FileInputStream fin = new FileInputStream(script.getAbsolutePath());
		ScriptParser parser = new ScriptParser(fin);

		ISQLCommand cmd;
		// #setup the context
		ScriptRunnerContext ctx = new ScriptRunnerContext();
		ctx.setBaseConnection(conn);

		// Capture the results without this it goes to STDOUT
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		BufferedOutputStream buf = new BufferedOutputStream(bout);

		ScriptRunner sr = new ScriptRunner(conn, buf, ctx);
		while ((cmd = parser.next()) != null) {
			// do something fancy based on a cmd
			sr.run(cmd);
			// check success/failure of the command

			String errMsg = (String) ctx.getProperty(ScriptRunnerContext.ERR_MESSAGE);
			if (errMsg != null) {
				// react to a failure
				System.out.println("**FAILURE**" + errMsg);
			}
		}

		String results = bout.toString("ISO-8859-1");
		results = results.replaceAll(" force_print\n", "");
		return results;
	}

	/**
	 * Function to run a script into database;
	 * 
	 * @param script
	 * @throws SQLException
	 * @throws IOException
	 */ // "@" + script.getAbsolutePath()
	public String runOracleScript(File script) throws SQLException, IOException {
		Connection conn = DriverManager.getConnection(Install.url, Install.username, Install.password);

		// #get a DBUtil but won't actually use it in this example
		// DBUtil util = DBUtil.getInstance(conn);

		// #create sqlcl
		ScriptExecutor sqlcl = new ScriptExecutor(conn);

		// Capture the results without this it goes to STDOUT
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		BufferedOutputStream buf = new BufferedOutputStream(bout);
		sqlcl.setOut(buf);

		// #setup the context
		ScriptRunnerContext ctx = new ScriptRunnerContext();

		// #set the context
		sqlcl.setScriptRunnerContext(ctx);
		ctx.setBaseConnection(conn);

		// # run a whole file
		sqlcl.setStmt("@" + script.getAbsolutePath());
		sqlcl.run();
		//
		String results = bout.toString("ISO-8859-1");
		results = results.replaceAll(" force_print\n", "");
		//
		return results;
	}
}
