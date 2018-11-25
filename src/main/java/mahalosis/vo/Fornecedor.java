package mahalosis.vo;

public class Fornecedor {

	private Integer codigo;
	private String nome;
	private String cnpj;
	private String endereco;
	private String numeroEndereco;
	private String bairro;
	private Cidade cidade = new Cidade();
	private String cep;
	private String observacoes;
	
	public Fornecedor() {
		
	}
	
	
	
	public Fornecedor(Integer codigo, String nome, String cnpj, String endereco, String numeroEndereco, String bairro,
			Cidade cidade, String cep, String observacoes) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.cnpj = cnpj;
		this.endereco = endereco;
		this.numeroEndereco = numeroEndereco;
		this.bairro = bairro;
		this.cidade = cidade;
		this.cep = cep;
		this.observacoes = observacoes;
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
	public String getCnpj() {
		return cnpj;
	}
	public void setCnpj(String cnpj) {
		this.cnpj = cnpj;
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
	public String getObservacoes() {
		return observacoes;
	}
	public void setObservacoes(String observacoes) {
		this.observacoes = observacoes;
	}

	public String getNumeroEndereco() {
		return numeroEndereco;
	}

	public void setNumeroEndereco(String numeroEndereco) {
		this.numeroEndereco = numeroEndereco;
	}
	
	
	
	
}
