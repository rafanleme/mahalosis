package mahalosis.controller;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedProperty;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.context.RequestContext;

import mahalosis.dao.ClienteDAO;
import mahalosis.dao.EstabelecimentoDAO;
import mahalosis.dao.LocalDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Telefone;
import mahalosis.vo.Usuario;

@Named
@ViewScoped
public class ClienteMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String metodo = "inserir";
	private Integer passo = 1;

	@Inject
	private Cliente novoC;
	
	@Inject
	private LocalDAO localDAO;
	
	@Inject
	private ClienteDAO cDao;
	

	@Inject
	UsuarioMBean usuarioMBean;

	private Telefone novoT;
	private List<Estabelecimento> estabelecimentos;
	private List<Estado> estados;
	private Cliente selC;
	private EstabelecimentoDAO eDao;
	private List<Cliente> clientes;
	private List<Cliente> filterClientes;
	private Estado estadoSel;
	private List<Cidade> cidades;
	
	private String tipoTel = "cel";

	@PostConstruct
	public void init() {
		cDao = new ClienteDAO();
		eDao = new EstabelecimentoDAO();
		novoT = new Telefone();
		carregarEstab();
		try {
			estados = localDAO.listarEstados();
			estadoSel = new Estado();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, erro ao buscar estados", "Desculpe, tente novamente mais tarde");
			e.printStackTrace();
		}
		atualizar();
	}

	public void carregarEstab() {
		try {
			estabelecimentos = eDao.listarCombo();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar BD", "Desculpe, tente novamente mais tarde");
			e.printStackTrace();
		}

	}

	public void onRowSelect() {

	}

	public void editar() {
		novoC = selC;
		metodo = "editar";
	}

	public void limpar() {
		selC = null;
		novoC = new Cliente();
		metodo = "inserir";
	}

	public boolean salvarPassoUm() {
		novoC.setCpf(novoC.getCpf().replaceAll("[.-]", ""));
		novoT.setCodArea(novoT.getCodArea().replaceAll("[)(]", ""));
		novoT.setNumero(novoT.getNumero().replace("-", ""));
		novoT.setTipo(tipoTel);
		novoC.getTelefones().add(novoT);
		String cpf = usuarioMBean.getUsuario().getCpf();
		novoC.setUsuarioCriacao(new PessoaFisica(null, null, null, cpf, null));
		novoC.setUsuario(new Usuario(novoC.getCpf(), novoC.getCpf().substring(7), "3"));
		try {
			if (cDao.inserir(novoC)) {
				FacesUtils.setMensagem("Cliente cadastrado com sucesso!", "");
				return true;
			}
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops... Erro ao inserir", "Desculpe! Tenta novamente...");
			e.printStackTrace();
		}
		return false;
	}
	
	public void salvarFim() {
		novoC.setCep(novoC.getCep().replace("-", ""));
		try {
			if(cDao.atualizaComplemento(novoC)){
				FacesUtils.setMensagem("Cliente salvo", "");
				PrimeFaces.current().executeScript("PF('cadastrarDialog').hide()");
				PrimeFaces.current().executeScript("$('#passoUm').show()");
				PrimeFaces.current().executeScript("$('#passoDois').hide()");
				
				reiniciaForm();
				atualizar();
				PrimeFaces.current().ajax().update(":formL:clienteTable");
				
			}else{
				FacesUtils.setMensagem("Ops, ocorreu um erro", "Desculpe, estamos trabalhando nisso.");
			}
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro", "Desculpe, estamos trabalhando nisso.");
			e.printStackTrace();
		}
	}
	
	private void reiniciaForm(){
		novoC = new Cliente();
		novoT = new Telefone();
		tipoTel = "cel";
	}

	public void salvarSair() {
		if (salvarPassoUm()) {
			PrimeFaces.current().executeScript("PF('cadastrarDialog').hide()");
			reiniciaForm();
			atualizar();
			PrimeFaces.current().ajax().update(":formL:clienteTable");
		}
	}

	public void avancar() {
		if (salvarPassoUm()) {
			try {
				estados = localDAO.listarEstados();
				estadoSel = new Estado();
			} catch (SQLException e) {
				FacesUtils.setMensagem("Ops, erro ao buscar estados", "Desculpe, tente novamente mais tarde");
				e.printStackTrace();
			}
			PrimeFaces.current().executeScript("$('#passoUm').slideUp(400);");
			PrimeFaces.current().executeScript("setTimeout(function () { $('#passoDois').slideDown();}, 400);");
		}
	}
	
	public void listarCidades(){
		try {
				cidades = localDAO.listarCidadesPorEstado(estadoSel);
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro ao buscar as cidades", "Desculpe, estamos trabalhando nisso.");
			e.printStackTrace();
		}
	}

	public void atualizar() {
		try {
			clientes = cDao.listar();
			selC = null;
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public Cliente getNovoC() {
		return novoC;
	}

	public void setNovoC(Cliente novaC) {
		this.novoC = novaC;
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

	public Telefone getNovoT() {
		return novoT;
	}

	public void setNovoT(Telefone novoT) {
		this.novoT = novoT;
	}

	public String getTipoTel() {
		return tipoTel;
	}

	public void setTipoTel(String tipoTel) {
		this.tipoTel = tipoTel;
	}

	public List<Estabelecimento> getEstabelecimentos() {
		return estabelecimentos;
	}

	public void setEstabelecimentos(List<Estabelecimento> estabelecimentos) {
		this.estabelecimentos = estabelecimentos;
	}

	public Integer getPasso() {
		return passo;
	}

	public void setPasso(Integer passo) {
		this.passo = passo;
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
