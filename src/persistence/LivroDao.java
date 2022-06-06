package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Autor;
import entity.Categoria;
import entity.Livro;

public class LivroDao implements ILivroDao {

	private Connection con;

	public LivroDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	@Override
	public void inserir(Livro l) throws SQLException {

		String sql = "INSERT INTO tb_livro VALUES(?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setInt(2, l.getAno());
		ps.setInt(3, l.getClassificacao());
		ps.setInt(4, l.getCategoria().getId());

		ps.execute();
		ps.close();
	}

	public int getId(Livro l) throws SQLException {

		String sql = "SELECT id_livro FROM tb_livro AS l WHERE titulo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, l.getTitulo());

		ResultSet rs = ps.executeQuery();
		int cod = 0;
		if (rs.next()) {
			cod = rs.getInt("id_livro");
		}

		ps.execute();
		ps.close();
		return cod;
	}

	@Override
	public Livro pesquisar(Livro l) throws SQLException {
		String sql = "SELECT * FROM tb_livro AS l INNER JOIN tb_categoria AS c "
				+ "ON l.categoria = c.id_categoria WHERE id_livro = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, l.getId());

		ResultSet rs = ps.executeQuery();
		int cout = 0;

		if (rs.next()) {
			Categoria cat = new Categoria();
			l.setId(rs.getInt("id_livro"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			cat.setId(rs.getInt("id_categoria"));
			cat.setNome(rs.getString("nome"));
			l.setCategoria(cat);
			l.setAutores(this.listarAutores(l.getId()));
			cout++;
		}

		if (cout == 0) {
			l = new Livro();
		}

		rs.close();
		ps.close();
		return l;
	}

	@Override
	public void alterar(Livro l) throws SQLException {

		String sql = "UPDATE tb_livro SET titulo = ?, ano_publicacao = ?, classificacao = ?,"
				+ "categoria = ? WHERE id_livro = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, l.getTitulo());
		ps.setInt(2, l.getAno());
		ps.setInt(3, l.getClassificacao());
		ps.setInt(4, l.getCategoria().getId());
		ps.setInt(5, l.getId());

		ps.execute();
		ps.close();

		this.removerAssociativa(l.getId());

		int tamanho = l.getAutores().size();
		for (int i = 0; i < tamanho; i++) {
			this.inserirAssociativa(l.getAutores().get(i).getCodigo(), l.getId());
		}

	}

	@Override
	public void excluir(Livro l) throws SQLException {

		this.removerAssociativa(l.getId());
		String sql = "DELETE tb_livro WHERE id_livro = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, l.getId());

		ps.execute();
		ps.close();

	}

	@Override
	public List<Livro> listar() throws SQLException {
		String sql = "SELECT l.id_livro, l.titulo, l.ano_publicacao, l.classificacao, c.id_categoria, c.nome "
				+ "FROM tb_livro AS l, tb_categoria AS c WHERE l.categoria = c.id_categoria";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Livro> livros = new ArrayList<Livro>();

		while (rs.next()) {
			Livro l = new Livro();
			Categoria cat = new Categoria();
			l.setId(rs.getInt("id_livro"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			cat.setId(rs.getInt("id_categoria"));
			cat.setNome(rs.getString("nome"));
			l.setCategoria(cat);
			l.setAutores(this.listarAutores(l.getId()));
			livros.add(l);
		}

		rs.close();
		ps.close();
		return livros;
	}

	@Override
	public List<Livro> listarPorColuna(String filtro, String coluna) throws SQLException {

		String sql = "";
		
		if(coluna.equalsIgnoreCase("ano")){
			coluna = "ano_publicacao";	
		}
		if(coluna.equalsIgnoreCase("título")){
			coluna = "titulo";	
		}
		if(coluna.equalsIgnoreCase("classificação")){
			coluna = "classificacao";	
		}
		
		if(coluna.equalsIgnoreCase("categoria")){
			sql = "SELECT livro.id_livro AS id, livro.titulo AS titulo, livro.ano_publicacao, livro.classificacao AS classificacao, livro.categoria AS categoria " +
					"FROM tb_livro AS livro INNER JOIN tb_categoria categoria " +
					"ON livro.categoria = categoria.id_categoria " +
					"WHERE categoria.nome LIKE ? " +
					"ORDER BY categoria.nome ASC";
			
		}else if(coluna.equalsIgnoreCase("autor")){
			sql = "SELECT livro.id_livro AS id, livro.titulo AS titulo, livro.ano_publicacao, livro.classificacao AS classificacao, livro.categoria AS categoria " +
					"FROM tb_livro AS livro INNER JOIN livro_autor " +
					"ON livro.id_livro = livro_autor.id_livro " +
					"INNER JOIN tb_autor AS autor " +
					"ON autor.id_autor = livro_autor.id_autor " +
					"WHERE autor.nome LIKE ? "+
					"ORDER BY autor.nome ASC";
		}else{
			sql = "SELECT id_livro AS id, titulo, ano_publicacao, classificacao, categoria "
					+ "FROM tb_livro WHERE " + coluna + " LIKE ? ORDER BY " + coluna + " ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");

		ResultSet rs = ps.executeQuery();

		List<Livro> livros = new ArrayList<Livro>();

		while (rs.next()) {
			Livro l = new Livro();
			Categoria cat = new Categoria();
			l.setId(rs.getInt("id"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			cat.setId(rs.getInt("categoria"));
			cat.setNome(this.nomeCategoria(cat.getId()));
			l.setCategoria(cat);
			l.setAutores(this.listarAutores(l.getId()));
			livros.add(l);
		}

		rs.close();
		ps.close();
		return livros;
	}

	@Override
	public List<Autor> listarAutores(int i) throws SQLException {

		String sql = "SELECT a.id_autor AS id, a.nome, a.ano, a.pais, a.biografia FROM tb_autor AS a, livro_autor AS la " +
				"WHERE a.id_autor = la.id_autor AND la.id_livro = ? ";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, i);

		ResultSet rs = ps.executeQuery();

		List<Autor> autores = new ArrayList<Autor>();

		while (rs.next()) {
			Autor aut = new Autor();
			aut.setCodigo(rs.getInt("id"));
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
	public void inserirAssociativa(int autor, int livro) throws SQLException {

		String sql = "INSERT INTO livro_autor VALUES (?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, livro);
		ps.setInt(2, autor);

		ps.execute();
		ps.close();

	}

	@Override
	public void removerAssociativa(int livro) throws SQLException {

		String sql = "DELETE livro_autor WHERE id_livro = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, livro);

		ps.execute();
		ps.close();

	}
	
	private String nomeCategoria(int id) throws SQLException{
		String sql = "SELECT nome FROM tb_categoria WHERE id_categoria = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();

		String nome = "";
		while (rs.next()) {
			nome = rs.getString("nome");
		}

		rs.close();
		ps.close();
		return nome;
	}

}
