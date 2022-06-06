package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Edicao;
import entity.Editora;
import entity.Emprestimo;
import entity.Livro;
import entity.Volume;

public class VolumeDao implements IVolumeDao {

	private Connection con;
	private GenericDao gDao;
	private IEmprestimoDao eDao;

	public VolumeDao() throws ClassNotFoundException, SQLException {
		gDao = new GenericDao();
		eDao = new EmprestimoDao();
		con = gDao.getConnection();
	}

	@Override
	public void inserir(Volume v, int qtdEstoque) throws SQLException {
		String sql = "INSERT INTO tb_volume VALUES (?, ?, ?, ?, ?)";

		PreparedStatement ps = con.prepareStatement(sql);
		for (int i = 1; i <= qtdEstoque; i++) {
			ps.setInt(1, v.getEdicao().getLivro().getId());
			ps.setString(2, v.getEdicao().getIsbn());
			ps.setInt(3, v.getEdicao().getEditora().getId());
			ps.setInt(4, i);
			ps.setString(5, "disponível");
			ps.execute();
		}
		ps.close();

	}
	@Override
	public Volume pesquisar(Volume v) throws SQLException, ClassNotFoundException{
		
		String sql = "SELECT *" + " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v"
				+ " WHERE e.isbn = v.edicao " + " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro "
				+ " AND e.isbn = ? " + " AND v.numero = ? ";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, v.getEdicao().getIsbn());
		ps.setInt(2, v.getNumero());

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
			
			v.setEdicao(e);
			v.setNumero(rs.getInt("numero"));
			v.setStatus(rs.getString("status"));
		}

		rs.close();
		ps.close();
		return v;
		
	}
	
	@Override
	public void alterar(Volume v) throws SQLException, ClassNotFoundException {
		String sql = "UPDATE tb_volume SET status = ? " + "WHERE livro = ? AND edicao = ? AND editora = ? AND numero = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, v.getStatus());
		ps.setInt(2, v.getEdicao().getLivro().getId());
		ps.setString(3, v.getEdicao().getIsbn());
		ps.setInt(4, v.getEdicao().getEditora().getId());
		ps.setInt(5, v.getNumero());

		ps.execute();
		ps.close();
	}
	
	@Override
	public void excluir(Volume v) throws SQLException, ClassNotFoundException {
		String sql = "DELETE tb_volume WHERE livro = ?, edicao = ?, editora = ?, numero = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, v.getEdicao().getLivro().getId());
		ps.setString(2, v.getEdicao().getIsbn());
		ps.setInt(3, v.getEdicao().getEditora().getId());
		ps.setInt(4, v.getNumero());

		ps.execute();
		ps.close();
	}
	
	@Override
	public void excluirTodos(Volume v) throws SQLException, ClassNotFoundException {
		
		Emprestimo e = new Emprestimo();
		e.setVolume(v);
		eDao.excluirTodos(e);
		
		String sql = "DELETE tb_volume WHERE livro = ? AND edicao = ? AND editora = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, v.getEdicao().getLivro().getId());
		ps.setString(2, v.getEdicao().getIsbn());
		ps.setInt(3, v.getEdicao().getEditora().getId());

		ps.execute();
		ps.close();
	}

	
	public List<Volume> listar() throws SQLException, ClassNotFoundException {

		String sql = "SELECT *"
				+ " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v" + " WHERE e.isbn = v.edicao "
				+ " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro ";

		PreparedStatement ps = con.prepareStatement(sql);

		List<Volume> volumes = new ArrayList<Volume>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			Edicao e = new Edicao();
			Editora d = new Editora();
			Livro l = new Livro();
			Volume v = new Volume();
			
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
			
			v.setEdicao(e);
			v.setNumero(rs.getInt("numero"));
			v.setStatus(rs.getString("status"));
			volumes.add(v);
		}

		rs.close();
		ps.close();
		return volumes;
	}

	

	@Override
	public List<Volume> listarPorColuna(String filtro, String coluna) throws SQLException, ClassNotFoundException {
		String sql = "";
		if (coluna.equalsIgnoreCase("livro")) {
			sql = "SELECT *"
					+ " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v" + " WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro " + " AND l.titulo = ? ";
		}
		if (coluna.equalsIgnoreCase("editora")) {
			sql = "SELECT *"
					+ " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v" + " WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro " + " AND d.nome = ? ";
		}
		if (coluna.equalsIgnoreCase("isbn")) {
			sql = "SELECT *"
					+ " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v" + " WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro " + " AND e.isbn = ?";
		}
		if (coluna.equalsIgnoreCase("situacao")) {
			sql = "SELECT *"
					+ " FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v" + " WHERE e.isbn = v.edicao "
					+ " AND d.id_editora = v.editora " + " AND l.id_livro = v.livro " + " AND v.status = ?";
		}
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");
		
		List<Volume> volumes = new ArrayList<Volume>();
		ResultSet rs = ps.executeQuery();

		while (rs.next()) {
			
			Edicao e = new Edicao();
			Editora d = new Editora();
			Livro l = new Livro();
			Volume v = new Volume();
			
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
			
			v.setEdicao(e);
			v.setNumero(rs.getInt("numero"));
			v.setStatus(rs.getString("status"));
			volumes.add(v);
		}

		rs.close();
		ps.close();
		return volumes;
	}
	
}
