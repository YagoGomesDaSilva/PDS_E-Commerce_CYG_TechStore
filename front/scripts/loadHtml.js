const token = localStorage.getItem('user') != null ? JSON.parse(localStorage.getItem('user')).token : "";

function loadLayoutHTML(elementId, url, componentId = "", componentUrl) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById(elementId).innerHTML = data;
            if(componentId !== ""){
                loadComponentHTML(componentId, componentUrl);
            }
        }).catch(error => {
            console.log("Erro ao carregar layout HTML: " + error);
        })
}

function loadComponentHTML(elementId, url) {
    fetch(url)
        .then(response => response.text())
        .then(data => {
            document.getElementById(elementId).innerHTML = data;
        }).catch(error => {
            console.log("Erro ao carregar componente HTML: " + error);
        })
}

window.onload = function() {
    loadLayoutHTML("header-placeholder", "../layouts/header.html", "component-search-bar", "../components/searchBar.html");
    if(token == null || token == undefined || token == ""){
        loadLayoutHTML("footer-placeholder", "../layouts/footerNotLogged.html");
        loadLayoutHTML("header-placeholder", "../layouts/cliente/header.html", "component-search-bar", "../components/searchBar.html");
    } else if (JSON.parse(localStorage.getItem('user')).tipoUsuario == "comum"){
        loadLayoutHTML("header-placeholder", "../layouts/cliente/header.html", "component-search-bar", "../components/searchBar.html");
    } else {
        loadLayoutHTML("header-placeholder", "../layouts/anunciante/header.html", "component-search-bar", "../components/searchBar.html");
    }

}