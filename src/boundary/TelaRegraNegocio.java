package boundary;


import java.sql.SQLException;

import controller.ControllerPrazos;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TelaRegraNegocio {
	
	private ControllerPrazos control;
	
	public TelaRegraNegocio() {
		control = new ControllerPrazos();
	}
	
	public GridPane render() {
		GridPane panRegra = new GridPane();
		BorderPane.setMargin(panRegra, new Insets(10, 10, 10, 10));
		panRegra.setHgap(15);
		panRegra.setVgap(20);
		
		panRegra.add(new Label(" "), 0, 0);
		panRegra.add(new Label("Definir Regras: "), 0, 1);
		panRegra.add(new Label("O prazo de empréstimo de um livro é de: "), 0, 2);
		panRegra.add(new Label("O prazo de multa por perda é de: "), 0, 3);
		panRegra.add(new Label("dias "), 2, 2);
		panRegra.add(new Label("dias "), 2, 3);
		
		TextField txtEmprestimo = new TextField();
		txtEmprestimo.setPrefSize(30, 15);
		panRegra.add(txtEmprestimo, 1, 2);
		TextField txtMulta = new TextField();
		txtMulta.setPrefSize(30, 15);
		panRegra.add(txtMulta, 1, 3);
		
		Button btnSalvar = new Button("Salvar Alterações");
		btnSalvar.setOnAction((e) -> {
			try {
				control.inserir();
				new Alert(Alert.AlertType.INFORMATION, "Atualizado com sucesso!").showAndWait();
			} catch (SQLException e1) {
				new Alert(Alert.AlertType.ERROR, e1.getMessage()).showAndWait();
			}
		});
		panRegra.add(btnSalvar, 0, 4);
		
		return panRegra;
	}

}
