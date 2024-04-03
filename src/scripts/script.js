// products.js

// Define your product data (you can fetch this from an API)
const products = [
    { name: 'Product 1', description: 'Description of Product 1', imageUrl: 'path/to/product1.jpg' },
    { name: 'Product 2', description: 'Description of Product 2', imageUrl: 'path/to/product2.jpg' },
    { name: 'Product 1', description: 'Description of Product 1', imageUrl: 'path/to/product1.jpg' },
    { name: 'Product 2', description: 'Description of Product 2', imageUrl: 'path/to/product2.jpg' },
    { name: 'Product 1', description: 'Description of Product 1', imageUrl: 'path/to/product1.jpg' },
    { name: 'Product 2', description: 'Description of Product 2', imageUrl: 'path/to/product2.jpg' },
    { name: 'Product 1', description: 'Description of Product 1', imageUrl: 'path/to/product1.jpg' },
    { name: 'Product 2', description: 'Description of Product 2', imageUrl: 'path/to/product2.jpg' },
    { name: 'Product 1', description: 'Description of Product 1', imageUrl: 'path/to/product1.jpg' },
    { name: 'Product 2', description: 'Description of Product 2', imageUrl: 'path/to/product2.jpg' },
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
      buttonElement.textContent = 'Add to Cart';
  
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