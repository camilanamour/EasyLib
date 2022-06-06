package boundary;

import java.sql.SQLException;

import controller.ControllerEditora;
import controller.ControllerLivro;
import controller.ControllerVolume;
import entity.Editora;
import entity.Livro;
import entity.Volume;
import javafx.beans.binding.Bindings;
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
import javafx.util.converter.NumberStringConverter;

public class TelaVolumes {
	private Button btnAlterar;

	private TableView<Volume> tableVolume = new TableView<Volume>();

	private ControllerEditora controlE;
	private ControllerLivro controlL;
	private ControllerVolume control;

	public TelaVolumes() {
		control = new ControllerVolume();
		controlL = new ControllerLivro();
		controlE = new ControllerEditora();
	}

	public BorderPane render() {

		GridPane panR = new GridPane();
		BorderPane panRegistro = new BorderPane();
		GridPane panFiltro = new GridPane();
		BorderPane panTabela = new BorderPane();
		BorderPane panTotal = new BorderPane();

		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));

		panR.add(new Label("ISBN:"), 0, 0);
		TextField txtCodigo = new TextField();
		txtCodigo.setDisable(true);
		panR.add(txtCodigo, 0, 1);

		panR.add(new Label("Livro:"), 0, 2);
		ComboBox<Livro> comboBoxLivro = new ComboBox<Livro>();
		comboBoxLivro.getItems().clear();
		try {
			comboBoxLivro.setItems(controlL.getLista());
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxLivro.setDisable(true);
		comboBoxLivro.setPrefWidth(150);
		panR.add(comboBoxLivro, 0, 3);

		panR.add(new Label("Número Volume:"), 1, 0);
		TextField txtNum = new TextField();
		txtNum.setDisable(true);
		panR.add(txtNum, 1, 1);

		panR.add(new Label("Editora:"), 1, 2);
		ComboBox<Editora> comboBoxEditora = new ComboBox<Editora>();
		comboBoxEditora.getItems().clear();
		try {
			comboBoxEditora.setItems(controlE.getLista());
		} catch (SQLException | ClassNotFoundException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
		comboBoxEditora.setDisable(true);
		comboBoxEditora.setPrefWidth(150);
		panR.add(comboBoxEditora, 1, 3);

		panR.add(new Label("Situação:"), 2, 2);
		ComboBox<String> comboBoxFor = new ComboBox<String>();
		comboBoxFor.getItems().clear();
		comboBoxFor.getItems().addAll("disponível", "atrasado", "emprestado", "perdido");
		comboBoxFor.setPrefWidth(150);
		panR.add(comboBoxFor, 2, 3);

		panR.setHgap(10);
		panR.setVgap(5);

		btnAlterar = new Button("Atualizar");
		btnAlterar.setOnAction((e) -> {
			try {
				control.atualizar();
				new Alert(Alert.AlertType.INFORMATION, "Volume alterado com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});
		panR.add(btnAlterar, 3, 3);

		panRegistro.setTop(panR);

		panFiltro.add(new Label(""), 0, 0);
		panFiltro.add(new Label("Listar:"), 0, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panFiltro.add(txtFiltro, 1, 1);

		panFiltro.add(new Label("por"), 2, 1);
		ComboBox<String> comboBoxSelecao = new ComboBox<String>();
		comboBoxSelecao.getItems().clear();
		comboBoxSelecao.getItems().addAll("livro", "editora", "isbn", "situacao");
		comboBoxSelecao.setPrefWidth(200);
		panFiltro.add(comboBoxSelecao, 3, 1);

		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableVolume.setItems(control.getListaPorColuna());
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});

		panFiltro.add(btnListar, 4, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		this.criarTabelaEdicao();
		panTabela.setCenter(tableVolume);
		
		Bindings.bindBidirectional(txtCodigo.textProperty(), control.isbn);
		comboBoxLivro.valueProperty().bindBidirectional(control.livro);
		comboBoxEditora.valueProperty().bindBidirectional(control.editora);
		Bindings.bindBidirectional(txtNum.textProperty(), control.num, new NumberStringConverter());
		comboBoxFor.valueProperty().bindBidirectional(control.status);

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxSelecao.valueProperty().bindBidirectional(control.coluna);

		panTotal.setTop(panRegistro);
		panTotal.setCenter(panTabela);
		return panTotal;

	}

	private void criarTabelaEdicao() {
		tableVolume = new TableView<Volume>();
		tableVolume.setMaxSize(2000, 2000);

		TableColumn<Volume, String> col1 = new TableColumn<Volume, String>("ISBN");
		col1.setCellValueFactory(new PropertyValueFactory<Volume, String>("isbn"));

		TableColumn<Volume, String> col2 = new TableColumn<Volume, String>("Livro");
		col2.setCellValueFactory(new PropertyValueFactory<Volume, String>("livro"));

		TableColumn<Volume, String> col3 = new TableColumn<Volume, String>("Edição");
		col3.setCellValueFactory(new PropertyValueFactory<Volume, String>("edicao"));

		TableColumn<Volume, String> col4 = new TableColumn<Volume, String>("Editora");
		col4.setCellValueFactory(new PropertyValueFactory<Volume, String>("editora"));

		TableColumn<Volume, String> col5 = new TableColumn<Volume, String>("Número");
		col5.setCellValueFactory(new PropertyValueFactory<Volume, String>("numero"));

		TableColumn<Volume, String> col6 = new TableColumn<Volume, String>("Situação");
		col6.setCellValueFactory(new PropertyValueFactory<Volume, String>("status"));
		
		tableVolume.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
		});

		tableVolume.getColumns().addAll(col1, col2, col3, col4, col5, col6);
		try {
			tableVolume.setItems(control.getLista());
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}

	}

}
