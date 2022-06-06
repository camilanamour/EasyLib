package boundary;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

public class TelaApp {
	public GridPane render() {
		
		GridPane panApp = new GridPane();
		BorderPane.setMargin(panApp, new Insets(10, 10, 10, 10));
		panApp.setHgap(5);
		
		panApp.add(new Label(""), 0, 0);
		panApp.add(new Label("EasyLib: "), 0, 1);
		panApp.add(new Label("O aplicativo tem a finalidade para o controle da gest�o para empr�stimos e devolu��es de livros."), 0, 2);
		panApp.add(new Label("Vers�o: 0.1 "), 0, 3);
		panApp.add(new Label(""), 0, 4);
		panApp.add(new Label("Camila Cec�lia Vlasich de Almeida Namour - 1110482012008 "), 0, 5);
		panApp.add(new Label("Gustavo Gon�alves Cavichioli - 1110482012009 "), 0, 6);
		
		return panApp;
	}

}
