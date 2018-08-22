package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import mahalosis.vo.Usuario;

public class UsuarioDAO implements Serializable{
	
	private Connection conn;
	private PreparedStatement ps;
	
	public Usuario login(Usuario usuario) throws SQLException{
		String sql = " SELECT * FROM usuario "
				+ " WHERE cpf = ? "
				+ " AND senha = ? ";
		
		conn = ConnectionDB.getConnection();
		ps = conn.prepareStatement(sql);
		ps.setString(1, usuario.getCpf());
		ps.setString(2, usuario.getSenha());
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			usuario.setPerfil(rs.getString("perfil"));
			return usuario;
		}
		return null;
		
	}
	
}
