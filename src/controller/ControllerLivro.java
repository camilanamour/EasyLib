package controller;

import java.sql.SQLException;

import entity.Autor;
import entity.Categoria;
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
import persistence.ILivroDao;
import persistence.LivroDao;

public class ControllerLivro {

	public IntegerProperty cod = new SimpleIntegerProperty(0);
	public StringProperty nome = new SimpleStringProperty("");
	public IntegerProperty ano = new SimpleIntegerProperty(0);
	public IntegerProperty classificacao = new SimpleIntegerProperty(0);
	public ObjectProperty<Categoria> cat = new SimpleObjectProperty<Categoria>();
	public ObjectProperty<Autor> at = new SimpleObjectProperty<Autor>();

	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();

	private ILivroDao lDao;

	private ObservableList<Autor> listaAutores = FXCollections.observableArrayList();
	private ObservableList<Livro> listaLivros = FXCollections.observableArrayList();
	private ObservableList<Livro> listaLivrosBusca = FXCollections.observableArrayList();


	public ControllerLivro() {
		try {
			lDao = new LivroDao();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException {
		Livro livro = toEntity();
		lDao.inserir(livro);
		livro.setId(lDao.getId(livro));
		for (Autor a : listaAutores) {
			lDao.inserirAssociativa(a.getCodigo(), livro.getId());
		}
		listar();
	}

	public void alterar() throws SQLException {
		Livro l = toEntity();
		lDao.alterar(l);
		listar();
	}

	public void deletar() throws SQLException {
		Livro l = toEntity();
		lDao.excluir(l);
		listar();
		limpar();
	}

	public void limpar() throws SQLException {
		Livro l = new Livro();
		this.fromEntity(l);
		listaAutores.removeAll(listaAutores);
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public void listar() throws SQLException {
		listaLivros.clear();
		listaLivros.addAll(lDao.listar());
	}

	public void listarBusca() throws SQLException {
		listaLivrosBusca.clear();
		listaLivrosBusca.addAll(lDao.listarPorColuna(filtro.get(), coluna.get()));
	}

	public void listaAutorAtualizar(int codigo) throws SQLException {
		listaAutores.clear();
		listaAutores.addAll(lDao.listarAutores(codigo));
	}

	public void listarPorColuna() throws SQLException {
		listaLivrosBusca.clear();
		listaLivrosBusca.addAll(lDao.listarPorColuna(filtro.get(), coluna.get()));
	}

	public void adicionarAutor() throws SQLException {
		Autor aut = new Autor();
		aut = at.get();
		listaAutores.add(aut);
	}

	public void removerAutor(Autor autor) {
		listaAutores.remove(autor);
	}

	public ObjectProperty<String> ColunaProperty() {
		return coluna;
	}

	public ObservableList<Autor> getListaAutores() {
		return listaAutores;
	}

	public ObservableList<Livro> getLista() throws SQLException {
		listar();
		return listaLivros;
	}

	public ObservableList<Livro> getListaBusca() {
		return listaLivrosBusca;
	}

	public Livro toEntity() {
		Livro l = new Livro();
		l.setId(cod.get());
		l.setTitulo(nome.get());
		l.setAno(ano.get());
		l.setClassificacao(classificacao.get());
		l.setCategoria(cat.get());
		l.setAutores(listaAutores);
		return l;
	}

	public void fromEntity(Livro l) throws SQLException {
		if (l != null) {
			cod.set(l.getId());
			nome.set(l.getTitulo());
			ano.set(l.getAno());
			classificacao.set(l.getClassificacao());
			cat.set(l.getCategoria());
			listaAutores.addAll(lDao.listarAutores(l.getId()));
		}
	}

}
