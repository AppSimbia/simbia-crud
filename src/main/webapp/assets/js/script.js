function chamarPopUpDeAdd(caminhoPopUp) {
    document.getElementById("btnAdicionar").addEventListener("click", async () => {
        const response = await fetch(caminhoPopUp);
        const popupHTML = await response.text();

        const popupContainer = document.getElementById("popup-container");
        popupContainer.innerHTML = popupHTML;
        popupContainer.style.display = "flex"; // mostra o fundo escurecido

        // adicionar evento de fechar
        const btnFechar = popupContainer.querySelector("#btnFechar");
        if (btnFechar) {
            btnFechar.addEventListener("click", () => {
                popupContainer.style.display = "none";
                popupContainer.innerHTML = "";
            });
        }

        // fecha se clicar fora do modal
        popupContainer.addEventListener("click", (e) => {
            if (e.target === popupContainer) {
                popupContainer.style.display = "none";
                popupContainer.innerHTML = "";
            }
        });
    });
}
