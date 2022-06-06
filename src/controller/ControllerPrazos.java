package controller;

import java.sql.SQLException;

import entity.Regra;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Alert;
import persistence.PrazosDao;

public class ControllerPrazos {
	
	public IntegerProperty dev = new SimpleIntegerProperty();
	public IntegerProperty blo = new SimpleIntegerProperty();
	
	private PrazosDao pDao; 
	
	public ControllerPrazos() {
		try {
			pDao = new PrazosDao();
			this.get();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException {
		pDao.alterar(dev.get(), blo.get());
		this.get();
	}

	private void get() throws SQLException{
		Regra r = new Regra();
		r = pDao.get();
		dev.set(r.getDevolver());
		blo.set(r.getBloquear());
	}
}
