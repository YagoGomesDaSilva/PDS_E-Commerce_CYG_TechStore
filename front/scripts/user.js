function createAnunciante() {
    const nome = document.getElementById('nome');
    const email = document.getElementById('email');
    const documento = document.getElementById('documento');
    const senha = document.getElementById('senha');
    const repeat = document.getElementById('repeatSenha');
    const telefone = document.getElementById('telefone');
    const cep = document.getElementById('cep');

    const userData = {
        nome: nome.value,
        email: email.value,
        documento: documento.value.replace(/\D/g, ''),
        password: senha.value,
        telefone: telefone.value.replace(/\D/g, ''),
        endereco: {
            cep: cep.value.replace(/\D/g, ''),
            estado: document.getElementById('estado').value,
            cidade: document.getElementById('cidade').value,
            bairro: document.getElementById('bairro').value,
            logradouro: document.getElementById('logradouro').value,
            numero: document.getElementById('numero').value,
        }
    };

    let isValid = true;

    isValid = isValid && validateCampoForm(nome);
    isValid = isValid && validateCampoForm(email);
    isValid = isValid && validateCampoForm(documento);
    isValid = isValid && validateCampoForm(senha);
    isValid = isValid && validateCampoForm(telefone);
    isValid = isValid && validateCampoForm(cep);
    
    isValid = isValid && validateNome(nome);
    isValid = isValid && validateSenhas(senha, repeat);

    if(isValid){
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
                window.location.href = '../index.html';
            })
            .catch(error => {
                console.error('Erro:', error);
                alert('Erro ao cadastrar usuário');
            });
    }

}

function validateSenhas(senha, repeat){
    const errorDiv = document.getElementById(`${repeat.id}Error`);

    try {
        if(senha.value != repeat.value){
            throw new Error('As senhas não coincidem');
        }
        return true;
    } catch (error) {
        repeat.classList.add('invalid');
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
        return false;
    }
}

function validateNome(nome){
    const errorDiv = document.getElementById(`${nome.id}Error`);
    
    try {
        const regex = /^[A-Za-zÀ-ÿ\s]+$/;
        console.log(regex.test(nome.value));
        if (!regex.test(nome.value)) {
            throw new Error('Nome de usuário inválido');
        }
        return true;
    } catch (error) {
        nome.classList.add('invalid');
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
        return false;
    }
}

function validateCampoForm(campo){
    const campoValue = campo.value;
    const campoName = campo.name;
    const errorDiv = document.getElementById(`${campo.id}Error`);

    try {
        if(campoValue == ""){
            throw new Error(campoName + " é obrigatório")
        }
        campo.classList.remove('invalid');
        errorDiv.textContent = '';
        errorDiv.style.display = 'none';
        return true;
    } catch (error) {
        campo.classList.add('invalid');
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
        return false;
    }
}

function pesquisarInfosCep() {
    const cep = document.querySelector("input[name=CEP]");
    const value = cep.value.replace(/[^0-9]+/, '');
    const url = `https://viacep.com.br/ws/${value}/json/`;
    fetch(url, {
        headers: {
            "Access-Control-Allow-Origin" : "*"
        }
    })
        .then(response => response.json())
        .then(data => {
            if (data.logradouro) {
                document.querySelector('input[name=logradouro]').value = data.logradouro;
                document.querySelector('input[name=logradouro]').disabled = true;
                document.querySelector('input[name=bairro]').value = data.bairro;
                document.querySelector('input[name=bairro]').disabled = true;
                document.querySelector('input[name=cidade]').value = data.localidade;
                document.querySelector('input[name=cidade]').disabled = true;
                document.querySelector('input[name=estado]').value = data.uf;
                document.querySelector('input[name=estado]').disabled = true;
            }
        })
        .catch(error => {
                document.querySelector('input[name=logradouro]').disabled = false;
                document.querySelector('input[name=bairro]').disabled = false;
                document.querySelector('input[name=cidade]').disabled = false;
                document.querySelector('input[name=estado]').disabled = false;
                document.querySelector('input[name=logradouro]').value = "";
                document.querySelector('input[name=bairro]').value = "";
                document.querySelector('input[name=cidade]').value = "";
                document.querySelector('input[name=estado]').value = "";
        }) 
}

document.addEventListener('DOMContentLoaded', function() {
    // adiciona máscara para telefone no formato (XX) 9 XXXX-XXXX
    const telefoneInput = document.getElementById('telefone');

    telefoneInput.addEventListener('input', function(e) {
        let value = e.target.value;
        value = value.replace(/\D/g, '');

        if (value.length > 0) {
            value = '(' + value;
        }
        if (value.length > 3) {
            value = value.slice(0, 3) + ') ' + value.slice(3);
        }
        if (value.length > 6) {
            value = value.slice(0, 6) + ' ' + value.slice(6);
        }
        if (value.length > 11) {
            value = value.slice(0, 11) + '-' + value.slice(11, 15);
        }

        e.target.value = value;
    });

    // adiciona máscara para CEP no formato XXXXX-XXX
    const cepInput = document.getElementById('cep');

    cepInput.addEventListener('input', function(e) {
        let value = e.target.value;

        value = value.replace(/\D/g, '');

        if (value.length > 5) {
            value = value.slice(0, 5) + '-' + value.slice(5);
        }

        e.target.value = value;
    });

    // adiciona máscara para CPF/CNPJ no formato XXX.XXX.XXX-XX ou XX.XXX.XXX/XXXX-XX
    const cpfInput = document.getElementById('documento');

    cpfInput.addEventListener('input', function(e) {
        let value = e.target.value;

        value = value.replace(/\D/g, '');

        if(value.length > 3) {
            value = value.slice(0, 3) + '.' + value.slice(3);
        } 
        if(value.length > 7) {
            value = value.slice(0, 7) + '.' + value.slice(7);
        } 
        if(value.length > 11) {
            value = value.slice(0, 11) + '-' + value.slice(11);
        } 
        if(value.replace(/\D/g, '').length > 11){
            value = value.replace(/\D/g, '');
            value = value.slice(0, 2) + '.' + value.slice(2, 5) + '.' + value.slice(5, 8) + '/' + value.slice(8);
        }
        if(value.replace(/\D/g, '').length > 12){
            value = value.replace(/\D/g, '');
            value = value.slice(0, 2) + '.' + value.slice(2, 5) + '.' + value.slice(5, 8) + '/' + value.slice(8, 12) + '-' + value.slice(12);
        }

        e.target.value = value;
    })
});

function validateCampo(event) {
    const campo = event.target;
    const campoValue = campo.value;
    const campoName = campo.name;
    const errorDiv = document.getElementById(`${campo.id}Error`);

    try {
        if(campoValue == ""){
            throw new Error(campoName + " é obrigatório")
        }
        campo.classList.remove('invalid');
        errorDiv.textContent = '';
        errorDiv.style.display = 'none';
    } catch (error) {
        campo.classList.add('invalid');
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
    }
}

function login() {
    const form = document.getElementById("formLogin");
    const formData = new FormData(form);

    const user = {
        email: formData.get('email'),
        password: formData.get('senha') 
    }

    fetch('http://localhost:8080/auth/login', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(user)
      })
      .then(response => {
        if (!response.ok) {
            throw new Error('Erro ao fazer login');
        }
        return response.json();
      })
      .then(data => {
          localStorage.setItem('token', data.token);
          alert('Login bem-sucedido');
          window.location.href = '../index.html';
      })
      .catch(error => alert('Erro:', error));
}