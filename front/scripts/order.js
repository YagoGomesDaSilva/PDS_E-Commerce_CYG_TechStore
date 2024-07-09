document.addEventListener("DOMContentLoaded", loadOrder());

function loadOrder() {
    const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;

    fetch("http://localhost:8080/pedido/"+ idUsuario, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            const itensByAnunciante = data.itensAnunciante;
            const valorTotal = data.valorTotal;
            const valorTotalComFrete = data.valorTotalComFrete;
            const valorTotalComDesconto = data.valorTotalComDesconto;
            const valorFrete = data.valorFrete;

            const orderDiv = document.getElementById("container-order");

            itensByAnunciante.forEach(element => {
                const nomeAnunciante = element.nomeAnunciante;
                const itens = element.itens;
                const desconto = element.desconto;

                const itensByAnuncianteDiv = document.createElement('div');
                itensByAnuncianteDiv.classList.add('itens-anunciante');

                const nomeAnuncianteDiv = document.createElement('h2');
                nomeAnuncianteDiv.textContent = nomeAnunciante;

                itensByAnuncianteDiv.appendChild(nomeAnuncianteDiv);

                itens.forEach(item => {
                    const itemPedidoDiv = document.createElement('div');
                    itemPedidoDiv.classList.add('item-pedido');

                    const titulo = document.createElement('h3');
                    titulo.textContent = "Produto: " + item.produto.nome;

                    itemPedidoDiv.appendChild(titulo);

                    const imagem = document.createElement('img');
                    imagem.src = "../assets/" + item.produto.imagems[0].caminhoImagem;
                    imagem.style.width = "100px";
                    imagem.style.height = "90px";
                    imagem.style.borderRadius = "8px";

                    itemPedidoDiv.appendChild(imagem);

                    const valorDiv = document.createElement('p');
                    valorDiv.classList.add('preco-item')
                    valorDiv.textContent = "Valor: " +  (item.produto.valorTotal * item.quantidade).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

                    itemPedidoDiv.appendChild(valorDiv);

                    itensByAnuncianteDiv.appendChild(itemPedidoDiv);
                })

                if(desconto != 0.0 && desconto != null && desconto != undefined){
                    const descontoDiv = document.createElement('p');
                    descontoDiv.textContent = "Desconto: " +  (desconto).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

                    itensByAnuncianteDiv.appendChild(descontoDiv);
                }

                orderDiv.appendChild(itensByAnuncianteDiv);
            });

            const valorTotalDiv = document.createElement('p');
            valorTotalDiv.textContent = "Valor Total: " +  (valorTotal).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

            const valorFreteDiv = document.createElement('p');
            valorFreteDiv.textContent = "Valor do Frete: " +  (valorFrete).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

            const valorTotalComFreteDiv = document.createElement('p');
            valorTotalComFreteDiv.textContent = "Valor Total com frete: " +  (valorTotalComFrete).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

            const valorTotalComDescontoDiv = document.createElement('p');
            valorTotalComDescontoDiv.textContent = "Valor com desconto: " +  (valorTotalComDesconto).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });


            orderDiv.appendChild(valorTotalDiv);
            orderDiv.appendChild(valorFreteDiv);
            orderDiv.appendChild(valorTotalComFreteDiv);
            orderDiv.appendChild(valorTotalComDescontoDiv);

            const comprarButton = document.createElement('button');
            comprarButton.textContent = 'Realizar pagamento';
            comprarButton.classList.add('cart-buy-button');
            comprarButton.onclick = () => {
                fetch("http://localhost:8080/pedido/pagamento", {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        'Authorization': `Bearer ${token}`
                    },
                    body: JSON.stringify({
                        "idUsuario" : idUsuario,
                        "idPedido" : data.idPedido,
                        "valorPagamento" : valorTotalComDesconto
                    })
                })
                    .then(response => {
                        window.location.href = "../pages/orderConcluded.html";
                    })
                    .catch(err => {
                        window.alert("Erro ao processar pagamento!");
                    })
            };
            orderDiv.appendChild(comprarButton);
        })
        .catch(err => {
            window.alert("Erro ao carregar o pedido! " + err);
        })
}