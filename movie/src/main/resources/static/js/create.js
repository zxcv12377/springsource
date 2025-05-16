// 등록버튼을 누르면 폼submit 중지
// li 태그 정보 수집 후 폼에 추가
// 폼 전송
document.querySelector("form").addEventListener("submit", (e) => {
  e.preventDefault();

  // li 정보 수집
  const outputs = document.querySelectorAll(".uploadResult li");
  let result = "";
  outputs.forEach((obj, idx) => {
    result += `<input type="hidden" name="movieImages[${idx}].path" value = "${obj.dataset.path}"></input>`;
    result += `<input type="hidden" name="movieImages[${idx}].uuid" value = "${obj.dataset.uuid}">`;
    result += `<input type="hidden" name="movieImages[${idx}].imgName" value = "${obj.dataset.name}"></input>`;
  });
  e.target.insertAdjacentHTML("beforeend", result);
  e.target.submit();
});
