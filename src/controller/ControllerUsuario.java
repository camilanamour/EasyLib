package controller;

import java.sql.SQLException;

import entity.Usuario;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import persistence.IUsuarioDao;
import persistence.UsuarioDao;

public class ControllerUsuario {
	
	public StringProperty username = new SimpleStringProperty("");
	public StringProperty email = new SimpleStringProperty("");
	public StringProperty senha = new SimpleStringProperty("");
	public StringProperty confirmaSenha = new SimpleStringProperty("");
	public StringProperty codA = new SimpleStringProperty("");

	private IUsuarioDao lDao;
	private String nomeFuncionario;

	public ControllerUsuario() throws ClassNotFoundException, SQLException {
		lDao = new UsuarioDao();
	}

	public void cadastrar() throws SQLException, IllegalAccessException {
		if (senha.get().equals(confirmaSenha.get()) && codA.get().equals("12345")) {
			Usuario usuario = toEntity();
			lDao.inserir(usuario);
		} else {
			throw new IllegalAccessException("ERRO NA CONFIRMAÇÃO: Senhas diferentes.");
		}

	}

	public boolean validar() throws SQLException, IllegalAccessException {
		Usuario u = new Usuario();
		u = toEntity();
		if (lDao.pesquisar(u)) {
			return true;
		} else {
			senha.set("");
			throw new IllegalAccessException("SEM ACESSO: Usuário ou senha inválidos.");
		}

	}

	public String getNomePrimeiro() {
		String[] vetor = this.nomeFuncionario.split(" ");
		return vetor[0];
	}

	public Usuario toEntity() {
		Usuario u = new Usuario();
		u.setUsuario(username.get());
		u.setSenha(senha.get());
		u.setEmail(email.get());
		return u;
	}

	public void fromEntity(Usuario u) {
		if(u != null){
			username.set(u.getUsuario());
			senha.set(u.getSenha());
			email.set(u.getEmail());
		}
	}

}
