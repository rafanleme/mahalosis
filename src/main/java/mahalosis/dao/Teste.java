package mahalosis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import mahalosis.vo.Telefone;

public class Teste {

	public static void main(String[] args) {

		Telefone t = new Telefone(1, "", "19 99820-8013", "cel", true);
		t.explodeCodArea();
		
		System.out.println(t.getCodArea() + "  ---  " + t.getNumero());
		
//		try {
//			
//			String sql = " SELECT c.*, ci.*, ci.nome as nome_cidade, e.cod_estabelecimento, e.descricao  FROM cliente c "
//					+ " INNER JOIN cidade ci ON (c.cod_cidade = ci.cod_cidade) "
//					+ " LEFT JOIN estabelecimento e ON (c.cod_estabelec = e.cod_estabelecimento) ";
//					
//			System.out.println(sql);
//					
//			Connection con = ConnectionDB.getConnection();
//			PreparedStatement ps = con.prepareStatement(sql);
//			
//			ResultSet rs = ps.executeQuery();
//			
//			while(rs.next()){
//				System.out.println(rs.getString("nome"));
//				System.out.println(rs.getString("nome_cidade"));
//			}
//			
//		} catch (SQLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		
	}
}
