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
        return response.text();
      })
      .then(token => {
          localStorage.setItem('token', token);
          console.log('Login bem-sucedido');
          window.location.href = '/cliente/areacliente.html';
      })
      .catch(error => console.error('Erro:', error));
}