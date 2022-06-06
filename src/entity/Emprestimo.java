package entity;

public class Emprestimo {
	
	int id;
	String dataEmprestimo;
	String dataDevolucao;
	String status;
	Volume volume;
	Aluno aluno;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getDataEmprestimo() {
		return dataEmprestimo;
	}
	public void setDataEmprestimo(String dataEmprestimo) {
		this.dataEmprestimo = dataEmprestimo;
	}
	public String getDataDevolucao() {
		return dataDevolucao;
	}
	public void setDataDevolucao(String dataDevolucao) {
		this.dataDevolucao = dataDevolucao;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Volume getVolume() {
		return volume;
	}
	public void setVolume(Volume volume) {
		this.volume = volume;
	}
	public Aluno getAluno() {
		return aluno;
	}
	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}
	@Override
	public String toString() {
		return "Emprestimo [id=" + id + ", dataEmprestimo=" + dataEmprestimo + ", dataDevolucao=" + dataDevolucao
				+ ", status=" + status + ", volume=" + volume + ", aluno=" + aluno + "]";
	}

}
