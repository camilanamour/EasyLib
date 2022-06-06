package boundary;

import java.sql.SQLException;

import controller.ControllerAluno;
import entity.Aluno;
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

public class TelaAluno {

	private Button btnInserir;
	private Button btnAlterar;
	private Button btnLimpar;
	private Button btnPesquisar;

	private TableView<Aluno> tableAluno = new TableView<Aluno>();

	private ControllerAluno control;

	public TelaAluno() {
		control = new ControllerAluno();
		this.criarTabelaAluno();
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

		pan.add(new Label("Buscar - RA:"), 0, 0);
		TextField txtCodigo = new TextField();
		pan.add(txtCodigo, 0, 1);
		btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction((e) -> {
			try {
				control.pesquisar();
				btnInserir.setDisable(true);
				btnAlterar.setDisable(false);
				tableAluno.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		pan.add(btnPesquisar, 1, 1);
		pan.add(new Label(""), 0, 2);

		panR.add(new Label("RA:"), 0, 0);
		TextField txtRa = new TextField();
		panR.add(txtRa, 0, 1);

		panR.add(new Label("RG:"), 1, 0);
		TextField txtRG = new TextField();
		panR.add(txtRG, 1, 1);

		panR.add(new Label("CPF:"), 2, 0);
		TextField txtCpf = new TextField();
		panR.add(txtCpf, 2, 1);

		panR.add(new Label("Nome:"), 0, 2);
		TextField txtNome = new TextField();
		txtNome.setPrefWidth(200);
		panR.add(txtNome, 0, 3);

		panR.add(new Label("Turma:"), 1, 2);
		TextField txtTurma = new TextField();
		panR.add(txtTurma, 1, 3);

		panR.add(new Label("Periodo:"), 2, 2);
		ComboBox<String> comboBoxPeriodo = new ComboBox<String>();
		comboBoxPeriodo.getItems().clear();
		comboBoxPeriodo.getItems().addAll("manhã", "tarde", "noite");
		comboBoxPeriodo.setPrefWidth(150);
		panR.add(comboBoxPeriodo, 2, 3);

		panR.add(new Label("Data Nascimento:"), 3, 2);
		TextField txtData = new TextField();
		panR.add(txtData, 3, 3);

		panR.add(new Label("Telefone:"), 0, 4);
		TextField txtTelefone = new TextField();
		panR.add(txtTelefone, 0, 5);

		panR.add(new Label("Celular:"), 1, 4);
		TextField txtCel = new TextField();
		panR.add(txtCel, 1, 5);

		panR.add(new Label("Situação:"), 2, 4);
		ComboBox<String> comboBoxSitAluno = new ComboBox<String>();
		comboBoxSitAluno.getItems().clear();
		comboBoxSitAluno.getItems().addAll("matriculado", "não matriculado", "concluinte", "bloqueado");
		comboBoxSitAluno.setPrefWidth(150);
		panR.add(comboBoxSitAluno, 2, 5);

		pan.setHgap(10);
		pan.setVgap(5);
		panR.setHgap(10);
		panR.setVgap(5);

		btnInserir = new Button("Cadastrar");
		btnInserir.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Aluno inserido com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		btnAlterar = new Button("Atualizar");
		btnAlterar.setOnAction((e) -> {
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Aluno alterado com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnLimpar = new Button("Limpar Campos");
		btnLimpar.setOnAction((e) -> {
			try {
				control.limpar();
				tableAluno.setItems(control.getLista());
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

		panPrimeiro.setTop(pan);
		panPrimeiro.setCenter(panR);

		panRegistro.setTop(panPrimeiro);
		panRegistro.setCenter(panBut);

		panFiltro.add(new Label(""), 0, 0);
		panFiltro.add(new Label("Listar:"), 0, 1);
		ComboBox<String> comboBoxSelecao = new ComboBox<String>();
		comboBoxSelecao.getItems().clear();
		comboBoxSelecao.getItems().addAll("nome", "turma", "situacao");
		comboBoxSelecao.setPrefWidth(200);
		panFiltro.add(comboBoxSelecao, 1, 1);

		panFiltro.add(new Label("por"), 2, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panFiltro.add(txtFiltro, 3, 1);

		panFiltro.add(new Label("no periodo"), 4, 1);

		ComboBox<String> comboBoxPeriodo1 = new ComboBox<String>();
		comboBoxPeriodo1.getItems().clear();
		comboBoxPeriodo1.getItems().addAll("manhã", "tarde", "noite", "todos");
		comboBoxPeriodo1.setPrefWidth(200);
		panFiltro.add(comboBoxPeriodo1, 5, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableAluno.setItems(control.getListaPorColuna());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		panFiltro.add(btnListar, 6, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		panTabela.setCenter(tableAluno);

		panTotal.setTop(panRegistro);
		panTotal.setCenter(panTabela);

		Bindings.bindBidirectional(txtCodigo.textProperty(), control.pesquisarRa);

		Bindings.bindBidirectional(txtRa.textProperty(), control.ra);
		Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
		Bindings.bindBidirectional(txtRG.textProperty(), control.rg);
		Bindings.bindBidirectional(txtCpf.textProperty(), control.cpf);
		Bindings.bindBidirectional(txtTurma.textProperty(), control.turma);
		comboBoxPeriodo.valueProperty().bindBidirectional(control.periodo);
		Bindings.bindBidirectional(txtData.textProperty(), control.nascimento);
		Bindings.bindBidirectional(txtTelefone.textProperty(), control.telefone);
		Bindings.bindBidirectional(txtCel.textProperty(), control.celular);
		comboBoxSitAluno.valueProperty().bindBidirectional(control.situacao);

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxSelecao.valueProperty().bindBidirectional(control.coluna);
		comboBoxPeriodo1.valueProperty().bindBidirectional(control.periodoLista);

		return panTotal;

	}

	private void criarTabelaAluno() {
		tableAluno.setMaxSize(2000, 2000);

		TableColumn<Aluno, Integer> col1 = new TableColumn<Aluno, Integer>("RA");
		col1.setCellValueFactory(new PropertyValueFactory<Aluno, Integer>("ra"));

		TableColumn<Aluno, String> col2 = new TableColumn<Aluno, String>("Nome");
		col2.setCellValueFactory(new PropertyValueFactory<Aluno, String>("nome"));

		TableColumn<Aluno, String> col3 = new TableColumn<Aluno, String>("Telefone");
		col3.setCellValueFactory(new PropertyValueFactory<Aluno, String>("telefone"));

		TableColumn<Aluno, String> col4 = new TableColumn<Aluno, String>("Celular");
		col4.setCellValueFactory(new PropertyValueFactory<Aluno, String>("celular"));

		TableColumn<Aluno, String> col5 = new TableColumn<Aluno, String>("Turma");
		col5.setCellValueFactory(new PropertyValueFactory<Aluno, String>("turma"));

		TableColumn<Aluno, String> col6 = new TableColumn<Aluno, String>("Situação");
		col6.setCellValueFactory(new PropertyValueFactory<Aluno, String>("status"));

		TableColumn<Aluno, String> col7 = new TableColumn<>("");
		col7.setCellFactory((tbcol) -> {
			Button btnRemover = new Button("Deletar");
			TableCell<Aluno, String> tcell = new TableCell<Aluno, String>() {
				@Override
				protected void updateItem(String item, boolean empty) {
					if (empty) {
						setGraphic(null);
						setText(null);
					} else {
						btnRemover.setOnAction((e) -> {
							try {
								Aluno a = getTableView().getItems().get(getIndex());
								control.deletar(a);
								control.limpar();
								btnInserir.setDisable(false);
								btnAlterar.setDisable(true);
								new Alert(Alert.AlertType.INFORMATION, "Aluno deletado com sucesso!").showAndWait();
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

		tableAluno.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
			btnInserir.setDisable(true);
			btnAlterar.setDisable(false);
		});

		tableAluno.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7);
		try {
			tableAluno.setItems(control.getLista());
		} catch (SQLException e1) {
			new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		}
	}

}
