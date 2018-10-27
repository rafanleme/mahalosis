package mahalosis.vo;

import java.io.Serializable;
import java.util.Date;

public class Estabelecimento implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String descricao;
	private String endereco;
	private String numeroEndereco;
	private String bairro;
	private Cidade cidade = new Cidade();
	private String cep;
	private Cliente clienteContato;
	private PessoaFisica usuarioCriacao;
	private Date dataCriacao;

	public Estabelecimento(Integer codigo, String descricao, String endereco, String numeroEndereco, String bairro, Cidade cidade, String cep,
			Cliente cliente_contato, PessoaFisica usuarioCriacao, Date dataCriacao) {
		super();
		this.codigo = codigo;
		this.descricao = descricao;
		this.endereco = endereco;
		this.numeroEndereco = numeroEndereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.clienteContato = cliente_contato;
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

	public Cliente getClienteContato() {
		if(clienteContato == null){
			clienteContato = new Cliente();
		}
		return clienteContato;
	}

	public void setClienteContato(Cliente cliente_contato) {
		this.clienteContato = cliente_contato;
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

	public PessoaFisica getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(PessoaFisica usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}
	
	
	
}
