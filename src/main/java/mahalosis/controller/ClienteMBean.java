package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.ClienteDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cliente;

@Named
@ViewScoped
public class ClienteMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String metodo = "inserir";
	
	@Inject
	private Cliente novaC;
	
	private Cliente selC;
	@Inject
	private ClienteDAO cDao;
	private List<Cliente> clientes;
	private List<Cliente> filterClientes;
	
	@PostConstruct
	public void init(){
		cDao = new ClienteDAO();
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
		novaC = new Cliente();
		metodo = "inserir";
	}
	
//	public void excluir(){
//		try {
//			if(cDao.excluir(selC)){
//				FacesUtils.setMensagem("Excluido com sucesso", "");
//				clientes.remove(selC);
//				limpar();
//			}else{
//				FacesUtils.setMensagem("Problemas ao excluir", "");
//			}
//		} catch (SQLException e) {
//			FacesUtils.setMensagem("Ops, ocorreu um erro ao excluir.", "Desculpe, tente novamente mais tarde.");
//			e.printStackTrace();
//		}
//	}
//	
//	
//	public void salvar(){
//		try{
//			if(metodo.equals("inserir")){
//				if(cDao.inserir(novaC)){
//					FacesUtils.setMensagem("Salvo com sucesso!", "");
//					novaC = new Cliente();
//				}else{
//					novaC = new Cliente();
//				}
//			}else if(metodo.equals("editar")){
//				if(cDao.editar(novaC)){
//					FacesUtils.setMensagem("Salvo com sucesso!", "");
//					novaC = new Cliente();
//					selC = null;
//					metodo = "inserir";
//				}
//			}
//		}catch (SQLException e) {
//			FacesUtils.setMensagem("Ops, ocorreu um erro ao salvar", "Desculpe, tente novamente mais tarde.");
//		}
//		atualizar();
//	}
	
	public void atualizar(){
		try {
			clientes = cDao.listar();
			selC = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Cliente getNovaC() {
		return novaC;
	}

	public void setNovaC(Cliente novaC) {
		this.novaC = novaC;
	}

	public Cliente getSelC() {
		return selC;
	}

	public void setSelC(Cliente selC) {
		this.selC = selC;
	}

	public List<Cliente> getClientes() {
		return clientes;
	}

	public void setClientes(List<Cliente> clientes) {
		this.clientes = clientes;
	}

	public List<Cliente> getFilterClientes() {
		return filterClientes;
	}

	public void setFilterClientes(List<Cliente> filterClientes) {
		this.filterClientes = filterClientes;
	}
	
}
