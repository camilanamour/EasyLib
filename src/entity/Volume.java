package entity;

public class Volume {

	private Edicao edicao;
	private int numero;
	private String status;
	
	public Edicao getEdicao() {
		return edicao;
	}
	public void setEdicao(Edicao edicao) {
		this.edicao = edicao;
	}
	public int getNumero() {
		return numero;
	}
	public void setNumero(int numero) {
		this.numero = numero;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	public String getISBN(){
		return edicao.getIsbn();
	}
	
	@Override
	public String toString() {
		return "Volume [ edicao=" + edicao + ", numero=" + numero
				+ ", status=" + status + "]";
	}

}
