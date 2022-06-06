package persistence;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import entity.Editora;

public class EditoraDao implements IEditoraDao{
	
	GenericDao gDao;
	
	public EditoraDao() throws ClassNotFoundException, SQLException{
		gDao = new GenericDao();
	}
	
	@Override
	public void inserir(Editora e) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "INSERT INTO tb_editora VALUES(?,?)";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNome());
		ps.setString(2, e.getSite());

		ps.execute();
		ps.close();
		con.close();
		
	}

	@Override
	public Editora pesquisar(Editora e) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM tb_editora WHERE nome LIKE ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNome());
		
		ResultSet rs = ps.executeQuery();
		int cout = 0;
		
		if (rs.next()){
			e.setId(rs.getInt("id_editora"));
			e.setNome(rs.getString("nome"));
			e.setSite(rs.getString("site"));

			cout++;
		}
		
		if(cout == 0){
			e = new Editora();
		}
		
		rs.close();		
		ps.close();
		con.close();
		return e;
	}

	@Override
	public void alterar(Editora e) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "UPDATE tb_editora SET nome = ?, site = ? WHERE id_editora = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNome());
		ps.setString(2, e.getSite());
		ps.setInt(3, e.getId());
		
		ps.execute();
		ps.close();
		con.close();
		
	}

	@Override
	public void excluir(Editora e) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "DELETE tb_editora WHERE nome = ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, e.getNome());
		
		ps.execute();
		ps.close();
		con.close();
		
	}

	@Override
	public List<Editora> listar() throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM tb_editora";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ResultSet rs = ps.executeQuery();
		
		List<Editora> editoras = new ArrayList<Editora>();
		
		while (rs.next()){
			Editora e = new Editora();
			e.setId(rs.getInt("id_editora"));
			e.setNome(rs.getString("nome"));
			e.setSite(rs.getString("site"));
			editoras.add(e);
		}
		
		rs.close();		
		ps.close();
		con.close();
		return editoras;
	}

	@Override
	public List<Editora> listarPorColuna(String filtro) throws SQLException, ClassNotFoundException {
		Connection con = gDao.getConnection();
		String sql = "SELECT * FROM tb_editora WHERE nome LIKE ?";
		
		PreparedStatement ps = con.prepareStatement(sql);
		ps.setString(1, "%" + filtro + "%");
		
		ResultSet rs = ps.executeQuery();
		
		List<Editora> editoras = new ArrayList<Editora>();
		
		while (rs.next()){
			Editora e = new Editora();
			e.setId(rs.getInt("id_editora"));
			e.setNome(rs.getString("nome"));
			e.setSite(rs.getString("site"));
			editoras.add(e);
		}
		
		rs.close();		
		ps.close();
		con.close();
		return editoras;
	}
	
}
