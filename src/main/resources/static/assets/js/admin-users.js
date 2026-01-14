(() => {
  // 관리자 삭제: 모달 + fetch 시도, 실패 시 form submit
  document.addEventListener("click", (e) => {
    const btn = e.target.closest("[data-delete-admin-id]");
    if (!btn) return;
    const form = btn.closest("form");
    if (!form) return;

    e.preventDefault();
    const id = btn.getAttribute("data-delete-admin-id");

    openModal({
      title: "관리자 삭제",
      body: `관리자 #${id} 를 삭제할까요?`,
      okText: "삭제",
      danger: true,
      onOk: async () => {
        try {
          await api(`/admin/users/${id}`, { method: "DELETE" });
          toast("삭제 완료", "관리자 삭제됨");
          location.reload();
        } catch (err) {
          form.submit();
        }
      }
    });
  });

  document.addEventListener("click", async (e) => {
    if (!e.target.matches("[data-admin-refresh]")) return;
    const q = (document.getElementById("adminSearch")?.value || "").trim();
    toast("조회", q ? `검색어: ${q} (API 연결 필요)` : "전체 조회 (API 연결 필요)");
  });

  // 관리자 저장: API 있으면 fetch, 없으면 submit
  const form = document.getElementById("adminForm");
  if (form) {
    form.addEventListener("submit", async (e) => {
      const mode = form.getAttribute("data-mode") || "new";
      const id = form.getAttribute("data-admin-id");

      const username = form.username.value.trim();
      const name = form.name.value.trim();
      if (!username || !name) {
        e.preventDefault();
        toast("입력 확인", "아이디/이름은 필수입니다.");
        return;
      }

      e.preventDefault();

      const payload = {
        username,
        name,
        role: form.role.value,
        enabled: form.enabled.value === "true",
        password: form.password.value
      };

      try {
        if (mode === "edit" && id) {
          await api(`/admin/users/${id}`, { method: "PUT", body: payload });
          toast("저장 완료", "관리자 수정 완료");
        } else {
          await api(`/admin/users`, { method: "POST", body: payload });
          toast("등록 완료", "관리자 추가 완료");
        }
        location.href = "/admin/users";
      } catch (err) {
        // API 없으면 일반 submit
        form.submit();
      }
    });
  }
})();