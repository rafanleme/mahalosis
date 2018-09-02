package mahalosis.controller;

import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.CategoriaDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Categoria;

@Named
@ViewScoped
public class CategoriaMBean {
	
	private String metodo = "inserir";
	
	@Inject
	private Categoria novaC;
	
	private Categoria selC;
	@Inject
	private CategoriaDAO cDao;
	private List<Categoria> categorias;
	private List<Categoria> filterCategorias;
	
	@PostConstruct
	public void init(){
		cDao = new CategoriaDAO();
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
		novaC = new Categoria();
		metodo = "inserir";
	}
	
	public void excluir(){
		try {
			if(cDao.excluir(selC)){
				FacesUtils.setMensagem("Excluido com sucesso", "");
				categorias.remove(selC);
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
					novaC = new Categoria();
				}else{
					novaC = new Categoria();
				}
			}else if(metodo.equals("editar")){
				if(cDao.editar(novaC)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novaC = new Categoria();
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
			categorias = cDao.listar();
			selC = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Categoria getNovaC() {
		return novaC;
	}

	public void setNovaC(Categoria novaC) {
		this.novaC = novaC;
	}

	public Categoria getSelC() {
		return selC;
	}

	public void setSelC(Categoria selC) {
		this.selC = selC;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Categoria> getFilterCategorias() {
		return filterCategorias;
	}

	public void setFilterCategorias(List<Categoria> filterCategorias) {
		this.filterCategorias = filterCategorias;
	}
	
}
