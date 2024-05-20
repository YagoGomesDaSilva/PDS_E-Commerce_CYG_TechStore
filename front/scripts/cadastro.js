function createUsuario() {
    const form = document.getElementById('formUsuario');
    const formData = new FormData(form);

    const userData = {
        nomeUsuario: formData.get('nomeUsuario'),
        email: formData.get('email'),
        password: formData.get('senha'),
        telefone: formData.get('telefone')
    };

    fetch('http://localhost:8080/auth/register', {
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

