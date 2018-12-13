package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Categoria;

public class CategoriaDAO implements Serializable{

	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public List<Categoria> listar() throws SQLException {
		String sql = " SELECT * FROM categoria ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Categoria> lista = new ArrayList<Categoria>();
		while (rs.next()) {
			Categoria c = new Categoria(rs.getInt("cod_categoria"), rs.getString("descricao"));
			lista.add(c);
		}
		return lista;
	}
	
	

	public boolean inserir(Categoria c) throws SQLException {
		if (validarInserir(c)) {
			String sql = "INSERT INTO categoria VALUES (0,?)";

			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, c.getDescricao());
			if (ps.executeUpdate() > 0) {
				return true;
			}

		}
		return false;
	}

	public boolean validarInserir(Categoria c) {
		String sql = "SELECT * FROM categoria " + " WHERE descricao = ?" + " OR descricao = ? ";

		try {
			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, FacesUtils.removerAcentos(c.getDescricao()));
			ps.setString(2, c.getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Já existe uma categoria com essa descrição:", c.getDescricao()));
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

	public boolean editar(Categoria c) throws SQLException {
		if (validarInserir(c)) {
			String sql = "UPDATE categoria " + " SET descricao = ? " + " WHERE cod_categoria = ? ";
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

	public boolean excluir(Categoria c) throws SQLException {
		String sql = "DELETE FROM categoria WHERE cod_categoria = ?";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, c.getCodigo());
		if (ps.executeUpdate() > 0) {
			return true;
		}
		return false;

	}

}
