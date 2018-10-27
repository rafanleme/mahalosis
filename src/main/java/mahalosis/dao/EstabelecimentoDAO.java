package mahalosis.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Usuario;

public class EstabelecimentoDAO {

	private Connection con;
	private PreparedStatement ps;
	
	@Inject
	ClienteDAO cDao;

	public List<Estabelecimento> listar() throws SQLException {
		String sql = " SELECT e.*, c.nome as nome_cidade, c.cod_estado as estado, cl.nome as nome_cliente_contato, "
				+ " cl.cod_cliente as cod_cliente_contato, uc.perfil as perfil, r.nome as nome_usuario_cr, "
				+ "	uc.cpf as cpf_usuario_criacao, " + " s.nome as nome_usuario_cs FROM estabelecimento e "
				+ " INNER JOIN cidade c ON (e.cod_cidade = c.cod_cidade) "
				+ " INNER JOIN usuario uc ON (e.cod_usuario_criacao = uc.cpf) "
				+ " LEFT JOIN cliente cl ON (e.cod_cliente_contato = cl.cpf) "
				+ " LEFT JOIN revendedor r ON (e.cod_usuario_criacao = r.cpf) "
				+ " LEFT JOIN socio s ON (e.cod_usuario_criacao = s.cpf) ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Estabelecimento> lista = new ArrayList<Estabelecimento>();
		while (rs.next()) {
			Cliente clienteContato = new Cliente();
			clienteContato.setNome(rs.getString("nome_cliente_contato"));
			Usuario uc = new Usuario(rs.getString("cod_usuario_criacao"), null, rs.getString("perfil"));
			String nomeUsuC = rs.getString("nome_usuario_cs");
			if (nomeUsuC == null) {
				nomeUsuC = rs.getString("nome_usuario_cr");
			}
			// Pessoa física do usuário de criação
			PessoaFisica pc = new PessoaFisica(null, nomeUsuC, null, rs.getString("cpf_usuario_criacao"), uc);
			Estabelecimento e = new Estabelecimento(rs.getInt("cod_estabelecimento"), rs.getString("descricao"),
					rs.getString("endereco"), rs.getString("numero_endereco"), rs.getString("bairro"),
					new Cidade(rs.getInt("cod_cidade"), rs.getString("nome_cidade"),
							new Estado(null, rs.getString("estado"))),
					rs.getString("cep"), clienteContato, pc, rs.getDate("data_criacao"));
			lista.add(e);
		}
		return lista;
	}

	public Integer qtdeClientesPorEstabelecimento(Integer codEstabelecimento) throws SQLException {
		String sql = " SELECT COUNT(cod_cliente) as qtde_clientes FROM cliente" + "	WHERE cod_estabelec = ? ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, codEstabelecimento);
		ResultSet rs = ps.executeQuery();
		if (rs.next()) {
			return rs.getInt(1);
		}
		return 0;
	}

	public List<Estabelecimento> listarCombo() throws SQLException {
		String sql = " SELECT cod_estabelecimento, descricao FROM estabelecimento ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Estabelecimento> lista = new ArrayList<Estabelecimento>();
		while (rs.next()) {
			Estabelecimento e = new Estabelecimento();
			e.setCodigo(rs.getInt("cod_estabelecimento"));
			e.setDescricao(rs.getString("descricao"));
			lista.add(e);
		}
		return lista;
	}

	public boolean inserir(Estabelecimento e) throws SQLException {
		String sql = "INSERT INTO estabelecimento (descricao, endereco, numero_endereco, bairro, "
				+ " cod_cidade, cep, cod_cliente_contato, cod_usuario_criacao ) " + " VALUES (?,?,?,?,?,?,?,?) ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		ps.setString(1, e.getDescricao());
		ps.setString(2, e.getEndereco());
		ps.setString(3, e.getNumeroEndereco());
		ps.setString(4, e.getBairro());
		ps.setInt(5, e.getCidade().getCodigo());
		ps.setString(6, e.getCep());
		String cpf = e.getClienteContato().getCpf();
		if (cpf == null || cpf.equals("")) {
			ps.setString(7, null);
		} else {
			ps.setString(7, e.getClienteContato().getCpf());
		}
		ps.setString(8, e.getUsuarioCriacao().getCpf());

		System.out.println(ps.toString());

		if (ps.executeUpdate() > 0) {
			if (cpf != null || !cpf.equals("")) {
				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					e.setCodigo(rs.getInt(1));
				}
				cDao = new ClienteDAO();
				cDao.atualizaEstabelecimento(cpf, e);
				return true;
			}
		}
		return false;
	}

	// public boolean editar(Estabelecimento c) throws SQLException {
	// if (validarInserir(c)) {
	// String sql = "UPDATE estabelecimento " + " SET descricao = ? " + " WHERE
	// cod_estabelecimento = ? ";
	// con = ConnectionDB.getConnection();
	// ps = con.prepareStatement(sql);
	// ps.setString(1, c.getDescricao());
	// ps.setInt(2, c.getCodigo());
	// if (ps.executeUpdate() > 0) {
	// return true;
	// }
	// }
	// return false;
	//
	// }

	public boolean excluir(Estabelecimento c) throws SQLException {
		String sql = "DELETE FROM estabelecimento WHERE cod_estabelecimento = ?";
		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ps.setInt(1, c.getCodigo());
		if (ps.executeUpdate() > 0) {
			return true;
		}
		return false;

	}

}
