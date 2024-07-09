document.addEventListener("DOMContentLoaded", loadCart());

function getProductToCart(idAnuncio){
    if(isInCart(idAnuncio)){
        return
    } else {        
        const apiURL = 'http://localhost:8080/anuncio/' + idAnuncio;
            
        fetch(apiURL)
            .then(response => response.json())
            .then(data => {
                console.log(data);
                let quantidade = 0;
                data.anunciante.estoques.forEach(produto => {
                    if(produto.id == idAnuncio){
                        quantidade = produto.quantidade;
                    }
                });
                const item = {
                    anuncio : {
                        idAnuncio : data.id,
                        titulo : data.titulo,
                        anunciante: data.anunciante.nome
                    },
                    produto : {
                        nome: data.produto.nome,
                        idProduto: data.produto.id,
                        preco: data.produto != null ? data.produto.valorTotal : 0,
                        estoque: quantidade
                    },
                    quantidade: 1,
                    imagem: "../assets/" + data.produto.imagems[0].caminhoImagem,
                };
                addToCart(item);
            })
            .catch(error => console.error('Erro ao obter os dados do produto:', error));
    }
}

function isInCart(idAnuncio) {
    let cart = getCartAsJSON();
    let validate = false;
    cart.forEach(item => {
        if(item.anuncio.idAnuncio == idAnuncio){
            item.quantidade++;
            localStorage.setItem('cart', JSON.stringify(cart));
            validate = true;
            window.alert("Item adiconado ao carrinho!")
            return;
        } 
    })
    return validate;
}


function getCartAsJSON() {
    const cartData = localStorage.getItem('cart');
    return cartData ? JSON.parse(cartData) : [];
}
  
  
function addToCart(item) {
    let cart = getCartAsJSON();
    cart.push(item);
    localStorage.setItem('cart', JSON.stringify(cart));
    window.alert("Item adiconado ao carrinho!")
}


function loadCart() {
    const cart = getCartAsJSON();
    const cartDiv = document.getElementById('container-cart');

    cartDiv.innerHTML = "";
    cart.forEach(item => {
        const cartItemDiv = document.createElement('div');
        cartItemDiv.classList.add('cart-item');
        
        const imagem = document.createElement('img');
        imagem.src = item.imagem;
        imagem.style.width = "100px";
        imagem.style.height = "90px";
        imagem.style.borderRadius = "8px";

        const titulo = document.createElement('h3');
        titulo.textContent = "Produto: " + item.anuncio.titulo;

        const price = document.createElement('p');
        price.textContent = "Valor: " +  (item.produto.preco * item.quantidade).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });

        const quantidadeLabel = document.createElement('label');
        quantidadeLabel.setAttribute('for', 'quantidade-select-' + item.anuncio.idAnuncio);
        quantidadeLabel.style.display = "none";

        const quantidadeSelect = document.createElement('select');
        quantidadeSelect.id = 'quantidade-select-' + item.anuncio.idAnuncio;
        for (let i = 1; i <= item.produto.estoque; i++) {
            const option = document.createElement('option');
            option.value = i;
            option.textContent = i;
            if (i === item.quantidade) {
                option.selected = true;
            }
            quantidadeSelect.appendChild(option);
        }

        quantidadeSelect.addEventListener('change', (event) => {
            const novaQuantidade = parseInt(event.target.value);
            item.quantidade = novaQuantidade;
            price.textContent = "Valor: " + (item.produto.preco * item.quantidade).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
            
            // Atualiza o carrinho no localStorage com a nova quantidade
            localStorage.setItem('cart', JSON.stringify(cart));
        });

        const removeButton = document.createElement('button');
        const removeImage = document.createElement('img');
        removeImage.style.width = "22px";
        removeImage.style.height = "22px";
        removeImage.src = "../imgs/remove.png";
        removeButton.appendChild(removeImage);
        removeButton.style.cursor = "pointer";

        removeButton.addEventListener('click', (event) => {
                    event.stopPropagation();
                    removeFromCart(item);
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
}

function sendPedido(){
    const cart = getCartAsJSON();
    let valorTotal = 0;
    const items = []

    cart.forEach(item => {
      valorTotal += (item.produto.preco * item.quantidade);
      items.push({
        produto: item.produto.nome,
        idProduto: item.produto.idProduto,
        preco: item.produto.preco
      });
    });
  
    console.log(items);

    const pedido = {
      valorTotal: valorTotal,
      items: items
    };
  
    fetch('http://localhost:8080/pedido', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(pedido)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log('Carrinho enviado com sucesso:', data);
        // localStorage.setItem('order', JSON.stringify(data));
        // setTimeout(() => {
        //     window.location.href = './pedido.html';
        // }, 3000)
        localStorage.setItem('cart', []);
      })
      .catch(error => {
        console.error('Erro ao enviar o carrinho:', error);
      });
}


function removeFromCart(itemToRemove) {
    let cart = getCartAsJSON();
    cart = cart.filter(item => item.anuncio.idAnuncio !== itemToRemove.anuncio.idAnuncio);
    localStorage.setItem('cart', JSON.stringify(cart));
    loadCart();
}

