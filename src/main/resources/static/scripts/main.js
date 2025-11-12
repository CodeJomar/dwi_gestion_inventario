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
      const currentSearchTerm = document.getElementById('search-orders')?.value;
      if (typeof handleOrderSearch === 'function') {
        handleOrderSearch(currentSearchTerm);
      }
    } else if (newTabId === 'products') {
      const currentSearchTerm = document.getElementById('search-products')?.value;
      if (typeof handleProductSearch === 'function') {
        handleProductSearch(currentSearchTerm);
      }
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