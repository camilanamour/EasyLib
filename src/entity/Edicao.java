package entity;

public class Edicao {
	
	private String isbn;
	private int qtdEstoque;
	private int anoEdicao;
	private int numEdicao;
	private int qtdPaginas;
	private String formato;
	private Livro livro;
	private Editora editora;
	
	public int getNumEdicao() {
		return numEdicao;
	}
	public void setNumEdicao(int numEdicao) {
		this.numEdicao = numEdicao;
	}
	public Livro getLivro() {
		return livro;
	}
	public void setLivro(Livro livro) {
		this.livro = livro;
	}
	public Editora getEditora() {
		return editora;
	}
	public void setEditora(Editora editora) {
		this.editora = editora;
	}
	public String getIsbn() {
		return isbn;
	}
	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}
	public int getQtdEstoque() {
		return qtdEstoque;
	}
	public void setQtdEstoque(int qtdEstoque) {
		this.qtdEstoque = qtdEstoque;
	}
	public int getAnoEdicao() {
		return anoEdicao;
	}
	public void setAnoEdicao(int anoEdicao) {
		this.anoEdicao = anoEdicao;
	}
	public int getQtdPaginas() {
		return qtdPaginas;
	}
	public void setQtdPaginas(int qtdPaginas) {
		this.qtdPaginas = qtdPaginas;
	}
	public String getFormato() {
		return formato;
	}
	public void setFormato(String formato) {
		this.formato = formato;
	}
	@Override
	public String toString() {
		return numEdicao + "ª";
	}
}
