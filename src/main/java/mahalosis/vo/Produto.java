package mahalosis.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Date;

import javax.management.monitor.MonitorSettingException;

import mahalosis.dao.FotoDAO;

public class Produto implements Serializable{

	
	private static final long serialVersionUID = 1L;
	private Integer codigo;
	private String codigoFornecedor;
	private Fornecedor fornecedor;
	private Categoria categoria;
	private String tamanho;
	private String genero;
	private String descricao;
	private Float comprimento;
	private Float espessura;
	private Float peso;
	private BigDecimal valorCusto;
	private BigDecimal valorVenda;
	private BigDecimal descontoPorcentagem;
	private BigDecimal descontoValor;
	private boolean promocao;
	private Date dataCadastro;
	private PessoaFisica usuarioCadastro;
	private String fotoPrincipal;

	public Produto() {
		// TODO Auto-generated constructor stub
	}

	public Produto(Integer codigo, String codigoFornecedor, Fornecedor fornecedor, Categoria categoria, String tamanho,
			String genero, String descricao, Float comprimento, Float espessura, Float peso, BigDecimal valorCusto,
			BigDecimal valorVenda, BigDecimal descontoPorcentagem, BigDecimal descontoValor, boolean promocao,
			Date dataCadastro, PessoaFisica usuarioCadastro) {
		super();
		this.codigo = codigo;
		this.codigoFornecedor = codigoFornecedor;
		this.fornecedor = fornecedor;
		this.categoria = categoria;
		this.tamanho = tamanho;
		this.genero = genero;
		this.descricao = descricao;
		this.comprimento = comprimento;
		this.espessura = espessura;
		this.peso = peso;
		this.valorCusto = valorCusto;
		this.valorVenda = valorVenda;
		this.descontoPorcentagem = descontoPorcentagem;
		this.descontoValor = descontoValor;
		this.promocao = promocao;
		this.dataCadastro = dataCadastro;
		this.usuarioCadastro = usuarioCadastro;
	}

	public void calcularDescontoValor() {
		if (descontoPorcentagem != null) {
			BigDecimal porc = descontoPorcentagem.divide(new BigDecimal(100)).subtract(new BigDecimal(1))
					.multiply(new BigDecimal(-1));
			descontoValor = porc.multiply(valorVenda);
			descontoValor = valorVenda.subtract(descontoValor);
		}
	}

	public void calcularDescontoPorcentagem() {
		if (descontoValor != null) {
			BigDecimal valor = descontoValor.divide(valorVenda);
			descontoPorcentagem = valor.multiply(new BigDecimal(100));
		}
	}
	
	public String getFotoPrincipal() throws SQLException{
		if(fotoPrincipal == null){
			FotoDAO fDao = new FotoDAO();
			fotoPrincipal = fDao.buscarPrincipal(codigo);
		}
		return fotoPrincipal;
	}

	public Integer getCodigo() {
		return codigo;
	}

	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}

	public Fornecedor getFornecedor() {
		return fornecedor;
	}

	public void setFornecedor(Fornecedor fornecedor) {
		this.fornecedor = fornecedor;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public String getTamanho() {
		return tamanho;
	}

	public void setTamanho(String tamanho) {
		this.tamanho = tamanho;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Float getComprimento() {
		return comprimento;
	}

	public void setComprimento(Float comprimento) {
		this.comprimento = comprimento;
	}

	public Float getEspessura() {
		return espessura;
	}

	public void setEspessura(Float espessura) {
		this.espessura = espessura;
	}

	public Float getPeso() {
		return peso;
	}

	public void setPeso(Float peso) {
		this.peso = peso;
	}

	public BigDecimal getValorCusto() {
		return valorCusto;
	}

	public void setValorCusto(BigDecimal valorCusto) {
		this.valorCusto = valorCusto;
	}

	public BigDecimal getValorVenda() {
		return valorVenda;
	}

	public void setValorVenda(BigDecimal valorVenda) {
		this.valorVenda = valorVenda;
	}

	public BigDecimal getDescontoPorcentagem() {
		return descontoPorcentagem;
	}

	public void setDescontoPorcentagem(BigDecimal descontoPorcentagem) {
		this.descontoPorcentagem = descontoPorcentagem;
	}

	public BigDecimal getDescontoValor() {
		return descontoValor;
	}

	public void setDescontoValor(BigDecimal descontoValor) {
		this.descontoValor = descontoValor;
	}

	public boolean isPromocao() {
		return promocao;
	}

	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
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

	public String getCodigoFornecedor() {
		return codigoFornecedor;
	}

	public void setCodigoFornecedor(String codigoFornecedor) {
		this.codigoFornecedor = codigoFornecedor;
	}

}
