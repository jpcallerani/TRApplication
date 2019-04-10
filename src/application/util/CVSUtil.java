package application.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.logging.Logger;

import org.netbeans.lib.cvsclient.CVSRoot;
import org.netbeans.lib.cvsclient.Client;
import org.netbeans.lib.cvsclient.admin.StandardAdminHandler;
import org.netbeans.lib.cvsclient.command.CommandAbortedException;
import org.netbeans.lib.cvsclient.command.CommandException;
import org.netbeans.lib.cvsclient.command.FileInfoContainer;
import org.netbeans.lib.cvsclient.command.GlobalOptions;
import org.netbeans.lib.cvsclient.command.checkout.CheckoutCommand;
import org.netbeans.lib.cvsclient.command.log.LogInformation;
import org.netbeans.lib.cvsclient.command.log.RlogCommand;
import org.netbeans.lib.cvsclient.connection.AuthenticationException;
import org.netbeans.lib.cvsclient.connection.PServerConnection;
import org.netbeans.lib.cvsclient.connection.StandardScrambler;
import org.netbeans.lib.cvsclient.event.BinaryMessageEvent;
import org.netbeans.lib.cvsclient.event.CVSListener;
import org.netbeans.lib.cvsclient.event.FileAddedEvent;
import org.netbeans.lib.cvsclient.event.FileInfoEvent;
import org.netbeans.lib.cvsclient.event.FileRemovedEvent;
import org.netbeans.lib.cvsclient.event.FileToRemoveEvent;
import org.netbeans.lib.cvsclient.event.FileUpdatedEvent;
import org.netbeans.lib.cvsclient.event.MessageEvent;
import org.netbeans.lib.cvsclient.event.ModuleExpansionEvent;
import org.netbeans.lib.cvsclient.event.TerminationEvent;

/**
 * 
 * @author <a href="mailto:jopaulo@sfw.com.br">jopaulo</a>
 * 
 */
@SuppressWarnings({"unused"})
public class CVSUtil {

	/** Creates a new instance of cvsUtil */
	public CVSUtil(String crot, String lp, String pass) {
		this.setCvsroot(crot);
		this.setLocalPath(lp);
		this.setSenha(pass);
		this.inicializa(crot);
	}

	/*
	 * Variaveis
	 */
	private String				cvsroot;
	private String				localPath;
	private GlobalOptions	globalOptions;
	private Client				client;
	private RlogCommand		cmd;
	private String				senha;
	private Logger				log;
	private ArrayList					sym;

	/**
	 * 
	 * @param arquivo
	 * @param Tag
	 * @throws AuthenticationException
	 * @throws CommandAbortedException
	 * @throws CommandException
	 * @throws IOException
	 */

	public void checkOut(String arquivo, String Tag) throws CommandAbortedException, CommandException, AuthenticationException {
		
		boolean executou = false;

		this.inicializa(this.getCvsroot());

		CheckoutCommand checkout = new CheckoutCommand();
		
		checkout.setModule(arquivo);
		checkout.setPruneDirectories(true);
		checkout.setNotShortenPaths(true);
		checkout.setRecursive(true);
		checkout.setCheckoutByRevision(Tag);

			try {
				client.executeCommand(checkout, globalOptions);
				executou = true;
			} catch (AuthenticationException e) {
				e.printStackTrace();
					this.inicializa(this.getCvsroot());
			} catch (Exception e) { //
				e.printStackTrace();
			}
		
	}


	/**
	 * 
	 * @param opcao
	 */
	private void inicializa(String cvsRoot) {
		CVSRoot root = CVSRoot.parse(cvsRoot);
		globalOptions = new GlobalOptions();
		globalOptions.setCVSRoot(cvsRoot);
		PServerConnection connection = new PServerConnection(root);
		connection.setEncodedPassword(StandardScrambler.getInstance().scramble(senha));
		client = new Client(connection, new StandardAdminHandler());
		client.setLocalPath(this.getLocalPath());

		client.getEventManager().addCVSListener(new CVSListener() {

			public void messageSent(MessageEvent e) {
			}

			public void messageSent(BinaryMessageEvent e) {
			}

			public void fileAdded(FileAddedEvent e) {
			}

			public void fileToRemove(FileToRemoveEvent e) {
			}

			public void fileRemoved(FileRemovedEvent e) {
			}

			public void fileUpdated(FileUpdatedEvent e) {
			}

			public void fileInfoGenerated(FileInfoEvent e) {
				FileInfoContainer fic = e.getInfoContainer();
				System.out.println("Arquivo: " + fic.getFile().getName());
			}

			public void commandTerminated(TerminationEvent e) {
			}

			public void moduleExpanded(ModuleExpansionEvent e) {
			}
		});
	}

	/**
	 * 
	 * @return
	 */
	public String getCvsroot() {
		return cvsroot;
	}

	/**
	 * 
	 * @param cvsroot
	 */
	public void setCvsroot(String cvsroot) {
		this.cvsroot = cvsroot;
	}

	/**
	 * 
	 * @return
	 */
	public GlobalOptions getGlobalOptions() {
		return globalOptions;
	}

	/**
	 * 
	 * @return
	 */
	public String getLocalPath() {
		return localPath;
	}

	/**
	 * 
	 * @param localPath
	 */
	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	/**
	 * 
	 * @return
	 */
	public String getSenha() {
		return senha;
	}

	/**
	 * 
	 * @param senha
	 */
	public void setSenha(String senha) {
		this.senha = senha;
	}

}
