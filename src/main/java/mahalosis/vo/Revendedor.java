package mahalosis.vo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Revendedor extends PessoaFisica {

	private static final long serialVersionUID = 1L;
	private String rg;
	private String referencia;
	private String endereco;
	private String numeroEndereco;
	private String bairro;
	private String cep;
	private Cidade cidade = new Cidade();
	private Date dataContrato;
	private Date dataCadastro;
	private Date dataAlteracao;
	private PessoaFisica usuarioCadastro;
	private PessoaFisica usuarioAlteracao;
	private Boolean ativo;
	private Usuario usuario;
	private List<Telefone> telefone;
	
	public Revendedor(Integer codigo, String nome, Date dataNasc, String cpf, Usuario usuario, String rg,
			String referencia, String endereco, String numeroEndereco, String bairro, String cep, Cidade cidade,
			Date dataContrato, Date dataCadastro, Date dataAlteracao, PessoaFisica usuarioCadastro,
			PessoaFisica usuarioAlteracao, Boolean ativo, Usuario usuario2, List<Telefone> telefone) {
		super(codigo, nome, dataNasc, cpf, usuario);
		this.rg = rg;
		this.referencia = referencia;
		this.endereco = endereco;
		this.numeroEndereco = numeroEndereco;
		this.bairro = bairro;
		this.cep = cep;
		this.cidade = cidade;
		this.dataContrato = dataContrato;
		this.dataCadastro = dataCadastro;
		this.dataAlteracao = dataAlteracao;
		this.usuarioCadastro = usuarioCadastro;
		this.usuarioAlteracao = usuarioAlteracao;
		this.ativo = ativo;
		usuario = usuario2;
		this.telefone = telefone;
	}

	public Revendedor() {
		telefone = new ArrayList<>();
	}

	public String getRg() {
		return rg;
	}

	public void setRg(String rg) {
		this.rg = rg;
	}

	public String getReferencia() {
		return referencia;
	}

	public void setReferencia(String referencia) {
		this.referencia = referencia;
	}

	public String getEndereco() {
		return endereco;
	}

	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

	public Date getDataContrato() {
		return dataContrato;
	}

	public void setDataContrato(Date dataContrato) {
		this.dataContrato = dataContrato;
	}

	public Date getDataCadastro() {
		return dataCadastro;
	}

	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}

	public PessoaFisica getUsuarioCadastro() {
		return usuarioCadastro;
	}

	public void setUsuarioCadastro(PessoaFisica usuarioCadastro) {
		this.usuarioCadastro = usuarioCadastro;
	}

	public List<Telefone> getTelefone() {
		return telefone;
	}

	public void setTelefone(List<Telefone> telefone) {
		this.telefone = telefone;
	}

	public Date getDataAlteracao() {
		return dataAlteracao;
	}

	public void setDataAlteracao(Date dataAlteracao) {
		this.dataAlteracao = dataAlteracao;
	}

	public PessoaFisica getUsuarioAlteracao() {
		return usuarioAlteracao;
	}

	public void setUsuarioAlteracao(PessoaFisica usuarioAlteracao) {
		this.usuarioAlteracao = usuarioAlteracao;
	}

	public Boolean getAtivo() {
		return ativo;
	}

	public void setAtivo(Boolean ativo) {
		this.ativo = ativo;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

}
