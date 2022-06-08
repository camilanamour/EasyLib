CREATE DATABASE easylibDB
GO
USE easylibDB

CREATE TABLE tb_autor(
id_autor INT IDENTITY(1,1),
nome VARCHAR(200),
ano INT,
pais VARCHAR(100),
biografia VARCHAR(300)
PRIMARY KEY (id_autor),
CONSTRAINT CHK_ano CHECK(ano <= YEAR(GETDATE()))
)
CREATE TABLE tb_usuario(
id_usuario INT IDENTITY(1,1),
nome VARCHAR(100),
senha VARCHAR(100),
email VARCHAR(100)
PRIMARY KEY(id_usuario)
)
CREATE TABLE tb_categoria(
id_categoria INT IDENTITY(1,1),
nome VARCHAR(100)
PRIMARY KEY(id_categoria)
)
CREATE TABLE tb_livro(
id_livro INT IDENTITY(1,1),
titulo VARCHAR(200),
ano_publicacao INT,
classificacao INT,
categoria INT
PRIMARY KEY(id_livro),
FOREIGN KEY(categoria) REFERENCES tb_categoria(id_categoria),
CONSTRAINT CHK_classificacao CHECK(classificacao >= 0 AND classificacao <= 18)
)
CREATE TABLE livro_autor(
id_livro INT,
id_autor INT,
PRIMARY KEY(id_livro, id_autor),
FOREIGN KEY (id_livro) REFERENCES tb_livro(id_livro),
FOREIGN KEY (id_autor) REFERENCES tb_autor(id_autor)
)
CREATE TABLE tb_edicao(
isbn VARCHAR(13),
num_edicao INT,
qtn_estoque INT,
ano_edicao INT,
qtd_paginas INT,
formato VARCHAR(50)
PRIMARY KEY(isbn),
CONSTRAINT CHK_isbn CHECK(LEN(isbn) = 13),
CONSTRAINT CHK_estoque CHECK(qtn_estoque >= 0),
CONSTRAINT CHK_ano_edicao CHECK(ano_edicao >= 0),
CONSTRAINT CHK_paginas CHECK(qtd_paginas > 0)
)
CREATE TABLE tb_editora(
id_editora INT IDENTITY(1,1),
nome VARCHAR(100),
site VARCHAR(100)
PRIMARY KEY (id_editora)
)
CREATE TABLE tb_volume(
livro INT,
edicao VARCHAR(13),
editora INT,
numero INT,
status VARCHAR(100)
PRIMARY KEY(livro, edicao, editora, numero)
FOREIGN KEY (livro) REFERENCES tb_livro(id_livro),
FOREIGN KEY (edicao) REFERENCES tb_edicao(isbn),
FOREIGN KEY (editora) REFERENCES tb_editora(id_editora),
)
CREATE TABLE tb_aluno(
ra VARCHAR(13),
nome VARCHAR(200),
cpf BIGINT,
rg BIGINT,
turma VARCHAR(50),
periodo VARCHAR(50),
data_nascimento DATE,
telefone VARCHAR(10),
celular VARCHAR(11),
status VARCHAR(100)
PRIMARY KEY(ra),
CONSTRAINT CHK_cpf CHECK(LEN(cpf) = 11),
CONSTRAINT CHK_rg CHECK(LEN(rg) = 9),
CONSTRAINT CHK_nascimento CHECK(data_nascimento < GETDATE())
)
CREATE TABLE tb_emprestimo(
id_emprestimo INT IDENTITY(0001,1),
data_emprestimo DATE,
data_devolucao DATE,
livro INT,
edicao VARCHAR(13),
editora INT,
numero INT,
aluno VARCHAR(13),
status VARCHAR(100)
PRIMARY KEY(id_emprestimo),
FOREIGN KEY(livro, edicao, editora, numero) REFERENCES tb_volume(livro, edicao, editora, numero),
FOREIGN KEY(aluno) REFERENCES tb_aluno(ra)
)
CREATE TABLE tb_regras_emprestimos(
id_regras INT IDENTITY(1,1),
dias INT,
nome_regra VARCHAR(100)
PRIMARY KEY(id_regras)
)
INSERT INTO tb_regras_emprestimos VAlUES
(7, 'Dias de emprestimo'),
(7, 'Dias de multa')

CREATE TRIGGER t_regras_emprestimos ON tb_regras_emprestimos
AFTER INSERT, DELETE
AS BEGIN
ROLLBACK TRANSACTION
RAISERROR('Não é permitido inserir ou deletar novas regras de empréstimo', 16, 1)
END

SELECT * FROM tb_aluno
DROP TABLE tb_aluno
DROP TABLE tb_emprestimo

DROP TABLE livro_autor
DROP TABLE tb_autor
DROP TABLE tb_edicao
DROP TABLE tb_volume

SELECT * FROM livro_autor
SELECT * FROM tb_autor
SELECT * FROM tb_editora
SELECT * FROM tb_livro
SELECT * FROM tb_edicao
SELECT * FROM tb_volume
SELECT * FROM tb_usuario

INSERT INTO tb_edicao VALUES
('1112223334445', 5, 1, 2012, 50, 'Brochura') 

INSERT INTO tb_volume VALUES
(6, '1112223334445', 2, 1,'Disponivel') 

INSERT INTO tb_volume VALUES
(6, '1112223334445', 2, 2,'Disponivel') 

SELECT * FROM tb_categoria
delete tb_categoria

SELECT * FROM tb_editora
delete tb_editora

SELECT * FROM tb_aluno WHERE nome LIKE ? AND periodo = 'tarde' ORDER BY data_nascimento ASC

SELECT * FROM tb_livro AS l INNER JOIN tb_categoria AS c ON l.categoria = c.id_categoria ORDER BY titulo ASC
delete tb_livro Where id_livro = 2
SELECT * 
FROM

SELECT *, c.nome AS nome_categoria, l.id_livro AS livro FROM tb_livro AS l 
INNER JOIN tb_categoria AS c ON l.categoria = c.id_categoria 
INNER JOIN livro_autor AS la ON l.id_livro = la.id_livro 
INNER JOIN tb_autor AS a ON la.id_autor = a.id_autor WHERE a.nome = 
ORDER BY a.nome ASC

SELECT e.isbn, l.titulo, e.num_edicao, d.nome, e.ano_edicao, e.qtd_paginas, e.formato, e.qtn_estoque
FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v
WHERE e.isbn = v.edicao 
	  AND d.id_editora = v.editora 
	  AND l.id_livro = v.livro 
      AND e.isbn = '1112223334445' 
	  AND v.numero = 1

SELECT e.isbn, l.titulo, e.num_edicao, d.nome, v.numero, v.status
FROM tb_edicao e, tb_editora d, tb_livro l, tb_volume v
WHERE e.isbn = v.edicao 
	  AND d.id_editora = v.editora 
	  AND l.id_livro = v.livro 
      AND e.isbn = '1112223334445' 

SELECT * FROM tb_usuario WHERE nome = 'Camila' AND senha = '1234'

