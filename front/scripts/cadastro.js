function createUsuario() {
    const form = document.getElementById('formUsuario');
    const formData = new FormData(form);
    
    const userData = {
        nomeUsuario: formData.get('nomeUsuario'),
        email: formData.get('email'),
        senha: formData.get('senha'),
        telefone: formData.get('telefone')
    };
    
    fetch('http://localhost:8080/user', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(userData)
    })
    .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao cadastrar usuário');
        }
        alert('Usuário cadastrado com sucesso!');
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar usuário');
    });
  }

  function createProduto() {
    const form = document.getElementById('formProduto');
    const formData = new FormData(form);
    
    const produtoData = {
        nome: formData.get('nomeProduto'),
        valorTotal: formData.get('valor'),
        marca: formData.get('marca'),
        descricao: formData.get('descricao'),
        observacao: formData.get('observacao')
    };


    console.log(formData)
    
    fetch('http://localhost:8080/produto', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(produtoData)
    })
    .then(response => {

        if (!response.ok) {
            throw new Error('Erro ao cadastrar produto');
        }
        alert('Produto cadastrado com sucesso!');
    })
    .catch(error => {
        console.error('Erro:', error);
        alert('Erro ao cadastrar produto');
    });
  }