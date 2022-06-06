package boundary;

import java.sql.SQLException;

import controller.ControllerAutor;
import entity.Autor;
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
import javafx.scene.layout.Pane;
import javafx.util.converter.NumberStringConverter;

public class TelaAutor {
		
	private Button btnInserir;
	private Button btnAlterar;
    private Button btnLimpar;
    
	private TableView<Autor> tableAutor = new TableView<Autor>();
	
	private ControllerAutor control;
	
	public TelaAutor() {
		control = new ControllerAutor();
	}
	
	public BorderPane render() {
		
		BorderPane pan = new BorderPane();
		GridPane panAutor = new GridPane();
		GridPane panAutorBut = new GridPane();
		BorderPane panTotal = new BorderPane();
		Pane panFiltro = new Pane();
		BorderPane panTabela = new BorderPane();
		
		BorderPane.setMargin(panTotal, new Insets(0, 10, 0, 10));
		panAutor.setHgap(15);
		panAutor.setVgap(20);
		
		panAutor.add(new Label("Código: "), 0, 1);
		panAutor.add(new Label("Nome: "), 0, 2);
		panAutor.add(new Label("Nascimento: "), 2, 2);
		panAutor.add(new Label("País: "), 0, 3);
		panAutor.add(new Label("Biografia: "), 0, 4);
		
		TextField txtCodigo = new TextField();
		txtCodigo.setDisable(true);
		panAutor.add(txtCodigo, 1, 1);
		TextField txtNome = new TextField();
		panAutor.add(txtNome, 1, 2);
		TextField txtNascimento = new TextField();
		panAutor.add(txtNascimento, 3, 2);
		TextField txtPais = new TextField();
		panAutor.add(txtPais, 1, 3);
		TextField txtBiografia = new TextField();
		panAutor.add(txtBiografia, 1, 4);
		
		btnInserir = new Button("Cadastrar");
		btnInserir.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Autor inserido com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		btnAlterar = new Button("Atualizar");
		btnAlterar.setOnAction((e) -> {
			try {
				control.alterar();
				new Alert(Alert.AlertType.INFORMATION, "Autor alterado com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnLimpar = new Button("Limpar Campos");
		btnLimpar.setOnAction((e) -> {
			try {
				control.limpar();
				tableAutor.setItems(control.getLista());
				btnInserir.setDisable(false);
				btnAlterar.setDisable(true);
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});

		btnInserir.setDisable(false);
		btnAlterar.setDisable(true);
		
		panAutorBut.add(new Label(""), 0, 0);
		panAutorBut.add(btnInserir, 0, 1);
		panAutorBut.add(btnAlterar, 1, 1);
		panAutorBut.add(btnLimpar, 2, 1);
		panAutorBut.add(new Label(""), 0, 2);
		panAutorBut.setHgap(15);
		
		Label lblPor = new Label("por");
		lblPor.relocate(230, 0);
		Label lblEspaco = new Label("");
		lblEspaco.relocate(0, 20);
		panFiltro.getChildren().addAll(new Label("Filtrar"), lblPor, lblEspaco);
		
		TextField txtFiltro = new TextField();
		txtFiltro.prefWidth(400);
		txtFiltro.relocate(50, 0);
		panFiltro.getChildren().add(txtFiltro);
		
		ComboBox<String> comboBoxFiltro = new ComboBox<String>();
		comboBoxFiltro.getItems().clear();
		comboBoxFiltro.getItems().addAll("nome", "nascimento", "pais", "biografia");
		comboBoxFiltro.setPrefWidth(400);
		comboBoxFiltro.relocate(290, 0);
		panFiltro.getChildren().add(comboBoxFiltro);
		
		Button btnListar = new Button("Listar");
		btnListar.setOnAction((e) -> {
			try {
				control.listarPorColuna();
				tableAutor.setItems(control.getListaPorColuna());
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		
		btnListar.relocate(710, 0);
		panFiltro.getChildren().add(btnListar);
		
		pan.setTop(panAutor);
		pan.setCenter(panAutorBut);
		
		panTabela.setTop(panFiltro);
		this.criarTabela();
		
		panTabela.setCenter(tableAutor);
		
		panTotal.setTop(pan);
		panTotal.setBottom(panTabela);
		
		Bindings.bindBidirectional(txtCodigo.textProperty(), control.cod, new NumberStringConverter());
		Bindings.bindBidirectional(txtNome.textProperty(), control.nome);
		Bindings.bindBidirectional(txtNascimento.textProperty(), control.nascimento, new NumberStringConverter());
		Bindings.bindBidirectional(txtPais.textProperty(), control.pais);
		Bindings.bindBidirectional(txtBiografia.textProperty(), control.biografia);

		Bindings.bindBidirectional(txtFiltro.textProperty(), control.filtro);
		comboBoxFiltro.valueProperty().bindBidirectional(control.coluna);
		
		return panTotal;
	}

	private void criarTabela(){
		tableAutor.setMaxSize(2000, 2000);
		
		TableColumn<Autor, String> col1 = new TableColumn<Autor, String>("Nome");
		col1.setCellValueFactory(
				new PropertyValueFactory<Autor,String>("nome")
		);
		
		TableColumn<Autor, Integer> col2 = new TableColumn<Autor, Integer>("Nascimento");
		col2.setCellValueFactory(
				new PropertyValueFactory<Autor, Integer>("nascimento")
		);
		
		TableColumn<Autor,String> col3 = new TableColumn<Autor, String>("Pais");
		col3.setCellValueFactory(
				new PropertyValueFactory<Autor,String>("pais")
		);
		TableColumn<Autor, String> col4 = new TableColumn<Autor, String>("Biografia");
		col4.setCellValueFactory(
				new PropertyValueFactory<Autor, String>("biografia")
		);
		
		TableColumn<Autor, String> col6 = new TableColumn<>("");
        col6.setCellFactory( (tbcol) -> {
            Button btnRemover = new Button("Deletar");
            TableCell<Autor, String> tcell = new TableCell<Autor, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    if (empty) {
                        setGraphic(null);
                        setText(null);
                    } else {
                        btnRemover.setOnAction( (e) -> {
                            try {
                            	Autor a = getTableView().getItems().get(getIndex());
								control.deletar(a);
								control.limpar();
								btnInserir.setDisable(false);
								btnAlterar.setDisable(true);
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
            }
        );
		
		tableAutor.getSelectionModel().selectedItemProperty().addListener((obs, old, novo) -> {
			control.fromEntity(novo);
			btnInserir.setDisable(true);
			btnAlterar.setDisable(false);
		});
		
		tableAutor.getColumns().addAll(col1, col2, col3, col4, col6);
		try {
			tableAutor.setItems(control.getLista());
			btnInserir.setDisable(false);
			btnAlterar.setDisable(true);
		} catch (SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
				
	}
}
