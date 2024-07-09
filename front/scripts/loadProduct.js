function getProducts() {
    fetch('http://localhost:8080/anuncio')
        .then(response => response.json())
        .then(data => {
            const productContainer = document.getElementById('component-products');
            data.forEach(product => {
                const productElement = document.createElement('div');
                productElement.classList.add('product-container', 'swiper-slide');
                productElement.style.cursor = "pointer";

                const imageElement = document.createElement('img');
                imageElement.src = product.produto != null ? "../assets/" + product.produto.imagems[0].caminhoImagem : "../assets/der_ecommerce.jpeg";
                imageElement.alt = product.nome;
                imageElement.style.width = "100px";
                imageElement.style.height = "90px";
                imageElement.style.borderRadius = "8px";

                const titleElement = document.createElement('h2');
                titleElement.textContent = product.titulo;


                const divPrice = document.createElement('div');
                const price = document.createElement('p');
                divPrice.appendChild(price);
                price.textContent = product.produto != null ? product.produto.valorTotal.toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) : "A negociar";

                const buttonElement = document.createElement('button');
                buttonElement.id = "listCartButton";
                buttonElement.style.cursor = "pointer";
                const imageCartElement = document.createElement('img');
                imageCartElement.src = "../imgs/cart.png";
                imageCartElement.style.width = "30px";
                imageCartElement.style.height = "30px";
                
                buttonElement.appendChild(imageCartElement);

                productElement.appendChild(imageElement);
                productElement.appendChild(titleElement);
                productElement.appendChild(divPrice);
                productElement.appendChild(buttonElement);

                productElement.addEventListener('click', () => {
                    window.location.href = '../pages/announcement.html?idAnuncio=' + product.id;
                });

                buttonElement.addEventListener("click", (event) => {
                    event.stopPropagation();
                    
                    const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;

                    fetch("http://localhost:8080/cart/"+ idUsuario + "/" + product.id, {
                        method: "POST",
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).token}`
                        },
                    })
                        .then(response => response.json())
                        .then(data => {
                            console.log(data);
                            window.alert("Item adiconado ao carrinho!");
                        })
                        .catch(err => {
                            window.alert("O item já foi adicionado ao carrinho");
                        });
                })

                productContainer.appendChild(productElement);

            });
            
            swiper.update();
        })
        .catch(error => {
            console.error('Erro ao buscar produtos:', error);
        });
}

document.addEventListener('DOMContentLoaded', function() {
    getProducts();
})