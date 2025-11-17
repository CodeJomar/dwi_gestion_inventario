document.addEventListener('DOMContentLoaded', () => {

    let activeOrderFilters = {
      status: '',
      priority: '',
      dateFrom: ''
    };

    window.toggleOrderFilters = function() {
      const panel = document.getElementById('order-filters-panel');
      if (panel.style.display === 'none' || panel.style.display === '') {
        panel.style.display = 'block';
      } else {
        panel.style.display = 'none';
      }
    }

    window.applyOrderFilters = function() {
      activeOrderFilters.status = document.getElementById('filter-order-status').value;
      activeOrderFilters.priority = document.getElementById('filter-order-priority').value;
      activeOrderFilters.dateFrom = document.getElementById('filter-order-date-from').value;
      handleOrderSearch(document.getElementById('search-orders').value);
    }

    window.clearOrderFilters = function() {
      document.getElementById('filter-order-status').value = '';
      document.getElementById('filter-order-priority').value = '';
      document.getElementById('filter-order-date-from').value = '';
      activeOrderFilters = { status: '', priority: '', dateFrom: '' };
      handleOrderSearch(document.getElementById('search-orders').value);
    }

    function handleOrderSearch(searchTerm) {
      const term = searchTerm.toLowerCase();
      const orderCards = document.querySelectorAll('#orders-list-container .card');
      const noFoundMessage = document.getElementById('no-orders-found');
      let visibleOrders = 0;

      orderCards.forEach(card => {
        const orderIdElement = card.querySelector('h3');
        const customerInfoElement = card.querySelector('.text-muted-foreground');

        if (orderIdElement && customerInfoElement) {
            const orderId = orderIdElement.textContent.toLowerCase();
            const customerInfo = customerInfoElement.textContent.toLowerCase();
            const matchesSearch = orderId.includes(term) || customerInfo.includes(term);

            if (matchesSearch) {
              card.style.display = 'block';
              visibleOrders++;
            } else {
              card.style.display = 'none';
            }
        }
      });

      if (noFoundMessage) {
        noFoundMessage.style.display = visibleOrders === 0 ? 'block' : 'none';
      }
    }

    window.abrirModalOrden = function() {
      const overlay = document.getElementById('modal-overlay');
      if (overlay) {
        overlay.classList.add('active');
      }
    }

    window.cerrarModalOrden = function() {
      const overlay = document.getElementById('modal-overlay');
      if (overlay) {
        overlay.classList.remove('active');

        const form = document.getElementById('new-order-form');
        if (form) {
            form.reset();
        }

        filtrarMotivos();

        if (window.location.search.includes("abrirModal")) {
            window.history.pushState({}, '', '/auth/ordenes');
        }
      }
    }

    function abrirModalDetalle() {
        const overlay = document.getElementById('detalle-modal-overlay');
        if (overlay) {
            overlay.style.display = 'flex';
            overlay.offsetHeight;
            overlay.classList.add('active');
        }
    }

    function cerrarModalDetalle() {
        const overlay = document.getElementById('detalle-modal-overlay');
        if (overlay) {
            overlay.classList.remove('active');
            setTimeout(() => {
                overlay.style.display = 'none';
                document.getElementById('detalle-orden-titulo').textContent = 'Detalle de Orden';
                document.getElementById('detalle-orden-id').textContent = 'ID: ...';
                document.getElementById('detalle-producto-nombre').textContent = 'Cargando...';
                document.getElementById('detalle-cantidad').textContent = 'Cargando...';
                document.getElementById('detalle-tipo').textContent = 'Cargando...';
                document.getElementById('detalle-motivo').textContent = 'Cargando...';
                document.getElementById('detalle-creado-por').textContent = 'Cargando...';
                document.getElementById('detalle-fecha').textContent = 'Cargando...';
                document.getElementById('detalle-monto').textContent = 'Cargando...';
            }, 300);
        }
    }

    function poblarModalDetalle(orden, index) {
        document.getElementById('detalle-orden-titulo').textContent = `Detalle de Orden #${index}`;
        document.getElementById('detalle-orden-id').textContent = `ID: ${orden.id}`;
        document.getElementById('detalle-producto-nombre').textContent = orden.productoNombre;
        document.getElementById('detalle-cantidad').textContent = orden.cantidad + ' unidades';
        document.getElementById('detalle-tipo').textContent = orden.tipo;
        document.getElementById('detalle-motivo').textContent = orden.motivo;
        document.getElementById('detalle-creado-por').textContent = orden.creadoPor;

        const fecha = new Date(orden.fechaCreacion);
        document.getElementById('detalle-fecha').textContent = fecha.toLocaleString('es-ES', { dateStyle: 'short', timeStyle: 'short' });

        document.getElementById('detalle-monto').textContent = `S/ ${orden.monto.toFixed(2)}`;
    }

    const tipoOrdenSelect = document.getElementById('order-tipo');
    const motivoOrdenSelect = document.getElementById('order-motivo');
    const motivoOptions = motivoOrdenSelect ? motivoOrdenSelect.querySelectorAll('.motivo-option') : [];

    function filtrarMotivos() {
        if (!tipoOrdenSelect || !motivoOrdenSelect) return;

        const tipoSeleccionado = tipoOrdenSelect.value;

        motivoOrdenSelect.value = "";

        motivoOptions.forEach(option => {
            if (tipoSeleccionado === "") {
                option.style.display = 'none';
            } else if (option.dataset.tipo === tipoSeleccionado) {
                option.style.display = 'block';
            } else {
                option.style.display = 'none';
            }
        });
    }

    filtrarMotivos();

    if (tipoOrdenSelect) {
        tipoOrdenSelect.addEventListener('change', filtrarMotivos);
    }

    const form = document.getElementById('new-order-form');
    if (form) {

    }

    const overlay = document.getElementById('modal-overlay');
    if (overlay) {
        overlay.addEventListener('click', function (event) {
          if (event.target === this) {
            cerrarModalOrden();
          }
        });
    }

    const closeModalBtn = document.getElementById('close-modal-btn');
    if (closeModalBtn) {
        closeModalBtn.addEventListener('click', cerrarModalOrden);
    }

    const cancelBtn = document.getElementById('cancel-btn');
    if (cancelBtn) {
        cancelBtn.addEventListener('click', cerrarModalOrden);
    }

    document.querySelectorAll('.btn-ver-detalle').forEach(button => {
        button.addEventListener('click', async () => {
            const id = button.dataset.id;
            const index = button.dataset.index;

            abrirModalDetalle();

            try {
                const csrfToken = document.querySelector('input[name="_csrf"]').value;
                const csrfHeader = document.querySelector('input[name="_csrf"]').name;

                const response = await fetch(`/api/ordenes/${id}`, {
                    method: 'GET',
                    headers: {
                        'Accept': 'application/json',
                        [csrfHeader]: csrfToken
                    }
                });

                if (!response.ok) {
                    throw new Error('No se pudo encontrar la orden.');
                }
                const orden = await response.json();

                poblarModalDetalle(orden, index);

            } catch (err) {
                console.error(err);
                cerrarModalDetalle();
                Swal.fire({
                  icon: 'error',
                  title: 'Error',
                  text: 'No se pudieron cargar los detalles de la orden.',
                });
            }
        });
    });

    const detalleCloseBtn = document.getElementById('detalle-close-modal-btn');
    if (detalleCloseBtn) {
        detalleCloseBtn.addEventListener('click', cerrarModalDetalle);
    }

    const detalleOverlay = document.getElementById('detalle-modal-overlay');
    if (detalleOverlay) {
        detalleOverlay.addEventListener('click', function(event) {
            if (event.target === this) {
                cerrarModalDetalle();
            }
        });
    }

    const searchInput = document.getElementById('search-orders');
    const currentSearchTerm = searchInput ? searchInput.value : '';
    handleOrderSearch(currentSearchTerm);

});