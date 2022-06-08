package boundary;

import java.sql.SQLException;

import controller.ControllerEmprestimo;
import entity.Emprestimo;
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

public class TelaEmprestimo {
	private Button btnLimpar;
	private Button btnPesquisar;
	private Button btnEmprestar;
	private Button btnDevolvido;
	private Button btnRenovar;
	private Button btnCancelar;

	private TableView<Emprestimo> tableEdicao = new TableView<Emprestimo>();
	
	private ControllerEmprestimo control;
	
	public TelaEmprestimo() {
		control = new ControllerEmprestimo();
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
	
		pan.add(new Label("Código do Empréstimo:"), 0, 0);
		TextField txtCodigo = new TextField();
		pan.add(txtCodigo, 0, 1);
		btnPesquisar = new Button("Pesquisar");
		btnPesquisar.setOnAction((e) -> {
			try {
				control.pesquisar();
				tableEdicao.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		pan.add(btnPesquisar, 1, 1);
		btnLimpar = new Button("Limpar campos");
		btnLimpar.setOnAction((e) -> {
			try {
				control.limpar();
				tableEdicao.setItems(control.getLista());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		pan.add(btnLimpar, 2, 1);
		pan.add(new Label(""), 0, 2);
				
		
		panR.add(new Label("Novo Empréstimo"), 0, 0);
		
		panR.add(new Label("RA:"), 0, 1);
		TextField txtRa = new TextField();
		panR.add(txtRa, 0, 2);
		
		panR.add(new Label("ISBN:"), 1, 1);
		TextField txtISBN = new TextField();
		panR.add(txtISBN, 1, 2);
		
		panR.add(new Label("Número Volume:"), 2, 1);
		TextField txtNumVolume = new TextField();
		panR.add(txtNumVolume, 2, 2);
		
		panR.add(new Label("Data empréstimo:"), 0, 3);
		TextField txtDtEmprestimo = new TextField();
		panR.add(txtDtEmprestimo, 0, 4);
		
		panR.add(new Label("Data devolução:"), 1, 3);
		TextField txtDtDevolucao = new TextField();
		panR.add(txtDtDevolucao, 1, 4);
			
		panR.add(new Label("Situação:"), 2, 3);
		ComboBox<String> comboBoxFor = new ComboBox<String>();
		comboBoxFor.getItems().clear();
		comboBoxFor.getItems().addAll("em dia", "atrasado", "renovado", "cancelado", "devolvido");
		comboBoxFor.setPrefWidth(150);
		panR.add(comboBoxFor, 2, 4);
						
		pan.setHgap(10);
		pan.setVgap(5);
		panR.setHgap(10);
		panR.setVgap(5);
		
		btnEmprestar= new Button("Emprestar");
		btnEmprestar.setOnAction((e) -> {
			try {
				control.inserir();;
				new Alert(Alert.AlertType.INFORMATION, "Emprestimo realizado com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});
		btnDevolvido = new Button("Devolvido");
		btnDevolvido.setOnAction((e) -> {
			try {
				control.devolver();
				new Alert(Alert.AlertType.INFORMATION, "Emprestimo devolvido com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});
		btnRenovar = new Button("Renovar");
		btnRenovar.setOnAction((e) -> {
			try {
				control.renovar();
				new Alert(Alert.AlertType.INFORMATION, "Emprestimo renovado com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});
		btnCancelar = new Button("Cancelar");
		btnCancelar.setOnAction((e) -> {
			try {
				control.cancelar();
				new Alert(Alert.AlertType.INFORMATION, "Emprestimo cancelado com sucesso!").showAndWait();
			} catch (SQLException | ClassNotFoundException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			} 
		});
		panBut.add(new Label(""), 0, 0);
		panBut.add(btnEmprestar, 0, 1);
		panBut.add(btnDevolvido, 1, 1);
		panBut.add(btnRenovar, 2, 1);
		panBut.add(btnCancelar, 3, 1);
		panBut.add(new Label(""), 0, 2);
		panBut.setHgap(15);

		panPrimeiro.setTop(pan);
		panPrimeiro.setCenter(panR);
		
		panRegistro.setTop(panPrimeiro);
		panRegistro.setCenter(panBut);
		
		
		panFiltro.add(new Label(""), 0, 0);
		panFiltro.add(new Label("Data de devolução:"), 0, 1);
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		panFiltro.add(txtFiltro, 1, 1);
		
		panFiltro.add(new Label("ordenar por"), 2, 1);
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
		 } catch (SQLException e1) {
		 new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		 }
		 });

		panFiltro.add(btnListar, 4, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		this.criarTabelaEmprestimo();
		panTabela.setCenter(tableEdicao);
		
		Bindings.bindBidirectional(txtCodigo.textProperty(), control.pesquisar, new NumberStringConverter());

		Bindings.bindBidirectional(txtRa.textProperty(), control.ra);
		Bindings.bindBidirectional(txtDtDevolucao.textProperty(), control.dataDevolucao);
		Bindings.bindBidirectional(txtDtEmprestimo.textProperty(), control.dataEmprestimo);
		Bindings.bindBidirectional(txtISBN.textProperty(), control.isbn);
		Bindings.bindBidirectional(txtNumVolume.textProperty(), control.numVolume, new NumberStringConverter() );
		comboBoxFor.valueProperty().bindBidirectional(control.status);

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxSelecao.valueProperty().bindBidirectional(control.coluna);

		panTotal.setTop(panRegistro);
		panTotal.setCenter(panTabela);
		return panTotal;
		
	}

	private void criarTabelaEmprestimo() {
		tableEdicao.setMaxSize(2000, 2000);

		TableColumn<Emprestimo, Integer> col1 = new TableColumn<Emprestimo, Integer>("Nome");
		col1.setCellValueFactory(new PropertyValueFactory<Emprestimo, Integer>("nome"));

		TableColumn<Emprestimo, String> col2 = new TableColumn<Emprestimo, String>("Turma");
		col2.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("turma"));

		TableColumn<Emprestimo, String> col3 = new TableColumn<Emprestimo, String>("Periodo");
		col3.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("Periodo"));

		TableColumn<Emprestimo, String> col4 = new TableColumn<Emprestimo, String>("Livro");
		col4.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("livro"));

		TableColumn<Emprestimo, String> col5 = new TableColumn<Emprestimo, String>("Edicao");
		col5.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("edicao"));

		TableColumn<Emprestimo, String> col6 = new TableColumn<Emprestimo, String>("Volume");
		col6.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("volume"));
		
		TableColumn<Emprestimo, String> col7 = new TableColumn<Emprestimo, String>("Devolucao");
		col7.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("dataDevolucao"));
		
		TableColumn<Emprestimo, String> col8 = new TableColumn<Emprestimo, String>("Situacao");
		col8.setCellValueFactory(new PropertyValueFactory<Emprestimo, String>("status"));

		tableEdicao.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
		});

		tableEdicao.getColumns().addAll(col1, col2, col3, col4, col5, col6, col7, col8);
		try {
			tableEdicao.setItems(control.getLista());
		} catch (SQLException e1) {
			new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		}
		
	}
}
