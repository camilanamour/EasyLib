package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Edicao;
import entity.Editora;
import entity.Livro;
import entity.Volume;

public class EdicaoDao implements IEdicaoDao {

	private Connection con;
	private GenericDao gDao;
	private VolumeDao vDao;

	public EdicaoDao() throws ClassNotFoundException, SQLException {
		gDao = new GenericDao();
		vDao = new VolumeDao();
		con = gDao.getConnection();
	}

	@Override
	public void inserir(Edicao e) throws SQLException, ClassNotFoundException {

		String sql = "INSERT INTO tb_edicao VALUES (?, ?, ?, ?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getIsbn());
		ps.setInt(2, e.getNumEdicao());
		ps.setInt(3, e.getQtdEstoque());
		ps.setInt(4, e.getAnoEdicao());
		ps.setInt(5, e.getQtdPaginas());
		ps.setString(6, e.getFormato());

		ps.execute();
		ps.close();
		
		Volume v = new Volume();
		v.setEdicao(e);
		vDao.inserir(v, e.getQtdEstoque());

	}

	@Override
	public Edicao pesquisar(String isbn) throws SQLException, ClassNotFoundException {

		String sql = "SELECT *" + " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v"
				+ " WHERE e.isbn = v.edicao " + " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro "
				+ " AND e.isbn = ? " + " AND v.numero = 1 ";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, isbn);

		ResultSet rs = ps.executeQuery();
		Edicao e = new Edicao();

		if (rs.next()) {
			Editora d = new Editora();
			Livro l = new Livro();

			d.setId(rs.getInt("id_editora"));
			d.setNome(rs.getString("nome"));
			d.setSite(rs.getString("site"));
			e.setEditora(d);

			l.setId(rs.getInt("id_livro"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			e.setLivro(l);

			e.setIsbn(rs.getString("isbn"));
			e.setNumEdicao(rs.getInt("num_edicao"));
			e.setAnoEdicao(rs.getInt("ano_edicao"));
			e.setQtdPaginas(rs.getInt("qtd_paginas"));
			e.setQtdEstoque(rs.getInt("qtn_estoque"));
			e.setFormato(rs.getString("formato"));
		}

		rs.close();
		ps.close();
		return e;
	}

	@Override
	public void excluir(Edicao e) throws SQLException, ClassNotFoundException {

		Volume v = new Volume();
		v.setEdicao(e);
		vDao.excluirTodos(v);

		String sql = "DELETE tb_edicao WHERE isbn = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getIsbn());

		ps.execute();
		ps.close();
	}

	@Override
	public List<Edicao> listar() throws SQLException, ClassNotFoundException {

		String sql = "SELECT *" + " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v"
				+ " WHERE e.isbn = v.edicao " + " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro "
				+ " AND v.numero = 1";

		PreparedStatement ps = con.prepareStatement(sql);

		List<Edicao> edicoes = new ArrayList<Edicao>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Edicao e = new Edicao();
			Editora d = new Editora();
			Livro l = new Livro();

			d.setId(rs.getInt("id_editora"));
			d.setNome(rs.getString("nome"));
			d.setSite(rs.getString("site"));
			e.setEditora(d);

			l.setId(rs.getInt("id_livro"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			e.setLivro(l);

			e.setIsbn(rs.getString("isbn"));
			e.setNumEdicao(rs.getInt("num_edicao"));
			e.setAnoEdicao(rs.getInt("ano_edicao"));
			e.setQtdPaginas(rs.getInt("qtd_paginas"));
			e.setQtdEstoque(rs.getInt("qtn_estoque"));
			e.setFormato(rs.getString("formato"));
			edicoes.add(e);
		}

		rs.close();
		ps.close();
		return edicoes;
	}

	@Override
	public List<Edicao> listarPorColunaEdicao(String filtro, String coluna)
			throws SQLException, ClassNotFoundException {

		String sql = "";
		boolean ok = false;

		if (coluna.equalsIgnoreCase("ano")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND e.ano_edicao LIKE ? AND v.numero = 1";
		}
		if (coluna.equalsIgnoreCase("livro")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND l.titulo LIKE ? AND v.numero = 1";
		}
		if (coluna.equalsIgnoreCase("editora")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND d.nome LIKE ? AND v.numero = 1";
		}
		if (coluna.equalsIgnoreCase("paginas")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND qtd_paginas LIKE ? AND v.numero = 1";
		}
		if (coluna.equalsIgnoreCase("volumes")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND e.qtn_estoque LIKE ? AND v.numero = 1";
		}
		if (coluna.equalsIgnoreCase("formato")) {
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro "
					+ " AND e.formato LIKE ? AND v.numero = 1";
		}
		
		if((filtro == null || filtro == "") && coluna.equalsIgnoreCase("Selecione") ){
			sql = "SELECT * FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora AND l.id_livro = v.livro AND v.numero = 1";
			ok = true;
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		if(ok == false){
			ps.setString(1, "%" + filtro + "%");
		}

		List<Edicao> edicoes = new ArrayList<Edicao>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Edicao e = new Edicao();
			Editora d = new Editora();
			Livro l = new Livro();

			d.setId(rs.getInt("id_editora"));
			d.setNome(rs.getString("nome"));
			d.setSite(rs.getString("site"));
			e.setEditora(d);

			l.setId(rs.getInt("id_livro"));
			l.setTitulo(rs.getString("titulo"));
			l.setAno(rs.getInt("ano_publicacao"));
			l.setClassificacao(rs.getInt("classificacao"));
			e.setLivro(l);

			e.setIsbn(rs.getString("isbn"));
			e.setNumEdicao(rs.getInt("num_edicao"));
			e.setAnoEdicao(rs.getInt("ano_edicao"));
			e.setQtdPaginas(rs.getInt("qtd_paginas"));
			e.setQtdEstoque(rs.getInt("qtn_estoque"));
			e.setFormato(rs.getString("formato"));
			edicoes.add(e);
		}

		rs.close();
		ps.close();
		return edicoes;
	}

}
