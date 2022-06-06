package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import entity.Usuario;

public class UsuarioDao implements IUsuarioDao{
	
	private Connection con;
	
	public UsuarioDao() throws ClassNotFoundException, SQLException {
		GenericDao gDao = new GenericDao();
		con = gDao.getConnection();
	}
	
	@Override
	public void inserir(Usuario u) throws SQLException {
		
		String sql = "INSERT INTO tb_usuario VALUES(?,?,?)";

		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getUsuario());
		ps.setString(2, u.getSenha());
		ps.setString(3, u.getEmail());

		ps.execute();
		ps.close();
		
	}

	@Override
	public boolean pesquisar(Usuario u) throws SQLException {
		
		String sql = "SELECT * FROM tb_usuario WHERE nome = ? AND senha = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, u.getUsuario());
		ps.setString(2, u.getSenha());
				
		ResultSet rs = ps.executeQuery();
		boolean cout = false;

		if (rs.next()) {
			u.setId(rs.getInt("id_usuario"));
			u.setUsuario(rs.getString("nome"));
			u.setSenha(rs.getString("senha"));
			u.setEmail(rs.getString("email"));
			cout = true;
		}

		if (cout == true) {
			return true;
		}

		rs.close();
		ps.close();
		return false;
	}

	
	
}
