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
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Telefone;
import mahalosis.vo.Usuario;

@Named
@ViewScoped
public class ClienteMBean implements Serializable {
	
	private static final long serialVersionUID = 1L;

	private String metodo = "inserir";
	
	@Inject
	private Cliente novoC;
	
	@Inject
	UsuarioMBean usuarioMBean;
	
	private Telefone novoT;
	private List<Estabelecimento> estabelecimentos;
	
	private Cliente selC;
	@Inject
	private ClienteDAO cDao;
	private EstabelecimentoDAO eDao;
	private List<Cliente> clientes;
	private List<Cliente> filterClientes;
	
	private String tipoTel;
	
	@PostConstruct
	public void init(){
		cDao = new ClienteDAO();
		eDao = new EstabelecimentoDAO();
		novoT = new Telefone();
		carregarEstab();
		atualizar();
	}
	
	public void carregarEstab(){
		try {
			estabelecimentos = eDao.listarCombo();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar BD", "Desculpe, tente novamente mais tarde");
			e.printStackTrace();
		}
		
	}
	
	public void onRowSelect(){
		
	}
	
	public void editar(){
		novoC = selC;
		metodo = "editar";
	}
	
	public void limpar(){
		selC = null;
		novoC = new Cliente();
		metodo = "inserir";
	}
		
	public void salvarSair(){
		novoC.setCpf(novoC.getCpf().replaceAll("[.-]", ""));
		novoT.setCodArea(novoT.getCodArea().replaceAll("[)(]", ""));
		novoT.setNumero(novoT.getNumero().replace("-", ""));
		novoT.setTipo(tipoTel);
		novoC.getTelefones().add(novoT);
		String cpf = usuarioMBean.getUsuario().getCpf();
		novoC.setUsuarioCriacao(new PessoaFisica(null, null, null, cpf, null));
		novoC.setUsuario(new Usuario(novoC.getCpf(), novoC.getCpf().substring(8), "3"));
		try {
			cDao.inserir(novoC);
			FacesUtils.setMensagem("Cliente cadastrado com sucesso!", "");
			PrimeFaces.current().executeScript("PF('cadastrarDialog').hide()");
			novoC = new Cliente();
			atualizar();
			PrimeFaces.current().ajax().update(":formL:clienteTable");
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops... Erro ao inserir", "Desculpe! Tenta novamente...");
			e.printStackTrace();
		}
	}
	
	public void atualizar(){
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

	
}
