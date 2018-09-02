package mahalosis.vo;

import java.io.Serializable;

public class Estado implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private Integer codigo;
	private String nome;
	private String uf;	
	
	public Estado(Integer codigo, String nome, String uf) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.uf = uf;
	}
	
	public Estado() {
		// TODO Auto-generated constructor stub
	}

	public String getUf() {
		return uf;
	}
	public void setUf(String uf) {
		this.uf = uf;
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
	
	
	
}
