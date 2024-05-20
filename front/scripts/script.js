const token = localStorage.getItem('token');

// products.js  
  function getProducts() {
    fetch('http://localhost:8080/produtos')
      .then(response => response.json())
        .then(data => {
          const productContainer = document.getElementById('product-container');

          data.forEach(product => {
              const productWrapper = document.createElement('div');
              productWrapper.classList.add('product-wrapper');

            const productElement = document.createElement('div');
            productElement.classList.add('product');

            const imageElement = document.createElement('img');
            imageElement.src = product.imageUrl;
            imageElement.alt = product.nome;

            const titleElement = document.createElement('h2');
            titleElement.textContent = product.nome;

            const descriptionElement = document.createElement('p');
            descriptionElement.textContent = product.descricao;

            const buttonElement = document.createElement('button');
            buttonElement.textContent = 'Comprar';

            productElement.appendChild(imageElement);
            productElement.appendChild(titleElement);
            productElement.appendChild(descriptionElement);
            productElement.appendChild(buttonElement);

            productWrapper.appendChild(productElement);
            productContainer.appendChild(productWrapper);
          });
      })
  }

  function redirectCreateAnuncio() {
    window.location.href = '/anunciante/cadastrarAnuncio.html';
  }
  
  // Call the function to insert products when the page loads
  window.onload = getProducts;