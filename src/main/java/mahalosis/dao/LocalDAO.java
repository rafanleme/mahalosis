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
import mahalosis.vo.Cidade;
import mahalosis.vo.Estado;

public class LocalDAO implements Serializable {
	
	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public List<Estado> listarEstados() throws SQLException {
		String sql = " SELECT * FROM estado ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Estado> lista = new ArrayList<Estado>();
		while (rs.next()) {
			Estado e = new Estado(
					rs.getString("nome"),
					rs.getString("uf"));
			
			lista.add(e);
		}
		return lista;
	}
	
	public List<Cidade> listarCidadesPorEstado(Estado e) throws SQLException{
		String sql = " SELECT * FROM cidade c "
				+ " INNER JOIN estado e ON (c.cod_estado = e.uf) "
				+ "WHERE c.cod_estado = ? ";
		
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setString(1, e.getUf());
		System.out.println(ps.toString());
		ResultSet rs = ps.executeQuery();
		List<Cidade> lista = new ArrayList<>();
		while(rs.next()){
			Cidade c = new Cidade(
					rs.getInt("cod_cidade"), 
					rs.getString("nome"), 
					e);
			
			lista.add(c);
		}
		return lista;
		
	}
	
	
}
