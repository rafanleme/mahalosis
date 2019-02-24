package mahalosis.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class NFEntrada implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private Integer formaPagamento;
	private String numeroNF;
	private Date dataEmissao;
	private Date dataCadastro;
	private BigDecimal valorItens;
	private BigDecimal descontoValor;
	private BigDecimal valorTotal;
	private Fornecedor fornecedor = new Fornecedor();
	private PessoaFisica usuarioCadastro;
	private String documento;
	private String observacoes;
	
	
	public String getDocumento() {
		return documento;
	}
	public void setDocumento(String documento) {
		this.documento = documento;
	}
	public PessoaFisica getUsuarioCadastro() {
		return usuarioCadastro;
	}
	public void setUsuarioCadastro(PessoaFisica usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public Integer getFormaPagamento() {
		return formaPagamento;
	}
	public void setFormaPagamento(Integer formaPagamento) {
		this.formaPagamento = formaPagamento;
	}
	public String getNumeroNF() {
		return numeroNF;
	}
	public void setNumeroNF(String numeroNF) {
		this.numeroNF = numeroNF;
	}
	public Date getDataEmissao() {
		return dataEmissao;
	}
	public void setDataEmissao(Date dataEmissao) {
		this.dataEmissao = dataEmissao;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public BigDecimal getValorItens() {
		return valorItens;
	}
	public void setValorItens(BigDecimal valorItens) {
		this.valorItens = valorItens;
	}
	public BigDecimal getValorTotal() {
		return valorTotal;
	}
	public void setValorTotal(BigDecimal valorTotal) {
		this.valorTotal = valorTotal;
	}
	public Fornecedor getFornecedor() {
		return fornecedor;
	}
	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}
	public BigDecimal getDescontoValor() {
		return descontoValor;
	}
	public void setDescontoValor(BigDecimal descontoValor) {
		this.descontoValor = descontoValor;
	}
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	

}
