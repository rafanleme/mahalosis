package mahalosis.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.CaracteristicaDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Caracterisica;

@Named
@ApplicationScoped
public class CaracteristicaMBean {
	
	private String metodo = "inserir";
	
	@Inject
	private Caracterisica novaC;
	
	private Caracterisica selC;
	@Inject
	private CaracteristicaDAO cDao;
	private List<Caracterisica> caracteristicas;
	
	@PostConstruct
	public void init(){
		cDao = new CaracteristicaDAO();
		atualizar();
	}
	
	public void onRowSelect(){
		
	}
	
	public void editar(){
		novaC = selC;
		metodo = "editar";
	}
	
	public void limpar(){
		selC = null;
		novaC = new Caracterisica();
		metodo = "inserir";
	}
	
	public void excluir(){
		if(cDao.excluir(selC)){
			FacesUtils.setMensagem("Excluido com sucesso", "");
			caracteristicas.remove(selC);
			limpar();
		}else{
			FacesUtils.setMensagem("Problemas ao excluir", "");
		}
	}
	
	
	public void salvar(){
		if(metodo.equals("inserir")){
			if(cDao.inserir(novaC)){
				FacesUtils.setMensagem("Salvo com sucesso!", "");
				novaC = new Caracterisica();
				return;
			}
		}else if(metodo.equals("editar")){
			if(cDao.editar(novaC)){
				FacesUtils.setMensagem("Salvo com sucesso!", "");
				novaC = new Caracterisica();
				selC = null;
				metodo = "inserir";
				return;
			}
		}
		atualizar();
		FacesUtils.setMensagem(FacesMessage.SEVERITY_ERROR,"Erro ao salvar!", "");
	}
	
	public void atualizar(){
		try {
			caracteristicas = cDao.listar();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Caracterisica getNovaC() {
		return novaC;
	}

	public void setNovaC(Caracterisica novaC) {
		this.novaC = novaC;
	}

	public Caracterisica getSelC() {
		return selC;
	}

	public void setSelC(Caracterisica selC) {
		this.selC = selC;
	}

	public List<Caracterisica> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<Caracterisica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
}
