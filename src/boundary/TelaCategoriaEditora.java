package boundary;

import java.sql.SQLException;

import controller.ControllerCategoria;
import controller.ControllerEditora;
import entity.Categoria;
import entity.Editora;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.util.converter.NumberStringConverter;

public class TelaCategoriaEditora {
	private Button btnInserirCat;
	private Button btnInserirEdit;
	private Button btnAlterarEdit;
	private Button btnLimparEdit;

	private TableView<Categoria> tableC = new TableView<Categoria>();
	private TableView<Editora> tableE = new TableView<Editora>();

	private ControllerEditora control;
	private ControllerCategoria controlC;

	public TelaCategoriaEditora() {
		control = new ControllerEditora();
		controlC = new ControllerCategoria();
	}

	public BorderPane render() {
		GridPane panCat = new GridPane();
		BorderPane panTabelaCat = new BorderPane();

		GridPane panEditora = new GridPane();
		GridPane panEditoraBut = new GridPane();
		GridPane panEditoraPesq = new GridPane();
		BorderPane panRegistro = new BorderPane();
		BorderPane panTabela = new BorderPane();
		BorderPane panEdiTotal = new BorderPane();
		BorderPane panTotal = new BorderPane();

		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));
		panCat.setHgap(10);
		panCat.setVgap(15);

		panCat.add(new Label("Gerenciar Categorias"), 0, 1);
		TextField txtCodigoC = new TextField();
		txtCodigoC.setVisible(false);
		panCat.add(txtCodigoC, 1, 1);
		panCat.add(new Label("Nome: "), 0, 2);
		TextField txtNome = new TextField();
		panCat.add(txtNome, 1, 2);
		btnInserirCat = new Button("Cadastrar");
		btnInserirCat.setOnAction((e) -> {
			try {
				controlC.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Categoria inserida com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		panCat.add(btnInserirCat, 2, 2);
		panTabelaCat.setLeft(panCat);
		this.criarTabelaCategoria();
		panTabelaCat.setRight(tableC);
		
		Bindings.bindBidirectional(txtCodigoC.textProperty(), controlC.cod, new NumberStringConverter());
		Bindings.bindBidirectional(txtNome.textProperty(), controlC.nome);

		panEditora.add(new Label("Gerenciar Editoras "), 0, 0);
		panEditora.add(new Label("Código:"), 0, 1);
		TextField txtCodigo = new TextField();
		txtCodigo.setDisable(true);
		panEditora.add(txtCodigo, 1, 1);
		panEditora.add(new Label("Nome: "), 2, 1);
		TextField txtNomeEditora = new TextField();
		panEditora.add(txtNomeEditora, 3, 1);
		panEditora.add(new Label("Site: "), 4, 1);
		TextField txtSite = new TextField();
		panEditora.add(txtSite, 5, 1);
		panEditora.setHgap(10);
		panEditora.setVgap(20);

		btnInserirEdit = new Button("Cadastrar");
		btnInserirEdit.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Editora inserido com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		btnAlterarEdit = new Button("Atualizar");
		btnAlterarEdit.setOnAction((e) ->

		{
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Editora alterado com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnLimparEdit = new Button("Limpar Campos");
		btnLimparEdit.setOnAction((e) -> {
			try {
				control.limpar();
				tableE.setItems(control.getLista());
				btnInserirEdit.setDisable(false);
				btnAlterarEdit.setDisable(true);
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnInserirEdit.setDisable(false);
		btnAlterarEdit.setDisable(true);

		
		panEditoraBut.add(new Label(""), 0, 0);
		panEditoraBut.add(btnInserirEdit, 0, 1);
		panEditoraBut.add(btnAlterarEdit, 1, 1);
		panEditoraBut.add(btnLimparEdit, 2, 1);
		panEditoraBut.setHgap(15);

		panEditoraPesq.add(new Label(""), 0, 0);
		panEditoraPesq.add(new Label("Buscar:"), 0, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panEditoraPesq.add(txtFiltro, 1, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableE.setItems(control.getListaPorColuna());
			} catch (SQLException | ClassNotFoundException  e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		panEditoraPesq.add(btnListar, 2, 1);
		panEditoraBut.add(new Label(""), 0, 3);
		panEditoraPesq.setHgap(15);

		panRegistro.setTop(panEditora);
		panRegistro.setCenter(panEditoraBut);
		panTabela.setTop(panEditoraPesq);
		this.criarTabelaEditora();
		panTabela.setCenter(tableE);
		panEdiTotal.setTop(panRegistro);
		panEdiTotal.setCenter(panTabela);

		panTotal.setTop(panTabelaCat);
		panTotal.setCenter(panEdiTotal);

		Bindings.bindBidirectional(txtCodigo.textProperty(), control.cod, new NumberStringConverter());
		Bindings.bindBidirectional(txtNomeEditora.textProperty(), control.nome);
		Bindings.bindBidirectional(txtSite.textProperty(), control.site);

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.buscaEditora);

		return panTotal;
	}

	private void criarTabelaCategoria() {
		tableC.setMaxSize(1000, 200);

		TableColumn<Categoria, String> col1 = new TableColumn<Categoria, String>("Código");
		col1.setCellValueFactory(new PropertyValueFactory<Categoria, String>("id"));

		TableColumn<Categoria, String> col2 = new TableColumn<Categoria, String>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<Categoria, String>("nome"));

		TableColumn<Categoria, String> col6 = new TableColumn<>("");
		col6.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Deletar");
			TableCell<Categoria, String> tcell = new TableCell<Categoria, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							try {
								Categoria c = getTableView().getItems().get(getIndex());
								controlC.deletar(c);
								controlC.limpar();
								tableC.setItems(controlC.getLista());
								new Alert(Alert.AlertType.INFORMATION, "Categoria deletada com sucesso!").showAndWait();
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

		tableC.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			controlC.fromEntity(novo);
		});

		tableC.getColumns().addAll(col2, col6);
		try {
			tableC.setItems(controlC.getLista());
		} catch (SQLException | ClassNotFoundException  e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}

	}

	private void criarTabelaEditora() {
		tableE.setMaxSize(2000, 2000);

		TableColumn<Editora, String> col1 = new TableColumn<Editora, String>("Código");
		col1.setCellValueFactory(new PropertyValueFactory<Editora, String>("id"));

		TableColumn<Editora, String> col2 = new TableColumn<Editora, String>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<Editora, String>("nome"));

		TableColumn<Editora, String> col3 = new TableColumn<Editora, String>("Site");
		col3.setCellValueFactory(new PropertyValueFactory<Editora, String>("site"));

		TableColumn<Editora, String> col6 = new TableColumn<>("");
		col6.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Deletar");
			TableCell<Editora, String> tcell = new TableCell<Editora, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							try {
								Editora ed = getTableView().getItems().get(getIndex());
								control.deletar(ed);
								control.limpar();
								btnInserirEdit.setDisable(false);
								btnAlterarEdit.setDisable(true);
							} catch (SQLException | ClassNotFoundException  e1) {
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

		tableE.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
			btnInserirEdit.setDisable(true);
			btnAlterarEdit.setDisable(false);
		});

		tableE.getColumns().addAll(col2, col3, col6);
		try {
			tableE.setItems(control.getLista());
		} catch (SQLException | ClassNotFoundException  e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}

	}

}
