package mahalosis.dao;

import java.io.Serializable;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Cidade;
import mahalosis.vo.Cliente;
import mahalosis.vo.Estabelecimento;
import mahalosis.vo.Estado;
import mahalosis.vo.PessoaFisica;
import mahalosis.vo.Telefone;
import mahalosis.vo.Usuario;

public class ClienteDAO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Connection con;
	private PreparedStatement ps;

	public List<Cliente> listar() throws SQLException {

		String sql = " SELECT c.*, ci.*, ci.nome as nome_cidade, e.cod_estabelecimento,"
				+ " e.descricao as desc_estabelecimento, r.nome as nome_usuario_cr, s.nome as nome_usuario_cs,"
				+ " uc.perfil as perfil, tel.numero as telefone, tel.ddd as cod_area"
				+ " FROM cliente c LEFT JOIN cidade ci ON (c.cod_cidade = ci.cod_cidade)"
				+ " INNER JOIN usuario uc ON (c.cod_usuario_criacao = uc.cpf)"
				+ " LEFT JOIN telefone tel ON (c.cpf = tel.cod_cliente)"
				+ " LEFT JOIN revendedor r ON (c.cod_usuario_criacao = r.cpf)"
				+ " LEFT JOIN socio s ON (c.cod_usuario_criacao = s.cpf)"
				+ " LEFT JOIN estabelecimento e ON (c.cod_estabelec = e.cod_estabelecimento)"
				+ " WHERE tel.principal = 1";

		con = ConnectionDB.getConnection();
		ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		List<Cliente> lista = new ArrayList<Cliente>();
		while (rs.next()) {
			Estado e = new Estado();
			e.setUf(rs.getString("cod_estado"));
			Cidade ci = new Cidade(rs.getInt("cod_cidade"), rs.getString("nome_cidade"), e);
			Estabelecimento est = new Estabelecimento();
			est.setCodigo(rs.getInt("cod_estabelecimento"));
			est.setDescricao(rs.getString("desc_estabelecimento"));
			Usuario uc = new Usuario(rs.getString("cod_usuario_criacao"), null, rs.getString("perfil"));
			String nomeUsuC = rs.getString("nome_usuario_cs");
			if (nomeUsuC == null) {
				nomeUsuC = rs.getString("nome_usuario_cr");
			}
			PessoaFisica pc = new PessoaFisica(null, nomeUsuC, null, rs.getString("cpf"), uc);

			List<Telefone> telefones = new ArrayList<>();
			Telefone telPrincipal = new Telefone(null, rs.getString("cod_area"), rs.getString("telefone"), null, true);
			telefones.add(telPrincipal);

			Cliente c = new Cliente(rs.getInt("cod_cliente"), rs.getString("nome"), rs.getDate("data_nasc"),
					rs.getString("cpf"), null, // usuario, não necessário para
												// esta consulta
					rs.getString("endereco"), rs.getString("bairro"), ci, rs.getString("cep"), est,
					rs.getDate("data_cadastro"), pc, rs.getDate("data_alteracao"), null, rs.getString("observacoes"),
					telefones);
			lista.add(c);
		}
		return lista;
	}

	public void inserir(Cliente c) throws SQLException {
		con = ConnectionDB.getConnection();
		
		//insere usuario
		String sqlUsu = " INSERT INTO usuario VALUES (?,?,?) ";
		ps = con.prepareStatement(sqlUsu);
		ps.setString(1, c.getUsuario().getCpf());
		ps.setString(2, c.getUsuario().getSenha());
		ps.setString(3, c.getUsuario().getPerfil());
		if (ps.executeUpdate() > 0) {
			//se usuario criado, cria cliente
			String sql = " INSERT INTO cliente  (nome,data_nasc,cpf,cod_estabelec,cod_usuario_criacao) "
					+ "VALUES (?,?,?,?,?) ";
			ps = con.prepareStatement(sql);
			ps.setString(1, c.getNome());
			ps.setDate(2, new Date(c.getDataNasc().getTime()));
			ps.setString(3, c.getCpf());
			ps.setInt(4, c.getEstabelecimento().getCodigo());
			ps.setString(5, c.getUsuarioCriacao().getCpf());
			Telefone t = c.getTelefones().get(0);
			System.out.println(ps.toString());
			if (ps.executeUpdate() > 0) {
				//se cliente criado, adiciona telefones
				if (t != null) {
					String sqlTel = " INSERT INTO telefone VALUES (0,?,?,?,1,?,null,null) ";
					ps = con.prepareStatement(sqlTel);
					ps.setString(1, t.getCodArea());
					ps.setString(2, t.getNumero());
					ps.setString(3, t.getTipo());
					ps.setString(4, c.getCpf());
					System.out.println(ps.toString());
					ps.executeUpdate();
				} else {
					FacesUtils.setMensagem(FacesMessage.SEVERITY_ERROR, "Ops... Erro ao adicionar telefone",
							"Desculpe, tente novamente.");
				}

			}
		}

	}

	// public boolean inserir(Cliente c) throws SQLException {
	// if (validarInserir(c)) {
	// String sql = "INSERT INTO cliente VALUES (0,?)";
	//
	// con = ConnectionDB.getConnection();
	// ps = con.prepareStatement(sql);
	// ps.setString(1, c.getDescricao());
	// if (ps.executeUpdate() > 0) {
	// return true;
	// }
	//
	// }
	// return false;
	// }
	//
	// public boolean validarInserir(Cliente c) {
	// String sql = "SELECT * FROM cliente " + " WHERE descricao = ?" + " OR
	// descricao = ? ";
	//
	// try {
	// con = ConnectionDB.getConnection();
	// ps = con.prepareStatement(sql);
	// ps.setString(1, FacesUtils.removerAcentos(c.getDescricao()));
	// ps.setString(2, c.getDescricao());
	// ResultSet rs = ps.executeQuery();
	// if (rs.next()) {
	// FacesContext.getCurrentInstance().addMessage(null,
	// new FacesMessage("Já existe uma cliente com essa descrição:",
	// c.getDescricao()));
	// return false;
	// } else {
	// return true;
	// }
	// } catch (SQLException e) {
	// // TODO Auto-generated catch block
	// e.printStackTrace();
	// } finally {
	// if (con != null) {
	// try {
	// con.close();
	// } catch (SQLException e) {
	// e.printStackTrace();
	// }
	// }
	//
	// }
	// return false;
	// }
	//
	// public boolean editar(Cliente c) throws SQLException {
	// if (validarInserir(c)) {
	// String sql = "UPDATE cliente " + " SET descricao = ? " + " WHERE
	// cod_cliente = ? ";
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
	//
	// public boolean excluir(Cliente c) throws SQLException {
	// String sql = "DELETE FROM cliente WHERE cod_cliente = ?";
	// con = ConnectionDB.getConnection();
	// ps = con.prepareStatement(sql);
	// ps.setInt(1, c.getCodigo());
	// if (ps.executeUpdate() > 0) {
	// return true;
	// }
	// return false;
	//
	// }
	//
}
