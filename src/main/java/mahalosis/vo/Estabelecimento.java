package mahalosis.vo;

import java.io.Serializable;
import java.util.Date;

public class Estabelecimento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String descricao;
	private String endereco;
	private String bairro;
	private Cidade cidade;
	private String cep;
	private Cliente cliente_contato;
	private Usuario usuarioCriacao;
	private Date dataCriacao;

	public Estabelecimento(Integer codigo, String descricao, String endereco, String bairro, Cidade cidade, String cep,
			Cliente cliente_contato, Usuario usuarioCriacao, Date dataCriacao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.cliente_contato = cliente_contato;
		this.usuarioCriacao = usuarioCriacao;
		this.dataCriacao = dataCriacao;
	}

	public Estabelecimento() {
		// TODO Auto-generated constructor stub
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cliente getCliente_contato() {
		return cliente_contato;
	}

	public void setCliente_contato(Cliente cliente_contato) {
		this.cliente_contato = cliente_contato;
	}

	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public Date getDataCriacao() {
		return dataCriacao;
	}

	public void setDataCriacao(Date dataCriacao) {
		this.dataCriacao = dataCriacao;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	
	
	
}
