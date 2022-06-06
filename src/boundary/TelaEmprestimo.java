package boundary;

import entity.Emprestimo;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TelaEmprestimo {
	private Button btnLimpar;
	private Button btnPesquisar;
	private Button btnEmprestar;
	private Button btnDevolvido;
	private Button btnRenovar;
	private Button btnCancelar;

	private TableView<Emprestimo> tableEdicao = new TableView<Emprestimo>();
	
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
		pan.add(btnPesquisar, 1, 1);
		btnLimpar = new Button("Limpar campos");
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
		comboBoxFor.getItems().addAll("em dia", "atrasado", "renovado", "cancelado", "desolvido");
		comboBoxFor.setPrefWidth(150);
		panR.add(comboBoxFor, 2, 4);
						
		pan.setHgap(10);
		pan.setVgap(5);
		panR.setHgap(10);
		panR.setVgap(5);
		
		btnEmprestar= new Button("Emprestar");
		btnDevolvido = new Button("Devolvido");
		btnRenovar = new Button("Renovar");
		btnCancelar = new Button("Cancelar");
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
		// btnListar.setOnAction((e) -> {
		// try {
		//// control.listarPorColuna();
		//// table.setItems(control.getLista());
		// } catch (SQLException e1) {
		// new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
		// }
		// });

		panFiltro.add(btnListar, 4, 1);
		panFiltro.add(new Label(""), 0, 3);
		panFiltro.setHgap(10);

		panTabela.setTop(panFiltro);
		this.criarTabelaEmprestimo();
		panTabela.setCenter(tableEdicao);

		panTotal.setTop(panRegistro);
		panTotal.setCenter(panTabela);
		return panTotal;
		
	}

	private void criarTabelaEmprestimo() {
		// TODO Auto-generated method stub
		
	}
}
