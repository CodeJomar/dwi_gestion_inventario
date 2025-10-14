let activeProductFilters = {
  category: '',
  status: '',
  priceMin: null,
  priceMax: null
};

function toggleProductFilters() {
  const panel = document.getElementById('product-filters-panel');
  if (panel.style.display === 'none') {
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

function renderProductsGrid(products) {
  const container = document.getElementById('products-grid-container');
  const noFound = document.getElementById('no-products-found');

  if (!container || !noFound) return;

  if (products.length === 0) {
    container.innerHTML = '';
    noFound.style.display = 'block';
    return;
  }

  noFound.style.display = 'none';

  container.innerHTML = products.map(product => {
    const badgeClass = getProductBadgeClass(product.status);
    const stockText = product.stock === 0 ? "Agotado" : `Stock: ${product.stock} unidades`;

    return `
                    <div class="card hover:shadow-md transition-shadow">
                        <div class="card-header pb-3">
                            <div class="flex items-start justify-between">
                                <div class="flex items-center space-x-3">
                                    
                                    <img
                                        src="${product.image}"
                                        alt="${product.name}"
                                        onerror="this.onerror=null;this.src='https://placehold.co/48x48/ccc/666?text=N/A';"
                                        class="w-12 h-12 rounded-lg object-cover bg-muted"
                                    />
                                    <div>
                                        <h4 class="text-base font-semibold text-foreground">${product.name}</h4>
                                        <p class="text-sm text-muted-foreground">${product.category}</p>
                                    </div>
                                </div>
                                <span class="badge ${badgeClass}">
                                    ${product.status}
                                </span>
                            </div>
                        </div>
                        <div class="card-content pt-0">
                            <div class="flex items-center justify-between mb-4">
                                <div>
                                    <p class="text-2xl font-bold text-primary">${product.price}</p>
                                    <p class="text-sm text-muted-foreground">${stockText}</p>
                                </div>
                            </div>
                            <div class="flex gap-2">
                                <button class="btn btn-outline text-sm flex-1 bg-transparent">
                                    
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 mr-2"><path d="M17 3a2.85 2.83 0 1 1 4 4L7.5 20.5 2 22l1.5-5.5L17 3z"/></svg>
                                    Editar
                                </button>
                                <button class="btn btn-outline text-sm flex-1 bg-transparent">
                                    
                                    <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="h-4 w-4 mr-2"><path d="M3 6h18"/><path d="M19 6v14c0 1-1 2-2 2H7c-1 0-2-1-2-2V6"/><path d="M8 6V4c0-1 1-2 2-2h4c1 0 2 1 2 2v2"/></svg>
                                    Eliminar
                                </button>
                            </div>
                        </div>
                    </div>
                `;
  }).join('');
}

function handleProductSearch(searchTerm) {
  const term = searchTerm.toLowerCase();
  let filteredProducts = allProducts.filter(
    (product) =>
      product.name.toLowerCase().includes(term) ||
      product.category.toLowerCase().includes(term)
  );

  if (activeProductFilters.category) {
    filteredProducts = filteredProducts.filter(product => product.category === activeProductFilters.category);
  }

  if (activeProductFilters.status) {
    filteredProducts = filteredProducts.filter(product => product.status === activeProductFilters.status);
  }

  if (activeProductFilters.priceMin !== null) {
    filteredProducts = filteredProducts.filter(product => {
      const price = parseFloat(product.price.replace('$', '').replace(',', ''));
      return price >= activeProductFilters.priceMin;
    });
  }

  if (activeProductFilters.priceMax !== null) {
    filteredProducts = filteredProducts.filter(product => {
      const price = parseFloat(product.price.replace('$', '').replace(',', ''));
      return price <= activeProductFilters.priceMax;
    });
  }

  renderProductsGrid(filteredProducts);
}

function toggleModal(show) {
  const modal = document.getElementById('add-product-modal');
  if (show) {
    modal.style.display = 'flex';
  } else {
    modal.style.display = 'none';
    document.getElementById('add-product-form').reset();
  }
}

document.addEventListener('DOMContentLoaded', () => {
  document.getElementById('add-product-btn').addEventListener('click', () => toggleModal(true));

  document.getElementById('close-modal-btn').addEventListener('click', () => toggleModal(false));

  document.getElementById('cancel-btn').addEventListener('click', () => toggleModal(false));

  document.getElementById('add-product-modal').addEventListener('click', (e) => {
    if (e.target.id === 'add-product-modal') {
      toggleModal(false);
    }
  });

  document.getElementById('add-product-form').addEventListener('submit', (e) => {
    e.preventDefault();
    toggleModal(false);
    alert('Producto agregado (simulado)');
  });

  const currentSearchTerm = document.getElementById('search-products').value;
  handleProductSearch(currentSearchTerm);
});

function renderProductsGrid(products) {
  const container = document.getElementById('products-grid-container');
  const noFound = document.getElementById('no-products-found');

  if (!container || !noFound) return;

  if (products.length === 0) {
    container.innerHTML = '';
    noFound.style.display = 'block';
    return;
  }

  noFound.style.display = 'none';

  container.innerHTML = products.map(product => {
    const badgeClass = getProductBadgeClass(product.status);
    const stockText = product.stock === 0 ? "Agotado" : `Stock: ${product.stock} unidades`;

    return `
            <div class="card hover:shadow-md transition-shadow">
                <div class="card-header pb-3">
                    <div class="flex items-start justify-between">
                        <div class="flex items-center space-x-3">
                            <!-- Imagen o Placeholder (usando placehold.co como fallback) -->
                            <img
                                src="${product.image}"
                                alt="${product.name}"
                                onerror="this.onerror=null;this.src='https:
                                class="w-12 h-12 rounded-lg object-cover bg-muted"
                            />
                            <div>
                                <h4 class="text-base font-semibold text-foreground">${product.name}</h4>
                                <p class="text-sm text-muted-foreground">${product.category}</p>
                            </div>
                        </div>
                        <span class="badge ${badgeClass}">
                            ${product.status}
                        </span>
                    </div>
                </div>
                <div class="card-content pt-0">
                    <div class="flex items-center justify-between mb-4">
                        <div>
                            <p class="text-2xl font-bold text-primary">${product.price}</p>
                            <p class="text-sm text-muted-foreground">${stockText}</p>
                        </div>
                    </div>
                    <div class="flex gap-2">
                        <button class="btn btn-outline text-sm flex-1 bg-transparent">
                            <!-- Icono Edit -->
                            <svg xmlns="http:
                            Editar
                        </button>
                        <button class="btn btn-outline text-sm flex-1 bg-transparent">
                            <!-- Icono Trash2 -->
                            <svg xmlns="http:
                            Eliminar
                        </button>
                    </div>
                </div>
            </div>
        `;
  }).join('');
}

function handleProductSearch(searchTerm) {
  const term = searchTerm.toLowerCase();
  const filteredProducts = allProducts.filter(
    (product) =>
      product.name.toLowerCase().includes(term) ||
      product.category.toLowerCase().includes(term)
  );
  renderProductsGrid(filteredProducts);
}

function toggleModal(show) {
  const modal = document.getElementById('add-product-modal');
  if (show) {
    modal.style.display = 'flex';
  } else {
    modal.style.display = 'none';

    document.getElementById('add-product-form').reset();
  }
}

document.addEventListener('DOMContentLoaded', () => {

  document.getElementById('add-product-btn').addEventListener('click', () => toggleModal(true));

  document.getElementById('close-modal-btn').addEventListener('click', () => toggleModal(false));

  document.getElementById('cancel-btn').addEventListener('click', () => toggleModal(false));

  document.getElementById('add-product-modal').addEventListener('click', (e) => {
    if (e.target.id === 'add-product-modal') {
      toggleModal(false);
    }
  });

  document.getElementById('add-product-form').addEventListener('submit', (e) => {
    e.preventDefault();

    toggleModal(false);

    alert('Producto agregado (simulado)');
  });

  const currentSearchTerm = document.getElementById('search-products').value;
  handleProductSearch(currentSearchTerm);
});
