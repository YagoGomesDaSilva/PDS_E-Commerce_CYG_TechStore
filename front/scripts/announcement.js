const urlParams = new URLSearchParams(window.location.search);
const idAnuncio = urlParams.get('idAnuncio');

document.addEventListener("DOMContentLoaded", function() {
    function createCarousel(images) {
        const carousel = document.getElementById('productCarousel');
        images.forEach(src => {
            const img = document.createElement('img');
            img.src = "../assets/"+src.caminhoImagem;
            img.style.width = "200px";
            img.style.height = "200px";
            carousel.appendChild(img);
        });
    }

    function populateAnnouncementDetails(data) {
        document.getElementById("cartButton").addEventListener("click", (event) => {
            event.stopPropagation();
            getProductToCart(data.id);
        })
        document.getElementById('productTitle').textContent = data.titulo;
        document.getElementById('productDescription').textContent = data.descricao;
        document.getElementById('productBrand').textContent += data.produto.marca;
        document.getElementById('productPrice').textContent = data.produto.valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' });
        createCarousel(data.produto.imagems);
    }

    function fetchAnuncioData() {
        const apiURL = 'http://localhost:8080/anuncio/' + idAnuncio;
        
        fetch(apiURL)
            .then(response => response.json())
            .then(data => populateAnnouncementDetails(data))
            .catch(error => console.error('Erro ao obter os dados do produto:', error));
    }

    fetchAnuncioData();
});