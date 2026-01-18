(() => {
    const root = document.documentElement;

    // theme
    const savedTheme = localStorage.getItem("theme");
    if (savedTheme) root.setAttribute("data-theme", savedTheme);

    document.addEventListener("click", (e) => {
        const t = e.target;

        // sidebar mobile toggle
        if (t?.matches("[data-sidebar-toggle]")) {
            document.querySelector(".sidebar")?.classList.toggle("is-open");
        }

        // theme toggle
        if (t?.matches("[data-theme-toggle]")) {
            const next = root.getAttribute("data-theme") === "light" ? "" : "light";
            if (next) root.setAttribute("data-theme", next);
            else root.removeAttribute("data-theme");
            localStorage.setItem("theme", next);
            toast("테마 변경", next ? "Light mode" : "Dark mode");
        }

        // modal close
        if (t?.matches("[data-modal-close]")) closeModal();
    });

    // highlight active nav
    const path = location.pathname;
    document.querySelectorAll("[data-nav]").forEach(a => {
        try {
            const href = a.getAttribute("href") || "";
            if (href && path === new URL(href, location.origin).pathname) a.classList.add("is-active");
        } catch { /* ignore */ }
    });

    // toast
    function toast(title, msg) {
        const wrap = document.querySelector(".toast-wrap");
        if (!wrap) return;
        const el = document.createElement("div");
        el.className = "toast";
        el.innerHTML = `<div class="toast__title">${escapeHtml(title)}</div><div class="toast__msg">${escapeHtml(msg || "")}</div>`;
        wrap.appendChild(el);
        setTimeout(() => el.remove(), 2800);
    }
    window.toast = toast;

    // modal
    let modalOkHandler = null;
    function openModal({ title = "확인", body = "정말 진행할까요?", okText = "확인", danger = true, onOk } = {}) {
        const m = document.querySelector("[data-modal]");
        if (!m) return;
        m.hidden = false;
        m.querySelector("[data-modal-title]").textContent = title;
        m.querySelector("[data-modal-body]").textContent = body;

        const okBtn = m.querySelector("[data-modal-ok]");
        okBtn.textContent = okText;
        okBtn.className = "btn " + (danger ? "btn--danger" : "btn--primary");

        modalOkHandler = onOk || null;
        okBtn.onclick = async () => {
            try { await modalOkHandler?.(); }
            finally { closeModal(); }
        };
    }
    function closeModal() {
        const m = document.querySelector("[data-modal]");
        if (!m) return;
        m.hidden = true;
        modalOkHandler = null;
    }
    window.openModal = openModal;

    // CSRF header helper (Spring Security)
    function getCsrf() {
        const token = document.querySelector('meta[name="_csrf"]')?.getAttribute("content") || "";
        const header = document.querySelector('meta[name="_csrf_header"]')?.getAttribute("content") || "";
        return { token, header };
    }

    // fetch helper (JSON)
    async function api(url, { method = "GET", body, headers } = {}) {
        const csrf = getCsrf();
        const hdr = {
            ...(headers || {}),
        };
        if (csrf.token && csrf.header) hdr[csrf.header] = csrf.token;

        const opts = { method, headers: hdr };

        if (body !== undefined) {
            opts.headers["Content-Type"] = "application/json";
            opts.body = JSON.stringify(body);
        }

        const res = await fetch(url, opts);
        const text = await res.text();
        let data = null;
        try { data = text ? JSON.parse(text) : null; } catch { data = text; }

        // 공통 401 처리
        if(res.status === 401) {
            alert("로그인 세션이 만료되었습니다. 다시 로그인해주세요.");
            location.href = "/login"
            throw err;
        }
        
        // if (!res.ok) {
        //     const msg = (data && data.message) ? data.message : (typeof data === "string" ? data : "요청 실패");
        //     throw new Error(msg);
        // }
        
        return {
            status: res.status,
            data
        };
    }
    window.api = api;

    function escapeHtml(s) {
        return String(s)
            .replaceAll("&", "&amp;")
            .replaceAll("<", "&lt;")
            .replaceAll(">", "&gt;")
            .replaceAll('"', "&quot;")
            .replaceAll("'", "&#039;");
    }
})();