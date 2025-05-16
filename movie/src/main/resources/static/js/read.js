const imgModal = document.querySelector("#imgModal");

if (imgModal) {
  imgModal.addEventListener("show.bs.modal", (e) => {
    const thumLi = e.relatedTarget;

    const file = thumLi.getAttribute("data-file");

    const modalTitle = imgModal.querySelector(".modal-title");

    const modalBodyImg = imgModal.querySelector(".modal-body");

    modalTitle.textContent = `${title}`;
    modalBodyImg.innerHTML = `<img src="/upload/display?fileName=${file}&size=1">`;
  });
}
