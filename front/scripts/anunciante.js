function getProducts() {
    fetch('http://localhost:8080/anuncio/anunciante/' + idUsuario)
        .then(response => response.json())
        .then(data => {
            const productContainer = document.getElementById('produtos-anunciante');
            data.forEach(product => {
                const productElement = document.createElement('div');
                productElement.classList.add('product-container', 'swiper-slide');

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
                buttonElement.id = "emptyEstoqueButton";
                buttonElement.style.cursor = "pointer";
                const imageCartElement = document.createElement('img');
                imageCartElement.src = "../imgs/editar.png";
                imageCartElement.style.width = "30px";
                imageCartElement.style.height = "30px";
                
                buttonElement.appendChild(imageCartElement);

                buttonElement.addEventListener("click", (event) => {
                    event.stopPropagation();
                    
                    const novaQuantidade = Number(window.prompt("Insira a quantidade que está sendo adicionada ao estoque: "))
                    const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;
                    
                    fetch("http://localhost:8080/estoque/" + idUsuario, {
                        method: "PUT",
                        headers: {
                            'Content-Type': 'application/json',
                            'Authorization': `Bearer ${JSON.parse(localStorage.getItem('user')).token}`
                        },
                        body: JSON.stringify({
                            "idProduto" : product.produto.id,
                            "quantidade" : novaQuantidade
                        })
                    })
                    .then(response => response.json())
                    .then(data => {
                        window.alert("Estoque atualizado com sucesso! Novo valor em estoque: " + data.quantidade);
                    })
                    .catch(err => {
                        window.alert("Não foi possível atualizar o estoque!");
                    });
                    
                });
                productElement.appendChild(imageElement);
                productElement.appendChild(titleElement);
                productElement.appendChild(divPrice);
                productElement.appendChild(buttonElement);

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