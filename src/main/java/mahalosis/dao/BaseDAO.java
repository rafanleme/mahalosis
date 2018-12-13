package mahalosis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class BaseDAO {

	Connection con;
	PreparedStatement ps;
	
	public BaseDAO() throws SQLException {
		con = ConnectionDB.getConnection();
	}
	
}
