const stats = [
  {
    title: "Total Productos",
    value: "1,234",
    change: "+12%",
    icon: "Package",
    color: "text-primary",
    iconSvg: '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><path d="m7.5 4.27 9 5.15"/><path d="m21 8-9 5.15-9-5.15"/><path d="M3.3 7 L12 12.5 M20.7 7 L12 12.5"/><path d="m12 22.5V13"/></svg>'
  },
  {
    title: "Órdenes Pendientes",
    value: "89",
    change: "+5%",
    icon: "ShoppingCart",
    color: "text-accent",
    iconSvg: '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.72a2 2 0 0 0 2-1.58L23 6H6"/></svg>'
  },
  {
    title: "Ingresos del Mes",
    value: "$45,231",
    change: "+18%",
    icon: "DollarSign",
    color: "text-chart-3",
    iconSvg: '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><line x1="12" x2="12" y1="2" y2="22"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/></svg>'
  },
  {
    title: "Stock Bajo",
    value: "23",
    change: "-8%",
    icon: "AlertTriangle",
    color: "text-destructive",
    iconSvg: '<svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4"><path d="m21.73 18.23-10-17a1.99 1.99 0 0 0-3.46 0l-10 17a1.99 1.99 0 0 0 1.73 2.77h20a1.99 1.99 0 0 0 1.73-2.77z"/><path d="M12 9v4"/><path d="M12 17h.01"/></svg>'
  },
];

const lowStockItems = [
  { name: "Laptop Dell XPS 13", stock: 5, min: 10 },
  { name: "Mouse Logitech MX", stock: 3, min: 15 },
  { name: "Teclado Mecánico", stock: 2, min: 8 },
  { name: 'Monitor 24"', stock: 1, min: 5 },
];

const allOrders = [
  {
    id: "ORD-001",
    customer: "Empresa ABC S.A.",
    date: "2024-01-15",
    total: "$2,340.00",
    items: 5,
    status: "Pendiente",
    priority: "Alta",
  },
  {
    id: "ORD-002",
    customer: "Tech Solutions Ltd.",
    date: "2024-01-14",
    total: "$1,890.50",
    items: 3,
    status: "Procesando",
    priority: "Media",
  },
  {
    id: "ORD-003",
    customer: "StartUp XYZ Inc.",
    date: "2024-01-13",
    total: "$3,450.75",
    items: 8,
    status: "Enviado",
    priority: "Baja",
  },
  {
    id: "ORD-004",
    customer: "Corp Industries",
    date: "2024-01-12",
    total: "$890.25",
    items: 2,
    status: "Entregado",
    priority: "Media",
  },
  {
    id: "ORD-005",
    customer: "Digital Agency Pro",
    date: "2024-01-11",
    total: "$4,567.80",
    items: 12,
    status: "Cancelado",
    priority: "Alta",
  },
  {
    id: "ORD-006",
    customer: "Retail Store Chain",
    date: "2024-01-10",
    total: "$6,789.00",
    items: 15,
    status: "Entregado",
    priority: "Alta",
  },
];

const allProducts = [
  {
    id: "PROD-001",
    name: "Laptop Dell XPS 13",
    category: "Electrónicos",
    price: "$1,299",
    stock: 5,
    status: "Bajo Stock",
    image: "https://placehold.co/48x48/0891b2/ffffff?text=LPT",
  },
  {
    id: "PROD-002",
    name: "Mouse Logitech MX Master 3",
    category: "Accesorios",
    price: "$99",
    stock: 25,
    status: "En Stock",
    image: "https://placehold.co/48x48/ec4899/ffffff?text=MOU",
  },
  {
    id: "PROD-003",
    name: "Teclado Mecánico RGB",
    category: "Accesorios",
    price: "$149",
    stock: 12,
    status: "En Stock",
    image: "https://placehold.co/48x48/10b981/ffffff?text=TCL",
  },
  {
    id: "PROD-004",
    name: 'Monitor 4K 27"',
    category: "Electrónicos",
    price: "$399",
    stock: 8,
    status: "En Stock",
    image: "https://placehold.co/48x48/3b82f6/ffffff?text=MON",
  },
  {
    id: "PROD-005",
    name: "Webcam HD 1080p",
    category: "Accesorios",
    price: "$79",
    stock: 0,
    status: "Agotado",
    image: "https://placehold.co/48x48/be123c/ffffff?text=WEB",
  },
  {
    id: "PROD-006",
    name: "Auriculares Bluetooth",
    category: "Audio",
    price: "$199",
    stock: 18,
    status: "En Stock",
    image: "https://placehold.co/48x48/9333ea/ffffff?text=AUR",
  },
];

function getStatusBadgeClass(status) {
  switch (status) {
    case "Entregado": return "badge-default";
    case "Enviado": return "badge-secondary";
    case "Procesando": return "badge-outline";
    case "Pendiente":
    case "Cancelado":
      return "badge-destructive";
    default: return "badge-outline";
  }
}

function getPriorityColor(priority) {
  switch (priority) {
    case "Alta":
      return "text-destructive";
    case "Media":
      return "text-accent";
    case "Baja":
      return "text-muted-foreground";
    default:
      return "text-muted-foreground";
  }
}

function formatDate(dateString) {
  const date = new Date(dateString);
  return date.toLocaleDateString("es-ES", { day: 'numeric', month: 'numeric', year: 'numeric' });
}

function getProductBadgeClass(status) {
  switch (status) {
    case "Agotado":
      return "badge-destructive";
    case "Bajo Stock":
      return "badge-secondary";
    case "En Stock":
      return "badge-default";
    default:
      return "badge-outline";
  }
}

function switchTab(newTabId) {
  document.querySelectorAll('.tabs-trigger').forEach(trigger => {
    trigger.classList.remove('tabs-active');
  });
  document.querySelectorAll('.tabs-content').forEach(content => {
    content.classList.remove('tabs-content-active');
  });

  const newTrigger = document.querySelector(`[data-tab="${newTabId}"]`);
  const newContent = document.querySelector(`[data-tab-content="${newTabId}"]`);

  if (newTrigger) {
    newTrigger.classList.add('tabs-active');
  }
  if (newContent) {
    newContent.classList.add('tabs-content-active');

    if (newTabId === 'orders') {
      const currentSearchTerm = document.getElementById('search-orders').value;
      handleOrderSearch(currentSearchTerm);
    } else if (newTabId === 'products') {
      const currentSearchTerm = document.getElementById('search-products').value;
      handleProductSearch(currentSearchTerm);
    }
  }
}

function toggleUserMenu() {
  const dropdown = document.getElementById('user-dropdown');
  if (dropdown) {
    dropdown.classList.toggle('show');
  }
}

document.addEventListener('click', function (event) {
  const dropdown = document.getElementById('user-dropdown');
  const button = document.querySelector('.dropdown button');
  if (dropdown && button && !button.contains(event.target) && !dropdown.contains(event.target)) {
    dropdown.classList.remove('show');
  }
});
