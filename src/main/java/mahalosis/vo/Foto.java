package mahalosis.vo;

import java.io.Serializable;

public class Foto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String nomeArquivo;
	private String codProduto;
	private Boolean principal;
	
	
	public Boolean getPrincipal() {
		return principal;
	}
	public void setPrincipal(Boolean principal) {
		this.principal = principal;
	}
	public String getCodProduto() {
		return codProduto;
	}
	public void setCodProduto(String codProduto) {
		this.codProduto = codProduto;
	}
	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNomeArquivo() {
		return nomeArquivo;
	}
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}	
	
	public String getPath(){
		return this.codProduto + "/" + this.nomeArquivo;
	}
		

}
