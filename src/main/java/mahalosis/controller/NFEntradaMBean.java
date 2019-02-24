package mahalosis.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.context.SessionScoped;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.UploadedFile;

import mahalosis.dao.CategoriaDAO;
import mahalosis.dao.ClienteDAO;
import mahalosis.dao.FornecedorDAO;
import mahalosis.dao.NFEntradaDAO;
import mahalosis.dao.ProdutoDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Categoria;
import mahalosis.vo.Cliente;
import mahalosis.vo.Fornecedor;
import mahalosis.vo.NFEntrada;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Produto;

@Named("nfentradaMBean")
@SessionScoped
public class NFEntradaMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private NFEntrada novaNFE;
	
	@Inject
	private NFEntradaDAO nfeDao;

	@Inject
	UsuarioMBean usuarioMBean;
	
	@Inject
	FornecedorDAO fDao;
	
	@Inject
	transient ProdutoDAO pDao;

	private UploadedFile uploadedFile;
	private NFEntrada selNFE;
	private Date dataVencimento;
	private Produto prodSel;
	
	private List<NFEntrada> filterNFEntradas;
	private List<NFEntrada> listaNFE;
	private List<Fornecedor> fornecedores;

	private Integer codFornecedorSel;

	private Integer codCategoriaSel;

	private Produto novoP;

	private String codGeneroSel;
	
	private Produto produtoAdd;

	private SelectOneMenu selOneMenu;

	@PostConstruct
	public void init() {
		System.out.println("Instanciou NFE Bean");
		try {
			prodSel = new Produto();
			novoP = new Produto();
			produtoAdd = new Produto();
			dataVencimento = new Date();
			novaNFE = new NFEntrada();
			novaNFE.setDescontoValor(new BigDecimal("0"));
			nfeDao = new NFEntradaDAO();
			fornecedores = fDao.listarCombo();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, erro ao buscar estados", "Desculpe, tente novamente mais tarde");
			e.printStackTrace();
		}
		atualizar();
	}
	
	public void onProdutoSelect(SelectEvent event) {
		Produto p = (Produto) event.getObject();
		try {
			p = pDao.buscarPorCodigo(p.getCodigo());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void handleFileUpload(FileUploadEvent event) {
		//System.out.println(event.getFile().getFileName());
//		uploadedFiles.add(event.getFile());
//		try {
//			Foto f = fotoDao.salvarFoto(novoP.getCodigo(), event.getFile());
//			if(f != null){
//				fotosSalvas.add(f);
//			}
//		} catch (SQLException e) {
//			FacesUtils.setMensagem("Erro ao salvar a foto.", "Tenta novamente mais tarde.");
//			e.printStackTrace();
//		}
	}
	
	public void setProdutoSelecionado(Produto p){
		prodSel = p;
		PrimeFaces.current().ajax().update(":form-item");
	}
	
	public List<Produto> completeProduto(String query) {
        List<Produto> results = new ArrayList<>();
        try {
			results = pDao.buscaComplete(query);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
         
        return results;
    }
	
	public String salvar(){
		try {
			String cpfCadastro = usuarioMBean.getUsuario().getCpf();
			novaNFE.setUsuarioCadastro(new PessoaFisica(null, null, null, cpfCadastro, null));
			if (nfeDao.salvarNFEntrada(novaNFE, uploadedFile)) {
				FacesUtils.setMensagem("Nota Fiscal cadastrada com sucesso!", "Adicione os itens da nota fiscal");
				return "form_item_nfentrada?faces-redirect=true";
			} else {
				FacesUtils.setMensagem("Ops... houve um problema.", "Estamos trabalhando para resolver.");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		return null;
	}
	
	public void uploadDocumento(FileUploadEvent event){
		uploadedFile = event.getFile();
	}
	
	public void atualizaValorTotal(){
		novaNFE.setValorTotal(novaNFE.getValorItens().subtract(novaNFE.getDescontoValor()));
	}

	public void editar() {
		novaNFE = selNFE;
	}

	public void atualizar() {
		try {
			listaNFE = nfeDao.listar();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao consultar o BD.", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}
	
	//corrigir bugs
	public void handleDateSelect(SelectEvent event) {
	    RequestContext.getCurrentInstance().execute("PF('nfTable').filter()");
	}
	
	public Date getCurrentDate(){
		return new Date();
	}

	public List<NFEntrada> getFilterNFEntradas() {
		return filterNFEntradas;
	}

	public void setFilterNFEntradas(List<NFEntrada> filterNFEntradas) {
		this.filterNFEntradas = filterNFEntradas;
	}

	public NFEntrada getNovaNFE() {
		return novaNFE;
	}


	public void setNovaNFE(NFEntrada novoNFE) {
		this.novaNFE = novoNFE;
	}


	public NFEntrada getSelNFE() {
		return selNFE;
	}


	public void setSelNFE(NFEntrada selNFE) {
		this.selNFE = selNFE;
	}


	public List<NFEntrada> getListaNFE() {
		return listaNFE;
	}


	public void setListaNFE(List<NFEntrada> listaNFE) {
		this.listaNFE = listaNFE;
	}


	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}


	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}


	public Date getDataVencimento() {
		return dataVencimento;
	}


	public void setDataVencimento(Date dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public UploadedFile getUploadedFile() {
		return uploadedFile;
	}

	public void setUploadedFile(UploadedFile uploadedFile) {
		this.uploadedFile = uploadedFile;
	}

	public Produto getProdSel() {
		return prodSel;
	}

	public void setProdSel(Produto prodSel) {
		this.prodSel = prodSel;
	}

	public Produto getNovoP() {
		return novoP;
	}

	public void setNovoP(Produto novoP) {
		this.novoP = novoP;
	}

	public SelectOneMenu getSelOneMenu() {
		return selOneMenu;
	}

	public void setSelOneMenu(SelectOneMenu selOneMenu) {
		this.selOneMenu = selOneMenu;
	}

	public Integer getCodFornecedorSel() {
		return codFornecedorSel;
	}

	public void setCodFornecedorSel(Integer codFornecedorSel) {
		this.codFornecedorSel = codFornecedorSel;
	}

	public Integer getCodCategoriaSel() {
		return codCategoriaSel;
	}

	public void setCodCategoriaSel(Integer codCategoriaSel) {
		this.codCategoriaSel = codCategoriaSel;
	}

	public Produto getProdutoAdd() {
		return produtoAdd;
	}

	public void setProdutoAdd(Produto produtoAdd) {
		this.produtoAdd = produtoAdd;
	}

	public String getCodGeneroSel() {
		return codGeneroSel;
	}

	public void setCodGeneroSel(String codGeneroSel) {
		this.codGeneroSel = codGeneroSel;
	}
	
}
