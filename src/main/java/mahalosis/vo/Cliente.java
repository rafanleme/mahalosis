package mahalosis.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;

public class Cliente extends PessoaFisica implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String endereco;
	private String numeroEndereco;
	private String bairro;
	private Cidade cidade = new Cidade();
	private String cep;
	@Inject
	private Estabelecimento estabelecimento;
	private Date dataCadastro;
	private PessoaFisica usuarioCriacao;
	private Date dataAlterado;
	private PessoaFisica usuarioAlteracao;
	private String observacoes;
	private List<Telefone> telefones;
	
	public Cliente(Integer codigo, String nome, Date dataNasc, String cpf, Usuario usuario, String endereco, String numeroEndereco,
			String bairro, Cidade cidade, String cep, Estabelecimento estabelecimento, Date dataCadastro,
			PessoaFisica usuarioCriacao, Date dataAlterado, PessoaFisica usuarioAlteracao, String observacoes,List<Telefone> telefones) {
		super(codigo, nome, dataNasc, cpf, usuario);
		this.endereco = endereco;
		this.numeroEndereco = numeroEndereco;
		this.cpf = cpf;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.estabelecimento = estabelecimento;
		this.dataCadastro = dataCadastro;
		this.usuarioCriacao = usuarioCriacao;
		this.dataAlterado = dataAlterado;
		this.usuarioAlteracao = usuarioAlteracao;
		this.observacoes = observacoes;
		this.telefones = telefones;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public Cliente() {
		super();
		estabelecimento = new Estabelecimento();
		telefones = new ArrayList<Telefone>();
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


	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public Date getDataAlterado() {
		return dataAlterado;
	}


	public void setDataAlterado(Date dataAlterado) {
		this.dataAlterado = dataAlterado;
	}


	public String getObservacoes() {
		return observacoes;
	}


	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public PessoaFisica getUsuarioCriacao() {
		return usuarioCriacao;
	}

	public void setUsuarioCriacao(PessoaFisica usuarioCriacao) {
		this.usuarioCriacao = usuarioCriacao;
	}

	public PessoaFisica getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(PessoaFisica usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	
}
