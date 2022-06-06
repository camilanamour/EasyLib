package boundary;

import java.sql.SQLException;

import controller.ControllerAutor;
import controller.ControllerCategoria;
import controller.ControllerLivro;
import entity.Autor;
import entity.Categoria;
import entity.Livro;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class TelaLivro {

	private Button btnInserir;
	private Button btnAlterar;
	private Button btnLimpar;
	private Button btnAdicionar;

	private TableView<Autor> tableAutor = new TableView<Autor>();
	private TableView<Livro> tableLivro = new TableView<Livro>();

	private ControllerAutor controlA;
	private ControllerCategoria controlC;
	private ControllerLivro control;

	public TelaLivro() {
		controlA = new ControllerAutor();
		controlC = new ControllerCategoria();
		control = new ControllerLivro();
		this.criarTabelaAutor();
	}

	public BorderPane render() {

		GridPane pan = new GridPane();
		BorderPane panAutores = new BorderPane();
		GridPane panBut = new GridPane();
		BorderPane panRegistro = new BorderPane();
		GridPane panFiltro = new GridPane();
		BorderPane panTabela = new BorderPane();
		BorderPane panPrimeiro = new BorderPane();
		BorderPane panTotal = new BorderPane();

		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));

		pan.add(new Label("Código:"), 0, 0);
		TextField txtCodigo = new TextField();
		txtCodigo.setDisable(true);
		pan.add(txtCodigo, 0, 1);
		pan.add(new Label("Classificação indicativa: "), 1, 0);
		TextField txtClass = new TextField();
		pan.add(txtClass, 1, 1);
		pan.add(new Label("Titulo: "), 0, 2);
		TextField txtTitulo = new TextField();
		txtTitulo.prefWidth(300);
		pan.add(txtTitulo, 0, 3);
		pan.add(new Label("Ano: "), 0, 4);
		TextField txtAno = new TextField();
		pan.add(txtAno, 0, 5);
		pan.add(new Label("Categoria: "), 1, 4);
		ComboBox<Categoria> comboBoxCat = new ComboBox<Categoria>();
		comboBoxCat.getItems().clear();
		try {
			comboBoxCat.setItems(controlC.getLista());
		} catch (SQLException | ClassNotFoundException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxCat.setPrefWidth(150);
		pan.add(comboBoxCat, 1, 5);
		pan.add(new Label("Autor: "), 0, 6);
		ComboBox<Autor> comboBoxAutor = new ComboBox<Autor>();
		comboBoxAutor.getItems().clear();
		try {
			comboBoxAutor.setItems(controlA.getLista());
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxAutor.setPrefWidth(150);
		pan.add(comboBoxAutor, 0, 7);

		btnAdicionar = new Button("Adicionar");
		btnAdicionar.setOnAction((e) -> {
			try {
				control.adicionarAutor();
				tableAutor.setItems(control.getListaAutores());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		pan.add(btnAdicionar, 1, 7);
		pan.setHgap(10);
		pan.setVgap(10);

		btnInserir = new Button("Cadastrar");
		btnInserir.setOnAction((e) -> {
			try {
				control.inserir();
				control.limpar();
				tableLivro.setItems(control.getLista());
				new Alert(Alert.AlertType.INFORMATION, "Livro inserido com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		btnAlterar = new Button("Atualizar");
		btnAlterar.setOnAction((e) -> {
			try {
				control.alterar();
				tableLivro.setItems(control.getLista());
				new Alert(Alert.AlertType.INFORMATION, "Livro alterado com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnLimpar = new Button("Limpar Campos");
		btnLimpar.setOnAction((e) -> {
			try {
				control.limpar();
				tableLivro.setItems(control.getLista());
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);

		panBut.add(new Label(""), 0, 0);
		panBut.add(btnInserir, 0, 1);
		panBut.add(btnAlterar, 1, 1);
		panBut.add(btnLimpar, 2, 1);
		panBut.add(new Label(""), 0, 2);
		panBut.setHgap(15);

		panAutores.setTop(new Label("Lista de autores do livro:"));
		this.criarTabelaAutor();
		panAutores.setCenter(tableAutor);

		panRegistro.setTop(pan);
		panRegistro.setCenter(panBut);

		panPrimeiro.setLeft(panRegistro);
		panPrimeiro.setRight(panAutores);

		panFiltro.add(new Label(""), 0, 0);
		panFiltro.add(new Label("Listar:"), 0, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panFiltro.add(txtFiltro, 1, 1);

		panFiltro.add(new Label("por"), 2, 1);
		ComboBox<String> comboBoxSelecao = new ComboBox<String>();
		comboBoxSelecao.getItems().clear();
		comboBoxSelecao.getItems().addAll("título", "classificação", "ano", "categoria", "autor");
		comboBoxSelecao.setPrefWidth(200);
		panFiltro.add(comboBoxSelecao, 3, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableLivro.setItems(control.getListaBusca());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		panFiltro.add(btnListar, 4, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		this.criarTabelaLivros();
		panTabela.setCenter(tableLivro);

		panTotal.setTop(panPrimeiro);
		panTotal.setCenter(panTabela);

		Bindings.bindBidirectional(txtCodigo.textProperty(), control.cod, new NumberStringConverter());
		Bindings.bindBidirectional(txtTitulo.textProperty(), control.nome);
		Bindings.bindBidirectional(txtAno.textProperty(), control.ano, new NumberStringConverter());
		Bindings.bindBidirectional(txtClass.textProperty(), control.classificacao, new NumberStringConverter());
		comboBoxCat.valueProperty().bindBidirectional(control.cat);
		comboBoxAutor.valueProperty().bindBidirectional(control.at);
		
		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxSelecao.valueProperty().bindBidirectional(control.coluna);

		return panTotal;

	}

	private void criarTabelaAutor() {
		tableAutor = new TableView<Autor>();
		tableAutor.setMaxSize(900, 200);

		TableColumn<Autor, String> col1 = new TableColumn<Autor, String>("Código");
		col1.setCellValueFactory(new PropertyValueFactory<Autor, String>("id"));

		TableColumn<Autor, String> col2 = new TableColumn<Autor, String>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<Autor, String>("nome"));

		TableColumn<Autor, String> col6 = new TableColumn<>("");
		col6.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Remover");
			TableCell<Autor, String> tcell = new TableCell<Autor, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							Autor a = getTableView().getItems().get(getIndex());
							control.removerAutor(a);
						});
						setGraphic(btnRemover);
						setText(null);
					}
				}
			};
			return tcell;
		});

		tableAutor.getColumns().addAll(col2, col6);
		tableAutor.setItems(control.getListaAutores());

	}

	private void criarTabelaLivros() {
		tableLivro.setMaxSize(2000, 2000);

		TableColumn<Livro, String> col1 = new TableColumn<Livro, String>("Código");
		col1.setCellValueFactory(new PropertyValueFactory<Livro, String>("id"));

		TableColumn<Livro, String> col2 = new TableColumn<Livro, String>("Título");
		col2.setCellValueFactory(new PropertyValueFactory<Livro, String>("titulo"));

		TableColumn<Livro, String> col3 = new TableColumn<Livro, String>("Indicativa");
		col3.setCellValueFactory(new PropertyValueFactory<Livro, String>("classificacao"));

		TableColumn<Livro, String> col4 = new TableColumn<Livro, String>("Ano");
		col4.setCellValueFactory(new PropertyValueFactory<Livro, String>("ano"));

		TableColumn<Livro, String> col5 = new TableColumn<>("Categoria");
		col5.setCellValueFactory((item) -> {
			String categoria = item.getValue().getCategoria().getNome();
			return new ReadOnlyStringWrapper(categoria);
		});

		TableColumn<Livro, String> col6 = new TableColumn<>("Autores");
		col6.setCellValueFactory((item) -> {
			String autores = "";
			int num = item.getValue().getAutores().size();
			for (int i = 0; i < num; i++) {
				if (i == 0) {
					autores = item.getValue().getAutores().get(i).getNome();
				} else {
					autores += ", " + item.getValue().getAutores().get(i).getNome();
				}
			}
			return new ReadOnlyStringWrapper(autores);
		});

		TableColumn<Livro, String> col7 = new TableColumn<>("");
		col7.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Deletar");
			TableCell<Livro, String> tcell = new TableCell<Livro, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							try {
								control.deletar();
								tableLivro.setItems(control.getLista());
								new Alert(Alert.AlertType.INFORMATION, "Livro deletado com sucesso!").showAndWait();
							} catch (SQLException e1) {
								new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
							}
						});
						setGraphic(btnRemover);
						setText(null);
					}
				}
			};

			return tcell;
		});

		tableLivro.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			try {
				control.limpar();
				control.fromEntity(novo);
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
			} catch (SQLException e) {
				new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			}
		});

		tableLivro.getColumns().addAll(col2, col3, col4, col5, col6, col7);
		try {
			tableLivro.setItems(control.getLista());
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}

	}
}
