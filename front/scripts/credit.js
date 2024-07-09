const idUsuario = JSON.parse(localStorage.getItem('user')).idUser;

document.addEventListener("DOMContentLoaded", () => {
    loadCredit();

    document.getElementById("credito").addEventListener('input', function(ev){
        const valorInput = ev.target;
        let valor = valorInput.value;

        valor = valor.replace(/\D/g, '');

        valor = (valor / 100).toFixed(2) + '';
        valor = valor.replace('.', ',');

        valorInput.value = 'R$ ' + valor;
    });
}); 

function loadCredit() {
    fetch("http://localhost:8080/user/credit/" + idUsuario, {
        method: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
    })
        .then(response => response.json())
        .then(data => {
            document.getElementById("welcome").textContent = "Olá " + JSON.parse(localStorage.getItem('user')).nome + "!";
            document.getElementById("credit").textContent = "Saldo atual: " + (data).toLocaleString('pt-BR', { style: 'currency', currency: 'BRL' }) + ".";
        })
        .catch(err => {
            window.alert("Erro ao acessar crédito do usuário!" + err);
        })
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

function sendCredit() {
    const credito = document.getElementById("credito").value.replace(/[R$\s.]/g, '').replace(',', '.');

    fetch("http://localhost:8080/user/credit", {
        method: "POST",
        headers: {
            'Content-Type': 'application/json',
            'Authorization': `Bearer ${token}`
        },
        body: JSON.stringify({
            "idUsuario" : idUsuario,
            "credito" : credito
        }) 
    })
        .then(response => {
            loadCredit();
        })
        .catch(err => {
            window.alert("Erro ao inserir crédito! " + err);
        })
}