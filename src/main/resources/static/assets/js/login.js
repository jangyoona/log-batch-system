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

    // URL에 error 파라미터 있으면 토스트
    const url = new URL(location.href);
    if (url.searchParams.has("error")) {
        toast("로그인 실패", "아이디/비밀번호를 확인해 주세요.");
    }
    if (url.searchParams.has("logout")) {
        toast("로그아웃", "정상적으로 로그아웃 되었습니다.");
    }
})();
