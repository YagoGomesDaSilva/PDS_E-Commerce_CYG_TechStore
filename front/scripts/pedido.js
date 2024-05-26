const orderData = JSON.parse(localStorage.getItem('order'));

window.onload = function() {
    if (orderData) {
        renderOrderDetails(orderData);
    }
};

function renderOrderDetails(order) {
    const orderInfoDiv = document.getElementById('order-info');
    console.log(order);
    // Informações gerais do pedido
    orderInfoDiv.innerHTML = `
        <h2>Pedido ID: ${order.id}</h2>
        <p><strong>Valor Total:</strong> R$${order.valorTotal.toFixed(2)}</p>
        <p><strong>Data:</strong> ${order.data}</p>
        <p><strong>Email do Usuário:</strong> ${order.usuario.email}</p>
    `;
    
    // Itens do pedido
    const orderItemsDiv = document.createElement('div');
    orderItemsDiv.classList.add('order-items');
    orderItemsDiv.innerHTML = `<h2>Itens do Pedido</h2>`;
    
    order.pedidoItems.forEach(item => {
        const product = item.produto;
        const orderItemDiv = document.createElement('div');
        orderItemDiv.classList.add('order-item');
        orderItemDiv.innerHTML = `
            <h3>${product.nome}</h3>
            <img src="${product.imagems[0].caminhoImagem}" alt="${product.nome}">
            <p><strong>Descrição:</strong> ${product.descricao}</p>
            <p><strong>Marca:</strong> ${product.marca}</p>
            <p><strong>Observação:</strong> ${product.observacao}</p>
            <p><strong>Quantidade:</strong> ${item.quantidade}</p>
            <p><strong>Valor Total:</strong> R$${product.valorTotal.toFixed(2)}</p>
        `;
        orderItemsDiv.appendChild(orderItemDiv);
    });
    
    orderInfoDiv.appendChild(orderItemsDiv);
  }