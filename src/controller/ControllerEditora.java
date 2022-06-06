package controller;

import java.sql.SQLException;

import entity.Editora;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.EditoraDao;
import persistence.IEditoraDao;

public class ControllerEditora {
	
	public IntegerProperty cod = new SimpleIntegerProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	public StringProperty site = new SimpleStringProperty("");

	public StringProperty buscaEditora = new SimpleStringProperty();

	private IEditoraDao eDao;

	private ObservableList<Editora> editoras = FXCollections.observableArrayList();
	private ObservableList<Editora> editorasFiltrados = FXCollections.observableArrayList();
	
	public ControllerEditora() {
		try {
			eDao = new EditoraDao();
		} catch(ClassNotFoundException | SQLException e){
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException, ClassNotFoundException {
		Editora e = toEntity();
		eDao.inserir(e);
		listar();
		limpar();
	}


	public void alterar() throws SQLException, ClassNotFoundException {
		Editora e = toEntity();
		eDao.alterar(e);
		listar();
	}

	public void deletar(Editora e) throws SQLException, ClassNotFoundException {
		e = toEntity();
		eDao.excluir(e);
		listar();
		limpar();
	}

	public void listar() throws SQLException, ClassNotFoundException {
		editoras.clear();
		editoras.addAll(eDao.listar());
	}

	public void limpar() throws SQLException, ClassNotFoundException {
		Editora e = new Editora();
		this.fromEntity(e);
		listar();
		buscaEditora.setValue("");
	}

	public void listarPorColuna() throws SQLException, ClassNotFoundException {
		editorasFiltrados.clear();
		editorasFiltrados.addAll(eDao.listarPorColuna(buscaEditora.get()));
		buscaEditora.setValue("");
	}

	public ObservableList<Editora> getLista() throws SQLException, ClassNotFoundException {
		listar();
		return editoras;
	}

	public ObservableList<Editora> getListaPorColuna() throws SQLException {
		return editorasFiltrados;
	}

	public Editora toEntity() {
		Editora e = new Editora();
		e.setId(cod.get());
		e.setNome(nome.get());
		e.setSite(site.get());;
		return e;
	}

	public void fromEntity(Editora e) {
		if (e != null) {
			cod.set(e.getId());
			nome.set(e.getNome());
			site.set(e.getSite());
		}
	}

}
