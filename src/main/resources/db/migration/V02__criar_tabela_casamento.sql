CREATE TABLE Casamento (
	id SERIAL,
	dataCasamento DATE NOT NULL,
	dataFechamentoDoCasamento DATE NOT NULL,
	localCerimonia VARCHAR(100),
	localRecepcao VARCHAR(100),
	valorPacote NUMERIC(10,2) NOT NULL,
	valorPago NUMERIC(10,2) NOT NULL,
	valorReceber NUMERIC(10,2) NOT NULL,
	formaPagamento VARCHAR(100) NOT NULL,
	preCasamento BOOLEAN NOT NULL,
	makingOfNoiva BOOLEAN NOT NULL,
	makingOfNoivo BOOLEAN NOT NULL,
	fotolivro BOOLEAN NOT NULL,
	qtdFotosCasamento INTEGER NOT NULL,
	qtdFotosPreCasamento INTEGER,
	caixa BOOLEAN NOT NULL,
	penDrive BOOLEAN NOT NULL,
	pessoa_id INTEGER NOT NULL,
	
	constraint pk_casamento primary key (id),
	constraint fk1_casamento foreign key (pessoa_id) references pessoa (id)
);