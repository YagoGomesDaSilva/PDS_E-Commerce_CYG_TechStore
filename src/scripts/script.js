// products.js

// Define your product data (you can fetch this from an API)
const products = [
    { name: 'Produto 1', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 2', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 3', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 4', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 5', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 6', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 7', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 8', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 9', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 10', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 11', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 12', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    { name: 'Produto 13', description: 'R$150,00', imageUrl: 'path/to/product2.jpg' },
    { name: 'Produto 14', description: 'R$100,00', imageUrl: 'path/to/product1.jpg' },
    // Add more products as needed
  ];
  
  // Function to create product elements and insert them into the product grid
  function insertProducts() {
    const productContainer = document.getElementById('product-container');
  
    products.forEach(product => {
        const productWrapper = document.createElement('div');
        productWrapper.classList.add('product-wrapper');
      
      const productElement = document.createElement('div');
      productElement.classList.add('product');
  
      const imageElement = document.createElement('img');
      imageElement.src = product.imageUrl;
      imageElement.alt = product.name;
  
      const titleElement = document.createElement('h2');
      titleElement.textContent = product.name;
  
      const descriptionElement = document.createElement('p');
      descriptionElement.textContent = product.description;
  
      const buttonElement = document.createElement('button');
      buttonElement.textContent = 'Comprar';
  
      productElement.appendChild(imageElement);
      productElement.appendChild(titleElement);
      productElement.appendChild(descriptionElement);
      productElement.appendChild(buttonElement);
      
      productWrapper.appendChild(productElement);
      productContainer.appendChild(productWrapper);
    });
  }
  
  // Call the function to insert products when the page loads
  window.onload = insertProducts;