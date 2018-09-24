package mahalosis.vo;

import java.io.Serializable;

public class Usuario implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cpf;
	private String senha;
	private String perfil;

	public Usuario(String cpf, String senha, String perfil) {
		super();
		this.cpf = cpf;
		this.senha = senha;
		this.perfil = perfil;
	}

	public Usuario() {

	}

	public String getMaskCpf() {
		return cpf.substring(0, 3) + "." + cpf.substring(3, 6) + "." + cpf.substring(6, 9) + "-" + cpf.substring(9);
	}

	public String getCpf() {
		return cpf;
	}

	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

}
