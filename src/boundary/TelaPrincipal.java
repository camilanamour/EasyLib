package boundary;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class TelaPrincipal extends Application{

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane bp = new BorderPane();
		Scene scn = new Scene(bp, 800, 600);
		
		MenuBar menuBar = new MenuBar();
		Menu mArquivo = new Menu("Arquivo");
		Menu mCadastro = new Menu("Cadastrar");
		Menu mEmprestimo = new Menu("Empréstimo");
		Menu mRelatorio = new Menu("Relatórios");
		Menu mConfiguracao = new Menu("Configurações");
		Menu mAjuda = new Menu("EasyLib");
		
		menuBar.getMenus().addAll(mArquivo, mCadastro, mEmprestimo, mRelatorio, mConfiguracao, mAjuda);
		
		MenuItem mItemSair = new MenuItem("Sair");
		mItemSair.setOnAction((e) ->{
		TelaAutenticar telaId = new TelaAutenticar();
		try {
			telaId.start(new Stage());
			stage.close();
		} catch (Exception e1) {
			new Alert(Alert.AlertType.ERROR, 
					"Erro: " + e1.getMessage()).showAndWait();
		}
		});
		
		MenuItem mItemAluno = new MenuItem("Aluno");
		mItemAluno.setOnAction((e) ->{
			TelaAluno t = new TelaAluno();
			bp.setCenter(t.render());
			stage.setTitle("Cadastro alunos");
		});
		
		MenuItem mItemAutor = new MenuItem("Autor");
		mItemAutor.setOnAction((e) ->{
			TelaAutor t = new TelaAutor();
			bp.setCenter(t.render());
			stage.setTitle("Cadastro autores");
		});
		
		MenuItem mItemCatEd = new MenuItem("Categoria | Editora");
		mItemCatEd.setOnAction((e) ->{
			TelaCategoriaEditora t = new TelaCategoriaEditora();
			bp.setCenter(t.render());
			stage.setTitle("Cadastro categoria e editora");
		});
		
		MenuItem mItemLivro = new MenuItem("Livro");
		mItemLivro.setOnAction((e) ->{
			TelaLivro t = new TelaLivro();
			bp.setCenter(t.render());
			stage.setTitle("Cadastro Livros");
		});
		
		MenuItem mItemEdicao = new MenuItem("Edição");
		mItemEdicao.setOnAction((e) ->{
			TelaEdicao t = new TelaEdicao();
			bp.setCenter(t.render());
			stage.setTitle("Cadastro Edições");
		});
		
		MenuItem mItemGerEmp = new MenuItem("Gerenciar");
		mItemGerEmp.setOnAction((e) ->{
			TelaEmprestimo t = new TelaEmprestimo();
			bp.setCenter(t.render());
			stage.setTitle("Gerenciar empréstimos");
		});
		
		MenuItem mItemVolume = new MenuItem("Verificar Volumes");
		mItemVolume.setOnAction((e) ->{
			TelaVolumes t = new TelaVolumes();
			bp.setCenter(t.render());
			stage.setTitle("Verificar volumes");
		});
		
		MenuItem mItemRelatorio = new MenuItem("Gerar Relatórios");
		mItemRelatorio.setOnAction((e) ->{
			TelaRelatorio t = new TelaRelatorio();
			bp.setCenter(t.render());
			stage.setTitle("Gerar relatórios");
		});
		
		MenuItem mItemRegra = new MenuItem("Regras de Negócios");
		mItemRegra.setOnAction((e) ->{
			TelaRegraNegocio t = new TelaRegraNegocio();
			bp.setCenter(t.render());
			stage.setTitle("Regras de Negócio");
		});
		
		MenuItem mItemCredito = new MenuItem("Sobre o aplicativo");
		mItemCredito.setOnAction((e) ->{
			TelaApp t = new TelaApp();
			bp.setCenter(t.render());
			stage.setTitle("Sobre o aplicativo");
		});
		
		
		mArquivo.getItems().addAll(mItemSair);
		mCadastro.getItems().addAll(mItemAluno, mItemAutor, mItemCatEd,mItemLivro, mItemEdicao);
	    mEmprestimo.getItems().addAll(mItemGerEmp, mItemVolume);
	    mRelatorio.getItems().addAll(mItemRelatorio);
	    mConfiguracao.getItems().addAll(mItemRegra);
		mAjuda.getItems().addAll(mItemCredito);

	    bp.setTop(menuBar);
		
		stage.setScene(scn);
		stage.show();
	}
}
