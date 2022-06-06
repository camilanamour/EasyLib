package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Aluno;

public class AlunoDao implements IAlunoDao {

	private Connection con;

	public AlunoDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}

	@Override
	public void inserir(Aluno aluno) throws SQLException {

		String sql = "INSERT INTO tb_aluno VALUES(?,?,?,?,?,?,?,?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aluno.getRa());
		ps.setString(2, aluno.getNome());
		ps.setString(3, aluno.getCpf());
		ps.setString(4, aluno.getRg());
		ps.setString(5, aluno.getTurma());
		ps.setString(6, aluno.getPeriodo());
		ps.setString(7, aluno.getDataNascimento());
		ps.setString(8, aluno.getTelefone());
		ps.setString(9, aluno.getCelular());
		ps.setString(10, aluno.getStatus());

		ps.execute();
		ps.close();
	}

	@Override
	public Aluno pesquisar(Aluno aluno) throws SQLException {
		String sql = "SELECT * FROM tb_aluno WHERE ra = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aluno.getRa());

		ResultSet rs = ps.executeQuery();
		int cout = 0;

		if (rs.next()) {
			aluno.setRa(rs.getString("ra"));
			aluno.setNome(rs.getString("nome"));
			aluno.setCpf(rs.getString("cpf"));
			aluno.setRg(rs.getString("rg"));
			aluno.setTurma(rs.getString("turma"));
			aluno.setPeriodo(rs.getString("periodo"));
			aluno.setDataNascimento(rs.getString("data_nascimento"));
			aluno.setTelefone(rs.getString("telefone"));
			aluno.setCelular(rs.getString("celular"));
			aluno.setStatus(rs.getString("status"));
			cout++;
		}

		if (cout == 0) {
			aluno = new Aluno();
		}

		rs.close();
		ps.close();
		return aluno;
	}

	@Override
	public void alterar(Aluno aluno) throws SQLException {

		String sql = "UPDATE tb_aluno SET nome = ?, cpf = ?, rg = ?, "
				+ "turma = ?, periodo = ?, data_nascimento = ?, telefone = ?, celular = ?, status = ? WHERE ra = ?";

		PreparedStatement ps = con.prepareStatement(sql);

		ps.setString(1, aluno.getNome());
		ps.setString(2, aluno.getCpf());
		ps.setString(3, aluno.getRg());
		ps.setString(4, aluno.getTurma());
		ps.setString(5, aluno.getPeriodo());
		ps.setString(6, aluno.getDataNascimento());
		ps.setString(7, aluno.getTelefone());
		ps.setString(8, aluno.getCelular());
		ps.setString(9, aluno.getStatus());
		ps.setString(10, aluno.getRa());

		ps.execute();
		ps.close();
	}

	@Override
	public void excluir(Aluno aluno) throws SQLException {

		String sql = "DELETE tb_aluno WHERE ra = ?";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, aluno.getRa());

		ps.execute();
		ps.close();

	}

	@Override
	public List<Aluno> listar() throws SQLException {
		String sql = "SELECT * FROM tb_aluno ORDER BY nome ASC";

		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();

		List<Aluno> alunos = new ArrayList<Aluno>();

		while (rs.next()) {
			Aluno aluno = new Aluno();
			aluno.setRa(rs.getString("ra"));
			aluno.setNome(rs.getString("nome"));
			aluno.setCpf(rs.getString("cpf"));
			aluno.setRg(rs.getString("rg"));
			aluno.setTurma(rs.getString("turma"));
			aluno.setPeriodo(rs.getString("periodo"));
			aluno.setDataNascimento(rs.getString("data_nascimento"));
			aluno.setTelefone(rs.getString("telefone"));
			aluno.setCelular(rs.getString("celular"));
			aluno.setStatus(rs.getString("status"));
			alunos.add(aluno);
		}

		rs.close();
		ps.close();
		return alunos;
	}

	@Override
	public List<Aluno> listarPorColuna(String filtro, String coluna, String periodo) throws SQLException {
		
		String sql = "";
		if (coluna.equalsIgnoreCase("nome") && !periodo.equalsIgnoreCase("todos")) {
			sql = "SELECT * FROM tb_aluno "
					+ "WHERE nome LIKE ? AND periodo = ? ORDER BY nome ASC";
		} else if (coluna.equalsIgnoreCase("turma") && !periodo.equalsIgnoreCase("todos")) {
			sql = "SELECT * FROM tb_aluno "
					+ "WHERE turma LIKE ? AND periodo = ? ORDER BY turma ASC";
		} else if (coluna.equalsIgnoreCase("situacao") && !periodo.equalsIgnoreCase("todos")) {
			sql = "SELECT * FROM tb_aluno "
					+ "WHERE status LIKE ? AND periodo = ? ORDER BY status ASC";
		} else if (coluna.equalsIgnoreCase("nome") && periodo.equalsIgnoreCase("todos")) {
			sql = "SELECT * FROM tb_aluno "
						+ "WHERE nome LIKE ? ORDER BY nome ASC";
		} else if (coluna.equalsIgnoreCase("turma") && periodo.equalsIgnoreCase("todos")) {
			sql = "SELECT * FROM tb_aluno "
						+ "WHERE turma LIKE ? ORDER BY turma ASC";
		} else {
			sql = "SELECT * FROM tb_aluno "
						+ "WHERE status LIKE ? ORDER BY status ASC";
		}

		PreparedStatement ps = con.prepareStatement(sql);
		
		ps.setString(1, filtro + "%");
		if(!periodo.equalsIgnoreCase("todos")){
			ps.setString(2, periodo);
		}

		ResultSet rs = ps.executeQuery();

		List<Aluno> alunos = new ArrayList<Aluno>();
		
		while (rs.next()) {
			Aluno aluno = new Aluno();
			aluno.setRa(rs.getString("ra"));
			aluno.setNome(rs.getString("nome"));
			aluno.setCpf(rs.getString("cpf"));
			aluno.setRg(rs.getString("rg"));
			aluno.setTurma(rs.getString("turma"));
			aluno.setPeriodo(rs.getString("periodo"));
			aluno.setDataNascimento(rs.getString("data_nascimento"));
			aluno.setTelefone(rs.getString("telefone"));
			aluno.setCelular(rs.getString("celular"));
			aluno.setStatus(rs.getString("status"));
			alunos.add(aluno);
		}
		
		rs.close();
		ps.close();
		return alunos;
	}
	
}
