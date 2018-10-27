package mahalosis.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.ClienteDAO;
import mahalosis.dao.EstabelecimentoDAO;
import mahalosis.dao.LocalDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;

@Named
@ViewScoped
public class EstabelecimentoMBean {
	
	private String metodo = "inserir";
	
	private Estabelecimento novoE;
	
	private Estabelecimento selE;
	
	@Inject
	UsuarioMBean usuarioMbean;
	
	@Inject
	private EstabelecimentoDAO eDao;
	@Inject
	private LocalDAO localDao;
	private List<Estabelecimento> estabelecimentos;
	private List<Estabelecimento> filterEstabelecimentos;
	private List<Cidade> cidades;
	private Estado estadoSel;
	
	@PostConstruct
	public void init(){ 
		novoE = new Estabelecimento();
		eDao = new EstabelecimentoDAO();
		atualizar();
	}
	
	public void onRowSelect(){
		
	}
	
	public void editar(){
		novoE = selE;
		metodo = "editar";
	}
	
	public void limpar(){
		selE = null;
		novoE = new Estabelecimento();
		metodo = "inserir";
	}
	
	public void excluir(){
		try {
			if(eDao.excluir(selE)){
				FacesUtils.setMensagem("Excluido com sucesso", "");
				estabelecimentos.remove(selE);
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
				novoE.setCep(novoE.getCep().replace("-", ""));
				novoE.setUsuarioCriacao(new PessoaFisica(null, null, null, usuarioMbean.getUsuario().getCpf(), null));
				if(eDao.inserir(novoE)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novoE = new Estabelecimento();
				}else{
					novoE = new Estabelecimento();
				}
			}else if(metodo.equals("editar")){
//				if(eDao.editar(novoE)){
//					FacesUtils.setMensagem("Salvo com sucesso!", "");
//					novoE = new Estabelecimento();
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
	
	public Integer qtdeClientesPorEstabelecimento(Integer codEstabelecimento){
		try {
			return eDao.qtdeClientesPorEstabelecimento(codEstabelecimento);
		} catch (SQLException e) {
			FacesUtils.setMensagem(FacesMessage.SEVERITY_ERROR, "Ops, ocorreu um erro ao buscar clientes", "Desculpe, estamos trabalhando nisso.");
			e.printStackTrace();
		}
		return null;
	}
	
	public void atualizar(){
		try {
			estabelecimentos = eDao.listar();
			selE = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Estabelecimento getSelC() {
		return selE;
	}

	public void setSelC(Estabelecimento selC) {
		this.selE = selC;
	}

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public List<Estabelecimento> getFilterEstabelecimentos() {
		return filterEstabelecimentos;
	}

	public void setFilterEstabelecimentos(List<Estabelecimento> filterEstabelecimentos) {
		this.filterEstabelecimentos = filterEstabelecimentos;
	}

	public Estabelecimento getNovoE() {
		return novoE;
	}

	public void setNovoE(Estabelecimento novoE) {
		this.novoE = novoE;
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
