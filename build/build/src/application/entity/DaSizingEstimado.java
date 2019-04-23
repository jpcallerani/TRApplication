package application.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DA_SIZING_ESTIMADO")
@XmlRootElement
public class DaSizingEstimado implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "DATA")
	@Temporal(TemporalType.TIMESTAMP)
	private Date data;

	@Basic(optional = false)
	@Column(name = "COD_SISTEMA")
	private Integer codSistema;

	@Basic(optional = false)
	@Column(name = "USUARIO")
	private String usuario;

	@Basic(optional = false)
	@Column(name = "VERSAO_REFERENCIA")
	private String versaoReferencia;

	@Column(name = "KB_INICIAL", precision = 9, scale = 2)
	private BigDecimal kbInicial;

	@Column(name = "KB_MENSAL", precision = 9, scale = 2)
	private BigDecimal kbMensal;

	@Column(name = "KB_ANUAL", precision = 9, scale = 2)
	private BigDecimal kbAnual;

	@Column(name = "MB_INICIAL", precision = 9, scale = 2)
	private BigDecimal mbInicial;

	@Column(name = "MB_MENSAL", precision = 9, scale = 2)
	private BigDecimal mbMensal;

	@Column(name = "MB_ANUAL", precision = 9, scale = 2)
	private BigDecimal mbAnual;

	@Column(name = "GB_INICIAL", precision = 9, scale = 2)
	private BigDecimal gbInicial;

	@Column(name = "GB_MENSAL", precision = 9, scale = 2)
	private BigDecimal gbMensal;

	@Column(name = "GB_ANUAL", precision = 9, scale = 2)
	private BigDecimal gbAnual;

	public DaSizingEstimado() {
	}

	public DaSizingEstimado(Integer id) {
		this.id = id;
	}

	public DaSizingEstimado(Integer id, Date data, Integer codSistema, String usuario, String versaoReferencia) {
		this.id = id;
		this.data = data;
		this.codSistema = codSistema;
		this.usuario = usuario;
		this.versaoReferencia = versaoReferencia;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getData() {
		return this.data;
	}

	public void setData(Date data) {
		this.data = data;
	}

	public Integer getCodSistema() {
		return this.codSistema;
	}

	public void setCodSistema(Integer codSistema) {
		this.codSistema = codSistema;
	}

	public String getUsuario() {
		return this.usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getVersaoReferencia() {
		return this.versaoReferencia;
	}

	public void setVersaoReferencia(String versaoReferencia) {
		this.versaoReferencia = versaoReferencia;
	}

	public BigDecimal getKbInicial() {
		return this.kbInicial;
	}

	public void setKbInicial(BigDecimal kbInicial) {
		this.kbInicial = kbInicial;
	}

	public BigDecimal getKbMensal() {
		return this.kbMensal;
	}

	public void setKbMensal(BigDecimal kbMensal) {
		this.kbMensal = kbMensal;
	}

	public BigDecimal getKbAnual() {
		return this.kbAnual;
	}

	public void setKbAnual(BigDecimal kbAnual) {
		this.kbAnual = kbAnual;
	}

	public BigDecimal getMbInicial() {
		return this.mbInicial;
	}

	public void setMbInicial(BigDecimal mbInicial) {
		this.mbInicial = mbInicial;
	}

	public BigDecimal getMbMensal() {
		return this.mbMensal;
	}

	public void setMbMensal(BigDecimal mbMensal) {
		this.mbMensal = mbMensal;
	}

	public BigDecimal getMbAnual() {
		return this.mbAnual;
	}

	public void setMbAnual(BigDecimal mbAnual) {
		this.mbAnual = mbAnual;
	}

	public BigDecimal getGbInicial() {
		return this.gbInicial;
	}

	public void setGbInicial(BigDecimal gbInicial) {
		this.gbInicial = gbInicial;
	}

	public BigDecimal getGbMensal() {
		return this.gbMensal;
	}

	public void setGbMensal(BigDecimal gbMensal) {
		this.gbMensal = gbMensal;
	}

	public BigDecimal getGbAnual() {
		return this.gbAnual;
	}

	public void setGbAnual(BigDecimal gbAnual) {
		this.gbAnual = gbAnual;
	}

	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	public boolean equals(Object object) {
		if (!(object instanceof DaSizingEstimado)) {
			return false;
		}
		DaSizingEstimado other = (DaSizingEstimado) object;

		return ((this.id != null) || (other.id == null)) && ((this.id == null) || (this.id.equals(other.id)));
	}

	public String toString() {
		return "tr.com.Modal.DaSizingEstimado[ id=" + this.id + " ]";
	}
}