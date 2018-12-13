package mahalosis.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import mahalosis.vo.Produto;

public class ProdutoDAO extends BaseDAO implements BasicDAO<Produto>{
	
	public ProdutoDAO() throws SQLException {
		super();
		
	}

	@Override
	public List<Produto> listar() throws SQLException {
		String sql = "SELECT * FROM produto ";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Produto> produtos = new ArrayList<>();
		while(rs.next()){
			Produto p = new Produto();
			p.setCodigo(rs.getInt("cod_produto"));
			p.setDescricao(rs.getString("descricao"));
			p.setGenero(rs.getString("genero"));
			p.setTamanho(rs.getString("tamanho"));
			p.setValorCusto(rs.getBigDecimal("valor_custo"));
			p.setValorVenda(rs.getBigDecimal("valor_venda"));
			
			
			produtos.add(p);
		}
		return produtos;
	}

	
	@Override
	public boolean editar(Produto t) throws SQLException {
		String sql = " UPDATE INTO produto (tamanho, comprimento, espessura, genero, "
				+ " descricao, peso, valor_custo, valor_venda, desconto_porcentagem, desconto_valor, "
				+ " promocao, cod_fornecedor, cod_categoria) "
				+ " VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?) ";
		ps = con.prepareStatement(sql);
		ps.setString(1, t.getTamanho());
		ps.setDouble(2, t.getComprimento());
		ps.setDouble(3, t.getEspessura());
		ps.setString(4, t.getGenero());
		ps.setString(5, t.getDescricao());
		ps.setDouble(6, t.getPeso());
		ps.setBigDecimal(7, t.getValorCusto());
		ps.setBigDecimal(8, t.getValorVenda());
		ps.setBigDecimal(9, t.getDescontoPorcentagem());
		ps.setBigDecimal(10, t.getDescontoValor());
		ps.setBoolean(11, t.isPromocao());
		ps.setInt(12, t.getFornecedor().getCodigo());
		ps.setInt(13, t.getCategoria().getCodigo());
		
		return ps.executeUpdate() > 0;
	}

	@Override
	public boolean inserir(Produto t) throws SQLException {
		StringBuilder sql = new StringBuilder();
		sql.append("INSERT INTO produto VALUES (?,?,?,"); 
		sql.append("?,");
		sql.append("?,");
		sql.append("?,?,?,?,?,?,?,?,?,?,0) ");
		
		ps = con.prepareStatement(sql.toString());
		ps.setInt(1, t.getCodigo());
		ps.setString(2, t.getCodigoFornecedor());
		ps.setString(3, t.getTamanho());
		if(t.getComprimento() != null) 
			ps.setFloat(4, t.getComprimento());
		else
			ps.setFloat(4, 0);
		if(t.getEspessura() != null) 
			ps.setFloat(5, t.getEspessura());
		else
			ps.setFloat(5, 0);
		if(t.getPeso() != null)
			ps.setDouble(6, t.getPeso());
		else
			ps.setFloat(6, 0);
		ps.setString(7, t.getGenero());
		ps.setString(8, t.getDescricao());		
		ps.setBigDecimal(9, t.getValorCusto());
		ps.setBigDecimal(10, t.getValorVenda());
		ps.setBigDecimal(11, t.getDescontoPorcentagem());
		ps.setBigDecimal(12, t.getDescontoValor());
		ps.setBoolean(13, t.isPromocao());
		ps.setInt(14, t.getFornecedor().getCodigo());
		ps.setInt(15, t.getCategoria().getCodigo());
		
		return ps.executeUpdate() > 0;
	}

	

	@Override
	public Produto buscarPorCodigo(int codigo) throws SQLException {
		String sql = " SELECT * FROM produto WHERE cod_produto = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		ResultSet rs = ps.executeQuery();
		
		if(rs.next()){
			Produto p = new Produto();
			p.setCodigo(rs.getInt("cod_produto"));
			p.setDescricao(rs.getString("descricao"));
			p.setGenero(rs.getString("genero"));
			p.setTamanho(rs.getString("tamanho"));
			p.setValorCusto(rs.getBigDecimal("valor_custo"));
			p.setValorVenda(rs.getBigDecimal("valor_venda"));
			
			return p;
		}
		return null;
	}

	@Override
	public boolean deletar(int codigo) throws SQLException {
		String sql = " DELETE FROM produto WHERE cod_produto = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		
		return ps.executeUpdate() > 0;
	}
	
	public Integer getCodigoMax(Integer codCategoria) throws SQLException{
		String sql = " SELECT MAX(cod_produto) as codigo FROM produto "
				+ " WHERE cod_categoria = ? "
				+ " AND cod_produto";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, codCategoria);
		ResultSet rs = ps.executeQuery();
		String c;
		if(rs.next()){
			c = rs.getString("codigo");
			if(c != null)
				return Integer.parseInt(c);
			
		}
		return 0;
	}
	
}
