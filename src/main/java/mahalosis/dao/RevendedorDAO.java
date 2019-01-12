package mahalosis.dao;

import java.io.Serializable;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estado;
import mahalosis.vo.Revendedor;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Revendedor;
import mahalosis.vo.Telefone;
import mahalosis.vo.Usuario;

public class RevendedorDAO extends BaseDAO implements BasicDAO<Revendedor>, Serializable {

	private static final long serialVersionUID = 1L;

	public RevendedorDAO() throws SQLException {
		super();

	}

	@Override
	public List<Revendedor> listar() throws SQLException {
		String sql = "SELECT * FROM revendedor r " + " INNER JOIN cidade c ON r.cod_cidade = c.cod_cidade "
				+ " INNER JOIN socio sc ON r.usuario_cadastro = sc.cpf "
				+ " LEFT JOIN socio sa ON r.usuario_alteracao = sa.cpf "
				+ " INNER JOIN telefone tel ON r.cpf = tel.cod_revendedor " + " WHERE tel.principal = 1";
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Revendedor> revendedores = new ArrayList<>();
		while (rs.next()) {
			Revendedor r = new Revendedor();
			r.setCodigo(rs.getInt("cod_revendedor"));
			r.setNome(rs.getString("nome"));
			r.setDataNasc(rs.getDate("data_nasc"));
			r.setDataContrato(rs.getDate("data_contrato"));
			r.setCpf(rs.getString("cpf"));
			r.setRg(rs.getString("rg"));
			r.setReferencia(rs.getString("referencia"));
			r.setEndereco(rs.getString("endereco"));
			r.setNumeroEndereco(rs.getString("numero_endereco"));
			r.setBairro(rs.getString("bairro"));
			r.setCidade(new Cidade(rs.getInt("cod_cidade"), rs.getString("c.nome"),
					new Estado(null, rs.getString("c.cod_estado"))));
			r.setCep(rs.getString("cep"));
			r.setDataCadastro(rs.getDate("data_cadastro"));
			PessoaFisica pc = new PessoaFisica(null, rs.getString("sc.nome"), rs.getDate("sc.data_nasc"),
					rs.getString("usuario_cadastro"), null);
			r.setUsuarioCadastro(pc);
			r.setDataAlteracao(rs.getDate("data_alteracao"));
			PessoaFisica pa = new PessoaFisica(null, rs.getString("sa.nome"), rs.getDate("sa.data_nasc"),
					rs.getString("usuario_alteracao"), null);
			r.setUsuarioAlteracao(pa);
			// pega telefone principal
			List<Telefone> telefones = new ArrayList<>();
			Telefone telPrincipal = new Telefone(null, rs.getString("ddd"), rs.getString("numero"), rs.getString("tel.tipo"), true);
			telefones.add(telPrincipal);
			r.setTelefone(telefones);
			r.setAtivo(rs.getBoolean("ativo"));

			revendedores.add(r);
		}
		return revendedores;
	}

	@Override
	public boolean editar(Revendedor r) throws SQLException {
		String sql = " UPDATE revendedor SET nome = ? " + " data_nasc = ? " + " data_contrato = ? " + " cpf = ? "
				+ " rg = ? " + " referencia = ? " + " endereco = ? " + " numero_endereco = ? " + " bairro = ? "
				+ " cod_cidade = ? " + " cep = ? " + " data_alteracao = ? " + " usuario_alteracao = ? " + " ativo = ? ";
		ps = con.prepareStatement(sql);
		ps.setString(1, r.getNome());
		ps.setDate(2, new Date(r.getDataNasc().getTime()));
		ps.setDate(3, new Date(r.getDataContrato().getTime()));
		ps.setString(4, r.getCpf());
		ps.setString(5, r.getRg());
		ps.setString(6, r.getReferencia());
		ps.setString(7, r.getEndereco());
		ps.setString(8, r.getNumeroEndereco());
		ps.setString(9, r.getBairro());
		ps.setInt(10, r.getCidade().getCodigo());
		ps.setString(11, r.getCep());
		ps.setDate(12, new Date(r.getDataAlteracao().getTime()));
		ps.setString(13, r.getUsuarioAlteracao().getCpf());
		ps.setBoolean(14, r.getAtivo());

		return ps.executeUpdate() > 0;
	}

	@Override
	public boolean inserir(Revendedor r) throws SQLException {
		String sqlUsu = " INSERT INTO usuario VALUES (?,?,?) ";
		ps = con.prepareStatement(sqlUsu);
		ps.setString(1, r.getUsuario().getCpf());
		ps.setString(2, r.getUsuario().getSenha());
		ps.setString(3, r.getUsuario().getPerfil());
		if (ps.executeUpdate() > 0) {
			String sql = "INSERT INTO revendedor VALUES (0 ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,? ,null ,? ,null ,null ,? )";
			ps = con.prepareStatement(sql);
			ps.setString(1, r.getNome());
			ps.setDate(2, new Date(r.getDataNasc().getTime()));
			ps.setDate(3, new Date(r.getDataContrato().getTime()));
			ps.setString(4, r.getCpf());
			ps.setString(5, r.getRg());
			ps.setString(6, r.getReferencia());
			ps.setString(7, r.getEndereco());
			ps.setString(8, r.getNumeroEndereco());
			ps.setString(9, r.getBairro());
			ps.setInt(10, r.getCidade().getCodigo());
			ps.setString(11, r.getCep());
			ps.setString(12, r.getUsuarioCadastro().getCpf());
			ps.setBoolean(13, true);

			System.out.println(ps.toString());

			if (ps.executeUpdate() > 0) {
				Telefone t = r.getTelefone().get(0);
				if (t != null) {
					String sqlTel = " INSERT INTO telefone VALUES (0,?,?,?,1,null,null,?,null) ";
					ps = con.prepareStatement(sqlTel);
					ps.setString(1, t.getCodArea());
					ps.setString(2, t.getNumero());
					ps.setString(3, t.getTipo());
					ps.setString(4, r.getCpf());
					ps.executeUpdate();
					return true;
				} else {
					FacesUtils.setMensagem(FacesMessage.SEVERITY_ERROR, "Ops... Erro ao adicionar telefone",
							"Desculpe, tente novamente.");
				}
			}
		}
		return false;
	}

	@Override
	public Revendedor buscarPorCodigo(int codigo) throws SQLException {
		String sql = " SELECT * FROM revendedor WHERE cod_revendedor = ?";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codigo);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			Revendedor r = new Revendedor();
			r.setCodigo(rs.getInt("cod_revendedor"));
			r.setNome(rs.getString("nome"));
			r.setDataNasc(rs.getDate("data_nasc"));
			r.setDataContrato(rs.getDate("data_contrato"));
			r.setCpf(rs.getString("cpf"));
			r.setRg(rs.getString("rg"));
			r.setReferencia(rs.getString("referencia"));
			r.setEndereco(rs.getString("endereco"));
			r.setNumeroEndereco(rs.getString("numero_endereco"));
			r.setBairro(rs.getString("bairro"));
			r.setCidade(new Cidade(rs.getInt("cod_cidade"), rs.getString("c.nome"),
					new Estado(null, rs.getString("c.cod_estado"))));
			r.setCep(rs.getString("cep"));
			r.setDataCadastro(rs.getDate("data_cadastro"));
			PessoaFisica pc = new PessoaFisica(null, rs.getString("sc.nome"), rs.getDate("sc.data_nasc"),
					rs.getString("usuario_criacao"), null);
			r.setUsuarioCadastro(pc);
			r.setDataAlteracao(rs.getDate("data_alteracao"));
			PessoaFisica pa = new PessoaFisica(null, rs.getString("sa.nome"), rs.getDate("sa.data_nasc"),
					rs.getString("usuario_alteracao"), null);
			r.setUsuarioAlteracao(pa);
			// pega telefone principal
			List<Telefone> telefones = new ArrayList<>();
			Telefone telPrincipal = new Telefone(null, rs.getString("cod_area"), rs.getString("telefone"), null, true);
			telefones.add(telPrincipal);
			r.setTelefone(telefones);
			r.setAtivo(rs.getBoolean("ativo"));

			return r;
		}
		return null;
	}

	public Revendedor buscarPorCpf(String cpf) throws SQLException {
		String sql = " SELECT * FROM revendedor WHERE cod_revendedor = ?";
		ps = con.prepareStatement(sql);
		ps.setString(1, cpf);
		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			Revendedor r = new Revendedor();
			r.setCodigo(rs.getInt("cod_revendedor"));
			r.setNome(rs.getString("nome"));
			r.setDataNasc(rs.getDate("data_nasc"));
			r.setDataContrato(rs.getDate("data_contrato"));
			r.setCpf(rs.getString("cpf"));
			r.setRg(rs.getString("rg"));
			r.setReferencia(rs.getString("referencia"));
			r.setEndereco(rs.getString("endereco"));
			r.setNumeroEndereco(rs.getString("numero_endereco"));
			r.setBairro(rs.getString("bairro"));
			r.setCidade(new Cidade(rs.getInt("cod_cidade"), rs.getString("c.nome"),
					new Estado(null, rs.getString("c.cod_estado"))));
			r.setCep(rs.getString("cep"));
			r.setDataCadastro(rs.getDate("data_cadastro"));
			PessoaFisica pc = new PessoaFisica(null, rs.getString("sc.nome"), rs.getDate("sc.data_nasc"),
					rs.getString("usuario_criacao"), null);
			r.setUsuarioCadastro(pc);
			r.setDataAlteracao(rs.getDate("data_alteracao"));
			PessoaFisica pa = new PessoaFisica(null, rs.getString("sa.nome"), rs.getDate("sa.data_nasc"),
					rs.getString("usuario_alteracao"), null);
			r.setUsuarioAlteracao(pa);
			// pega telefone principal
			List<Telefone> telefones = new ArrayList<>();
			Telefone telPrincipal = new Telefone(null, rs.getString("cod_area"), rs.getString("telefone"), null, true);
			telefones.add(telPrincipal);
			r.setTelefone(telefones);
			r.setAtivo(rs.getBoolean("ativo"));

			return r;
		}
		return null;
	}

	@Override
	public boolean deletar(int codigo) throws SQLException {
		// revendedor só desativa, não deleta.
		return false;
	}

	public List<Revendedor> listarCombo() throws SQLException {
		String sql = " SELECT cod_revendedor, nome FROM revendedor " + " WHERE ativo = true ";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Revendedor> lista = new ArrayList<Revendedor>();
		while (rs.next()) {
			Revendedor r = new Revendedor();
			r.setCodigo(rs.getInt("cod_revendedor"));
			r.setNome(rs.getString("nome"));
			lista.add(r);
		}
		return lista;
	}
}
