const token = localStorage.getItem('token'); 

// fetch('sua-url-api', {
//   method: 'GET', // ou 'POST', 'PUT', etc., dependendo do tipo de requisição
//   headers: {
//     'Authorization': `Bearer ${token}`
//   }
// })

function createProduto() {
    const form = document.getElementById('formProduto');
    const formData = new FormData(form);

    const imageFiles = formData.getAll('images');
    const imagePaths = imageFiles.map(file => file.name); // Supondo que o nome do arquivo seja o path

    
    const produtoData = {
        nome: formData.get('nomeProduto'),
        valorTotal: formData.get('valor'),
        marca: formData.get('marca'),
        descricao: formData.get('descricao'),
        observacao: formData.get('observacao'),
    };

    const anuncio = {
        titulo: formData.get('titulo'),
        descricaoAnuncio: formData.get('descricaoAnuncio'),
    }
    
    const anuncioDTO = {
        imagem : imagePaths,
        produto : produtoData,
        anuncio : anuncio,
        estoque : formData.get('quantidade')
    }

    fetch('http://localhost:8080/produto', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(produtoData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao cadastrar produto');
        }
        alert('Produto cadastrado com sucesso!');
        // Redirecionar para uma rota específica, se necessário
        // window.location.href = '/rota-especifica';
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar produto');
    });
}