package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mahalosis.vo.Cidade;
import mahalosis.vo.Fornecedor;
import mahalosis.vo.Estado;

public class FornecedorDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public boolean inserir(Fornecedor f) throws SQLException{
		
		String sql = " INSERT INTO fornecedor VALUES (0,?,?,?,?,?,?,?,?); ";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		
		ps.setString(1, f.getNome());
		ps.setString(2, f.getCnpj());
		ps.setString(3, f.getEndereco());
		ps.setString(4, f.getNumeroEndereco());
		ps.setString(5, f.getBairro());
		ps.setInt(6, f.getCidade().getCodigo());
		ps.setString(7, f.getCep());
		ps.setString(8, f.getObservacoes());
		
		System.out.println(ps.toString());
		
		return ps.executeUpdate() > 0;
	}
	
	public List<Fornecedor> listar() throws SQLException {
		String sql = " SELECT f.*, c.nome as cidade, c.cod_estado as uf FROM fornecedor f "
				+ " INNER JOIN cidade c ON f.cod_cidade = c.cod_cidade ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Fornecedor> lista = new ArrayList<Fornecedor>();
		while (rs.next()) {
			Fornecedor f = new Fornecedor();
			f.setCodigo(rs.getInt("cod_fornecedor"));
			f.setNome(rs.getString("nome"));
			f.setCnpj(rs.getString("cnpj"));
			f.setEndereco(rs.getString("endereco"));
			f.setNumeroEndereco(rs.getString("numero_endereco"));
			f.setBairro(rs.getString("bairro"));
			f.setCidade(
					new Cidade(rs.getInt("cod_cidade"), rs.getString("cidade"), new Estado(null, rs.getString("uf"))));
			f.setCep(rs.getString("cep"));
			f.setObservacoes(rs.getString("observacoes"));

			lista.add(f);
		}
		return lista;
	}

	public List<Fornecedor> listarCombo() throws SQLException {
		String sql = " SELECT cod_fornecedor, nome FROM fornecedor ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Fornecedor> lista = new ArrayList<Fornecedor>();
		while (rs.next()) {
			Fornecedor e = new Fornecedor();
			e.setCodigo(rs.getInt("cod_fornecedor"));
			e.setNome(rs.getString("nome"));
			lista.add(e);
		}
		return lista;
	}

	public boolean excluir(Fornecedor f) throws SQLException {
		String sql = "DELETE FROM fornecedor WHERE cod_fornecedor = ?";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, f.getCodigo());
		if (ps.executeUpdate() > 0) {
			return true;
		}
		return false;
	}

}
