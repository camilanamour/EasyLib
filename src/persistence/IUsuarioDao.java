package persistence;

import java.sql.SQLException;

import entity.Usuario;

public interface IUsuarioDao {
	
	public void inserir(Usuario u) throws SQLException;
	public boolean pesquisar(Usuario u) throws SQLException;
	
}
