package application.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

@Entity
@Table(name = "v_cm_log_scripts")
public class Erro extends RecursiveTreeObject<Erro>  implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Column(name = "id")
	private int id;

	@Column(name = "usuario")
	private String usuario;

	@Column(name = "versao")
	private String versao;

	@Column(name = "tipo")
	private String tipo;

	@Column(name = "objeto")
	private String objeto;

	@Column(name = "erro")
	private String erro;

	@Column(name = "data")
	private Date data;

	@Column(name = "rm")
	private String rm;

	public Erro() {
	}

	public Erro(String usuario, String versao, String tipo, String objeto, String erro, Date data, String rm) {
		this.usuario = usuario;
		this.versao = versao;
		this.tipo = tipo;
		this.objeto = objeto;
		this.erro = erro;
		this.data = data;
		this.rm = rm;
	}

	public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVersao() {
		return versao;
	}

	public void setVersao(String versao) {
		this.versao = versao;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getObjeto() {
		return objeto;
	}

	public void setObjeto(String objeto) {
		this.objeto = objeto;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public Date getData() {
		return data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public String getRm() {
		return rm;
	}

	public void setRm(String rm) {
		this.rm = rm;
	}

	@Override
	public String toString() {
		return "Erro [usuario=" + usuario + ", versao=" + versao + ", tipo=" + tipo + ", objeto=" + objeto + ", erro="
				+ erro + ", data=" + data + ", rm=" + rm + "]";
	}
}
