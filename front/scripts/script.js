const token = localStorage.getItem('token');

// products.js  
function getProducts() {
  fetch('http://localhost:8080/anuncio')
    .then(response => response.json())
    .then(data => {
      const productContainer = document.getElementById('product-container');

      data.forEach(product => {

        if(product.produto != null) {
          console.log(product.produto.imagems[0].caminhoImagem);
        }
        
        const productWrapper = document.createElement('div');
        productWrapper.classList.add('product-wrapper');

        const productElement = document.createElement('div');
        productElement.classList.add('product');

        const imageElement = document.createElement('img');
        imageElement.src = product.produto != null ? "../assets/imgs/"+product.produto.imagems[0].caminhoImagem : "../assets/der_ecommerce.jpeg";
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

        productWrapper.style.cursor = 'pointer';

        

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
    window.location.href = '/anunciante/cadastrarAnuncio.html';
  }

  function redirectCreateAnunciante() {
    window.location.href = '/cliente/cadastroAnunciante.html';
  }
  
  // Call the function to insert products when the page loads
  window.onload = getProducts;