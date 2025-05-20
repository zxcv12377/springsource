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
// 리뷰 가져오기
const reviewDiv = document.querySelector(".reviewList");
const reviewForm = document.querySelector("#reviewForm");
const reviewCnt = document.querySelector(".review-cnt");
const reviewList = () => {
  axios.get(`/reviews/${mno}/all`).then((res) => {
    console.log(res.data);
    const data = res.data;

    reviewCnt.innerHTML = data.length;

    let result = "";
    data.forEach((review) => {
      result += `<div class="d-flex justify-content-between py-2 border-bottom review-row" data-rno=${review.rno} data-email=${review.email}>`;
      result += `<div class="flex-grow-1 align-self-center">`;
      result += `<div><span class="font-semibold">${review.text}</span></div>`;
      result += `<div class="text-muted"><span class="d-inline-block mr-3">${review.nickname}</span>`;
      result += `평점 : <span class="grade">${review.grade}</span><div class="starrr"></div></div>`;
      result += `<div class="text-muted"><span class="small">${formatDate(review.createdDate)}</span></div></div>`;
      result += `<div class="d-flex flex-column align-self-center">`;
      if (loginUser == review.email) {
        result += `<div class="mb-2"><button class="btn btn-outline-danger btn-sm">삭제</button></div>`;
        result += `<div><button class="btn btn-outline-success btn-sm">수정</button></div>`;
      }
      result += `</div></div>`;
    });

    reviewDiv.innerHTML = result;
  });
};

reviewList();

// 리뷰 삭제 및 수정
reviewDiv.addEventListener("click", (e) => {
  const btn = e.target;
  const rno = btn.closest(".review-row").dataset.rno;
  console.log(rno);
  const email = btn.closest(".review-row").dataset.email;

  if (btn.classList.contains("btn-outline-danger")) {
    if (!confirm("정말로 삭제하시겠습니까?")) return;
    axios
      .delete(`/reviews/${mno}/${rno}`, {
        // data: {email:email},
        headers: {
          //   "Content-Type": "application/json",
          "X-CSRF-TOKEN": csrf,
        },
      })
      .then((res) => {
        console.log(res.data);
        reviewList();
      });
  } else if (btn.classList.contains("btn-outline-success")) {
    axios.get(`/reviews/${mno}/${rno}`).then((res) => {
      // console.log(res.data);
      const data = res.data;
      reviewForm.rno.value = data.rno;
      reviewForm.nickname.value = data.nickname;
      reviewForm.mid.value = data.mid;
      reviewForm.querySelector(".starrr a:nth-child(" + data.grade + ")").click();
      reviewForm.text.value = data.text;
    });
  }
});

if (reviewForm) {
  reviewForm.addEventListener("submit", (e) => {
    e.preventDefault();
    const form = e.target;
    console.log(e.target);
    const rno = form.rno.value;

    form.grade.value = grade;
    // form.email.value = email;

    if (rno) {
      // 수정
      axios
        .put(`/reviews/${mno}/${rno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert("리뷰 수정 완료");
          // 기존 내용 삭제
          reviewForm.rno.value = "";
          // reviewForm.mid.value = "";
          // reviewForm.nickname.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

          reviewList();
        });
    } else {
      // 생성
      axios
        .post(`/reviews/${mno}`, form, {
          headers: {
            "Content-Type": "application/json",
            "X-CSRF-TOKEN": csrf,
          },
        })
        .then((res) => {
          console.log(res.data);
          alert(res.data + " 리뷰 등록");
          reviewForm.rno.value = "";
          // reviewForm.mid.value = "";
          // reviewForm.nickname.value = "";
          reviewForm.text.value = "";
          reviewForm.querySelector(".starrr a:nth-child(" + grade + ")").click();

          reviewList();
        });
    }
  });
}
