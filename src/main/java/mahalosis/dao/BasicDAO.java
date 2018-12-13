package mahalosis.dao;

import java.sql.SQLException;
import java.util.List;

public interface BasicDAO<T> {
	
	public List<T> listar() throws SQLException;
	public boolean editar(T t) throws SQLException;
	public boolean inserir(T t) throws SQLException;
	public boolean deletar(int codigo) throws SQLException;
	public T buscarPorCodigo(int codigo) throws SQLException;
	

}
