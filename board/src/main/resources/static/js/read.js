// 날짜 포맷 함수
const formatDate = (str) => {
  const date = new Date(str);
  return (
    date.getFullYear() +
    "/" +
    (date.getMonth() + 1) +
    "/" +
    date.getDate() +
    " " +
    date.getHours() +
    ":" +
    date.getMinutes()
  );
};

const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);

    const data = res.data;
    let result = "";
    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
      result += `<div class="p-3"> <img src="/img/default.png" alt="" class= "rounded-circle mx-auto d-block" style="width: 60px; height: 60px"/></div>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div>${reply.replyer}</div><div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
      result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      result += `</div>`;
      result += `</div>`;
    });

    document.querySelector(".replyList").innerHTML = result;
  });
};
// 댓글 삭제
// 삭제 버튼 클릭 시  data-rno 가져오기
document.querySelector(".replyList").addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".reply-row").dataset.rno;
  console.log(rno);

  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    axios.delete(`/replies/${rno}`).then((res) => {
      console.log(res.data);
      replyList();
    });
  } else if (btn.classList.contains("btn-outline-success")) {
    axios.put(`/replies/${rno}`).then((res) => {
      console.log(res.data);
    });
  }
});
replyList();
