package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Autor;

public interface IAutorDao {
	
	public void inserir(Autor autor) throws SQLException;
	public Autor pesquisar(Autor autor) throws SQLException;
	public void alterar(Autor autor) throws SQLException;
	public void excluir(Autor autor) throws SQLException;
	public List<Autor> listar() throws SQLException;
	public List<Autor> listarPorColuna(String filtro, String coluna) throws SQLException;
}
