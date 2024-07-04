Framework:

Pontos fixos: 
	* Cadastro e Login de usuários 
	* Forma de pagamento
	* Anunciante cadastra os anúncios
	* Gerenciamento do estoque

1. Marketplace: 
	* É possível cadastrar novos Anunciantes;
	* Anunciante pode realizar pedidos;
	* Serviço de email informando de chegada de item em estoque (notificação) e pré-compra [Template Method]
	* Desconto de 5% a partir de R$200,00 e 10% a partir de R$400,00 em produtos do mesmo anunciante. [Strategy]
	* Valor do pagamento do pedido será distribuído para os anunciantes com base no valor dos produtos vendidos por cada um [Strategy];

2. Ecommerce 
	* Não é possível cadastrar novos Anunciantes;
	* Anunciante não pode fazer pedido (o anunciante será a própria loja);
	* Serviço de email informando de chegada de item em estoque [Template Method]
	* Frete Grátis a partir de valor especificado por região [Strategy]:
		+ Região Sul: R$400,00
		+ Região Norte: R$300,00
		+ Região Sudeste: R$350,00
		+ Região Nordeste: R$200,00
		+ Região Centro-Oeste: R$300,00
	* Forma de pagamento comum, todo valor do pagamento do pedido será destinado para a loja [Strategy]

3. Serviço de Assinatura
	* Não é possível cadastrar novos Anunciantes 
	* Anunciante não pode fazer pedido (anunciante será admin da loja)
	* Serviço de email informando sobre a cobrança recorrente [Template Method
	* Recompra sugerindo ou removendo produtos em casos de produtos faltantes (pedido diferente) [Template Method]
	* Desconto de 3% por trimestre de compra recorrente [Strategy]:
		+ O pagamento do valor mensal de cada uma da compras recorrente terá que ser feito de forma única;
		+ A cada três meses acumalados, recebe desconto de 3%.
		+ Exemplo 1: Usuário solicita a compra recorrente para 3 meses e paga o valor das 3 compras de uma vez só, recebe 3% de desconto no valor total (valor total = valor das três compras somadas);
		+ Exemplo 2: Usuário solicita a compra recorrente para 9 meses e paga o valor das 9 compras de uma vez só, recebe 9% de desconto no valor total (valor total = valor das nove compras somadas);
		DESCONTO MÁXIMO = 15%;		

	* Forma de pagamento comum igual a do Ecommerce. Ou somatório da quantidade de compras recorrentes que serão feitas [Strategy]
		+ Exemplo: Usuário solicita compra recorrente com um valor de R$200,00 por 4 meses. Ele pode optar por pagar parcelado, cada mês pagar os R$200,00 ou então pagar R$776,00 no primeiro mês (R$800 - (3% de 800) valor considerando o desconto);

