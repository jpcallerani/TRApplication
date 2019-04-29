package application.util;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import application.entity.Objeto;
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
	/*
	 * public String runOracleScriptWithLogs(File script) throws SQLException,
	 * IOException { Connection conn = DriverManager.getConnection(Install.url,
	 * Install.username, Install.password);
	 * 
	 * FileInputStream fin = new FileInputStream(script.getAbsolutePath());
	 * ScriptParser parser = new ScriptParser(fin);
	 * 
	 * ISQLCommand cmd; // #setup the context ScriptRunnerContext ctx = new
	 * ScriptRunnerContext(); ctx.setBaseConnection(conn);
	 * 
	 * // Capture the results without this it goes to STDOUT ByteArrayOutputStream
	 * bout = new ByteArrayOutputStream(); BufferedOutputStream buf = new
	 * BufferedOutputStream(bout);
	 * 
	 * ScriptRunner sr = new ScriptRunner(conn, buf, ctx); while ((cmd =
	 * parser.next()) != null) { // do something fancy based on a cmd sr.run(cmd);
	 * // check success/failure of the command
	 * 
	 * String errMsg = (String) ctx.getProperty(ScriptRunnerContext.ERR_MESSAGE); if
	 * (errMsg != null) { // react to a failure System.out.println("**FAILURE**" +
	 * errMsg); } }
	 * 
	 * String results = bout.toString("ISO-8859-1"); results =
	 * results.replaceAll(" force_print\n", ""); return results; }
	 */

	/**
	 * Function to run a script into database;
	 * 
	 * @param script
	 * @throws SQLException
	 * @throws IOException
	 */ // "@" + script.getAbsolutePath()
	/*
	 * public String runOracleScript(File script) throws SQLException, IOException {
	 * Connection conn = DriverManager.getConnection(Install.url, Install.username,
	 * Install.password);
	 * 
	 * // #get a DBUtil but won't actually use it in this example // DBUtil util =
	 * DBUtil.getInstance(conn);
	 * 
	 * // #create sqlcl ScriptExecutor sqlcl = new ScriptExecutor(conn);
	 * 
	 * // Capture the results without this it goes to STDOUT ByteArrayOutputStream
	 * bout = new ByteArrayOutputStream(); BufferedOutputStream buf = new
	 * BufferedOutputStream(bout); sqlcl.setOut(buf);
	 * 
	 * // #setup the context ScriptRunnerContext ctx = new ScriptRunnerContext();
	 * 
	 * // #set the context sqlcl.setScriptRunnerContext(ctx);
	 * ctx.setBaseConnection(conn);
	 * 
	 * // # run a whole file sqlcl.setStmt("@" + script.getAbsolutePath());
	 * sqlcl.run(); // String results = bout.toString("ISO-8859-1"); results =
	 * results.replaceAll(" force_print\n", ""); // conn.close(); return results; }
	 */

	/**
	 * Verifica se a versão do XML é a mesma do banco de dados;
	 * 
	 * @param objeto
	 * @return
	 * @throws IOException
	 * @throws SQLException
	 */
	public boolean verificaVersao(Objeto objeto) throws IOException, SQLException {
		//
		String contador = "";
		//
		ResultSet rs = this.Query("select count(1) as contador " + "from sfw_sistema_versao where valido = 'S' "
				+ "and cod_versao like '%" + objeto.getId().replace("tag_", "") + "%'");
		// verifica se achou a mesma versão do objeto;
		if (rs.next()) {
			contador = rs.getString("contador");
		}
		// se a versão for a mesma retorna verdadeiro;
		if (contador.equalsIgnoreCase("1")) {
			return true;
		} else {
			// caso contrário retorna falso;
			return false;
		}
	}

	/**
	 * 
	 * @param p_baseGenerica
	 * @return
	 * @throws SQLException
	 */
	public Objeto returnObjectFromDatabase(Objeto objeto) throws SQLException {
		Objeto objeto1 = null;
		try {
			//
			objeto1 = new Objeto();
			objeto1.setNome(objeto.getNome());
			objeto1.setTipo(objeto.getTipo());
			objeto1.setId(objeto.getId());
			objeto1.setCodSistema(objeto.getCodSistema());
			//
			if (objeto.getTipo().equals("VIEW")) {
				String codview = "create or replace view as ";

				ResultSet codv = this.Query("select * from all_views " + "where owner = " + "(select cm.s_schema_owner "
						+ "from sfw_cm_schema cm where cod_sistema = '" + objeto.getCodSistema() + "') "
						+ "and view_name = '" + objeto.getNome() + "'");

				while (codv.next()) {

					codview = codview + codv.getString("TEXT");

				}
				codv.getStatement().close();
				codview.trim();
				codview = codview + "\n;";
				//
				objeto1.setCodigo(codview);

			} else {
				ResultSet rscodigo = this.Query("select TEXT" + "  from all_source" + " where name = '"
						+ objeto.getNome() + "'" + "   and type = '" + objeto.getTipo() + "'" + "   and owner = "
						+ "       (select cm.s_schema_owner from sfw_cm_schema cm where cod_sistema = '"
						+ objeto.getCodSistema() + "')" + " order by line");

				String cod = "create or replace ";

				while (rscodigo.next()) {
					cod = cod + rscodigo.getString("TEXT");

				}
				rscodigo.getStatement().close();
				cod.trim();
				cod = cod + "\n/";
				//
				objeto1.setCodigo(cod);
			}
			this.con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return objeto1;

	}

	/**
	 * close oracle connection;
	 * 
	 * @throws SQLException
	 */
	public void closeConnection(){
		try {
			this.con.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @return
	 * @throws SQLException
	 */
	public List<Objeto> returnObjectsFromDatabase() throws SQLException {
		Objeto objeto1;

		// Versao versao1;
		List<Objeto> v_list_usuario = new ArrayList<Objeto>();

		ResultSet rs = this.Query("select sfw.s_nome_objeto," + 
							"       sfw.s_owner_objeto," + 
							"       scm.cod_sistema," + 
							"       sfw.s_tipo_objeto," + 
							"       sfw.s_tipo_inconformidade," + 
							"       sfw.s_conteudo_objeto" + 
							"  from sfw_verific_integridade_obj sfw, sfw_cm_schema scm" + 
							" where id_verificacao = (select max(id_verificacao) from sfw_verific_integridade)" + 
							"   and sfw.s_owner_objeto = scm.s_schema_owner" + 
							//"   and sfw.s_tipo_inconformidade != 'A MAIS'" + 
							"   and scm.cod_sistema in ('0', '2', '3', '6', '9', '10', '11', '21')" + 
							"   and s_tipo_objeto in ('FUNCTION'," + 
							"                         'PACKAGE'," + 
							"                         'PACKAGE BODY'," + 
							"                         'VIEW'," + 
							"                         'TRIGGER'," + 
							"                         'PROCEDURE')");

		while (rs.next()) {

			// popula objeto1
			objeto1 = new Objeto();
			objeto1.setNome(rs.getString("s_nome_objeto"));
			objeto1.setCodSistema(rs.getString("cod_sistema"));
			objeto1.setTipo(rs.getString("s_tipo_objeto"));
			objeto1.setErro(rs.getString("s_tipo_inconformidade"));

			String nome = rs.getString("s_nome_objeto");
			String tipo = rs.getString("s_tipo_objeto");
			// System.out.println(nome);

			if (tipo.equals("VIEW")) {
				String codview = "create or replace view as ";

				ResultSet codv = this.Query("select TEXT from all_views where view_name = '" + nome + "' "
						+ "and owner = '" + rs.getString("s_owner_objeto") + "'");

				while (codv.next()) {

					codview = codview + codv.getString("TEXT");

				}
				codv.getStatement().close();
				codview.trim();
				codview = codview + "\n;";
				objeto1.setCodigo(codview);

			} else {
				ResultSet rscodigo = this.Query("select TEXT from all_source where name = '" + nome + "' and owner = '"
						+ rs.getString("s_owner_objeto") + "' and type =  '" + tipo + "' order by line");

				String cod = "create or replace ";

				while (rscodigo.next()) {
					cod = cod + rscodigo.getString("TEXT");

				}
				rscodigo.getStatement().close();
				cod.trim();
				cod = cod + "\n/";
				objeto1.setCodigo(cod);
			}

			System.out.println("adicionando objeto " + objeto1.getNome());
			v_list_usuario.add(objeto1);

		}
		rs.getStatement().close();
		return v_list_usuario;

	}

	/**
	 * 
	 * 
	 * @throws Exception
	 */

	//
	public String pegarVersao() throws SQLException {

		String v2 = null;
		ResultSet ver = this
				.Query("select cod_versao from sfw_sistema_versao where valido = 'S' and cod_sistema = '0'");

		while (ver.next()) {
			v2 = ver.getString("cod_versao");
		}
		System.out.println(v2);
		return v2;
	}

	/**
	 * 
	 * @return
	 */
	public boolean verificaSistemaVersao() {
		try {
			ResultSet rs = this
					.Query("SELECT 1 FROM USER_OBJECTS WHERE OBJECT_NAME = 'SFW_SISTEMA_VERSAO' AND ROWNUM = 1");

			if (rs.next()) {
				return true;
			} else {
				return false;
			}
		} catch (Exception e) {
			return false;
		}
	}
}
