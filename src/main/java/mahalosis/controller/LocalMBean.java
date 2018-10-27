package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.CaracteristicaDAO;
import mahalosis.dao.LocalDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Caracteristica;
import mahalosis.vo.Cidade;
import mahalosis.vo.Estado;

@Named
@ViewScoped
public class LocalMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Inject
	private LocalDAO localDao;
	
	private Estado estadoSel;
	private List<Estado> estados;
	private List<Cidade> cidades;
	
	@PostConstruct
	public void init(){
		try {
			estados = localDao.listarEstados();
			estadoSel = new Estado();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro.", "Desculpe, estamos trabalhando nisso.");
			e.printStackTrace();
		}
	}
	
	public List<Cidade> listarCidades(){
		try {
			cidades = localDao.listarCidadesPorEstado(estadoSel);
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro ao buscar as cidades", "Desculpe, estamos trabalhando nisso.");
			e.printStackTrace();
		}
		return null;
	}
	
	
	public List<Estado> getEstados() {
		return estados;
	}

	public void setEstados(List<Estado> estados) {
		this.estados = estados;
	}

	public Estado getEstadoSel() {
		return estadoSel;
	}

	public void setEstadoSel(Estado estadoSel) {
		this.estadoSel = estadoSel;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}
	
}
