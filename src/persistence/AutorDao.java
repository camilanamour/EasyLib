package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Autor;

public class AutorDao implements IAutorDao{
	
	private Connection con;

	public AutorDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	@Override
	public void inserir(Autor autor) throws SQLException {
		String sql = "INSERT INTO tb_autor VALUES(?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, autor.getNome());
		ps.setInt(2, autor.getNascimento());
		ps.setString(3, autor.getPais());
		ps.setString(4, autor.getBiografia());

		ps.execute();
		ps.close();
	}

	//Com a tabela view não é necessário
	@Override
	public Autor pesquisar(Autor autor) throws SQLException {
		String sql = "SELECT id_autor, nome, ano, pais, biografia FROM tb_autor WHERE id_autor = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, autor.getCodigo());

		ResultSet rs = ps.executeQuery();
		int cout = 0;

		if (rs.next()) {
			autor.setCodigo(rs.getInt("id_autor"));
			autor.setNome(rs.getString("nome"));
			autor.setNascimento(rs.getInt("ano"));
			autor.setPais(rs.getString("pais"));
			autor.setBiografia(rs.getString("biografia"));
			cout++;
		}

		if (cout == 0) {
			autor = new Autor();
		}

		rs.close();
		ps.close();
		return autor;
	}

	@Override
	public void alterar(Autor autor) throws SQLException {
		String sql = "UPDATE tb_autor SET nome = ?, ano = ?, pais = ?, biografia = ? WHERE id_autor = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, autor.getNome());
		ps.setInt(2, autor.getNascimento());
		ps.setString(3, autor.getPais());
		ps.setString(4, autor.getBiografia());
		ps.setInt(5, autor.getCodigo());

		ps.execute();
		ps.close();
		
	}

	@Override
	public void excluir(Autor autor) throws SQLException {
		
		String sql = "DELETE tb_autor WHERE id_autor = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, autor.getCodigo());

		ps.execute();
		ps.close();
		
	}

	@Override
	public List<Autor> listar() throws SQLException {
		String sql = "SELECT id_autor, nome, ano, pais, biografia FROM tb_autor ORDER BY nome ASC";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor aut = new Autor();
			aut.setCodigo(rs.getInt("id_autor"));
			aut.setNome(rs.getString("nome"));
			aut.setNascimento(rs.getInt("ano"));
			aut.setPais(rs.getString("pais"));
			aut.setBiografia(rs.getString("biografia"));
			autores.add(aut);
		}

		rs.close();
		ps.close();
		return autores;
	}

	@Override
	public List<Autor> listarPorColuna(String filtro, String coluna) throws SQLException {
		String sql = "";
		if (coluna.equalsIgnoreCase("nascimento")) {
			sql = "SELECT id_autor, nome, ano, pais, biografia FROM tb_autor "
					+ "WHERE ano LIKE ? ORDER BY ano ASC";
		} else if (coluna.equalsIgnoreCase("selecione") && filtro.equals("")) {
			sql = "SELECT id_autor, nome, ano, pais, biografia FROM tb_autor "
					+ "WHERE nome LIKE ? ORDER BY nome ASC";
		} else {
			sql = "SELECT id_autor, nome, ano, pais, biografia FROM tb_autor " + "WHERE " + coluna
					+ " LIKE ? ORDER BY " + coluna + " ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");

		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor a = new Autor();
			a.setCodigo(rs.getInt("id_autor"));
			a.setNome(rs.getString("nome"));
			a.setNascimento(rs.getInt("ano"));
			a.setPais(rs.getString("pais"));
			a.setBiografia(rs.getString("biografia"));
			autores.add(a);
		}

		rs.close();
		ps.close();
		return autores;
	}
	
	
}
