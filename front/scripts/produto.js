// Obter a URL atual
const urlParams = new URLSearchParams(window.location.search);

// Obter o valor do parâmetro 'idAnuncio' da URL
const idAnuncio = urlParams.get('idAnuncio');

console.log(idAnuncio);

document.addEventListener("DOMContentLoaded", function() {
    // Função para adicionar imagens ao carrossel
    function createCarousel(images) {
        const carousel = document.getElementById('productCarousel');
        images.forEach(src => {
            const img = document.createElement('img');
            img.src = "../assets/imgs/"+src.caminhoImagem;
            carousel.appendChild(img);
        });
    }

    // Função para preencher os detalhes do produto
    function populateProductDetails(data) {
        document.getElementById('productName').textContent = data.titulo;
        document.getElementById('productTitle').textContent = data.produto.nome;
        document.getElementById('productDescription').textContent = data.descricao;
        document.getElementById('productObservations').textContent = data.produto.observacao;
        document.getElementById('productBrand').textContent = data.produto.marca;
        document.getElementById('productPrice').textContent = data.produto.valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        createCarousel(data.produto.imagems);
    }

    // Função para fazer requisição à API e obter os dados do produto
    function fetchProductData() {
        // Exemplo de URL de API fictícia
        const apiURL = 'http://localhost:8080/anuncio/'+idAnuncio;
        
        fetch(apiURL)
            .then(response => response.json())
            .then(data => populateProductDetails(data))
            .catch(error => console.error('Erro ao obter os dados do produto:', error));
    }

    // Chama a função para buscar os dados do produto
    fetchProductData();
});