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

    const matchesSearch = productName.includes(term) || productCategory.includes(term);
    const matchesCategory = !activeProductFilters.category || productCategory.includes(activeProductFilters.category.toLowerCase());
    const matchesStatus = !activeProductFilters.status || card.querySelector('.badge').textContent.toLowerCase().includes(activeProductFilters.status.toLowerCase());

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
  
  document.getElementById('add-product-form')?.addEventListener('submit', (e) => {
    e.preventDefault();
    toggleModal(false);
    alert('Producto agregado (simulado)');
  });

  document.querySelectorAll('.btn-edit').forEach(button => {
    button.addEventListener('click', e => {
      const card = e.currentTarget.closest('.product-card');
      const productName = card.querySelector('.product-name').textContent;
      alert(`Editar producto: ${productName}`);
    });
  });

  document.querySelectorAll('.btn-delete').forEach(button => {
    button.addEventListener('click', e => {
      const card = e.currentTarget.closest('.product-card');
      const productName = card.querySelector('.product-name').textContent;
      if (confirm(`¿Estás seguro de que deseas eliminar "${productName}"?`)) {
        card.remove();
        handleProductSearch(document.getElementById('search-products').value);
      }
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