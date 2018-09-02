SELECT c.*, ci.*, ci.nome as nome_cidade, e.cod_estabelecimento, e.descricao  FROM cliente c 
	INNER JOIN cidade ci ON (c.cod_cidade = ci.cod_cidade)
    LEFT JOIN estabelecimento e ON (c.cod_estabelec = e.cod_estabelecimento)