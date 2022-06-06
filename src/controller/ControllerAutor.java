package controller;

import java.sql.SQLException;

import entity.Autor;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.AutorDao;
import persistence.IAutorDao;

public class ControllerAutor {

	public IntegerProperty cod = new SimpleIntegerProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	public StringProperty pais = new SimpleStringProperty("");
	public IntegerProperty nascimento = new SimpleIntegerProperty(0);
	public StringProperty biografia = new SimpleStringProperty("");

	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();
	
	public ObjectProperty<Autor> at = new SimpleObjectProperty<Autor>();

	private IAutorDao aDao;

	private ObservableList<Autor> autores = FXCollections.observableArrayList();
	private ObservableList<Autor> autoresFiltrados = FXCollections.observableArrayList();

	public ControllerAutor() {
		try {
			aDao = new AutorDao();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException {
		Autor a = toEntity();
		aDao.inserir(a);
		listar();
		limpar();
	}

	public void pesquisar(Autor aut) throws SQLException {
		aDao.pesquisar(aut);
	}

	public void alterar() throws SQLException {
		Autor a = toEntity();
		aDao.alterar(a);
		listar();
	}

	public void deletar(Autor a) throws SQLException {
		a = toEntity();
		aDao.excluir(a);
		listar();
		limpar();
	}

	public void listar() throws SQLException {
		autores.clear();
		autores.addAll(aDao.listar());
	}

	public void limpar() throws SQLException {
		Autor a = new Autor();
		this.fromEntity(a);
		listar();
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public void listarPorColuna() throws SQLException {
		autoresFiltrados.clear();
		autoresFiltrados.addAll(aDao.listarPorColuna(filtro.get(), coluna.get()));
		filtro.setValue("");
		coluna.setValue("Selecione");
	}

	public ObservableList<Autor> getLista() throws SQLException {
		listar();
		return autores;
	}

	public ObservableList<Autor> getListaPorColuna() throws SQLException {
		return autoresFiltrados;
	}

	public Autor toEntity() {
		Autor a = new Autor();
		a.setCodigo(cod.get());
		a.setNome(nome.get());
		a.setNascimento(nascimento.get());;
		a.setPais(pais.get());
		a.setBiografia(biografia.get());
		return a;
	}

	public void fromEntity(Autor a) {
		if (a != null) {
			cod.set(a.getCodigo());
			nome.set(a.getNome());
			nascimento.set(a.getNascimento());
			pais.set(a.getPais());
			biografia.set(a.getBiografia());
		}
	}

}
