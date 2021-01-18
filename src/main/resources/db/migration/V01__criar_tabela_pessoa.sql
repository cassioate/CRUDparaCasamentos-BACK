CREATE TABLE Pessoa (
	id SERIAL,
	noiva VARCHAR(50) NOT NULL,
	noivo VARCHAR(50),
	rg VARCHAR(50),
	cpf VARCHAR(50) NOT NULL,
	telefone VARCHAR(50) NOT NULL,
	logradouro Varchar(200),
	numero INTEGER,
	complemento varchar(50),
	bairro varchar(50),
	cep varchar(20),
	cidade varchar(50),
	estado varchar(20),
	constraint pk_pessoa primary key (id)
);