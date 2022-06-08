package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Aluno;
import entity.Edicao;
import entity.Editora;
import entity.Emprestimo;
import entity.Livro;
import entity.Volume;

public class EmprestimoDao implements IEmprestimoDao {

	private Connection con;

	public EmprestimoDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	@Override
	public void inserir(Aluno aluno, Volume volume, Emprestimo emprestimo) throws SQLException {
		String sql = "INSERT INTO tb_emprestimo VALUES(?,?,?,?,?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, emprestimo.getDataEmprestimo());
		ps.setString(2, emprestimo.getDataDevolucao());
		ps.setInt(3, volume.getEdicao().getLivro().getId());
		ps.setString(4, volume.getEdicao().getIsbn());
		ps.setInt(5, volume.getEdicao().getEditora().getId());
		ps.setInt(6, volume.getNumero());
		ps.setString(7, aluno.getRa());
		ps.setString(8, "em dia");

		ps.execute();
		ps.close();

		sql = "UPDATE tb_volume SET status = 'emprestado' WHERE livro = ? AND editora = ? AND edicao = ? AND numero = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getVolume().getEdicao().getLivro().getId());
		ps.setInt(2, emprestimo.getVolume().getEdicao().getEditora().getId());
		ps.setString(3, emprestimo.getVolume().getEdicao().getIsbn());
		ps.setInt(4, emprestimo.getVolume().getNumero());
		ps.execute();
		ps.close();

	}

	@Override
	public Emprestimo pesquisar(Emprestimo emprestimo) throws SQLException {

		String sql = "SELECT e.id_emprestimo, e.data_emprestimo, e.data_devolucao, e.edicao AS edicao, e.status, a.ra AS aluno, d.id_editora AS editora, l.id_livro AS livro, ed.isbn, v.numero AS num FROM tb_emprestimo e, tb_editora d, tb_livro l, tb_volume v, tb_aluno a, tb_edicao ed "
				+ "WHERE ed.isbn = e.edicao " + "AND d.id_editora = e.editora " + "AND e.livro = l.id_livro "
				+ "AND e.numero = v.numero " + "AND e.aluno = a.ra " + "AND e.id_emprestimo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getId());

		ResultSet rs = ps.executeQuery();

		if (rs.next()) {
			Edicao edicao = new Edicao();
			Editora editora = new Editora();
			Livro livro = new Livro();
			Volume v = new Volume();
			Aluno a = new Aluno();
			emprestimo.setId(rs.getInt("id_emprestimo"));
			emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
			emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
			edicao.setIsbn(rs.getString("edicao"));
			editora.setId(rs.getInt("editora"));
			edicao.setEditora(editora);
			livro.setId(rs.getInt("livro"));
			edicao.setLivro(livro);
			v.setEdicao(edicao);
			v.setNumero(rs.getInt("num"));
			a.setRa(rs.getString("aluno"));
			emprestimo.setVolume(v);
			emprestimo.setAluno(a);
			emprestimo.setStatus(rs.getString("status"));
		}

		rs.close();
		ps.close();
		return emprestimo;
	}

	@Override
	public void excluir(Emprestimo emprestimo) throws SQLException {

		String sql = "DELETE tb_emprestimo WHERE id_emprestimo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getId());

		ps.execute();
		ps.close();

	}

	@Override
	public void excluirTodos(Emprestimo emprestimo) throws SQLException {

		String sql = "DELETE tb_emprestimo WHERE edicao = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, emprestimo.getVolume().getEdicao().getIsbn());

		ps.execute();
		ps.close();

	}

	@Override
	public List<Emprestimo> listar() throws SQLException {
		String sql = "SELECT * FROM tb_emprestimo e, tb_livro l, tb_aluno a WHERE "
				+ "e.livro = l.id_livro AND e.aluno = a.ra";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		while (rs.next()) {
			Emprestimo emprestimo = new Emprestimo();
			Edicao edicao = new Edicao();
			Editora editora = new Editora();
			Livro livro = new Livro();
			Volume v = new Volume();
			Aluno a = new Aluno();
			emprestimo.setId(rs.getInt("id_emprestimo"));
			emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
			emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
			edicao.setIsbn(rs.getString("edicao"));
			editora.setId(rs.getInt("editora"));
			edicao.setEditora(editora);
			livro.setId(rs.getInt("livro"));
			livro.setTitulo(rs.getString("titulo"));
			edicao.setLivro(livro);
			v.setEdicao(edicao);

			v.setNumero(rs.getInt("numero"));
			a.setRa(rs.getString("aluno"));
			a.setNome(rs.getString("nome"));
			a.setTurma(rs.getString("turma"));
			a.setPeriodo(rs.getString("periodo"));
			emprestimo.setVolume(v);
			emprestimo.setAluno(a);
			emprestimo.setStatus(rs.getString("status"));
			emprestimos.add(emprestimo);
		}

		rs.close();
		ps.close();
		return emprestimos;
	}

	@Override
	public List<Emprestimo> listarPorColuna(String filtro, String coluna) throws SQLException {
		String sql = "";

		if (coluna.equalsIgnoreCase("selecione") && filtro.equals("")) {
			sql = "SELECT * FROM tb_emprestimo e, tb_livro l, tb_aluno a WHERE "
					+ "e.livro = l.id_livro AND e.aluno = a.ra AND nome LIKE ?";
		} else {
			sql = "SELECT * FROM tb_emprestimo e, tb_livro l, tb_aluno a WHERE "
					+ "e.livro = l.id_livro AND e.aluno = a.ra AND " + coluna + " LIKE ? ORDER BY " + coluna + " ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");

		ResultSet rs = ps.executeQuery();

		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		while (rs.next()) {
			Emprestimo emprestimo = new Emprestimo();
			Edicao edicao = new Edicao();
			Editora editora = new Editora();
			Livro livro = new Livro();
			Volume v = new Volume();
			Aluno a = new Aluno();
			emprestimo.setId(rs.getInt("id_emprestimo"));
			emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
			emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
			edicao.setIsbn(rs.getString("edicao"));
			editora.setId(rs.getInt("editora"));
			edicao.setEditora(editora);
			livro.setId(rs.getInt("livro"));
			livro.setTitulo(rs.getString("titulo"));
			edicao.setLivro(livro);
			v.setEdicao(edicao);

			v.setNumero(rs.getInt("numero"));
			a.setRa(rs.getString("aluno"));
			a.setNome(rs.getString("nome"));
			a.setTurma(rs.getString("turma"));
			a.setPeriodo(rs.getString("periodo"));
			emprestimo.setVolume(v);
			emprestimo.setAluno(a);
			emprestimo.setStatus(rs.getString("status"));
		}

		rs.close();
		ps.close();
		return emprestimos;
	}

	@Override
	public List<Emprestimo> listarRelatorio(String periodo, String ordenacao, String filtro) throws SQLException {
		String sql = "";

		if (filtro.equals("")) {
			sql = "SELECT * FROM tb_emprestimo e, tb_livro l, tb_aluno a WHERE "
					+ "e.livro = l.id_livro AND e.aluno = a.ra AND periodo LIKE ? ORDER BY " + ordenacao + " ASC";
		} else {
			sql = "SELECT * FROM tb_emprestimo e, tb_livro l, tb_aluno a WHERE "
					+ "e.livro = l.id_livro AND e.aluno = a.ra AND periodo LIKE ? AND titulo LIKE %" + filtro
					+ "%ORDER BY " + ordenacao + " ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + periodo + "%");

		ResultSet rs = ps.executeQuery();

		List<Emprestimo> emprestimos = new ArrayList<Emprestimo>();

		while (rs.next()) {
			Emprestimo emprestimo = new Emprestimo();
			Edicao edicao = new Edicao();
			Editora editora = new Editora();
			Livro livro = new Livro();
			Volume v = new Volume();
			Aluno a = new Aluno();
			emprestimo.setId(rs.getInt("id_emprestimo"));
			emprestimo.setDataEmprestimo(rs.getString("data_emprestimo"));
			emprestimo.setDataDevolucao(rs.getString("data_devolucao"));
			edicao.setIsbn(rs.getString("edicao"));
			editora.setId(rs.getInt("editora"));
			edicao.setEditora(editora);
			livro.setId(rs.getInt("livro"));
			livro.setTitulo(rs.getString("titulo"));
			edicao.setLivro(livro);
			v.setEdicao(edicao);

			v.setNumero(rs.getInt("numero"));
			a.setRa(rs.getString("aluno"));
			a.setNome(rs.getString("nome"));
			a.setTurma(rs.getString("turma"));
			a.setPeriodo(rs.getString("periodo"));
			emprestimo.setVolume(v);
			emprestimo.setAluno(a);
			emprestimo.setStatus(rs.getString("status"));
		}

		rs.close();
		ps.close();
		return emprestimos;
	}

	@Override
	public void devolvido(Emprestimo emprestimo) throws SQLException {
		String sql = "UPDATE tb_emprestimo SET status = 'devolvido' WHERE id_emprestimo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getId());
		ps.execute();
		ps.close();

		sql = "UPDATE tb_volume SET status = 'disponivel' WHERE livro = ? AND editora = ? AND edicao = ? AND numero = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getVolume().getEdicao().getLivro().getId());
		ps.setInt(2, emprestimo.getVolume().getEdicao().getEditora().getId());
		ps.setString(3, emprestimo.getVolume().getEdicao().getIsbn());

		ps.execute();
		ps.close();

	}

	@Override
	public void renovar(Emprestimo emprestimo) throws SQLException {
		String sql = "UPDATE tb_emprestimo SET status = 'renovado', data_devolucao = DATEADD(DAY, 7, data_devolucao)  WHERE id_emprestimo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getId());
		ps.execute();
		ps.close();
	}

	@Override
	public void cancelar(Emprestimo emprestimo) throws SQLException {
		String sql = "UPDATE tb_emprestimo SET status = 'cancelado' WHERE id_emprestimo = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getId());
		ps.execute();
		ps.close();

		sql = "UPDATE tb_aluno SET status = 'bloqueado' WHERE ra = ?";

		ps = con.prepareStatement(sql);
		ps.setString(1, emprestimo.getAluno().getRa());
		ps.execute();
		ps.close();

		sql = "UPDATE tb_volume SET status = 'perdido' WHERE livro = ? AND editora = ? AND edicao = ? AND AND numero = ?";

		ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo.getVolume().getEdicao().getLivro().getId());
		ps.setInt(2, emprestimo.getVolume().getEdicao().getEditora().getId());
		ps.setString(3, emprestimo.getVolume().getEdicao().getIsbn());
		ps.execute();
		ps.close();
	}

	@Override
	public void atrasados() throws SQLException {
		String sql = "UPDATE tb_emprestimo SET status = 'atrasado' WHERE data_devolucao = GETDATE() + 1";
		PreparedStatement ps = con.prepareStatement(sql);
		ps.execute();
		ps.close();
	}

}
