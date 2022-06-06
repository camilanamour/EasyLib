package controller;

import java.sql.SQLException;

import entity.Edicao;
import entity.Editora;
import entity.Livro;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.EdicaoDao;
import persistence.IEdicaoDao;

public class ControllerEdicao {
	
	public StringProperty pesquisaEdicao = new SimpleStringProperty("");
	
	public StringProperty cod = new SimpleStringProperty("");
	public ObjectProperty<Livro> livro = new SimpleObjectProperty<Livro>();
	public IntegerProperty numEd = new SimpleIntegerProperty(0);
	public ObjectProperty<Editora> editora = new SimpleObjectProperty<Editora>();
	public IntegerProperty ano = new SimpleIntegerProperty(0);
	public IntegerProperty qtdPagina = new SimpleIntegerProperty(0);
	public ObjectProperty<String> formato = new SimpleObjectProperty<String>("Selecione");
	public IntegerProperty qtdVolumes = new SimpleIntegerProperty(0);
	
	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();

	private IEdicaoDao eDao;

	private ObservableList<Edicao> edicao = FXCollections.observableArrayList();
	private ObservableList<Edicao> edicaoFiltrados = FXCollections.observableArrayList();
	
	public ControllerEdicao() {
		try {
			eDao = new EdicaoDao();
		} catch(ClassNotFoundException | SQLException e){
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException, ClassNotFoundException {
		Edicao e = toEntity();
		eDao.inserir(e);
		listar();
		limpar();
	}
	
	public void pesquisar() throws SQLException, ClassNotFoundException {
		Edicao a1 = new Edicao();
		a1.setIsbn(pesquisaEdicao.get());
		Edicao a = eDao.pesquisar(a1.getIsbn());
		this.fromEntity(a);
		pesquisaEdicao.setValue("");
	}


	public void deletar(Edicao e) throws SQLException, ClassNotFoundException {
		e = toEntity();
		eDao.excluir(e);
		listar();
		limpar();
	}

	public void listar() throws SQLException, ClassNotFoundException {
		edicao.clear();
		edicao.addAll(eDao.listar());
	}

	public void limpar() throws SQLException, ClassNotFoundException {
		Edicao e = new Edicao();
		this.fromEntity(e);
		listar();
		pesquisaEdicao.setValue("");
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public void listarPorColuna() throws SQLException, ClassNotFoundException {
		edicaoFiltrados.clear();
		edicaoFiltrados.addAll(eDao.listarPorColunaEdicao(filtro.get(), coluna.get()));
		pesquisaEdicao.setValue("");
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public ObservableList<Edicao> getLista() throws SQLException, ClassNotFoundException {
		listar();
		return edicao;
	}

	public ObservableList<Edicao> getListaPorColuna() throws SQLException {
		return edicaoFiltrados;
	}

	public Edicao toEntity() {
		Edicao e = new Edicao();
		if(e.getIsbn() != ""){
			e.setIsbn(cod.get());
		}
		e.setLivro(livro.get());
		e.setAnoEdicao(ano.get());
		e.setEditora(editora.get());
		e.setFormato(formato.get());
		e.setNumEdicao(numEd.get());
		e.setQtdEstoque(qtdVolumes.get());
		e.setQtdPaginas(qtdPagina.get());
		return e;
	}

	public void fromEntity(Edicao e) {
		if (e != null) {
			if(e.getIsbn() != ""){
				cod.set(e.getIsbn());
    		}
			livro.set(e.getLivro());
			formato.set(e.getFormato());
			ano.set(e.getAnoEdicao());
			editora.set(e.getEditora());
			numEd.set(e.getNumEdicao());
			qtdVolumes.set(e.getQtdEstoque());
			qtdPagina.set(e.getQtdPaginas());
		}
	}


}
