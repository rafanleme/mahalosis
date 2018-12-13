package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mahalosis.vo.Usuario;

public class UsuarioDAO extends BaseDAO implements Serializable{
	
	public UsuarioDAO() throws SQLException {
		super();
	}

	
	public Usuario login(Usuario usuario) throws SQLException{
		String sql = " SELECT * FROM usuario "
				+ " WHERE cpf = ? "
				+ " AND senha = ? ";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, usuario.getCpf());
		ps.setString(2, usuario.getSenha());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			usuario.setPerfil(rs.getString("perfil"));
			con.close();
			return usuario;
		}
		con.close();
		return null;
		
	}
	
}
