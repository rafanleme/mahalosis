package mahalosis.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.CaracteristicaDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Caracteristica;

@Named
@ViewScoped
public class CaracteristicaMBean {
	
	private String metodo = "inserir";
	
	@Inject
	private Caracteristica novaC;
	
	private Caracteristica selC;
	@Inject
	private CaracteristicaDAO cDao;
	private List<Caracteristica> caracteristicas;
	
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
		novaC = new Caracteristica();
		metodo = "inserir";
	}
	
	public void excluir(){
		try {
			if(cDao.excluir(selC)){
				FacesUtils.setMensagem("Excluido com sucesso", "");
				caracteristicas.remove(selC);
				limpar();
			}else{
				FacesUtils.setMensagem("Problemas ao excluir", "");
			}
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro ao excluir.", "Desculpe, tente novamente mais tarde.");
			e.printStackTrace();
		}
	}
	
	
	public void salvar(){
		try{
			if(metodo.equals("inserir")){
				if(cDao.inserir(novaC)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novaC = new Caracteristica();
				}else{
					novaC = new Caracteristica();
				}
			}else if(metodo.equals("editar")){
				if(cDao.editar(novaC)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novaC = new Caracteristica();	
					selC = null;
					metodo = "inserir";
				}
			}
		}catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro ao salvar", "Desculpe, tente novamente mais tarde.");
		}
		atualizar();
	}
	
	public void atualizar(){
		try {
			caracteristicas = cDao.listar();
			selC = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Caracteristica getNovaC() {
		return novaC;
	}

	public void setNovaC(Caracteristica novaC) {
		this.novaC = novaC;
	}

	public Caracteristica getSelC() {
		return selC;
	}

	public void setSelC(Caracteristica selC) {
		this.selC = selC;
	}

	public List<Caracteristica> getCaracteristicas() {
		return caracteristicas;
	}

	public void setCaracteristicas(List<Caracteristica> caracteristicas) {
		this.caracteristicas = caracteristicas;
	}
	
}
