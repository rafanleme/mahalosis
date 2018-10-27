package mahalosis.vo;

import java.io.Serializable;
import java.util.Date;

public class PessoaFisica implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private Integer codigo;
	private String nome;
	private Date dataNasc;
	private Usuario usuario;
	protected String cpf;
	
	public PessoaFisica(Integer codigo, String nome, Date dataNasc, String cpf, Usuario usuario) {
		super();
		this.codigo = codigo;
		this.nome = nome;
		this.dataNasc = dataNasc;
		this.cpf = cpf;
		this.usuario = usuario;
	}

	public PessoaFisica() {
		super();
	}

	public String getMaskCpf() {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
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
	public Date getDataNasc() {
		return dataNasc;
	}
	public void setDataNasc(Date dataNasc) {
		this.dataNasc = dataNasc;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
		System.out.println(cpf);
	}
	
	

}
