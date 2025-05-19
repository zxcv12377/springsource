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

const replyListElement = document.querySelector(".replyList");
const replyForm = document.querySelector("#replyForm");

const replyList = () => {
  axios.get(`/replies/board/${bno}`).then((res) => {
    console.log(res.data);
    // 댓글 수정

    const data = res.data;
    console.log("댓글 수 : ", data.length);
    // document.querySelector("#replyCount").innerHTML = data.length;
    replyListElement.previousElementSibling.querySelector("span").innerHTML = data.length;
    let result = "";
    data.forEach((reply) => {
      result += `<div class="d-flex justify-content-between my-2 border-bottom reply-row" data-rno=${reply.rno}>`;
      result += `<div class="p-3"> <img src="/img/default.png" alt="" class= "rounded-circle mx-auto d-block" style="width: 60px; height: 60px"/></div>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div>${reply.replyer}</div><div><span class="fs-5">${reply.text}</span></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(reply.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      if (loginUser == reply.replyEmail) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div>`;
      result += `</div>`;
    });

    replyListElement.innerHTML = result;
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
    axios.get(`/replies/${rno}`).then((res) => {
      // console.log(res.data);
      const data = res.data;

      replyForm.rno.value = data.rno;
      replyForm.replyer.value = data.replyer;
      replyForm.text.value = data.text;
    });
  }
});

replyForm.addEventListener("submit", (e) => {
  e.preventDefault();
  const form = e.target;
  const rno = form.rno.value;
  if (form.rno.value) {
    // 수정
    axios
      .put(`/replies/${rno}`, form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert("댓글 수정 완료");
        // 기존 내용 삭제
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";

        replyList();
      });
  } else {
    // 생성
    axios
      .post("/replies/new", form, {
        headers: {
          "Content-Type": "application/json",
        },
      })
      .then((res) => {
        console.log(res.data);
        alert(res.data + " 댓글 등록");
        replyForm.rno.value = "";
        replyForm.replyer.value = "";
        replyForm.text.value = "";
        replyList();
      });
  }
});
replyList();
