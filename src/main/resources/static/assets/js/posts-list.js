(() => {
    // AJAX 조회 버튼 (선택)
    document.addEventListener("click", async (e) => {
        if (!e.target.matches("[data-posts-refresh]")) return;

        // 현재 URL의 쿼리 그대로 사용
        const url = new URL(location.href);
        const q = url.searchParams.get("q") || "";
        const sort = url.searchParams.get("sort") || "recent";
        const size = url.searchParams.get("size") || "10";
        const page = url.searchParams.get("page") || "0";

        try {
            // 예) 서버에 이런 API가 있으면 사용:
            // GET /api/posts?q=&sort=&size=&page=
            const data = await api(`/api/posts?q=${encodeURIComponent(q)}&sort=${encodeURIComponent(sort)}&size=${encodeURIComponent(size)}&page=${encodeURIComponent(page)}`);

            // data 예시:
            // { items:[{id,title,content,author,status,viewCount,createdAt}], totalElements, totalPages, page }
            const tbody = document.getElementById("postsTbody");
            if (!tbody) return;

            const items = data?.items || [];
            if (items.length === 0) {
                tbody.innerHTML = `<tr><td colspan="6" class="muted">게시글이 없습니다.</td></tr>`;
                toast("조회 완료", "0건");
                return;
            }

            tbody.innerHTML = items.map(p => `
        <tr>
          <td>${escapeHtml(p.id)}</td>
          <td>
            <a class="link" href="/posts/${escapeHtml(p.id)}">${escapeHtml(p.title || "")}</a>
            <div class="muted" style="font-size:12px; margin-top:4px;">${escapeHtml((p.content || "").slice(0, 80))}</div>
          </td>
          <td>${escapeHtml(p.author || "")}</td>
          <td><span class="badge">${escapeHtml(p.status || "")}</span></td>
          <td>${escapeHtml(p.viewCount ?? 0)}</td>
          <td>${escapeHtml(p.createdAt || "-")}</td>
        </tr>
      `).join("");

            toast("조회 완료", `${items.length}건 표시`);
        } catch (err) {
            toast("AJAX 조회 실패", err.message || "API가 없으면 무시해도 됩니다.");
        }
    });

    function escapeHtml(s) {
        return String(s)
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");
    }
})();
