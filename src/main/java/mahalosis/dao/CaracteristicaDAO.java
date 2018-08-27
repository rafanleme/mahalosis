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
import mahalosis.vo.Caracterisica;

public class CaracteristicaDAO {

	private Connection con;
	private PreparedStatement ps;

	public List<Caracterisica> listar() throws SQLException {
		String sql = " SELECT * FROM caracteristica ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Caracterisica> lista = new ArrayList<Caracterisica>();
		while (rs.next()) {
			Caracterisica c = new Caracterisica(rs.getInt("codigo"), rs.getString("descricao"));
			lista.add(c);
		}
		return lista;
	}

	public boolean inserir(Caracterisica c) {
		if (validarInserir(c)) {
			String sql = "INSERT INTO caracteristica VALUES (0,?)";

			try {
				con = ConnectionDB.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, c.getDescricao());
				if (ps.executeUpdate() > 0) {
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
		}
		return false;
	}

	public boolean validarInserir(Caracterisica c) {
		String sql = "SELECT * FROM caracteristica " + " WHERE descricao = ?" + " OR descricao = ? ";
		
		try {
			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setString(1, FacesUtils.removerAcentos(c.getDescricao()));
			ps.setString(2, c.getDescricao());
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				FacesContext.getCurrentInstance().addMessage(null,
						new FacesMessage("Já existe uma característica com essa descrição:", c.getDescricao()));
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

	public boolean editar(Caracterisica c) {
		if(validarInserir(c)){
			String sql = "UPDATE caracteristica " + " SET descricao = ? " + " WHERE codigo = ? ";
			try {
				con = ConnectionDB.getConnection();
				ps = con.prepareStatement(sql);
				ps.setString(1, c.getDescricao());
				ps.setInt(2, c.getCodigo());
				if (ps.executeUpdate() > 0) {
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
		}
		return false;

	}

	public boolean excluir(Caracterisica c) {
		String sql = "DELETE FROM caracteristica " + " WHERE codigo = ?";
		try {
			con = ConnectionDB.getConnection();
			ps = con.prepareStatement(sql);
			ps.setInt(1, c.getCodigo());
			if (ps.executeUpdate() > 0) {
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

}
