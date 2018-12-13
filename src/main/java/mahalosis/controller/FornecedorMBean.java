package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.ClienteDAO;
import mahalosis.dao.FornecedorDAO;
import mahalosis.dao.LocalDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Fornecedor;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;

@Named
@ViewScoped
public class FornecedorMBean implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private String metodo = "inserir";
	
	private Fornecedor novoF;
	
	private Fornecedor selF;
	
	@Inject
	UsuarioMBean usuarioMbean;
	
	@Inject
	private FornecedorDAO fDao;
	@Inject
	private LocalDAO localDao;
	private List<Fornecedor> fornecedores;
	private List<Fornecedor> filterFornecedores;
	private List<Cidade> cidades;
	private Estado estadoSel;
	
	@PostConstruct
	public void init(){ 
		novoF = new Fornecedor();
		fDao = new FornecedorDAO();
		atualizar();
	}
	
	public void onRowSelect(){
		
	}
	
	public void editar(){
		novoF = selF;
		metodo = "editar";
	}
	
	public void limpar(){
		selF = null;
		novoF = new Fornecedor();
		metodo = "inserir";
	}
	
	public void excluir(){
		try {
			if(fDao.excluir(selF)){
				FacesUtils.setMensagem("Excluido com sucesso", "");
				fornecedores.remove(selF);
				limpar();
			}else{
				FacesUtils.setMensagem("Problemas ao excluir", "");
			}
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro ao excluir.", "Desculpe, tente novamente mais tarde.");
			e.printStackTrace();
		}
	}
	
	public List<Cliente> completeCliente(String query) {
        List<Cliente> results = new ArrayList<>();
        ClienteDAO cDao = new ClienteDAO();
        try {
			results = cDao.buscaComplete(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        return results;
    }
	
	
	public void salvar(){
		try{
			if(metodo.equals("inserir")){
				novoF.setCep(novoF.getCep().replace("-", ""));
				if(fDao.inserir(novoF)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novoF = new Fornecedor();
				}else{
					novoF = new Fornecedor();
				}
			}else if(metodo.equals("editar")){
//				if(eDao.editar(novoE)){
//					FacesUtils.setMensagem("Salvo com sucesso!", "");
//					novoE = new Fornecedor();
//					selE = null;
//					metodo = "inserir";
//				}
			}
		}catch (SQLException e) {
			e.printStackTrace();
			FacesUtils.setMensagem("Ops, ocorreu um erro ao salvar", "Desculpe, tente novamente mais tarde.");
		}
		atualizar();
	}
	
	
	public void atualizar(){
		try {
			fornecedores = fDao.listar();
			selF = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Fornecedor getSelC() {
		return selF;
	}

	public void setSelC(Fornecedor selC) {
		this.selF = selC;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedor) {
		this.fornecedores = fornecedor;
	}

	public List<Fornecedor> getFilterFornecedores() {
		return filterFornecedores;
	}

	public void setFilterFornecedores(List<Fornecedor> filterFornecedores) {
		this.filterFornecedores = filterFornecedores;
	}

	
	public Fornecedor getNovoF() {
		return novoF;
	}

	public void setNovoF(Fornecedor novoF) {
		this.novoF = novoF;
	}

	public Fornecedor getSelF() {
		return selF;
	}

	public void setSelF(Fornecedor selF) {
		this.selF = selF;
	}

	public List<Cidade> getCidades() {
		return cidades;
	}

	public void setCidades(List<Cidade> cidades) {
		this.cidades = cidades;
	}

	public Estado getEstadoSel() {
		return estadoSel;
	}

	public void setEstadoSel(Estado estadoSel) {
		this.estadoSel = estadoSel;
	}
	
}
