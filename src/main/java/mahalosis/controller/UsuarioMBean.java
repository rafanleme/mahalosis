package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;

import javax.annotation.PostConstruct;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.application.FacesMessage.Severity;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpSession;

import mahalosis.dao.UsuarioDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Usuario;

@Named
@SessionScoped
public class UsuarioMBean implements Serializable {
	
	//private static final long serialVersionUID = 1L;
	@Inject
	private Usuario usuario;
	
	@Inject
	private UsuarioDAO uDao;
	
	@PostConstruct
	public void init(){
		System.out.println("entrou! ");
	}
	
	public String autenticar(){
		try {
 			if(uDao.login(usuario) != null){
				HttpSession session = (HttpSession) FacesContext.getCurrentInstance().
						getExternalContext().getSession(true);
				session.setAttribute("logado", true);
				session.setAttribute("perfil", usuario.getPerfil());
				switch (usuario.getPerfil()) {
				case "1":
					return "admin/index.xhtml?faces-redirect=true";
					
				default:
					break;
				}
			}else{
				FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage("Usuário e/ou senha incorretos","Tente Novamente"));
			}
			
		} catch (SQLException e) {
			FacesUtils.setMensagem(FacesMessage.SEVERITY_FATAL, 
							"Opsss, ocorreu um erro.", "Desculpe, tente novamente mais tarde.");
			e.printStackTrace();
		}
		return "";
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	public boolean isLoged(){
		String mensagem = (String) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().remove("mensagem");
		
		if(mensagem != null){
			FacesContext.getCurrentInstance().addMessage(null, 
					new FacesMessage(FacesMessage.SEVERITY_WARN,"Acesso negado!",mensagem));
			return false;
		}else{
			return true;
		}
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	
	

}
