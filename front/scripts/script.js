const token = localStorage.getItem('token');

// products.js  
function getProducts() {
  fetch('http://localhost:8080/anuncio')
      .then(response => response.json())
      .then(data => {
          const productContainer = document.getElementById('product-container');

          data.forEach(product => {
              const productWrapper = document.createElement('div');
              productWrapper.classList.add('product-wrapper');

              const productElement = document.createElement('div');
              productElement.classList.add('product');

              const imageElement = document.createElement('img');
              imageElement.src = product.produto != null ? "../assets/imgs/" + product.produto.imagems[0].caminhoImagem : "../assets/der_ecommerce.jpeg";
              imageElement.alt = product.nome;

              const titleElement = document.createElement('h2');
              titleElement.textContent = product.titulo;

              const descriptionElement = document.createElement('p');
              descriptionElement.textContent = product.descricao;

              const price = document.createElement('p');
              price.classList.add("price");
              price.textContent = product.produto != null ? product.produto.valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : "A negociar";

              const buttonElement = document.createElement('button');
              buttonElement.textContent = 'Adicionar ao Carrinho';
              buttonElement.classList.add('add-to-cart-button');

              const cartIcon = document.createElement('i');
              cartIcon.classList.add('fas', 'fa-shopping-cart');
              buttonElement.appendChild(cartIcon);

              buttonElement.addEventListener('click', (event) => {
                  event.stopPropagation();
                  const pedido = {
                      valorTotal: product.produto != null ? product.produto.valorTotal : 0,
                      items: [{
                          produto: product.produto.nome,
                          idProduto: product.produto.id,
                          preco: product.produto != null ? product.produto.valorTotal : 0
                      }]
                  };
                  addToCart(pedido);
              });

              productWrapper.addEventListener('click', () => {
                  if(token == "" || token == null || token == undefined) {
                      window.location.href = '/public/anuncio.html?idAnuncio=' + product.id;
                  } else {
                      window.location.href = '/cliente/anuncio.html?idAnuncio=' + product.id;
                  }
              });

              productElement.appendChild(imageElement);
              productElement.appendChild(titleElement);
              productElement.appendChild(descriptionElement);
              productElement.appendChild(price);
              productElement.appendChild(buttonElement);

              productWrapper.appendChild(productElement);
              productContainer.appendChild(productWrapper);
          });
      })
      .catch(error => {
          console.error('Erro ao buscar produtos:', error);
      });
}

  function redirectCreateAnuncio() {
    window.location.href = 'cadastrarAnuncio.html';
  }

  function redirectCreateAnunciante() {
    window.location.href = 'cadastroAnunciante.html';
  }
  
  window.onload = function() {
    getProducts();
    loadCart();
};

  
function getCartAsJSON() {
  const cartData = localStorage.getItem('cart');
  return cartData ? JSON.parse(cartData) : [];
}


function addToCart(pedido) {
  let cart = getCartAsJSON();
  cart.push(pedido);
  localStorage.setItem('cart', JSON.stringify(cart));
}


function loadCart() {
  const cart = getCartAsJSON();
  const cartDiv = document.getElementById('cart');
  cartDiv.innerHTML = '';
  cart.forEach((pedido, index) => {
      const cartItemDiv = document.createElement('div');
      cartItemDiv.classList.add('cart-item');
      cartItemDiv.innerHTML = `
          <h3>Produto: ${pedido.items[0].produto}</h3>
          <p>Valor Total: R$${pedido.valorTotal.toFixed(2)}</p>
          <ul>
              ${pedido.items.map(item => `<li>${item.produto} - R$${item.preco.toFixed(2)}</li>`).join('')}
          </ul>
          <button onclick="removeFromCart(${index})">Remover</button>
      `;
      cartDiv.appendChild(cartItemDiv);
  });

  // Adiciona o botão "Comprar" ao final do carrinho
  if (cart.length > 0) {
      const comprarButton = document.createElement('button');
      comprarButton.textContent = 'Comprar';
      comprarButton.classList.add('comprar-button');
      comprarButton.onclick = sendCartToBackend;
      cartDiv.appendChild(comprarButton);
  }
}

function sendCartToBackend() {
    const cart = getCartAsJSON();
    let valorTotal = 0;
    const productQuantities = {};
  
    cart.forEach(pedido => {
      valorTotal += pedido.valorTotal;
      pedido.items.forEach(item => {
        if (productQuantities[item.idProduto]) {
          productQuantities[item.idProduto]++;
        } else {
          productQuantities[item.idProduto] = 1;
        }
      });
    });
  
    const items = Object.keys(productQuantities).map(idProduto => ({
      produto: parseInt(idProduto),
      quantidade: productQuantities[idProduto]
    }));
  
    const payload = {
      valorTotal: valorTotal,
      items: items
    };
  
    fetch('http://localhost:8080/pedido', {
      method: 'POST',
      headers: {
        'Content-Type': 'application/json',
        'Authorization': `Bearer ${token}`
      },
      body: JSON.stringify(payload)
    })
      .then(response => {
        if (!response.ok) {
          throw new Error('Network response was not ok ' + response.statusText);
        }
        return response.json();
      })
      .then(data => {
        console.log('Carrinho enviado com sucesso:', data);
        localStorage.setItem('order', JSON.stringify(data));
        setTimeout(() => {
            window.location.href = './pedido.html';
        }, 3000)
      })
      .catch(error => {
        console.error('Erro ao enviar o carrinho:', error);
      });
  }
  

function removeFromCart(index) {
  let cart = getCartAsJSON();
  cart.splice(index, 1);
  localStorage.setItem('cart', JSON.stringify(cart));
  loadCart();
}

