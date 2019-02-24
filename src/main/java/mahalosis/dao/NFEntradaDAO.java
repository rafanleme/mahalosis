package mahalosis.dao;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import org.primefaces.model.UploadedFile;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Fornecedor;
import mahalosis.vo.NFEntrada;
import mahalosis.vo.PessoaFisica;


public class NFEntradaDAO extends BaseDAO implements BasicDAO<NFEntrada>, Serializable {

	public NFEntradaDAO() throws SQLException {
		super();
	}

	private static final long serialVersionUID = 1L;

	@Override
	public List<NFEntrada> listar() throws SQLException {
		String sql = " SELECT * FROM nf_entrada nf"
				+ " INNER JOIN fornecedor f ON nf.cod_fornecedor = f.cod_fornecedor "
				+ " INNER JOIN socio s ON nf.cod_usuario_cadastro = s.cpf ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<NFEntrada> nfs = new ArrayList<>();
		while(rs.next()){
			NFEntrada nf = new NFEntrada();
			nf.setCodigo(rs.getInt("cod_nf_entrada"));
			nf.setDataEmissao(rs.getDate("data_emissao"));
			nf.setDataCadastro(rs.getDate("data_cadastro"));
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("cod_fornecedor"));
			f.setNome(rs.getString("nome"));
			nf.setFornecedor(f);
			nf.setFormaPagamento(rs.getInt("forma_pagamento"));
			nf.setNumeroNF(rs.getString("numero_nf"));
			PessoaFisica uc = new PessoaFisica();
			uc.setCpf(rs.getString("cod_usuario_cadastro"));
			uc.setNome(rs.getString("s.nome"));
			nf.setUsuarioCadastro(uc);
			nf.setValorItens(rs.getBigDecimal("valor_itens"));
			nf.setDescontoValor(rs.getBigDecimal("valor_desconto"));
			nf.setValorTotal(rs.getBigDecimal("valor_total"));
			nf.setDocumento(rs.getString("documento"));
			nf.setObservacoes(rs.getString("observacoes"));
			nfs.add(nf);
		}
		return nfs;
	}

	@Override
	public boolean editar(NFEntrada nf) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean inserir(NFEntrada nf) throws SQLException {
		String sql = " INSERT INTO nf_entrada VALUES (0, ?, ?, null, ?, ?, ?, ?, ?, ?, ?, ?) ";
		ps = con.prepareStatement(sql);
		ps.setString(1, nf.getNumeroNF());
		ps.setDate(2, new Date(nf.getDataEmissao().getTime()));
		ps.setString(3, nf.getUsuarioCadastro().getCpf());
		ps.setInt(4, nf.getFornecedor().getCodigo());
		ps.setBigDecimal(5, nf.getValorItens());
		ps.setBigDecimal(6, nf.getDescontoValor());
		ps.setBigDecimal(7, nf.getValorTotal());
		ps.setInt(8, nf.getFormaPagamento());
		ps.setString(9, nf.getDocumento());
		ps.setString(10, nf.getObservacoes());
		
		return ps.executeUpdate() > 0;
	}

	@Override
	public boolean deletar(int codigo) throws SQLException {
		String sql = " SELECT * FROM item_nf_entrada WHERE cod_nf_entrada = ? ";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			FacesUtils.setMensagem("NF de Entrada ainda tem itens", "Impossível Excluir");
			return false;
		}else{
			sql = " DELETE FROM nf_entrada WHERE cod_nf_entrada = ? ";
			ps = con.prepareStatement(sql);
			return ps.executeUpdate() > 0;
		}
	}

	@Override
	public NFEntrada buscarPorCodigo(int codigo) throws SQLException {
		String sql = " SELECT * FROM nf_entrada nf"
				+ " INNER JOIN fornecedor f ON nf.cod_fornecedor = f.cod_fornecedor "
				+ " INNER JOIN socio s ON nf.cod_usuario_criacao = s.cod_socio "
				+ " WHERE cod_nf_entrada = ? ";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			NFEntrada nf = new NFEntrada();
			nf.setCodigo(rs.getInt("cod_nf_entrada"));
			nf.setDataEmissao(rs.getDate("data_emissao"));
			nf.setDataCadastro(rs.getDate("data_cadastro"));
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("cod_fornecedor"));
			f.setNome(rs.getString("nome"));
			nf.setFornecedor(f);
			nf.setFormaPagamento(rs.getInt("forma_pagamento"));
			nf.setNumeroNF(rs.getString("numero_nf"));
			PessoaFisica uc = new PessoaFisica();
			uc.setCpf(rs.getString("cod_usuario_criacao"));
			uc.setNome(rs.getString("s.nome"));
			nf.setUsuarioCadastro(uc);
			nf.setValorItens(rs.getBigDecimal("valor_itens"));
			nf.setDescontoValor(rs.getBigDecimal("valor_desconto"));
			nf.setValorTotal(rs.getBigDecimal("valor_total"));
			nf.setDocumento(rs.getString("documento"));
			nf.setObservacoes(rs.getString("observacoes"));
			
			return nf;
		}
		return null;
	}

	public boolean salvarNFEntrada(NFEntrada novaNFE, UploadedFile up) throws SQLException {
		if(up != null){
			Long time = Calendar.getInstance().getTimeInMillis();
			String ext = up.getFileName();
			ext = ext.substring(ext.lastIndexOf("."));
			String nomeArquivo = time.toString() + ext;
			try {
				File file = new File(FacesUtils.diretorioDocumentos("nfentrada"), nomeArquivo);
				OutputStream out;
				out = new FileOutputStream(file);
				out.write(up.getContents());
				out.close();
				novaNFE.setDocumento(nomeArquivo);
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}
		return inserir(novaNFE);
	}
}
