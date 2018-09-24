
--Select tela home clientes
SELECT c.*, ci.*, ci.nome as nome_cidade, e.cod_estabelecimento,
				  e.descricao as desc_estabelecimento, r.nome as nome_usuario_cr, s.nome as nome_usuario_cs,
				  uc.perfil as perfil, tel.numero as telefone, tel.ddd as cod_area
				  FROM cliente c LEFT JOIN cidade ci ON (c.cod_cidade = ci.cod_cidade)
				  INNER JOIN usuario uc ON (c.cod_usuario_criacao = uc.cpf)
				  LEFT JOIN telefone tel ON (c.cpf = tel.cod_cliente)
				  LEFT JOIN revendedor r ON (c.cod_usuario_criacao = r.cpf)
				  LEFT JOIN socio s ON (c.cod_usuario_criacao = s.cpf)
				  LEFT JOIN estabelecimento e ON (c.cod_estabelec = e.cod_estabelecimento)
				  WHERE tel.principal = 1;