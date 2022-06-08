package controller;

import java.sql.SQLException;

import entity.Aluno;
import entity.Edicao;
import entity.Emprestimo;
import entity.Volume;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.AlunoDao;
import persistence.EmprestimoDao;
import persistence.IAlunoDao;
import persistence.IEmprestimoDao;
import persistence.IVolumeDao;
import persistence.VolumeDao;

public class ControllerEmprestimo {
	public IntegerProperty pesquisar = new SimpleIntegerProperty();
	
	public IntegerProperty id = new SimpleIntegerProperty(0);
	public StringProperty dataEmprestimo = new SimpleStringProperty("");
	public StringProperty dataDevolucao = new SimpleStringProperty("");
	public StringProperty status = new SimpleStringProperty("");
	public ObjectProperty<Volume> volume = new SimpleObjectProperty<Volume>();
	public ObjectProperty<Aluno> aluno = new SimpleObjectProperty<Aluno>();
	public StringProperty ra = new SimpleStringProperty("");
	public StringProperty isbn = new SimpleStringProperty();
	public IntegerProperty numVolume = new SimpleIntegerProperty(0);
	
	
	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();
	
	public ObjectProperty<Emprestimo> ep = new SimpleObjectProperty<Emprestimo>();

	private IEmprestimoDao eDao;
	private IAlunoDao aDao;
	private IVolumeDao dDao;

	private ObservableList<Emprestimo> emprestimos = FXCollections.observableArrayList();
	private ObservableList<Emprestimo> emprestimosFiltrados = FXCollections.observableArrayList();

	public ControllerEmprestimo() {
		try {
			eDao = new EmprestimoDao();
			aDao = new AlunoDao();
			dDao = new VolumeDao();
			eDao.atrasados();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException, ClassNotFoundException {
		Emprestimo e = toEntity();
		eDao.inserir(e.getAluno(), e.getVolume(), e);
		listar();
		limpar();
	}
	
	public void devolver() throws SQLException, ClassNotFoundException {
		eDao.devolvido(pesquisar());
		listar();
		limpar();
	}
	
	public void renovar() throws SQLException, ClassNotFoundException {
		eDao.renovar(pesquisar());
		listar();
		limpar();
	}
	
	public void cancelar() throws SQLException, ClassNotFoundException {
		eDao.renovar(pesquisar());
		listar();
		limpar();
	}

	public Emprestimo pesquisar() throws SQLException {
		Emprestimo emp = new Emprestimo();
		emp.setId(pesquisar.get());
		eDao.pesquisar(emp);
		return emp;
	}

	public void deletar(Emprestimo e) throws SQLException, ClassNotFoundException {
		e = toEntity();
		eDao.excluir(e);
		listar();
		limpar();
	}

	public void listar() throws SQLException {
		emprestimos.clear();
		emprestimos.addAll(eDao.listar());
	}

	public void limpar() throws SQLException {
		Emprestimo e = new Emprestimo();
		this.fromEntity(e);
		listar();
		coluna.setValue("Selecione");
		filtro.setValue("");
	}

	public void listarPorColuna() throws SQLException {
		emprestimosFiltrados.clear();
		emprestimosFiltrados.addAll(eDao.listarPorColuna(filtro.get(), coluna.get()));
		filtro.setValue("");
		coluna.setValue("Selecione");
	}

	public ObservableList<Emprestimo> getLista() throws SQLException {
		listar();
		return emprestimos;
	}

	public ObservableList<Emprestimo> getListaPorColuna() throws SQLException {
		return emprestimosFiltrados;
	}

	public Emprestimo toEntity() throws SQLException, ClassNotFoundException {
		Emprestimo e = new Emprestimo();
		Volume v = new Volume();
		Aluno a = new Aluno();
		Edicao e1 = new Edicao();
		a.setRa(ra.get());
		e.setId(id.get());
		e.setDataEmprestimo(converterDataEUA(dataEmprestimo.get()));
		e.setDataDevolucao(converterDataEUA(dataDevolucao.get()));
		e.setAluno(aDao.pesquisar(a));
		v.setNumero(numVolume.get());
		e1.setIsbn(isbn.get());
		v.setEdicao(e1);
		e.setVolume(dDao.pesquisar(v));
		a.setStatus(status.get());
		return e;
	}

	public void fromEntity(Emprestimo e) {
		if (e != null) {
			id.set(e.getId());
			dataEmprestimo.set(converterDataBR(e.getDataEmprestimo()));
			dataDevolucao.set(converterDataBR(e.getDataDevolucao()));
			volume.set(e.getVolume());
			aluno.set(e.getAluno());
			status.set(e.getStatus());
		}
	}
	
	public String converterDataBR (String data){
		if(data != null){
			String[] dataBR = data.split("-");
			return dataBR[2]+"/"+dataBR[1]+"/"+dataBR[0];
		}
		return null;
	}
	
	public String converterDataEUA (String data){
		if(data != null){
			String[] dataEUA = data.split("/");
			return dataEUA[2]+"-"+dataEUA[1]+"-"+dataEUA[0];
		}
		return null;
	}
}
