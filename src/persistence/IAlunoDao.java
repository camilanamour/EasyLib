package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Aluno;

public interface IAlunoDao {

	public void inserir(Aluno aluno) throws SQLException;
	public Aluno pesquisar(Aluno aluno) throws SQLException;
	public void alterar(Aluno aluno) throws SQLException;
	public void excluir(Aluno aluno) throws SQLException;
	public List<Aluno> listar() throws SQLException;
	public List<Aluno> listarPorColuna(String filtro, String coluna, String periodo) throws SQLException;
}
