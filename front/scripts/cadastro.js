
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
            return response.text();
        })
        .then(token => {
            localStorage.setItem('token', token);
            alert('Usuário cadastrado com sucesso!');
            window.location.href = '/cliente/areacliente.html';
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao cadastrar usuário');
        });
}

function createAnunciante() {
    const form = document.getElementById('formAnunciante');
    const formData = new FormData(form);

    const userData = {
        documento: formData.get('documento'),
        nomeAnunciante: formData.get('nomeAnunciante'),
    };

    const token = localStorage.getItem('token');

    fetch('http://localhost:8080/anunciante', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify(userData)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error('Erro ao cadastrar anunciante');
            }
            alert('Anunciante cadastrado com sucesso!');
            window.location.href = '/anunciante/areacliente.html';
        })
        .catch(error => {
            console.error('Erro:', error);
            alert('Erro ao cadastrar anunciante');
        });
}

