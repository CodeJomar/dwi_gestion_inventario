let activeOrderFilters = {
  status: '',
  priority: '',
  dateFrom: ''
};

function toggleOrderFilters() {
  const panel = document.getElementById('order-filters-panel');
  if (panel.style.display === 'none') {
    panel.style.display = 'block';
  } else {
    panel.style.display = 'none';
  }
}

function applyOrderFilters() {
  activeOrderFilters.status = document.getElementById('filter-order-status').value;
  activeOrderFilters.priority = document.getElementById('filter-order-priority').value;
  activeOrderFilters.dateFrom = document.getElementById('filter-order-date-from').value;

  const searchTerm = document.getElementById('search-orders').value;
  handleOrderSearch(searchTerm);
}

function clearOrderFilters() {
  document.getElementById('filter-order-status').value = '';
  document.getElementById('filter-order-priority').value = '';
  document.getElementById('filter-order-date-from').value = '';

  activeOrderFilters = {
    status: '',
    priority: '',
    dateFrom: ''
  };

  const searchTerm = document.getElementById('search-orders').value;
  handleOrderSearch(searchTerm);
}

function renderOrdersList(orders) {
  const container = document.getElementById('orders-list-container');
  const noFound = document.getElementById('no-orders-found');

  if (!container || !noFound) return;

  if (orders.length === 0) {
    container.innerHTML = '';
    noFound.style.display = 'block';
    return;
  }

  noFound.style.display = 'none';

  container.innerHTML = orders.map(order => {
    const statusClass = getStatusBadgeClass(order.status);
    const priorityColor = getPriorityColor(order.priority);
    const formattedDate = formatDate(order.date);

    return `
  <div class="card hover:shadow-md transition-shadow">
    <div class="card-content p-6">
      <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">

        <div class="flex items-start space-x-4 flex-1 min-w-0">
          <div class="flex items-center justify-center w-12 h-12 bg-primary/10 rounded-lg flex-shrink-0">

            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-6 w-6 text-primary"><circle cx="8" cy="21" r="1" /><circle cx="19" cy="21" r="1" /><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.72a2 2 0 0 0 2-1.58L23 6H6" /></svg>
          </div>
          <div class="min-w-0">
            <div class="flex flex-wrap items-center gap-2 md:gap-3 mb-1">
              <h3 class="font-semibold text-lg truncate text-foreground">${order.id}</h3>
              <span class="badge ${statusClass}">${order.status}</span>
              <span class="text-sm font-medium ${priorityColor}">
                Prioridad ${order.priority}
              </span>
            </div>
            <p class="text-muted-foreground truncate">${order.customer}</p>
            <div class="flex items-center gap-4 mt-2 text-sm text-muted-foreground">
              <div class="flex items-center gap-1">

                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><path d="M8 2v4" /><path d="M16 2v4" /><rect width="18" height="18" x="3" y="4" rx="2" /><path d="M3 10h18" /></svg>
                ${formattedDate}
              </div>
              <span>${order.items} productos</span>
            </div>
          </div>
        </div>

        <div class="text-right flex-shrink-0 w-full md:w-auto mt-4 md:mt-0">
          <p class="text-2xl font-bold text-primary mb-2">${order.total}</p>
          <button class="btn btn-outline text-sm w-full md:w-auto">

            <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 mr-2"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z" /><circle cx="12" cy="12" r="3" /></svg>
            Ver Detalles
          </button>
        </div>
      </div>
    </div>
  </div>
  `;
  }).join('');
}

function handleOrderSearch(searchTerm) {
  const term = searchTerm.toLowerCase();
  let filteredOrders = allOrders.filter(
    (order) =>
      order.id.toLowerCase().includes(term) ||
      order.customer.toLowerCase().includes(term)
  );

  if (activeOrderFilters.status) {
    filteredOrders = filteredOrders.filter(order => order.status === activeOrderFilters.status);
  }

  if (activeOrderFilters.priority) {
    filteredOrders = filteredOrders.filter(order => order.priority === activeOrderFilters.priority);
  }

  if (activeOrderFilters.dateFrom) {
    filteredOrders = filteredOrders.filter(order => {
      const orderDate = new Date(order.date);
      const filterDate = new Date(activeOrderFilters.dateFrom);
      return orderDate >= filterDate;
    });
  }

  renderOrdersList(filteredOrders);
}

function abrirModalOrden() {
  const overlay = document.getElementById('modal-overlay');
  if (overlay) {
    overlay.classList.add('active');
  }
}

function cerrarModalOrden() {
  const overlay = document.getElementById('modal-overlay');
  if (overlay) {
    overlay.classList.remove('active');
  }
}

document.getElementById('new-order-form').addEventListener('submit', function (event) {
  event.preventDefault();

  const customer = document.getElementById('order-customer').value;
  const status = document.getElementById('order-status').value;
  const priority = document.getElementById('order-priority').value;
  const date = document.getElementById('order-date').value;
  const total = parseFloat(document.getElementById('order-total').value);
  const items = parseInt(document.getElementById('order-items').value);
  const notes = document.getElementById('order-notes').value;

  if (customer && status && priority && date && total && items) {
    const newOrder = {
      id: `ORD-${String(allOrders.length + 1).padStart(3, '0')}`,
      customer: customer,
      date: date,
      total: `$${total.toFixed(2)}`,
      items: items,
      status: status,
      priority: priority,
      notes: notes
    };

    allOrders.unshift(newOrder);

    const currentSearchTerm = document.getElementById('search-orders').value;
    handleOrderSearch(currentSearchTerm);

    cerrarModalOrden();
    this.reset();
  }
});

document.getElementById('modal-overlay').addEventListener('click', function (event) {
  if (event.target === this) {
    cerrarModalOrden();
  }
});

document.addEventListener('DOMContentLoaded', () => {
  const currentSearchTerm = document.getElementById('search-orders').value;
  handleOrderSearch(currentSearchTerm);
});

function renderOrdersList(orders) {
  const container = document.getElementById('orders-list-container');
  const noFound = document.getElementById('no-orders-found');

  if (!container || !noFound) return;

  if (orders.length === 0) {
    container.innerHTML = '';
    noFound.style.display = 'block';
    return;
  }

  noFound.style.display = 'none';

  container.innerHTML = orders.map(order => {
    const statusClass = getStatusBadgeClass(order.status);
    const priorityColor = getPriorityColor(order.priority);
    const formattedDate = formatDate(order.date);

    return `
            <div class="card hover:shadow-md transition-shadow">
                <div class="card-content p-6">
                    <div class="flex flex-col md:flex-row items-start md:items-center justify-between gap-4">

                        <div class="flex items-start space-x-4 flex-1 min-w-0">
                            <div class="flex items-center justify-center w-12 h-12 bg-primary/10 rounded-lg flex-shrink-0">
                                <!-- Icono ShoppingCart -->
                                <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-6 w-6 text-primary"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.72a2 2 0 0 0 2-1.58L23 6H6"/></svg>
                            </div>
                            <div class="min-w-0">
                                <div class="flex flex-wrap items-center gap-2 md:gap-3 mb-1">
                                    <h3 class="font-semibold text-lg truncate text-foreground">${order.id}</h3>
                                    <span class="badge ${statusClass}">${order.status}</span>
                                    <span class="text-sm font-medium ${priorityColor}">
                                        Prioridad ${order.priority}
                                    </span>
                                </div>
                                <p class="text-muted-foreground truncate">${order.customer}</p>
                                <div class="flex items-center gap-4 mt-2 text-sm text-muted-foreground">
                                    <div class="flex items-center gap-1">
                                        <!-- Icono Calendar -->
                                        <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><path d="M8 2v4"/><path d="M16 2v4"/><rect width="18" height="18" x="3" y="4" rx="2"/><path d="M3 10h18"/></svg>
                                        ${formattedDate}
                                    </div>
                                    <span>${order.items} productos</span>
                                </div>
                            </div>
                        </div>

                        <div class="text-right flex-shrink-0 w-full md:w-auto mt-4 md:mt-0">
                            <p class="text-2xl font-bold text-primary mb-2">${order.total}</p>
                            <button class="btn btn-outline text-sm w-full md:w-auto">
                                <!-- Icono Eye -->
                                <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 mr-2"><path d="M2 12s3-7 10-7 10 7 10 7-3 7-10 7-10-7-10-7Z"/><circle cx="12" cy="12" r="3"/></svg>
                                Ver Detalles
                            </button>
                        </div>
                    </div>
                </div>
            </div>
        `;
  }).join('');
}

function handleOrderSearch(searchTerm) {
  const term = searchTerm.toLowerCase();
  const filteredOrders = allOrders.filter(
    (order) =>
      order.id.toLowerCase().includes(term) ||
      order.customer.toLowerCase().includes(term)
  );
  renderOrdersList(filteredOrders);
}

function abrirModalOrden() {
  const modal = document.getElementById('new-order-modal');
  if (modal) {
    modal.style.display = 'flex';
  }
}

function cerrarModalOrden() {
  const modal = document.getElementById('new-order-modal');
  if (modal) {
    modal.style.display = 'none';
  }
}

document.getElementById('new-order-form').addEventListener('submit', function (event) {
  event.preventDefault();
  const type = document.getElementById('order-type').value;
  const status = document.getElementById('order-status').value;
  const total = parseFloat(document.getElementById('order-total').value);
  const date = document.getElementById('order-date').value;

  if (type && status && total && date) {
    const newOrder = {
      id: `ORD-${String(allOrders.length + 1).padStart(3, '0')}`,
      customer: 'Nuevo Cliente',
      date: date,
      total: `$${total.toFixed(2)}`,
      items: 1,
      status: status,
      priority: 'Media',
    };

    allOrders.unshift(newOrder);

    const currentSearchTerm = document.getElementById('search-orders').value;
    handleOrderSearch(currentSearchTerm);

    cerrarModalOrden();
    this.reset();
  }
});

document.getElementById('new-order-modal').addEventListener('click', function (event) {
  if (event.target === this) {
    cerrarModalOrden();
  }
});

document.addEventListener('DOMContentLoaded', () => {
  const currentSearchTerm = document.getElementById('search-orders').value;
  handleOrderSearch(currentSearchTerm);
});
