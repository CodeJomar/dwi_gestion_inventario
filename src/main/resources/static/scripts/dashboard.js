function renderStatsCards() {
  const container = document.getElementById('stats-cards-container');
  if (!container) return;

  container.innerHTML = stats.map(stat => `
        <div class="card hover:shadow-lg transition-shadow">
            <div class="card-header flex flex-row items-center justify-between space-y-0 pb-2">
                <h3 class="card-title text-sm font-medium">${stat.title}</h3>
                <div class="${stat.color}">${stat.iconSvg}</div>
            </div>
            <div class="card-content pt-2">
                <div class="text-2xl font-bold text-foreground">${stat.value}</div>
                <p class="text-xs text-muted-foreground">
                    <span class="text-chart-4">${stat.change}</span> desde el mes pasado
                </p>
            </div>
        </div>
    `).join('');
}

function renderLowStockList() {
  const container = document.getElementById('low-stock-list');
  if (!container) return;

  container.innerHTML = lowStockItems.map(item => {
    const percentage = (item.stock / item.min) * 100;
    return `
            <div class="flex items-center justify-between space-x-4">
                <div class="flex-1 min-w-0">
                    <p class="text-sm font-medium truncate text-foreground">${item.name}</p>
                    <div class="flex items-center gap-2 mt-1">
                        <div class="progress-container flex-1">
                            <div class="progress-bar" style="width: ${percentage}%;"></div>
                        </div>
                        <span class="text-xs text-muted-foreground">
                            ${item.stock}/${item.min}
                        </span>
                    </div>
                </div>
                <span class="badge badge-destructive text-xs">
                    Crítico
                </span>
            </div>
        `;
  }).join('');
}

function renderRecentOrders() {
  const container = document.getElementById('recent-orders-list');
  if (!container) return;

  container.innerHTML = allOrders.slice(0, 4).map(order => {
    const badgeClass = getStatusBadgeClass(order.status);
    return `
            <div class="flex items-center justify-between">
                <div class="space-y-1">
                    <p class="text-sm font-medium text-foreground">${order.id}</p>
                    <p class="text-xs text-muted-foreground">${order.customer}</p>
                </div>
                <div class="text-right space-y-1">
                    <p class="text-sm font-medium text-foreground">${order.total}</p>
                    <span class="badge ${badgeClass} text-xs">
                        ${order.status}
                    </span>
                </div>
            </div>
        `;
  }).join('');
}

document.addEventListener('DOMContentLoaded', () => {
  renderStatsCards();
  renderLowStockList();
  renderRecentOrders();
});
