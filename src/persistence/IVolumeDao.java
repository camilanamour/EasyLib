package persistence;

import java.sql.SQLException;
import java.util.List;

import entity.Volume;

public interface IVolumeDao {
	
	public void inserir(Volume v, int qtdEstoque) throws SQLException, ClassNotFoundException;
	public Volume pesquisar(Volume v) throws SQLException, ClassNotFoundException;
	public void alterar(Volume v) throws SQLException, ClassNotFoundException;
	public void excluir(Volume v) throws SQLException, ClassNotFoundException;
	public void excluirTodos(Volume v) throws SQLException, ClassNotFoundException;
	public List<Volume> listar() throws SQLException, ClassNotFoundException;
	public List<Volume> listarPorColuna(String filtro, String coluna) throws SQLException, ClassNotFoundException;

}
