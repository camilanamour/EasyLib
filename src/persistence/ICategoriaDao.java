package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Categoria;

public interface ICategoriaDao {
	
	public void inserir(Categoria c) throws SQLException, ClassNotFoundException;
	public Categoria pesquisar(Categoria c) throws SQLException;
	public void excluir(Categoria c) throws SQLException, ClassNotFoundException;
	public List<Categoria> listar() throws SQLException, ClassNotFoundException;

}
