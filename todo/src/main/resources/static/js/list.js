// checkbox 클릭이 되면
// checkbox value, data-id 가져오기

// const dataId = document.querySelectorAll(".label");
// const chkValue =
// console.log(dataId.getAttribute("data-id"));
// console.log(chkValue.getAttribute("value"));

document.querySelector(".list-group").addEventListener("click", (e) => {
  const chk = e.target;
  // checkbox 체크, 해제 여부확인
  //   console.log(chk.checked);

  // closest("선택자") : 부모에서 제일 가까운 요소 찾기
  // data- 의 속성 가져오려면 dataset을 사용
  const id = chk.closest("label").dataset.id;
  //   console.log(id);

  const form = document.querySelector("#actionForm");
  form.id.value = id;
  form.completed.value = chk.checked;
  //   console.log(form.id.value);
  //   console.log(form.completed.value);
  form.submit();
});
