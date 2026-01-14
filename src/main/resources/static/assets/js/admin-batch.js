(() => {
  document.addEventListener("click", async (e) => {
    if (e.target.matches("[data-batch-refresh]")) {
      try {
        // 예: const rows = await api("/admin/api/batch/status");
        toast("새로고침", "SSR 페이지라면 새로고침(F5)도 OK. API 연결 시 여기서 갱신하세요.");
      } catch (err) {
        toast("실패", err.message || "error");
      }
    }

    if (e.target.matches("[data-dashboard-refresh]")) {
      try {
        toast("새로고침", "API 연결 시 차트 데이터 재로딩 구현 가능");
      } catch (err) {
        toast("실패", err.message || "error");
      }
    }
  });

  const dash = window.__BATCH_DASHBOARD__;
  const trendEl = document.getElementById("chartTrend");
  if (dash && trendEl && window.Chart) {
    new Chart(trendEl, {
      type: "line",
      data: {
        labels: dash.trendLabels || [],
        datasets: [
          { label: "SUCCESS", data: dash.trendSuccess || [], tension: 0.3 },
          { label: "FAIL", data: dash.trendFail || [], tension: 0.3 }
        ]
      },
      options: { responsive: true }
    });
  }

  const durEl = document.getElementById("chartDuration");
  if (dash && durEl && window.Chart) {
    new Chart(durEl, {
      type: "bar",
      data: {
        labels: dash.durationLabels || [],
        datasets: [{ label: "avg(ms)", data: dash.durationValues || [] }]
      },
      options: { responsive: true }
    });
  }
})();