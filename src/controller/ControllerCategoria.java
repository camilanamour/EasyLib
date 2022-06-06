package controller;

import java.sql.SQLException;

import entity.Categoria;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.CategoriaDao;
import persistence.ICategoriaDao;

public class ControllerCategoria {
	public IntegerProperty cod = new SimpleIntegerProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	
	public ObjectProperty<Categoria> cat = new SimpleObjectProperty<Categoria>();

	private ICategoriaDao cDao;

	private ObservableList<Categoria> categorias = FXCollections.observableArrayList();
	
	public ControllerCategoria() {
		try {
			cDao = new CategoriaDao();
		} catch(ClassNotFoundException | SQLException e){
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException, ClassNotFoundException {
		Categoria c = toEntity();
		cDao.inserir(c);
		listar();
		limpar();
	}
	
	public void pesquisar() throws SQLException {
		Categoria c = new Categoria();
		c = cat.get();
		c = cDao.pesquisar(c);
		this.fromEntity(c);
	}

	public void deletar(Categoria c) throws SQLException, ClassNotFoundException {
		c = toEntity();
		cDao.excluir(c);
		listar();
		limpar();
	}

	public void listar() throws SQLException, ClassNotFoundException {
		categorias.clear();
		categorias.addAll(cDao.listar());
	}

	public void limpar() throws SQLException, ClassNotFoundException {
		Categoria c = new Categoria();
		this.fromEntity(c);
		listar();
	}

	public ObservableList<Categoria> getLista() throws SQLException, ClassNotFoundException {
		listar();
		return categorias;
	}

	public Categoria toEntity() {
		Categoria c = new Categoria();
		c.setId(cod.get());
		c.setNome(nome.get());
		return c;
	}

	public void fromEntity(Categoria c) {
		if (c != null) {
			cod.set(c.getId());
			nome.set(c.getNome());
		}
	}

}
