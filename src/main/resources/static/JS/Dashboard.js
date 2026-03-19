function qs(sel, root = document) {
    return root.querySelector(sel);
}

function qsa(sel, root = document) {
    return Array.from(root.querySelectorAll(sel));
}

function clamp(n, min, max) {
    return Math.max(min, Math.min(max, n));
}

function formatCompact(n) {
    const num = Number(n);
    if (!Number.isFinite(num)) return '--';
    if (Math.abs(num) >= 1_000_000) return (num / 1_000_000).toFixed(1) + 'm';
    if (Math.abs(num) >= 1_000) return (num / 1_000).toFixed(1) + 'k';
    return Math.round(num).toString();
}

function formatDateLabel(yyyyMMdd) {
    if (!yyyyMMdd || yyyyMMdd.length !== 8) return '';
    const dd = yyyyMMdd.substring(6, 8);
    const mm = yyyyMMdd.substring(4, 6);
    return `${dd}/${mm}`;
}

function safeLucide() {
    if (window.lucide && typeof window.lucide.createIcons === 'function') {
        window.lucide.createIcons();
    }
}

async function fetchDashboard(endpoint) {
    const res = await fetch(endpoint, {
        method: 'GET',
        headers: {
            'Accept': 'application/json'
        }
    });
    if (!res.ok) {
        throw new Error(`Dashboard API failed: ${res.status}`);
    }
    return await res.json();
}

function normalizeOverviewDaily(overviewDaily) {
    if (!Array.isArray(overviewDaily)) return [];

    const out = overviewDaily.map((d) => {
        const date = d?.date;
        return {
            rawDate: date,
            label: formatDateLabel(date),
            users: Number(d?.users ?? 0),
            pageViews: Number(d?.pageViews ?? 0),
            sessions: Number(d?.sessions ?? 0),
            avgSessionDurationSec: Number(d?.avgSessionDuration ?? 0),
            engagementRate: Number(d?.engagementRate ?? 0)
        };
    });

    out.sort((a, b) => String(a.rawDate || '').localeCompare(String(b.rawDate || '')));
    return out;
}

function normalizeOverview(overview, overviewDaily) {
    const fallback = Array.isArray(overviewDaily) && overviewDaily.length > 0
        ? overviewDaily[overviewDaily.length - 1]
        : null;
    const raw = overview && typeof overview === 'object' ? overview : {};
    return {
        users: Number(raw.users ?? fallback?.users ?? 0),
        pageViews: Number(raw.pageViews ?? fallback?.pageViews ?? 0),
        sessions: Number(raw.sessions ?? fallback?.sessions ?? 0),
        avgSessionDurationSec: Number(raw.avgSessionDuration ?? fallback?.avgSessionDurationSec ?? 0),
        engagementRate: Number(raw.engagementRate ?? fallback?.engagementRate ?? 0)
    };
}

const METRICS = [
    {
        key: 'pageViews',
        jsonKey: 'pageViews',
        label: 'Lượt xem trang',
        color: '#6366f1',
        unit: '',
        formatValue: (v) => Number(v).toLocaleString('vi-VN')
    },
    {
        key: 'users',
        jsonKey: 'users',
        label: 'Người dùng',
        color: '#22d3ee',
        unit: '',
        formatValue: (v) => Number(v).toLocaleString('vi-VN')
    },
    {
        key: 'avgTime',
        jsonKey: 'avgSessionDuration',
        label: 'Thời gian TB',
        color: '#f59e0b',
        unit: 'phút',
        formatValue: (sec) => `${(Number(sec) / 60).toFixed(1)} phút`
    },
    {
        key: 'engagement',
        jsonKey: 'engagementRate',
        label: 'Tỉ lệ tương tác',
        color: '#ef4444',
        unit: '%',
        formatValue: (rate) => `${(Number(rate) * 100).toFixed(1)}%`
    }
];

function renderHeaderDate() {
    const el = qs('#date-display');
    if (!el) return;
    const today = new Date();
    const opts = {weekday: 'long', day: 'numeric', month: 'long', year: 'numeric'};
    el.textContent = today.toLocaleDateString('vi-VN', opts);
}

function renderMetricCards({overview, growth}) {
    for (const m of METRICS) {
        const valueEl = document.getElementById(`value-${m.key}`);
        const changeEl = document.getElementById(`change-${m.key}`);
        if (!valueEl || !changeEl) continue;

        let rawValue;
        if (m.key === 'avgTime') rawValue = overview.avgSessionDurationSec;
        else if (m.key === 'engagement') rawValue = overview.engagementRate;
        else rawValue = overview[m.key] ?? 0;

        valueEl.textContent = m.formatValue(rawValue);

        const g = Number(growth?.[m.jsonKey] ?? 0);
        const isUp = g >= 0;
        const pctText = `${Math.abs(g).toFixed(1)}%`;
        changeEl.textContent = `${isUp ? '↑' : '↓'} ${pctText}`;
        changeEl.style.color = isUp ? '#34d399' : '#f87171';
        changeEl.style.background = isUp ? 'rgba(52,211,153,0.1)' : 'rgba(248,113,113,0.1)';
    }
}

function renderChart({daily, metricKey, rangeDays}) {
    const metric = METRICS.find((m) => m.key === metricKey) || METRICS[0];

    const yAxis = qs('#y-axis');
    const barsContainer = qs('#chart-bars');
    if (!yAxis || !barsContainer) return;

    const values = daily.map((d) => {
        if (metric.key === 'avgTime') return d.avgSessionDurationSec / 60;
        if (metric.key === 'engagement') return d.engagementRate * 100;
        return d[metric.key] ?? 0;
    });

    const maxVal = Math.max(0, ...values);

    yAxis.innerHTML = '';
    for (let i = 4; i >= 0; i--) {
        const val = maxVal === 0 ? 0 : (maxVal / 4) * i;
        const label = document.createElement('span');
        label.className = 'text-xs';
        label.style.color = 'rgba(255,255,255,0.25)';
        label.textContent = metric.key === 'engagement' ? `${Math.round(val)}%` : formatCompact(val);
        yAxis.appendChild(label);
    }

    barsContainer.innerHTML = '';
    daily.forEach((d, i) => {
        const v = values[i];
        const pct = maxVal > 0 ? (v / maxVal) * 100 : 0;
        const wrapper = document.createElement('div');
        wrapper.className = 'flex-1 flex flex-col items-center h-full relative min-w-0';

        const displayVal =
            metric.key === 'engagement'
                ? `${v.toFixed(1)}%`
                : metric.key === 'avgTime'
                    ? `${v.toFixed(1)} phút`
                    : formatCompact(v);

        const labelText = d.label;

        wrapper.innerHTML = `
          <div class="chart-bar-slot w-full flex-1 flex items-end">
            <div class="chart-bar w-full relative" style="height: ${Math.max(pct, 3)}%; background: ${metric.color}; opacity: 0.8;">
              <div class="tooltip-box" style="background: #1e293b; border: 1px solid rgba(255,255,255,0.1);">
                <p class="font-semibold text-white text-sm">${displayVal}</p>
                <p style="color:rgba(255,255,255,0.45); font-size: 11px;">${metric.label} · ${d.label}</p>
              </div>
            </div>
          </div>
          <span class="chart-date-label text-center mt-1.5 block" style="font-size:10px; color:rgba(255,255,255,0.3);">${labelText}</span>
        `;
        barsContainer.appendChild(wrapper);
    });
}

function pickSourceIcon(sourceName) {
    const s = (sourceName || '').toLowerCase();
    if (s.includes('organic') && s.includes('search')) return 'search';
    if (s.includes('paid') && s.includes('search')) return 'megaphone';
    if (s.includes('social')) return 'share-2';
    if (s.includes('direct')) return 'link';
    if (s.includes('referral')) return 'external-link';
    if (s.includes('email')) return 'mail';
    return 'globe';
}

function renderSourcesAndDonut({sources}) {
    const listEl = qs('#sources-list');
    const totalEl = qs('#total-visits');
    const donutRoot = qs('#donut-segments');
    if (!listEl || !totalEl || !donutRoot) return;

    const normalized = (Array.isArray(sources) ? sources : []).map((s) => ({
        source: String(s?.source ?? ''),
        sessions: Number(s?.sessions ?? 0)
    })).filter((s) => s.sessions > 0);

    const totalSessions = normalized.reduce((acc, s) => acc + s.sessions, 0);
    totalEl.textContent = formatCompact(totalSessions);

    // top 4 sources; bucket remainder into "Khac"
    const top = normalized.slice(0, 4);
    const rest = normalized.slice(4);
    const restSessions = rest.reduce((acc, s) => acc + s.sessions, 0);
    const entries = restSessions > 0 ? [...top.slice(0, 3), {source: 'Khac', sessions: restSessions}] : top;

    const palette = ['#6366f1', '#22d3ee', '#f59e0b', '#ef4444'];

    // List
    listEl.innerHTML = '';
    entries.forEach((e, idx) => {
        const pct = totalSessions > 0 ? (e.sessions / totalSessions) * 100 : 0;
        const color = palette[idx % palette.length];
        const icon = pickSourceIcon(e.source);
        const item = document.createElement('div');
        item.className = 'flex items-center gap-3';
        item.innerHTML = `
          <div class="w-8 h-8 rounded-lg flex items-center justify-center flex-shrink-0" style="background: ${color}20;">
            <i data-lucide="${icon}" style="width:14px;height:14px;color:${color};"></i>
          </div>
          <div class="flex-1 min-w-0">
            <div class="flex items-center justify-between mb-1.5">
              <span class="text-xs font-medium text-white truncate">${e.source}</span>
              <span class="text-xs font-semibold" style="color:${color};">${pct.toFixed(1)}%</span>
            </div>
            <div class="w-full rounded-full" style="height:6px; background:rgba(255,255,255,0.06);">
              <div class="source-bar-fill" style="width:${clamp(pct, 0, 100)}%; background:${color};"></div>
            </div>
          </div>
        `;
        listEl.appendChild(item);
    });

    // Donut segments
    donutRoot.innerHTML = '';
    const circumference = 2 * Math.PI * 48;
    let offset = 0;
    entries.forEach((e, idx) => {
        const pct = totalSessions > 0 ? (e.sessions / totalSessions) * 100 : 0;
        const len = (pct / 100) * circumference;
        const circle = document.createElementNS('http://www.w3.org/2000/svg', 'circle');
        circle.setAttribute('cx', '60');
        circle.setAttribute('cy', '60');
        circle.setAttribute('r', '48');
        circle.setAttribute('fill', 'none');
        circle.setAttribute('stroke', palette[idx % palette.length]);
        circle.setAttribute('stroke-width', '14');
        circle.setAttribute('stroke-linecap', 'round');
        circle.setAttribute('stroke-dasharray', `${len} ${circumference - len}`);
        circle.setAttribute('stroke-dashoffset', `${-offset}`);
        offset += len;
        donutRoot.appendChild(circle);
    });
}

function setActiveRangeButton(rangeDays) {
    qsa('.tab-btn').forEach((btn) => {
        const isActive = Number(btn.dataset.range) === Number(rangeDays);
        btn.style.background = isActive ? '#6366f1' : 'transparent';
        btn.style.color = isActive ? '#fff' : 'rgba(255,255,255,0.4)';
        btn.classList.toggle('active-tab', isActive);
    });
}

function setActiveMetricButton(metricKey) {
    const metric = METRICS.find((m) => m.key === metricKey) || METRICS[0];
    qsa('.chart-tab-btn').forEach((btn) => {
        const isActive = btn.dataset.metric === metricKey;
        btn.style.background = isActive ? `${metric.color}22` : 'transparent';
        btn.style.color = isActive ? metric.color : 'rgba(255,255,255,0.4)';
    });
}

export async function initDashboard(options = {}) {
    const endpoint = options.endpoint || '/api/analytics/dashboard';

    renderHeaderDate();

    let apiData;
    try {
        apiData = await fetchDashboard(endpoint);
    } catch (e) {
        console.error(e);
        safeLucide();
        return;
    }

    const overviewDaily = normalizeOverviewDaily(apiData?.overview?.overviewDaily);
    const overview = normalizeOverview(apiData?.overview?.overview, overviewDaily);
    const growth = apiData?.overview?.growth || {};
    const sources = apiData?.sources || [];

    // Default UI state
    let rangeDays = 7;
    let metricKey = 'pageViews';

    const getDailyForRange = () => {
        const want = rangeDays === 30 ? 30 : 7;
        return overviewDaily.slice(-want);
    };

    const renderAll = () => {
        const daily = getDailyForRange();
        renderMetricCards({overview, growth});
        renderChart({daily, metricKey, rangeDays});
        renderSourcesAndDonut({sources});
        setActiveRangeButton(rangeDays);
        setActiveMetricButton(metricKey);
        safeLucide();
    };

    // Wire UI events (no inline onclick required)
    qsa('.tab-btn').forEach((btn) => {
        btn.addEventListener('click', () => {
            const next = Number(btn.dataset.range);
            if (next !== 7 && next !== 30) return;
            rangeDays = next;
            renderAll();
        });
    });

    qsa('.chart-tab-btn').forEach((btn) => {
        btn.addEventListener('click', () => {
            const next = String(btn.dataset.metric || '');
            if (!METRICS.some((m) => m.key === next)) return;
            metricKey = next;
            renderAll();
        });
    });

    renderAll();
}

// Backward compatibility: old template imported this name.
export async function UpdateMetricCards() {
    return initDashboard({endpoint: '/api/analytics/dashboard'});
}
