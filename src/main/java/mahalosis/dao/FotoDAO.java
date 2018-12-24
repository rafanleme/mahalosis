package mahalosis.dao;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.List;

import javax.imageio.ImageIO;

import org.primefaces.model.UploadedFile;

import mahalosis.utils.FacesUtils;
import mahalosis.vo.Foto;

public class FotoDAO extends BaseDAO implements BasicDAO<Foto>, Serializable {

	private static final long serialVersionUID = 1L;

	public FotoDAO() throws SQLException {
		super();
	}

	@Override
	public List<Foto> listar() throws SQLException {

		return null;
	}

	@Override
	public boolean editar(Foto f) throws SQLException {

		return false;
	}

	@Override
	public boolean inserir(Foto f) throws SQLException {
		String sql = " INSERT INTO foto VALUES (?,?,?) ";
		ps = con.prepareStatement(sql);
		ps.setString(1, f.getNomeArquivo());
		ps.setString(2, f.getCodProduto());
		if(f.getPrincipal() != null)
			ps.setInt(3, 1);
		else
			ps.setInt(3, 0);
		
		return ps.executeUpdate() > 0;
	}
	
	public boolean temPrincipal(Foto f) throws SQLException{
		String sql = " SELECT * FROM foto WHERE cod_produto = ? AND principal = 1";
		ps = con.prepareStatement(sql);
		ps.setString(1, f.getCodProduto());
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return true;
		}
		return false;
	}

	public Foto salvarFoto(Integer codProduto, UploadedFile up) throws SQLException {
		Long time = Calendar.getInstance().getTimeInMillis();
		String ext = up.getFileName();
		ext = ext.substring(ext.lastIndexOf("."));
		String nomeArquivo = time.toString() + ext;
		
		try {
			BufferedImage image = ImageIO.read(up.getInputstream());
			int w = image.getWidth();
			int h = image.getHeight();
			int dif = w - h;
			if (dif < 0 || dif > 100) {
				FacesUtils.setMensagem("Imagem fora dos tamanhos padrões",
						"A imagem " + up.getFileName() + " não é quadrada");
				System.out.println("Mahalosis erro: Imagem "
						+ up.getFileName() + " fora não quadrada. Diferença de tamanho = " + dif
						+ ", diferença aceita <= 100");
				return null;
			}
			
			File file = new File(FacesUtils.diretorioFotosProdutos(codProduto), nomeArquivo);

			OutputStream out;
			out = new FileOutputStream(file);
			out.write(up.getContents());
			out.close();
			
			Foto f = new Foto();
			f.setCodProduto(codProduto.toString());
			f.setNomeArquivo(nomeArquivo);
			if(!temPrincipal(f)){
				f.setPrincipal(true);
			}
			
			if(inserir(f)){
				return f;
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String buscarPrincipal(Integer codProduto) throws SQLException{
		String sql = " SELECT * FROM foto "
				+ " WHERE cod_produto = ? "
				+ " AND principal = 1 ";
		ps = con.prepareStatement(sql);
		ps.setInt(1, codProduto);
		ResultSet rs = ps.executeQuery();
		if(rs.next()){
			return rs.getString("nome_arquivo");
		}
		return null;
	}

	@Override
	public boolean deletar(int codigo) throws SQLException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Foto buscarPorCodigo(int codigo) throws SQLException {
		// TODO Auto-generated method stub
		return null;
	}


}
