document.querySelector("[name='keyword']").addEventListener("keyup", (e) => {
  if (e.keyCode == 13) {
    const searchForm = document.querySelector("#searchForm");
    searchForm.submit();
  }
});
