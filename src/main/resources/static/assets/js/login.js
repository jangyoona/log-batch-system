(() => {
    const form = document.getElementById("loginForm");
    if (!form) return;

    form.addEventListener("submit", (e) => {
        const u = form.username?.value?.trim();
        const p = form.password?.value?.trim();
        if (!u || !p) {
            e.preventDefault();
            toast("입력 확인", "아이디/비밀번호를 입력해 주세요.");
        }
    });

    // login result
    const url = new URL(location.href);
    if (url.searchParams.has("error")) {
        login_toast("alert--danger", "로그인 실패", "아이디/비밀번호를 확인해 주세요.");
    }
    if (url.searchParams.has("logout")) {
        login_toast("alert--ok", "로그아웃", "정상적으로 로그아웃 되었습니다.");
    }

    // login result toast
    function login_toast(className, title, msg) {
        const wraps = document.getElementsByClassName(className);
        if (!wraps) return;

        for (let wrap of wraps) {
            wrap.style.display = "block";

            setTimeout(() => {
                wrap.style.display = "none";
            }, 2800);
        }
    }
    window.toast = toast;

})();
