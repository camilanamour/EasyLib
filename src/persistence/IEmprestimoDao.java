package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Aluno;
import entity.Emprestimo;
import entity.Volume;

public interface IEmprestimoDao {
	
	public void inserir(Aluno aluno, Volume volume, Emprestimo emprestimo) throws SQLException;
	public Emprestimo pesquisar(Emprestimo emprestimo) throws SQLException;
	public void excluir(Emprestimo emprestimo) throws SQLException;
	public void excluirTodos(Emprestimo emprestimo) throws SQLException;
	public List<Emprestimo> listar() throws SQLException;
	public List<Emprestimo> listarPorColuna(String filtro, String coluna) throws SQLException;
	public List<Emprestimo> listarRelatorio(String periodo, String ordenacao, String filtro) throws SQLException;
}
