package mahalosis.vo;

import java.io.Serializable;

public class Telefone implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String codArea;
	private String numero;
	private String tipo;
	private boolean principal;
	
	public Telefone(Integer codigo, String codArea, String numero, String tipo, boolean principal) {
		super();
		this.codigo = codigo;
		this.codArea = codArea;
		this.numero = numero;
		this.tipo = tipo;
		this.principal = principal;
	}
	
	public Telefone() {

	}

	public Integer getCodigo() {
		return codigo;
	}
	public void setCodigo(Integer codigo) {
		this.codigo = codigo;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public boolean isPrincipal() {
		return principal;
	}
	public void setPrincipal(boolean principal) {
		this.principal = principal;
	}

	public String getCodArea() {
		return codArea;
	}

	public void setCodArea(String codArea) {
		this.codArea = codArea;
	}
	
	
	
	
}
