package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.Estado;
import mahalosis.vo.Usuario;

public class ClienteDAO implements Serializable {


	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public List<Cliente> listar() throws SQLException {
		String sql = " SELECT c.*, ci.*, ci.nome as nome_cidade, e.cod_estabelecimento, e.descricao as desc_estabelecimento  FROM cliente c "
				+ " INNER JOIN cidade ci ON (c.cod_cidade = ci.cod_cidade) "
				+ " LEFT JOIN estabelecimento e ON (c.cod_estabelec = e.cod_estabelecimento) ";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Cliente> lista = new ArrayList<Cliente>();
		while (rs.next()) {
			Estado e = new Estado();
			e.setUf(rs.getString("cod_estado"));
			Cidade ci = new  Cidade(
					rs.getInt("cod_cidade"), 
					rs.getString("nome_cidade"),
					e);
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getInt("cod_estabelecimento"));
			est.setDescricao(rs.getString("desc_estabelecimento"));
			Cliente c = new Cliente(rs.getInt("cod_cliente"), 
					rs.getString("nome"), 
					rs.getString("cpf"), 
					rs.getString("endereco"), 
					rs.getString("bairro"), 
					ci,
					rs.getString("cep"), 
					est, 
					rs.getDate("data_nasc"), 
					rs.getDate("data_cadastro"), 
					new Usuario(rs.getString("cod_usuario_criacao"), null, null), 
					rs.getDate("data_alteracao"), 
					new Usuario(rs.getString("cod_usuario_alteracao"),null,null), 
					rs.getString("observacoes"));
			lista.add(c);
		}
		return lista;
	}

//	public boolean inserir(Cliente c) throws SQLException {
//		if (validarInserir(c)) {
//			String sql = "INSERT INTO cliente VALUES (0,?)";
//
//			con = ConnectionDB.getConnection();
//			ps = con.prepareStatement(sql);
//			ps.setString(1, c.getDescricao());
//			if (ps.executeUpdate() > 0) {
//				return true;
//			}
//
//		}
//		return false;
//	}
//
//	public boolean validarInserir(Cliente c) {
//		String sql = "SELECT * FROM cliente " + " WHERE descricao = ?" + " OR descricao = ? ";
//
//		try {
//			con = ConnectionDB.getConnection();
//			ps = con.prepareStatement(sql);
//			ps.setString(1, FacesUtils.removerAcentos(c.getDescricao()));
//			ps.setString(2, c.getDescricao());
//			ResultSet rs = ps.executeQuery();
//			if (rs.next()) {
//				FacesContext.getCurrentInstance().addMessage(null,
//						new FacesMessage("Já existe uma cliente com essa descrição:", c.getDescricao()));
//				return false;
//			} else {
//				return true;
//			}
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} finally {
//			if (con != null) {
//				try {
//					con.close();
//				} catch (SQLException e) {
//					e.printStackTrace();
//				}
//			}
//
//		}
//		return false;
//	}
//
//	public boolean editar(Cliente c) throws SQLException {
//		if (validarInserir(c)) {
//			String sql = "UPDATE cliente " + " SET descricao = ? " + " WHERE cod_cliente = ? ";
//			con = ConnectionDB.getConnection();
//			ps = con.prepareStatement(sql);
//			ps.setString(1, c.getDescricao());
//			ps.setInt(2, c.getCodigo());
//			if (ps.executeUpdate() > 0) {
//				return true;
//			}
//		}
//		return false;
//
//	}
//
//	public boolean excluir(Cliente c) throws SQLException {
//		String sql = "DELETE FROM cliente WHERE cod_cliente = ?";
//		con = ConnectionDB.getConnection();
//		ps = con.prepareStatement(sql);
//		ps.setInt(1, c.getCodigo());
//		if (ps.executeUpdate() > 0) {
//			return true;
//		}
//		return false;
//
//	}
//
}
