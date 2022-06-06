package entity;

public class Autor {
	
	private int codigo;
	private String nome;
	private int dataNascimento;
	private String pais;
	private String biografia;
	public int getCodigo() {
		return codigo;
	}
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public int getNascimento() {
		return dataNascimento;
	}
	public void setNascimento(int nascimento) {
		this.dataNascimento = nascimento;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getBiografia() {
		return biografia;
	}
	public void setBiografia(String biografia) {
		this.biografia = biografia;
	}
	@Override
	public String toString() {
		return nome;
	}

}
