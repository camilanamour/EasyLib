package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Editora;

public interface IEditoraDao {

	public void inserir(Editora e) throws SQLException, ClassNotFoundException;
	public Editora pesquisar(Editora e) throws SQLException, ClassNotFoundException;
	public void alterar(Editora e) throws SQLException, ClassNotFoundException;
	public void excluir(Editora e) throws SQLException, ClassNotFoundException;
	public List<Editora> listar() throws SQLException, ClassNotFoundException;
	public List<Editora> listarPorColuna(String filtro) throws SQLException, ClassNotFoundException;
}
