package boundary;

import java.sql.SQLException;

import controller.ControllerRelatorio;
import entity.Emprestimo;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TelaRelatorio {

	private TableView<Emprestimo> table = new TableView<Emprestimo>();

	private ControllerRelatorio control;

	public TelaRelatorio() {
		control = new ControllerRelatorio();
	}

	public BorderPane render() {

		GridPane pan = new GridPane();
		BorderPane panTotal = new BorderPane();
		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));

		pan.add(new Label("Periodo:"), 0, 0);
		ComboBox<String> comboBoxPeriodo = new ComboBox<String>();
		comboBoxPeriodo.getItems().clear();
		comboBoxPeriodo.getItems().addAll("manhã", "tarde", "noite");
		comboBoxPeriodo.setPrefWidth(200);
		pan.add(comboBoxPeriodo, 1, 0);

		pan.add(new Label("Ordenar por:"), 2, 0);
		ComboBox<String> comboBoxFiltro = new ComboBox<String>();
		comboBoxFiltro.getItems().clear();
		comboBoxFiltro.getItems().addAll("turma", "data_devolucao", "situacao_emprestimo", "livro");
		comboBoxFiltro.setPrefWidth(200);
		pan.add(comboBoxFiltro, 3, 0);

		pan.add(new Label("com o filtro:"), 0, 1);
		TextField txtFiltro = new TextField();
		pan.add(txtFiltro, 1, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				table.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		pan.add(btnListar, 2, 1);
		pan.setHgap(10);
		pan.setVgap(15);

		this.criarTabela();
		panTotal.setTop(pan);
		panTotal.setCenter(table);

		return panTotal;
	}

	private void criarTabela() {
		table = new TableView<Emprestimo>();
		table.setMaxSize(2000, 2000);

		TableColumn<Emprestimo, String> col1 = new TableColumn<Emprestimo, String>("Turma");
		col1.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("turma"));

		TableColumn<Emprestimo, String> col2 = new TableColumn<Emprestimo, String>("Aluno");
		col2.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("nomeAluno"));

		TableColumn<Emprestimo, String> col3 = new TableColumn<Emprestimo, String>("ISBN");
		col3.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("ISBN"));
		TableColumn<Emprestimo, String> col4 = new TableColumn<Emprestimo, String>("Livro");
		col4.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("nomeLivro"));
		TableColumn<Emprestimo, String> col5 = new TableColumn<Emprestimo, String>("Edição");
		col5.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("numEdicao"));
		TableColumn<Emprestimo, String> col6 = new TableColumn<Emprestimo, String>("Volume");
		col6.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("numVolume"));
		TableColumn<Emprestimo, String> col7 = new TableColumn<Emprestimo, String>("Situação");
		col7.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("sitEmprestimo"));

		TableColumn<Emprestimo, String> col8 = new TableColumn<Emprestimo, String>("Devolução");
		col8.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("dataDevolucao"));

		table.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			// try {
			//// control.fromEntity(novo);
			// btnInserir.setDisable(true);
			// btnAlterar.setDisable(false);
			// } catch (SQLException e) {
			// new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
			// }
		});

		table.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
		// table.setItems(control.getLista());

	}

}
