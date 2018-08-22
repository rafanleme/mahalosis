package mahalosis.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.swing.plaf.synth.SynthSeparatorUI;

public class ConnectionDB {
	private static Connection con;
	private static String dns = "jdbc:mysql://localhost:3306/mahalo?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";
	private static String user = "root";
	private static String pass = "";
	
	public static Connection getConnection(){
		
		try {
			DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
			con = DriverManager.getConnection(dns,user,pass);
			System.out.println("DB conectado com sucesso!");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.out.println("Erro ao conectar no DB");
			e.printStackTrace();
		}
	
		return con;
	}
}