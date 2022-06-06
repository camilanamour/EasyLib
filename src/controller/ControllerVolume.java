package controller;

import java.sql.SQLException;

import entity.Edicao;
import entity.Editora;
import entity.Livro;
import entity.Volume;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.IVolumeDao;
import persistence.VolumeDao;

public class ControllerVolume {
	
	public StringProperty pesquisaEdicao = new SimpleStringProperty("");
	
	public StringProperty isbn = new SimpleStringProperty("");
	public ObjectProperty<Livro> livro = new SimpleObjectProperty<Livro>();
	public ObjectProperty<Editora> editora = new SimpleObjectProperty<Editora>();
	public IntegerProperty num = new SimpleIntegerProperty(0);
	public ObjectProperty<String> status = new SimpleObjectProperty<String>("Selecione");
	
	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();

	private IVolumeDao eDao;

	private ObservableList<Volume> volumes = FXCollections.observableArrayList();
	private ObservableList<Volume> volumesFiltrados = FXCollections.observableArrayList();
	
	public ControllerVolume() {
		try {
			eDao = new VolumeDao();
		} catch(ClassNotFoundException | SQLException e){
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}
	
	public void atualizar() throws SQLException, ClassNotFoundException {
		Volume v = toEntity();
		eDao.alterar(v);
		listar();
		limpar();
	}

	public void listar() throws SQLException, ClassNotFoundException {
		volumes.clear();
		volumes.addAll(eDao.listar());
	}

	public void limpar() throws SQLException, ClassNotFoundException {
		Volume e = new Volume();
		this.fromEntity(e);
		status.setValue("Selecione");
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public void listarPorColuna() throws SQLException, ClassNotFoundException {
		volumesFiltrados.clear();
		if(filtro.get().equals("")){
			volumesFiltrados.addAll(eDao.listar());
		} else {
			volumesFiltrados.addAll(eDao.listarPorColuna(filtro.get(), coluna.get()));
		}
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public ObservableList<Volume> getLista() throws SQLException, ClassNotFoundException {
		listar();
		return volumes;
	}

	public ObservableList<Volume> getListaPorColuna() throws SQLException {
		return volumesFiltrados;
	}

	public Volume toEntity() {
		Volume e = new Volume();
		Edicao e1 = new Edicao();
		e1.setIsbn(isbn.get());
		e1.setLivro(livro.get());
		e1.setEditora(editora.get());
		e.setEdicao(e1);
		e.setNumero(num.get());
		e.setStatus(status.get());
		return e;
	}

	public void fromEntity(Volume e) {
		if (e != null) {
			isbn.set(e.getEdicao().getIsbn());
			livro.set(e.getEdicao().getLivro());
			editora.set(e.getEdicao().getEditora());
			num.set(e.getNumero());
			status.set(e.getStatus());
		}
	}

}
