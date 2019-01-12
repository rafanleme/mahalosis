package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import mahalosis.dao.LocalDAO;
import mahalosis.dao.RevendedorDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Revendedor;
import mahalosis.vo.Telefone;
import mahalosis.vo.Usuario;

@Named
@ViewScoped
public class RevendedorMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String metodo = "inserir";
	
	private Revendedor novoR;
	
	private Revendedor selR;
	
	@Inject
	UsuarioMBean usuarioMbean;
	
	@Inject
	private RevendedorDAO rDao;
	
	@Inject
	private LocalDAO localDao;
	
	private List<Revendedor> revendedores;
	private List<Revendedor> filterRevendedores;
	private List<Cidade> cidades;
	private Estado estadoSel;
	
	private Telefone novoT;
	
	private String tipoTel = "cel";
	
	@PostConstruct
	public void init(){ 
		try {
			revendedores = rDao.listar();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, erro ao buscar revendedores", "Tente novamente");
			e.printStackTrace();
		}
		novoR = new Revendedor();
		novoT = new Telefone();
		atualizar();
	}
	
	public void onRowSelect(){
		
	}
	
	public void editar(){
		novoR = selR;
		metodo = "editar";
	}
	
	public void limpar(){
		selR = null;
		novoR = new Revendedor();
		metodo = "inserir";
	}
	
	
	public void salvar(){
		try{
			if(metodo.equals("inserir")){
				novoR.setCep(novoR.getCep().replace("-", ""));
				novoR.setCpf(novoR.getCpf().replaceAll("[.-]", ""));
				Usuario u = new Usuario();
				u.setCpf(novoR.getCpf());
				u.setSenha(novoR.getCpf().substring(0, 5));
				u.setPerfil("2");
				novoR.setUsuario(u);
				novoR.setUsuarioCadastro(new PessoaFisica(null, null, null, usuarioMbean.getUsuario().getCpf(), null));
				novoT.explodeCodArea();
				novoT.setTipo(tipoTel);
				novoR.getTelefone().add(novoT);
				if(rDao.inserir(novoR)){
					FacesUtils.setMensagem("Salvo com sucesso!", "");
					novoR = new Revendedor();
				}else{
					novoR = new Revendedor();
				}
			}else if(metodo.equals("editar")){
//				if(eDao.editar(novoE)){
//					FacesUtils.setMensagem("Salvo com sucesso!", "");
//					novoE = new Revendedor();
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
			revendedores = rDao.listar();
			selR = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public String getMetodo() {
		return metodo;
	}

	public void setMetodo(String metodo) {
		this.metodo = metodo;
	}

	public Revendedor getNovoR() {
		return novoR;
	}

	public void setNovoR(Revendedor novoR) {
		this.novoR = novoR;
	}

	public Revendedor getSelR() {
		return selR;
	}

	public void setSelR(Revendedor selR) {
		this.selR = selR;
	}

	public List<Revendedor> getRevendedores() {
		return revendedores;
	}

	public void setRevendedores(List<Revendedor> revendedores) {
		this.revendedores = revendedores;
	}

	public List<Revendedor> getFilterRevendedores() {
		return filterRevendedores;
	}

	public void setFilterRevendedores(List<Revendedor> filterRevendedores) {
		this.filterRevendedores = filterRevendedores;
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

	public String getTipoTel() {
		return tipoTel;
	}

	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}

	public Telefone getNovoT() {
		return novoT;
	}

	public void setNovoT(Telefone novoT) {
		this.novoT = novoT;
	}

	
	
}
