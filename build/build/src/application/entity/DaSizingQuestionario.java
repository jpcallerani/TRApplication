package application.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedStoredProcedureQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

@Entity
@Table(name = "DA_SIZING_QUESTIONARIO")
@NamedStoredProcedureQuery(name = "sizingCalculate", procedureName = "pkg_da_sizing.prc_estima_sizing")
@XmlRootElement
public class DaSizingQuestionario implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@Basic(optional = false)
	@Column(name = "ID")
	private Integer id;

	@Basic(optional = false)
	@Column(name = "COD_SISTEMA")
	private Integer codSistema;

	@Basic(optional = false)
	@Column(name = "ENTIDADE")
	private String entidade;

	@Basic(optional = false)
	@Column(name = "QUESTAO")
	private String questao;

	@Basic(optional = false)
	@Column(name = "SEQUENCIA")
	private BigInteger sequencia;

	@Basic(optional = false)
	@Column(name = "QTDE_INICIAL")
	private BigDecimal qtdeInicial;

	@Column(name = "PERC_CRESCIMENTO_MENSAL")
	private BigDecimal percCrescimentoMensal;

	@Basic(optional = false)
	@Column(name = "QTDE_MENSAL")
	private BigDecimal qtdeMensal;

	@Column(name = "RESPOSTA_SIM_NAO")
	private Character respostaSimNao;

	public DaSizingQuestionario() {
	}

	public DaSizingQuestionario(Integer id) {
		this.id = id;
	}

	public DaSizingQuestionario(Integer id, Integer codSistema, String entidade, String questao, BigInteger sequencia,
			BigDecimal qtdeInicial, BigDecimal qtdeMensal) {
		this.id = id;
		this.codSistema = codSistema;
		this.entidade = entidade;
		this.questao = questao;
		this.sequencia = sequencia;
		this.qtdeInicial = qtdeInicial;
		this.qtdeMensal = qtdeMensal;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCodSistema() {
		return this.codSistema;
	}

	public void setCodSistema(Integer codSistema) {
		this.codSistema = codSistema;
	}

	public String getEntidade() {
		return this.entidade;
	}

	public void setEntidade(String entidade) {
		this.entidade = entidade;
	}

	public String getQuestao() {
		return this.questao;
	}

	public void setQuestao(String questao) {
		this.questao = questao;
	}

	public BigInteger getSequencia() {
		return this.sequencia;
	}

	public void setSequencia(BigInteger sequencia) {
		this.sequencia = sequencia;
	}

	public BigDecimal getQtdeInicial() {
		return this.qtdeInicial;
	}

	public void setQtdeInicial(BigDecimal qtdeInicial) {
		this.qtdeInicial = qtdeInicial;
	}

	public BigDecimal getPercCrescimentoMensal() {
		return this.percCrescimentoMensal;
	}

	public void setPercCrescimentoMensal(BigDecimal percCrescimentoMensal) {
		this.percCrescimentoMensal = percCrescimentoMensal;
	}

	public BigDecimal getQtdeMensal() {
		return this.qtdeMensal;
	}

	public void setQtdeMensal(BigDecimal qtdeMensal) {
		this.qtdeMensal = qtdeMensal;
	}

	public Character getRespostaSimNao() {
		return this.respostaSimNao;
	}

	public void setRespostaSimNao(Character respostaSimNao) {
		this.respostaSimNao = respostaSimNao;
	}

	public int hashCode() {
		int hash = 0;
		hash += (this.id != null ? this.id.hashCode() : 0);
		return hash;
	}

	public boolean equals(Object object) {
		if (!(object instanceof DaSizingQuestionario)) {
			return false;
		}
		DaSizingQuestionario other = (DaSizingQuestionario) object;

		return ((this.id != null) || (other.id == null)) && ((this.id == null) || (this.id.equals(other.id)));
	}

	public String toString() {
		return "tr.com.Modal.DaSizingQuestionario[ id=" + this.id + " ]";
	}
}