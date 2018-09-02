package mahalosis.vo;

import java.io.Serializable;
import java.util.Date;

public class Cliente implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String nome;
	private String cpf;
	private String endereco;
	private String bairro;
	private Cidade cidade;
	private String cep;
	private Estabelecimento estabelecimento;
	private Date dataNasc;
	private Date dataCadastro;
	private Usuario usuarioCriacao;
	private Date dataAlterado;
	private Usuario usuarioAlteracao;
	private String observacoes;
	
	public Cliente(Integer codigo, String nome, String cpf, String endereco, String bairro, Cidade cidade, String cep,
			Estabelecimento estabelecimento, Date dataNasc, Date dataCadastro, Usuario usuarioCriacao,
			Date dataAlterado, Usuario usuarioAlteracao, String observacoes) {
		this.codigo = codigo;
		this.nome = nome;
		this.cpf = cpf;
		this.endereco = endereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.estabelecimento = estabelecimento;
		this.dataNasc = dataNasc;
		this.dataCadastro = dataCadastro;
		this.usuarioCriacao = usuarioCriacao;
		this.dataAlterado = dataAlterado;
		this.usuarioAlteracao = usuarioAlteracao;
		this.observacoes = observacoes;
	}


	public Cliente() {
		
	}


	public Integer getCodigo() {
		return codigo;
	}


	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}


	public String getNome() {
		return nome;
	}


	public void setNome(String nome) {
		this.nome = nome;
	}


	public String getCpf() {
		return cpf;
	}


	public void setCpf(String cpf) {
		this.cpf = cpf;
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


	public Estabelecimento getEstabelecimento() {
		return estabelecimento;
	}


	public void setEstabelecimento(Estabelecimento estabelecimento) {
		this.estabelecimento = estabelecimento;
	}


	public Date getDataNasc() {
		return dataNasc;
	}


	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Usuario getUsuarioCriacao() {
		return usuarioCriacao;
	}


	public void setUsuarioCriacao(Usuario usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}


	public Date getDataAlterado() {
		return dataAlterado;
	}


	public void setDataAlterado(Date dataAlterado) {
		this.dataAlterado = dataAlterado;
	}


	public Usuario getUsuarioAlteracao() {
		return usuarioAlteracao;
	}


	public void setUsuarioAlteracao(Usuario usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}


	public String getObservacoes() {
		return observacoes;
	}


	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}
	
		
	
}
