package boundary;

import java.sql.SQLException;

import controller.ControllerEdicao;
import controller.ControllerEditora;
import controller.ControllerLivro;
import entity.Edicao;
import entity.Editora;
import entity.Livro;
import javafx.beans.binding.Bindings;
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

public class TelaEdicao {
	private Button btnInserir;
	private Button btnLimpar;
	private Button btnPesquisar;

	private TableView<Edicao> tableEdicao = new TableView<Edicao>();
	private ControllerEdicao control;
	private ControllerLivro controlL;
	private ControllerEditora controlE;

	public TelaEdicao() {
		control = new ControllerEdicao();
		controlL = new ControllerLivro();
		controlE = new ControllerEditora();
	}

	public BorderPane render() {

		GridPane pan = new GridPane();
		GridPane panR = new GridPane();
		GridPane panBut = new GridPane();
		BorderPane panRegistro = new BorderPane();
		GridPane panFiltro = new GridPane();
		BorderPane panTabela = new BorderPane();
		BorderPane panPrimeiro = new BorderPane();
		BorderPane panTotal = new BorderPane();

		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));

		pan.add(new Label("ISBN:"), 0, 0);
		TextField txtCodigo = new TextField();
		pan.add(txtCodigo, 0, 1);
		btnPesquisar = new Button("Pesquisar");
		btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction((e) -> {
			try {
				control.pesquisar();
				btnInserir.setDisable(true);
				tableEdicao.setItems(control.getLista());
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		pan.add(btnPesquisar, 1, 1);
		pan.add(new Label(""), 0, 2);

		panR.add(new Label("Livro:"), 0, 0);
		ComboBox<Livro> comboBoxLivro = new ComboBox<Livro>();
		comboBoxLivro.getItems().clear();
		try {
			comboBoxLivro.setItems(controlL.getLista());
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxLivro.setPrefWidth(150);
		panR.add(comboBoxLivro, 0, 1);

		panR.add(new Label("Número Edição:"), 1, 0);
		TextField txtNumEdicao = new TextField();
		panR.add(txtNumEdicao, 1, 1);

		panR.add(new Label("Editora:"), 2, 0);
		ComboBox<Editora> comboBoxEditora = new ComboBox<Editora>();
		comboBoxEditora.getItems().clear();
		try {
			comboBoxEditora.setItems(controlE.getLista());
		} catch (SQLException | ClassNotFoundException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxEditora.setPrefWidth(150);
		panR.add(comboBoxEditora, 2, 1);

		panR.add(new Label("ISBN:"), 3, 0);
		TextField txtISBN = new TextField();
		panR.add(txtISBN, 3, 1);

		panR.add(new Label("Ano:"), 0, 2);
		TextField txtAno = new TextField();
		panR.add(txtAno, 0, 3);

		panR.add(new Label("Quant. páginas:"), 1, 2);
		TextField txtNumPag = new TextField();
		panR.add(txtNumPag, 1, 3);

		panR.add(new Label("Formato:"), 2, 2);
		ComboBox<String> comboBoxFor = new ComboBox<String>();
		comboBoxFor.getItems().clear();
		comboBoxFor.getItems().addAll("brochura", "capa-dura");
		comboBoxFor.setPrefWidth(150);
		panR.add(comboBoxFor, 2, 3);

		panR.add(new Label("Quantidade Volume:"), 3, 2);
		TextField txtNumVol = new TextField();
		panR.add(txtNumVol, 3, 3);

		pan.setHgap(10);
		pan.setVgap(5);
		panR.setHgap(10);
		panR.setVgap(5);

		btnInserir = new Button("Cadastrar");
		btnInserir.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Edição inserida com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		btnLimpar = new Button("Limpar Campos");
		btnLimpar.setOnAction((e) -> {
			try {
				control.limpar();
				tableEdicao.setItems(control.getLista());
				btnInserir.setDisable(false);
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		panBut.add(new Label(""), 0, 0);
		panBut.add(btnInserir, 0, 1);
		panBut.add(btnLimpar, 1, 1);
		panBut.add(new Label(""), 0, 2);
		panBut.setHgap(15);

		panPrimeiro.setTop(pan);
		panPrimeiro.setCenter(panR);

		panRegistro.setTop(panPrimeiro);
		panRegistro.setCenter(panBut);

		panFiltro.add(new Label(""), 0, 0);
		panFiltro.add(new Label("Listar:"), 0, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panFiltro.add(txtFiltro, 1, 1);

		panFiltro.add(new Label("por"), 2, 1);
		ComboBox<String> comboBoxSelecao = new ComboBox<String>();
		comboBoxSelecao.getItems().clear();
		comboBoxSelecao.getItems().addAll("livro", "editora", "ano", "paginas", "volumes", "formato");
		comboBoxSelecao.setPrefWidth(200);
		panFiltro.add(comboBoxSelecao, 3, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableEdicao.setItems(control.getListaPorColuna());
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		panFiltro.add(btnListar, 4, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		try {
			this.criarTabelaEdicao();
		} catch (ClassNotFoundException | SQLException e1) {
			new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		}
		panTabela.setCenter(tableEdicao);

		Bindings.bindBidirectional(txtCodigo.textProperty(), control.pesquisaEdicao);

		Bindings.bindBidirectional(txtISBN.textProperty(), control.cod);
		comboBoxLivro.valueProperty().bindBidirectional(control.livro);
		Bindings.bindBidirectional(txtNumEdicao.textProperty(), control.numEd, new NumberStringConverter());
		comboBoxEditora.valueProperty().bindBidirectional(control.editora);
		Bindings.bindBidirectional(txtAno.textProperty(), control.ano, new NumberStringConverter());
		comboBoxFor.valueProperty().bindBidirectional(control.formato);
		Bindings.bindBidirectional(txtNumPag.textProperty(), control.qtdPagina, new NumberStringConverter());
		Bindings.bindBidirectional(txtNumVol.textProperty(), control.qtdVolumes, new NumberStringConverter());

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxSelecao.valueProperty().bindBidirectional(control.coluna);

		panTotal.setTop(panRegistro);
		panTotal.setCenter(panTabela);
		return panTotal;

	}

	private void criarTabelaEdicao() throws ClassNotFoundException, SQLException {
		tableEdicao = new TableView<Edicao>();
		tableEdicao.setMaxSize(2000, 2000);

		TableColumn<Edicao, String> col1 = new TableColumn<Edicao, String>("ISBN");
		col1.setCellValueFactory(new PropertyValueFactory<Edicao, String>("isbn"));

		TableColumn<Edicao, String> col2 = new TableColumn<Edicao, String>("Livro");
		col2.setCellValueFactory(new PropertyValueFactory<Edicao, String>("livro"));

		TableColumn<Edicao, String> col3 = new TableColumn<Edicao, String>("Edição");
		col3.setCellValueFactory(new PropertyValueFactory<Edicao, String>("numEdicao"));

		TableColumn<Edicao, String> col4 = new TableColumn<Edicao, String>("Editora");
		col4.setCellValueFactory(new PropertyValueFactory<Edicao, String>("editora"));

		TableColumn<Edicao, String> col5 = new TableColumn<Edicao, String>("Ano");
		col5.setCellValueFactory(new PropertyValueFactory<Edicao, String>("anoEdicao"));

		TableColumn<Edicao, String> col6 = new TableColumn<Edicao, String>("Formato");
		col6.setCellValueFactory(new PropertyValueFactory<Edicao, String>("formato"));

		TableColumn<Edicao, String> col8 = new TableColumn<Edicao, String>("Páginas");
		col8.setCellValueFactory(new PropertyValueFactory<Edicao, String>("qtdPaginas"));

		TableColumn<Edicao, String> col9 = new TableColumn<Edicao, String>("Volume");
		col9.setCellValueFactory(new PropertyValueFactory<Edicao, String>("qtdEstoque"));

		TableColumn<Edicao, String> col7 = new TableColumn<>("");
		col7.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Deletar");
			TableCell<Edicao, String> tcell = new TableCell<Edicao, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							try {
								Edicao a = getTableView().getItems().get(getIndex());
								control.deletar(a);
								control.limpar();
								btnInserir.setDisable(false);
								new Alert(Alert.AlertType.INFORMATION, "Edicao deletado com sucesso!").showAndWait();
							} catch (SQLException | ClassNotFoundException e1) {
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
		
		tableEdicao.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
			btnInserir.setDisable(true);
		});

		tableEdicao.getColumns().addAll(col1, col2, col3, col4, col5, col6, col8, col9, col7);
		tableEdicao.setItems(control.getLista());

	}

}
