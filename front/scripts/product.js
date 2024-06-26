const token = localStorage.getItem('token'); 

function createAnuncio() {
    const form = document.getElementById('formAnuncio');
    const formData = new FormData(form);
    const imageFiles = formData.getAll('images');
    const imagePaths = imageFiles.map(file => file.name); 

    const nome = document.getElementById("nomeProduto");
    const valor = document.getElementById("valor");
    const marca = document.getElementById("marca");
    const observacao = document.getElementById("observacao");
    const titulo = document.getElementById("titulo");
    const descricao = document.getElementById("descricaoAnuncio");
    const quantidade = document.getElementById("quantidade");

    const produtoData = {
        nome: nome.value,
        valorTotal: valor.value.replace(/[R$\s.]/g, '').replace(',', '.'),
        marca: marca.value,
        observacao: observacao.value,
    };

    const anuncio = {
        titulo: titulo.value,
        descricao: descricao.value,
    }

    const imagens = imagePaths.map(path => ({
        caminhoImagem: path
    }));
    
    const anuncioDTO = {
        imagem : imagens,
        produto : produtoData,
        anuncio : anuncio,
        estoque : {
            quantidade : quantidade.value
        }
    }

    let isValid = true;

    isValid = isValid && validateCampoForm(nome);
    isValid = isValid && validateValor(valor);
    isValid = isValid && validateCampoForm(marca);
    isValid = isValid && validateCampoForm(titulo);
    isValid = isValid && validateCampoForm(quantidade);
    isValid = isValid && validateCampoForm(descricao);

    if(isValid){
        fetch('http://localhost:8080/anuncio', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                'Authorization': `Bearer ${token}`
            },
            body: JSON.stringify(anuncioDTO)
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
}

document.addEventListener('DOMContentLoaded', function(){

    valor.addEventListener('input', function(ev){
        const valorInput = ev.target;
        let valor = valorInput.value;

        valor = valor.replace(/\D/g, '');

        valor = (valor / 100).toFixed(2) + '';
        valor = valor.replace('.', ',');

        valorInput.value = 'R$ ' + valor;
    });
});

function validateValor(campo) {
    const campoValue = parseFloat(campo.value.replace(/[R$\s.]/g, '').replace(',', '.'));
    const campoName = campo.name;
    const errorDiv = document.getElementById(`${campo.id}Error`);
    try {
        if(campoValue <= 0){
            throw new Error("Valor inválido");
        }
        return true;
    } catch (error) {
        campo.classList.add('invalid');
        errorDiv.textContent = error.message;
        errorDiv.style.display = 'block';
    }
}

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