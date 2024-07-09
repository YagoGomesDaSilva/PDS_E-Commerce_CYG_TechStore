document.addEventListener("DOMContentLoaded", loadCart()); 

function loadCart() {
    const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;

    fetch("http://localhost:8080/cart/"+ idUsuario, {
        method: "GET",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => response.json())
        .then(data => {
            const cart = data;
            const cartDiv = document.getElementById('container-cart');

            cartDiv.innerHTML = "";
            cart.forEach(item => {
                const cartItemDiv = document.createElement('div');
                cartItemDiv.classList.add('cart-item');
                
                const imagem = document.createElement('img');
                imagem.src = "../assets/" + item.produto.imagems[0].caminhoImagem;
                imagem.style.width = "100px";
                imagem.style.height = "90px";
                imagem.style.borderRadius = "8px";

                const titulo = document.createElement('h3');
                titulo.textContent = "Produto: " + item.produto.nome;

                const price = document.createElement('p');
                price.textContent = "Valor: " +  (item.produto.valorTotal * item.quantidade).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

                const quantidadeLabel = document.createElement('label');
                quantidadeLabel.setAttribute('for', 'quantidade-select-' + item.id);
                quantidadeLabel.style.display = "none";

                const quantidadeSelect = document.createElement('select');
                quantidadeSelect.id = 'quantidade-select-' + item.id;
                for (let i = 1; i <= 20; i++) {
                    const option = document.createElement('option');
                    option.value = i;
                    option.textContent = i;
                    if (i === item.quantidade) {
                        option.selected = true;
                    }
                    quantidadeSelect.appendChild(option);
                }

                // quantidadeSelect.addEventListener('change', (event) => {
                //     const novaQuantidade = parseInt(event.target.value);
                //     item.quantidade = novaQuantidade;
                //     price.textContent = "Valor: " + (item.produto.preco * item.quantidade).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
                    
                //     // Atualiza o carrinho no localStorage com a nova quantidade
                //     localStorage.setItem('cart', JSON.stringify(cart));
                // });

                const removeButton = document.createElement('button');
                const removeImage = document.createElement('img');
                removeImage.style.width = "22px";
                removeImage.style.height = "22px";
                removeImage.src = "../imgs/remove.png";
                removeButton.appendChild(removeImage);
                removeButton.style.cursor = "pointer";

                removeButton.addEventListener('click', (event) => {
                            event.stopPropagation();
                            removeFromCart(item.id);
                        });

                const cartDivAux = document.createElement('div');
                cartDivAux.classList.add('container-infos-cart');
                
                const divAux = document.createElement('div');
                divAux.classList.add('container-selects');

                cartItemDiv.appendChild(imagem);
                cartDivAux.appendChild(titulo);
                cartDivAux.appendChild(price);
                divAux.appendChild(quantidadeLabel);
                divAux.appendChild(quantidadeSelect);
                divAux.appendChild(removeButton); 
                cartDivAux.appendChild(divAux)
                cartItemDiv.appendChild(cartDivAux);

                cartDiv.appendChild(cartItemDiv);
            });
        
            // Adiciona o botão "Comprar" ao final do carrinho
            if (cart.length > 0) {
                const comprarButton = document.createElement('button');
                comprarButton.textContent = 'Comprar';
                comprarButton.classList.add('cart-buy-button');
                comprarButton.onclick = sendPedido;
                cartDiv.appendChild(comprarButton);
            }
        })
        .catch(err => {
            window.alert("Erro ao carregar o carrinho! " + err);
        })
}

function sendPedido(){ 
    const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;

    fetch('http://localhost:8080/pedido/' + idUsuario, {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      }
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log('Carrinho enviado com sucesso:', data);
        loadCart();
      })
      .catch(error => {
        console.error('Erro ao enviar o carrinho:', error);
      });
}


function removeFromCart(idItem) {
    fetch("http://localhost:8080/cart/" + idItem, {
        method: "DELETE",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        }
    })
        .then(response => {
            loadCart();
        })
        .catch(err => {
            window.alert("Erro ao excluir item do carrinho! " + err);
        }) 
    
}

