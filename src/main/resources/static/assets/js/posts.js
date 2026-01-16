(() => {
  // 게시글 삭제: JS가 있으면 confirm 모달 + fetch 시도. 실패 시 기본 submit로 진행되도록 그냥 두기.
  document.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-delete-post-id]");
    if (!btn) return;

    // 버튼이 form 안에 있으므로, JS에서 막을지 말지 선택.
    // API가 없는 경우도 "작동"해야 해서: 모달 OK에서만 submit 진행.
    const form = btn.closest("form");
    if (!form) return;

    e.preventDefault();
    const id = btn.getAttribute("data-delete-post-id");

    openModal({
      title: "게시글 삭제",
      body: `게시글 #${id} 를 삭제할까요?`,
      okText: "삭제",
      danger: true,
      onOk: async () => {
        // JSON API가 있으면 비동기, 없으면 form submit
        try {
          await api(`/posts/${id}`, { method: "DELETE" });
          toast("삭제 완료", `게시글 #${id} 삭제됨`);
          location.href = "/posts/new";
        } catch (err) {
          // API 없거나 실패 시: 서버 form submit로 삭제 처리
          form.submit();
        }
      }
    });
  });

  // 게시글 저장(등록/수정): API 있으면 fetch로, 없으면 기본 submit 유지
  const form = document.getElementById("postForm");
  if (form) {
    form.addEventListener("submit", async (e) => {
      const mode = form.getAttribute("data-mode") || "new";
      const id = form.getAttribute("data-post-id");

      // 필수값 체크는 공통
      const title = form.title.value.trim();
      const userName = form.userName.value.trim();
      const content = form.content.value.trim();
      if (!title || !userName || !content) {
        e.preventDefault();
        toast("입력 확인", "제목/내용은 필수입니다.");
        return;
      }

      // API가 없는 환경에서도 작동해야 하므로: fetch는 '시도'만 하고 실패하면 submit 계속.
      // 여기서는 JS가 SPA 느낌을 주기 위해 기본 submit을 막고 fetch 성공 시 redirect.
      e.preventDefault();

      const payload = {
        title,
        userName,
        content,
        status: form.active.value
      };

      try {
        if (mode === "edit" && id) {
          await api(`/posts/${id}`, { method: "POST", body: payload });
          toast("저장 완료", "수정이 반영되었습니다.");
          location.href = `/posts/${id}`;
        } else {
          const created = await api(`/posts`, { method: "POST", body: payload });
          const newId = created?.id || "";
          toast("등록 완료", "게시글이 등록되었습니다.");
          location.href = newId ? `/posts/${newId}` : "/posts/1";
        }
      } catch (err) {
        // JSON API가 없으면 서버 form submit로 처리
        form.submit();
      }
    });
  }
})();