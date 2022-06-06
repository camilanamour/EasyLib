package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Edicao;

public interface IEdicaoDao {

	public void inserir(Edicao e) throws SQLException, ClassNotFoundException;
	public Edicao pesquisar(String isbn) throws SQLException, ClassNotFoundException;
	public void excluir(Edicao e) throws SQLException, ClassNotFoundException;
	public List<Edicao> listar() throws SQLException, ClassNotFoundException;
	public List<Edicao> listarPorColunaEdicao(String filtro, String coluna) throws SQLException, ClassNotFoundException;
}
