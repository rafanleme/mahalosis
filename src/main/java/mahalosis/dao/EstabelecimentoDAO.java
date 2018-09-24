package mahalosis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Estabelecimento;

public class EstabelecimentoDAO {

	private Connection con;
	private PreparedStatement ps;

	public List<Estabelecimento> listar() throws SQLException {
		String sql = " SELECT * FROM estabelecimento ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Estabelecimento> lista = new ArrayList<Estabelecimento>();
		while (rs.next()) {
//			Estabelecimento c = new Estabelecimento(
//					rs.getInt("cod_estabelecimento"),
//					rs.getString("descricao"),
//					rs.getString("endereco"),
//					rs.getString("bairro"),
//					rs.getString(""),
//					rs.getString("descricao"),
//					rs.getString("descricao"),
//					rs.getString("descricao"),
//					rs.getString("descricao")
//					);
//			lista.add(c);
		}
		return lista;
	}
	
	public List<Estabelecimento> listarCombo() throws SQLException {
		String sql = " SELECT cod_estabelecimento, descricao FROM estabelecimento ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Estabelecimento> lista = new ArrayList<Estabelecimento>();
		while (rs.next()) {
			Estabelecimento e = new Estabelecimento();
			e.setCodigo(rs.getInt("cod_estabelecimento"));
			e.setDescricao(rs.getString("descricao"));
			lista.add(e);
		}
		return lista;
	}

	public boolean inserir(Estabelecimento c) throws SQLException {
		if (validarInserir(c)) {
			String sql = "INSERT INTO estabelecimento VALUES (0,?)";

			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, c.getDescricao());
			if (ps.executeUpdate() > 0) {
				return true;
			}

		}
		return false;
	}

	public boolean validarInserir(Estabelecimento c) {
		String sql = "SELECT * FROM estabelecimento " + " WHERE descricao = ?" + " OR descricao = ? ";

		try {
			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, FacesUtils.removerAcentos(c.getDescricao()));
			ps.setString(2, c.getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Já existe uma estabelecimento com essa descrição:", c.getDescricao()));
				return false;
			} else {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}

		}
		return false;
	}

	public boolean editar(Estabelecimento c) throws SQLException {
		if (validarInserir(c)) {
			String sql = "UPDATE estabelecimento " + " SET descricao = ? " + " WHERE cod_estabelecimento = ? ";
			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, c.getDescricao());
			ps.setInt(2, c.getCodigo());
			if (ps.executeUpdate() > 0) {
				return true;
			}
		}
		return false;

	}

	public boolean excluir(Estabelecimento c) throws SQLException {
		String sql = "DELETE FROM estabelecimento WHERE cod_estabelecimento = ?";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, c.getCodigo());
		if (ps.executeUpdate() > 0) {
			return true;
		}
		return false;

	}

}
