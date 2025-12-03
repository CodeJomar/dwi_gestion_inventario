document.addEventListener("DOMContentLoaded", () => {
    cargarOrdenesRecientes();
    productosBajoStock();
    cargarTarjetasDashboard();
});

function cargarTarjetasDashboard() {
    fetch("/auth/dashboard/tarjetas")
        .then(res => res.json())
        .then(data => {

            document.getElementById("card-total-productos").textContent =
                data.totalProductosActivos;

            document.getElementById("card-ingresos-anuales").textContent =
                data.ingresosAnuales;

            document.getElementById("card-ingresos-mensuales").textContent =
                data.ingresosMensuales;

            document.getElementById("card-stock-bajo").textContent =
                data.productosStockBajo;

        })
        .catch(err => console.error("Error cargando tarjetas:", err));
}

function cargarOrdenesRecientes() {
    fetch("/auth/dashboard/ordenes-recientes")
        .then(res => res.json())
        .then(data => {
            const contenedor = document.getElementById("recent-orders-list");
            contenedor.innerHTML = "";

            data.forEach(o => {
                contenedor.innerHTML += `
                    <div class="flex items-center justify-between">
                        <div class="space-y-1">
                            <p class="text-sm font-medium text-foreground">${o.id}</p>
                            <p class="text-xs text-muted-foreground">${o.productoNombre}</p>
                        </div>

                        <div class="text-right space-y-1">
                            <p class="text-sm font-medium text-foreground">${o.monto}</p>
                            <span class="badge ${o.cssClass} text-xs">${o.tipo}</span>
                        </div>
                    </div>
                `;
            });
        })
        .catch(err => console.error("Error cargando órdenes recientes:", err));
}

function productosBajoStock() {
    fetch("/auth/dashboard/productos-stock-bajo")
        .then(res => res.json())
        .then(data => {

            const cont = document.getElementById("low-stock-list");
            cont.innerHTML = "";

            // Limitar a máximo 4 productos
            const productos = data.slice(0, 4);

            productos.forEach(p => {
                cont.innerHTML += `
                <div class="flex items-center justify-between space-x-4">

                    <div class="flex-1 min-w-0">
                        <p class="text-sm font-medium truncate text-foreground">${p.nombre}</p>

                        <div class="flex items-center gap-2 mt-1">
                            <div class="progress-container flex-1">
                                <div class="progress-bar" style="width: ${p.porcentaje}%"></div>
                            </div>
                            <span class="text-xs text-muted-foreground">${p.stockActual}/${p.stockMaximo}</span>
                        </div>
                    </div>

                    <span class="badge ${p.cssClass} text-xs">${p.nivel}</span>
                </div>
            `;
            });

            // Si no hay productos críticos o bajos
            if (productos.length === 0) {
                cont.innerHTML = `
                <p class="text-sm text-muted-foreground">No hay productos con stock bajo</p>
            `;
            }
        })
        .catch(err => {
            console.error(err);
        });
}