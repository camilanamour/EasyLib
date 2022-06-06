package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Categoria;

public class CategoriaDao implements ICategoriaDao {

	private GenericDao gDao;

	public CategoriaDao() throws ClassNotFoundException, SQLException {
		gDao = new GenericDao();
	}

	@Override
	public void inserir(Categoria c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "INSERT INTO tb_categoria VALUES(?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, c.getNome());

		ps.execute();
		ps.close();
		con.close();

	}

	@Override
	public void excluir(Categoria c) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "DELETE tb_categoria WHERE id_categoria = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, c.getId());

		ps.execute();
		ps.close();
		con.close();

	}

	@Override
	public List<Categoria> listar() throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM tb_categoria ORDER BY nome ASC";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Categoria> categorias = new ArrayList<Categoria>();

		while (rs.next()) {
			Categoria cat = new Categoria();
			cat.setId(rs.getInt("id_categoria"));
			cat.setNome(rs.getString("nome"));
			categorias.add(cat);
		}

		rs.close();
		ps.close();
		con.close();
		return categorias;
	}

	@Override
	public Categoria pesquisar(Categoria c) throws SQLException {
		return c;
		
	}

}
