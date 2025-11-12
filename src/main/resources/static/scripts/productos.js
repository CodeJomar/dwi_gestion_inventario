let activeProductFilters = {
  category: '',
  status: '',
  priceMin: null,
  priceMax: null
};

function toggleProductFilters() {
  const panel = document.getElementById('product-filters-panel');
  if (panel.style.display === 'none' || panel.style.display === '') {
    panel.style.display = 'block';
  } else {
    panel.style.display = 'none';
  }
}

function applyProductFilters() {
  activeProductFilters.category = document.getElementById('filter-product-category').value;
  activeProductFilters.status = document.getElementById('filter-product-status').value;
  activeProductFilters.priceMin = parseFloat(document.getElementById('filter-product-price-min').value) || null;
  activeProductFilters.priceMax = parseFloat(document.getElementById('filter-product-price-max').value) || null;

  const searchTerm = document.getElementById('search-products').value;
  handleProductSearch(searchTerm);
}

function clearProductFilters() {
  document.getElementById('filter-product-category').value = '';
  document.getElementById('filter-product-status').value = '';
  document.getElementById('filter-product-price-min').value = '';
  document.getElementById('filter-product-price-max').value = '';

  activeProductFilters = {
    category: '',
    status: '',
    priceMin: null,
    priceMax: null
  };

  const searchTerm = document.getElementById('search-products').value;
  handleProductSearch(searchTerm);
}

function handleProductSearch(searchTerm) {
  const term = searchTerm.toLowerCase();
  const productCards = document.querySelectorAll('.product-card');
  const noFoundMessage = document.getElementById('no-products-found');
  let visibleProducts = 0;

  productCards.forEach(card => {
    const productName = card.querySelector('.product-name').textContent.toLowerCase();
    const productCategory = card.querySelector('.product-category').textContent.toLowerCase();

    const productStatusText = card.querySelector('.badge').textContent.toUpperCase();
    const statusValue = activeProductFilters.status.toUpperCase();

    const matchesSearch = productName.includes(term) || productCategory.includes(term);

    const matchesCategory = !activeProductFilters.category || productCategory.includes(activeProductFilters.category.toLowerCase());
    const matchesStatus = !statusValue || productStatusText === statusValue;

    if (matchesSearch && matchesCategory && matchesStatus) {
      card.style.display = 'block';
      visibleProducts++;
    } else {
      card.style.display = 'none';
    }
  });

  if (noFoundMessage) {
    noFoundMessage.style.display = visibleProducts === 0 ? 'block' : 'none';
  }
}

function toggleModal(show) {
  const modal = document.getElementById('add-product-modal');
  if (show) {
    modal.style.display = 'flex';
  } else {
    modal.style.display = 'none';
    document.getElementById('add-product-form').reset();
    document.getElementById('file-name-display').textContent = '';

    if (window.location.search.includes("editarId")) {
        window.history.pushState({}, '', '/auth/productos');
    }
  }
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('add-product-btn')?.addEventListener('click', () => toggleModal(true));
  document.getElementById('close-modal-btn')?.addEventListener('click', () => toggleModal(false));
  document.getElementById('cancel-btn')?.addEventListener('click', () => toggleModal(false));

  const modal = document.getElementById('add-product-modal');
  if (modal) {
    modal.addEventListener('click', (e) => {
      if (e.target.id === 'add-product-modal') {
        toggleModal(false);
      }
    });
  }

  document.querySelectorAll('.btn-edit').forEach(button => {
  });

  document.querySelectorAll('.btn-delete').forEach(button => {
    button.addEventListener('click', e => {
      const id = e.currentTarget.dataset.id;
      const nombre = e.currentTarget.dataset.nombre;
      const form = document.getElementById('form-delete-' + id);

      if (!form) {
        console.error('No se encontró el formulario de eliminación para el id:', id);
        return;
      }

      Swal.fire({
        title: `¿Desactivar "${nombre}"?`,
        text: "Esta acción cambiará el estado del producto a INACTIVO. No se borrará permanentemente.",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6',
        confirmButtonText: 'Sí, desactivar',
        cancelButtonText: 'Cancelar'
      }).then((result) => {
        if (result.isConfirmed) {
          form.submit();
        }
      });
    });
  });

  const fileInput = document.getElementById('product-image');
  const fileUploadArea = document.getElementById('file-upload-area');
  const fileNameDisplay = document.getElementById('file-name-display');

  if (fileInput && fileUploadArea) {
    fileUploadArea.addEventListener('click', () => {
      fileInput.click();
    });

    fileInput.addEventListener('change', (event) => {
      const file = event.target.files[0];
      if (file) {
        fileNameDisplay.textContent = `Archivo seleccionado: ${file.name}`;
      } else {
        fileNameDisplay.textContent = '';
      }
    });

    fileUploadArea.addEventListener('dragover', (event) => {
      event.preventDefault();
      fileUploadArea.classList.add('border-primary', 'bg-primary/10');
    });

    fileUploadArea.addEventListener('dragleave', () => {
      fileUploadArea.classList.remove('border-primary', 'bg-primary/10');
    });

    fileUploadArea.addEventListener('drop', (event) => {
      event.preventDefault();
      fileUploadArea.classList.remove('border-primary', 'bg-primary/10');

      const files = event.dataTransfer.files;
      if (files.length > 0) {
        fileInput.files = files;
        const file = files[0];
        if (file) {
          fileNameDisplay.textContent = `Archivo seleccionado: ${file.name}`;
        }
      }
    });
  }
});