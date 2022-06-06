package controller;

import java.sql.SQLException;

import entity.Emprestimo;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.EmprestimoDao;
import persistence.IEmprestimoDao;

public class ControllerRelatorio {
	public StringProperty ordem = new SimpleStringProperty("");
	public StringProperty periodo = new SimpleStringProperty("");
	public StringProperty filtro = new SimpleStringProperty();
	
	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");

	private IEmprestimoDao eDao;
	
	private ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList();

	public ControllerRelatorio() {
		try {
			eDao = new EmprestimoDao();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void listarPorColuna() throws SQLException {
		emprestimos.clear();
		emprestimos.addAll(eDao.listarRelatorio(periodo.get(), ordem.get(), filtro.get()));
	}
	
	public ObservableList<Emprestimo> getLista() throws SQLException {
		listarPorColuna();
		return emprestimos;
	}
}
