package application.entity;

import java.io.Serializable;

import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;

public class Objeto extends RecursiveTreeObject<Objeto>  implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String nome;
	String codSistema;
	String tipo;
	String erro;
	String codigo;	
	String id;
	
	public Objeto() {
		
	}
	
	public Objeto(String nome, String tipo, String erro) {
		super();
		this.nome = nome;
		this.tipo = tipo;
		this.erro = erro;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getCodSistema() {
		return codSistema;
	}

	public void setCodSistema(String codSistema) {
		this.codSistema = codSistema;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	public String getErro() {
		return erro;
	}

	public void setErro(String erro) {
		this.erro = erro;
	}

	public String getCodigo() {
		return codigo;
	}

	public void setCodigo(String codigo) {
		this.codigo = codigo;
	}

	//novo
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}
	//
}

