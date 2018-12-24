package mahalosis.controller;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.selectonemenu.SelectOneMenu;
import org.primefaces.event.FileUploadEvent;
import org.primefaces.model.UploadedFile;

import mahalosis.dao.CategoriaDAO;
import mahalosis.dao.FornecedorDAO;
import mahalosis.dao.FotoDAO;
import mahalosis.dao.ProdutoDAO;
import mahalosis.utils.FacesUtils;
import mahalosis.vo.Categoria;
import mahalosis.vo.Fornecedor;
import mahalosis.vo.Foto;
import mahalosis.vo.Produto;

@Named
@ConversationScoped
public class ProdutoMBean implements Serializable {

	private static final long serialVersionUID = 1L;

	@Inject
	private Produto novoP;

	@Inject
	private FotoDAO fotoDao;

	@Inject
	private FornecedorDAO fDao;

	@Inject
	private CategoriaDAO catDao;

	@Inject
	UsuarioMBean usuarioMBean;

	private UploadedFile fotoPrincipal;

	private List<Produto> produtos;
	private List<Fornecedor> fornecedores;
	private List<Categoria> categorias;
	private List<UploadedFile> uploadedFiles;
	private List<Foto> fotosSalvas;
	private ProdutoDAO pDao;

	private Integer codFornecedorSel;
	private Integer codCategoriaSel;
	private String codGeneroSel;
	private boolean mostraFoto = false;
	private boolean promocao = true;
	private Produto produtoSel;
	private String filtro;
	
	private SelectOneMenu selOneMenu;

	@PostConstruct
	public void init() {
		System.out.println("produtoMbean criado");
		try {
			pDao = new ProdutoDAO();
			fotoDao = new FotoDAO();
			produtos = pDao.listar();
			fornecedores = fDao.listarCombo();
			categorias = catDao.listar();
			uploadedFiles = new ArrayList<>();
			fotosSalvas = new ArrayList<>();
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao conectar ao banco de dados", "Tente novamente mais tarde.");
			e.printStackTrace();
		}
	}

	public void calcularPrecoVenda() {
		BigDecimal aux = novoP.getValorCusto().multiply(new BigDecimal(2.97));
		novoP.setValorVenda(aux.setScale(0, BigDecimal.ROUND_UP));
	}

	public void setSugestaoCodigo() {
		try {
			novoP.setCodigo(pDao.getCodigoMax(codCategoriaSel) + 1);
			setPreDescricao();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	private void setPreDescricao() {
		CategoriaDAO cDao = new CategoriaDAO();
		try {
			novoP.setDescricao(cDao.buscarPorId(codCategoriaSel).getDescricao() + " ");
		} catch (SQLException e) {
			FacesUtils.setMensagem("Ops, ocorreu um erro", "");
			e.printStackTrace();
		}
	}

	public void setPromocao() {
		promocao = !promocao;
	}

	public void limparForm() {
		System.out.println("passou");
		codFornecedorSel = null;
		codCategoriaSel = null;
		codGeneroSel = null;
		novoP = new Produto();
	}

	public void handleFileUpload(FileUploadEvent event) {
		System.out.println(event.getFile().getFileName());
		uploadedFiles.add(event.getFile());
		try {
			Foto f = fotoDao.salvarFoto(novoP.getCodigo(), event.getFile());
			if(f != null){
				fotosSalvas.add(f);
			}
		} catch (SQLException e) {
			FacesUtils.setMensagem("Erro ao salvar a foto.", "Tenta novamente mais tarde.");
			e.printStackTrace();
		}
	}

	

	public String salvar() throws SQLException, IOException {
		Fornecedor f = new Fornecedor();
		f.setCodigo(codFornecedorSel);
		Categoria c = new Categoria();
		c.setCodigo(codCategoriaSel);
		novoP.setFornecedor(f);
		novoP.setCategoria(c);
		novoP.setGenero(codGeneroSel);

		try {

			if (pDao.inserir(novoP)) {
				FacesUtils.setMensagem("Produto inserido com sucesso", "");
				return "form-produto-foto?faces-redirect=true";
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;

	}

	@Inject
	private Conversation conversation;

	public void beginConversation() {
		if (conversation.isTransient()) {
			conversation.setTimeout(1800000L);
			conversation.begin();
		}
	}

	public void endConversation() {
		System.out.println("Encerrando conversa");
		if (!conversation.isTransient()) {
			conversation.end();
			System.out.println("Conversa encerrada");
		}
	}
	
	public void filtrar(){
		try {
			produtos = pDao.listarFiltro(filtro);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void teste() {

	}

	public List<Produto> getProdutos() {
		return produtos;
	}

	public void setProdutos(List<Produto> produtos) {
		this.produtos = produtos;
	}

	public List<Fornecedor> getFornecedores() {
		return fornecedores;
	}

	public void setFornecedores(List<Fornecedor> fornecedores) {
		this.fornecedores = fornecedores;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
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

	public String getCodGeneroSel() {
		return codGeneroSel;
	}

	public void setCodGeneroSel(String codGeneroSel) {
		this.codGeneroSel = codGeneroSel;
	}

	public Produto getNovoP() {
		return novoP;
	}

	public void setNovoP(Produto novoP) {
		this.novoP = novoP;
	}

	public UploadedFile getFotoPrincipal() {
		return fotoPrincipal;
	}

	public void setFotoPrincipal(UploadedFile fotoPrincipal) {
		this.fotoPrincipal = fotoPrincipal;
	}

	public boolean isMostraFoto() {
		return mostraFoto;
	}

	public void setMostraFoto(boolean mostraFoto) {
		this.mostraFoto = mostraFoto;
	}

	public boolean isPromocao() {
		return promocao;
	}

	public void setPromocao(boolean promocao) {
		this.promocao = promocao;
	}

	public List<Foto> getFotosSalvas() {
		return fotosSalvas;
	}

	public void setFotosSalvas(List<Foto> fotosSalvas) {
		this.fotosSalvas = fotosSalvas;
	}

	public Produto getProdutoSel() {
		return produtoSel;
	}

	public void setProdutoSel(Produto produtoSel) {
		this.produtoSel = produtoSel;
	}

	public SelectOneMenu getSelOneMenu() {
		return selOneMenu;
	}

	public void setSelOneMenu(SelectOneMenu selOneMenu) {
		this.selOneMenu = selOneMenu;
	}

	public String getFiltro() {
		return filtro;
	}

	public void setFiltro(String filtro) {
		this.filtro = filtro;
	}
}
