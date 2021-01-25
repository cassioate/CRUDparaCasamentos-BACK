CREATE TABLE Casamento(
	id SERIAL,
	data_Casamento DATE NOT NULL,
	data_Fechamento_Do_Casamento DATE NOT NULL,
	local_Cerimonia VARCHAR(100),
	local_Recepcao VARCHAR(100),
	valor_Pacote NUMERIC(10,2) NOT NULL,
	valor_Pago NUMERIC(10,2) NOT NULL,
	valor_Receber NUMERIC(10,2) NOT NULL,
	forma_Pagamento VARCHAR(100) NOT NULL,
	pre_Casamento BOOLEAN NOT NULL,
	fotolivro_Pre_Casamento BOOLEAN NOT NULL,
	making_Of_Noiva BOOLEAN NOT NULL,
	making_Of_Noivo BOOLEAN NOT NULL,
	fotolivro BOOLEAN NOT NULL,
	kit_Sogra BOOLEAN NOT NULL,
	qtd_Fotos_Casamento INTEGER NOT NULL,
	qtd_Fotos_Pre_Casamento INTEGER,
	caixa BOOLEAN NOT NULL,
	pen_Drive BOOLEAN NOT NULL,
	tamanho_Fotolivros VARCHAR(100) NOT NULL,
	observacoes VARCHAR(100),
	pessoa_id INTEGER NOT NULL,
	constraint pk_casamento primary key (id),
	constraint fk1_casamento foreign key (pessoa_id) references pessoa (id)
);