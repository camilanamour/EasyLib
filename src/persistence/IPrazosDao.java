package persistence;

import java.sql.SQLException;

import entity.Regra;

public interface IPrazosDao {
	
	public void alterar(int emprestimo, int multa) throws SQLException;
	public Regra get() throws SQLException;
	
}
