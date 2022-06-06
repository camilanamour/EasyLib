package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Regra;

public class PrazosDao implements IPrazosDao{
	
	private Connection con;

	public PrazosDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	@Override
	public void alterar(int emprestimo, int multa) throws SQLException {
			
		String sql = "UPDATE tb_regras_emprestimos SET dias = ? WHERE id_regras = 1"
				+ "UPDATE tb_regras_emprestimos SET dias = ? WHERE id_regras = 2";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setInt(1, emprestimo);
		ps.setInt(2, multa);

		ps.execute();
		ps.close();
		
	}
	
	@Override
	public Regra get() throws SQLException {
		String sql = "SELECT * FROM tb_regras_emprestimos";
		
		PreparedStatement ps = con.prepareStatement(sql);

		ResultSet rs = ps.executeQuery();
		Regra r = new Regra();
		int cout = 0;

		while (rs.next()) {
			if(cout == 0){
				r.setDevolver(rs.getInt("dias"));
			}
			if(cout == 1){
				r.setBloquear(rs.getInt("dias"));
			}
			cout++;
		}

		ps.execute();
		ps.close();
		return r;
		
	}

}
