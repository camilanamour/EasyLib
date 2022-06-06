package controller;

import java.sql.SQLException;

import entity.Aluno;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import persistence.AlunoDao;
import persistence.IAlunoDao;

public class ControllerAluno {
	
	public StringProperty pesquisarRa = new SimpleStringProperty();
	
	public StringProperty ra = new SimpleStringProperty();
	public StringProperty nome = new SimpleStringProperty("");
	public StringProperty rg = new SimpleStringProperty("");
	public StringProperty cpf = new SimpleStringProperty("");
	public StringProperty turma = new SimpleStringProperty("");
	public ObjectProperty<String> periodo = new SimpleObjectProperty<>("Selecione");
	public StringProperty nascimento = new SimpleStringProperty("");
	public StringProperty telefone = new SimpleStringProperty("");
	public StringProperty celular = new SimpleStringProperty("");
	public ObjectProperty<String> situacao = new SimpleObjectProperty<>("Selecione");
	
	public ObjectProperty<String> coluna = new SimpleObjectProperty<>("Selecione");
	public StringProperty filtro = new SimpleStringProperty();
	public ObjectProperty<String> periodoLista = new SimpleObjectProperty<>("Selecione");
	
	private IAlunoDao aDao; 
	
	private ObservableList<Aluno> alunos = FXCollections.observableArrayList();
	private ObservableList<Aluno> alunosFiltrados = FXCollections.observableArrayList();
	
	public ControllerAluno() {
		try {
			aDao = new AlunoDao();
		} catch (ClassNotFoundException | SQLException e) {
			new Alert(Alert.AlertType.ERROR, e.getMessage()).showAndWait();
		}
	}

	public void inserir() throws SQLException {
		Aluno a = toEntity();
		aDao.inserir(a);
		listar();
		limpar();
	}

	public void pesquisar() throws SQLException {
		Aluno a1 = new Aluno();
		a1.setRa(pesquisarRa.get());
		Aluno a = aDao.pesquisar(a1);
		this.fromEntity(a);
		pesquisarRa.setValue("");
	}

	public void alterar() throws SQLException {
		Aluno a = toEntity();
		aDao.alterar(a);
		listar();
		limpar();
	}

	public void deletar(Aluno a) throws SQLException {
		a = toEntity();
		aDao.excluir(a);
		listar();
		limpar();
	}

	public void listar() throws SQLException {
		alunos.clear();
		alunos.addAll(aDao.listar());
	}
	
	
	public void limpar() throws SQLException{
		Aluno a = new Aluno();
		this.fromEntity(a);
		pesquisarRa.setValue("");
		periodo.setValue("Selecione");
		situacao.set("Selecione");
		coluna.set("Selecione");
		periodoLista.set("Selecione");
		filtro.set("");
		listar();
	}
	
	public void listarPorColuna() throws SQLException{
		alunosFiltrados.clear();
		alunosFiltrados.addAll(aDao.listarPorColuna(filtro.get(), coluna.get(), periodoLista.get()));
		filtro.set("");
		coluna.set("Selecione");
		periodoLista.set("Selecione");
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
	
	public ObservableList<Aluno> getLista() throws SQLException {
		this.listar();
		return alunos;
	}
	
	public ObservableList<Aluno> getListaPorColuna() throws SQLException {
		return alunosFiltrados;
	}
	
	public Aluno toEntity() {
        Aluno a = new Aluno();
        if(ra.get() != ""){
        	a.setRa(ra.get());
        }
        a.setNome(nome.get());
        a.setRg(rg.get());
        a.setCpf(cpf.get());
        a.setDataNascimento(this.converterDataEUA(nascimento.get()));
        a.setTurma(turma.get());
        a.setPeriodo(periodo.get());
        a.setTelefone(telefone.get());
        a.setCelular(celular.get());
        a.setStatus(situacao.get());
        return a;
    }

    public void fromEntity(Aluno a){
    	if(a != null){
    		if(a.getRa() != ""){
    			ra.set(a.getRa());
    		}
        	nome.set(a.getNome());
        	rg.set(a.getRg());
        	cpf.set(a.getCpf());
        	turma.set(a.getTurma());
        	periodo.set(a.getPeriodo());
        	nascimento.set(this.converterDataBR(a.getDataNascimento()));
        	telefone.set(a.getTelefone());
        	celular.set(a.getCelular());
        	situacao.set(a.getStatus());
    	}
    }
}
