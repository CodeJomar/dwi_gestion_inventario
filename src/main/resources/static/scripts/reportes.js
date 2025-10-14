const reportCards = [
  {
    title: "Ventas Mensuales",
    description: "Reporte de ventas del último mes",
    value: "$45,231",
    change: "+18%",
    trend: "up",
    icon: "DollarSign",
    color: "text-report-chart-1",
  },
  {
    title: "Productos Más Vendidos",
    description: "Top 10 productos con mayor rotación",
    value: "156",
    change: "+12%",
    trend: "up",
    icon: "Package",
    color: "text-report-chart-2",
  },
  {
    title: "Órdenes Completadas",
    description: "Órdenes procesadas exitosamente",
    value: "89",
    change: "-5%",
    trend: "down",
    icon: "ShoppingCart",
    color: "text-report-chart-3",
  },
  {
    title: "Inventario Total",
    description: "Valor total del inventario actual",
    value: "$234,567",
    change: "+8%",
    trend: "up",
    icon: "BarChart3",
    color: "text-report-chart-4",
  },
];

const availableReports = [
  {
    name: "Reporte de Inventario Completo",
    description: "Estado detallado de todos los productos en stock",
    type: "PDF",
    lastGenerated: "Hace 2 horas",
    category: "Inventario",
  },
  {
    name: "Análisis de Ventas Trimestral",
    description: "Tendencias y métricas de ventas de los últimos 3 meses",
    type: "Excel",
    lastGenerated: "Ayer",
    category: "Ventas",
  },
  {
    name: "Productos con Bajo Stock",
    description: "Lista de productos que requieren reabastecimiento",
    type: "PDF",
    lastGenerated: "Hace 1 hora",
    category: "Alertas",
  },
  {
    name: "Historial de Órdenes",
    description: "Registro completo de todas las órdenes procesadas",
    type: "CSV",
    lastGenerated: "Hace 3 días",
    category: "Órdenes",
  },
  {
    name: "Análisis de Rentabilidad",
    description: "Márgenes de ganancia por producto y categoría",
    type: "Excel",
    lastGenerated: "Hace 1 semana",
    category: "Finanzas",
  },
  {
    name: "Reporte de Proveedores",
    description: "Evaluación de rendimiento de proveedores",
    type: "PDF",
    lastGenerated: "Hace 2 días",
    category: "Proveedores",
  },
];

const lucideIcons = {
  BarChart3: `<path d="M3 3v18h18"/><path d="M18 17V9"/><path d="M13 17V5"/><path d="M8 17v-3"/>`,
  PieChart: `<path d="M21.21 15.89A10 10 0 1 1 8 2.83"/><path d="M22 12A10 10 0 0 0 12 2v10z"/>`,
  Download: `<path d="M21 15v4a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2v-4"/><polyline points="7 10 12 15 17 10"/><line x1="12" x2="12" y1="15" y2="3"/>`,
  Calendar: `<rect width="18" height="18" x="3" y="4" rx="2" ry="2"/><line x1="16" x2="16" y1="2" y2="6"/><line x1="8" x2="8" y1="2" y2="6"/><line x1="3" x2="21" y1="10" y2="10"/>`,
  DollarSign: `<line x1="12" x2="12" y1="2" y2="22"/><path d="M17 5H9.5a3.5 3.5 0 0 0 0 7h5a3.5 3.5 0 0 1 0 7H6"/>`,
  Package: `<path d="m7.5 4.27 9 5.15"/><path d="m21 8-9 5.15-9-5.15"/><path d="M3.3 7 L12 12.5 M20.7 7 L12 12.5"/><path d="m12 22.5V13"/>`,
  ShoppingCart: `<circle cx="8" cy="21" r="1"/><circle cx="19" cy="21" r="1"/><path d="M2.05 2.05h2l2.66 12.42a2 2 0 0 0 2 1.58h9.72a2 2 0 0 0 2-1.58L23 6H6"/>`,
  TrendingUp: `<polyline points="18 10 12 10 12 16 17 16 17 10 22 15"/><path d="M17 7l5-5m-7 0H2"/>`,
  TrendingDown: `<polyline points="18 14 12 14 12 8 17 8 17 14 22 9"/><path d="M17 17l5 5m-7 0H2"/>`,
};

const getIconSvg = (name, classes = 'h-4 w-4', stroke = 'currentColor') => {
  const path = lucideIcons[name] || '';
  return `<svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="${stroke}" stroke-width="2" stroke-linecap="round" stroke-linejoin="round" class="${classes}">${path}</svg>`;
};

const renderKeyMetrics = () => {
  const container = document.getElementById('key-metrics');
  let html = '';

  reportCards.forEach((metric) => {
    const iconSvg = getIconSvg(metric.icon, `h-4 w-4 ${metric.color}`);
    const trendIconName = metric.trend === "up" ? "TrendingUp" : "TrendingDown";
    const trendColorClass = metric.trend === "up" ? "text-report-chart-4" : "text-destructive";
    const trendIconSvg = getIconSvg(trendIconName, `h-3 w-3 mr-1 ${trendColorClass}`, 'currentColor');

    html += `
            <div class="card p-4 hover:shadow-lg transition-shadow rounded-xl">
                <div class="flex flex-row items-center justify-between space-y-0 pb-2">
                    <h3 class="text-sm font-medium text-muted-foreground">${metric.title}</h3>
                    ${iconSvg}
                </div>
                <div>
                    <div class="text-3xl font-bold mt-2 text-foreground">${metric.value}</div>
                    <div class="flex items-center text-xs text-muted-foreground mt-2">
                        ${trendIconSvg}
                        <span class="${trendColorClass} font-medium">${metric.change}</span>
                        <span class="ml-1">desde el mes pasado</span>
                    </div>
                </div>
            </div>
        `;
  });

  container.innerHTML = html;
};

const renderAvailableReports = () => {
  const cardContainer = document.getElementById('available-reports-card');

  let headerHtml = `
        <div class="p-6">
            <div class="flex items-center justify-between flex-wrap gap-4">
                <div>
                    <h2 class="flex items-center gap-2 text-xl font-semibold text-foreground">
                        ${getIconSvg('BarChart3', 'h-5 w-5 text-primary', 'var(--primary)')}
                        Reportes Disponibles
                    </h2>
                    <p class="text-sm text-muted-foreground mt-1">Genera y descarga reportes detallados del sistema</p>
                </div>
                <button class="btn btn-outline text-sm">
                    ${getIconSvg('Calendar', 'h-4 w-4 mr-2 text-primary', 'var(--primary)')}
                    Programar Reporte
                </button>
            </div>
        </div>
    `;

  let reportsListHtml = '<div class="p-6 pt-0">';
  reportsListHtml += '<div class="grid gap-4 md:grid-cols-2">';

  availableReports.forEach((report) => {
    let typeBadgeClass = 'bg-gray-200 text-gray-700 border-gray-300';
    if (report.type === 'PDF') typeBadgeClass = 'bg-red-100 text-red-800 border-red-200';
    if (report.type === 'Excel') typeBadgeClass = 'bg-green-100 text-green-800 border-green-200';
    if (report.type === 'CSV') typeBadgeClass = 'bg-blue-100 text-blue-800 border-blue-200';

    reportsListHtml += `
            <div class="card border-l-4 border-l-primary/20 hover:border-l-primary transition-colors p-4">
                <div class="flex items-start justify-between mb-3">
                    <div class="flex-1 pr-4">
                        <h4 class="font-bold text-base mb-1 text-foreground">${report.name}</h4>
                        <p class="text-xs text-muted-foreground mb-2">${report.description}</p>
                        <div class="flex items-center gap-2 mt-2">
                            <span class="badge badge-outline">
                                ${report.category}
                            </span>
                            <span class="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium border ${typeBadgeClass}">
                                ${report.type}
                            </span>
                        </div>
                    </div>
                    <button class="flex items-center justify-center h-9 px-3 bg-white text-muted-foreground border border-border hover:bg-card rounded-lg shadow-sm font-medium text-xs transition-colors whitespace-nowrap">
                        ${getIconSvg('Download', 'h-4 w-4 mr-1 text-muted-foreground', 'currentColor')}
                        Descargar
                    </button>
                </div>
                <p class="text-xs text-muted-foreground mt-2">Última generación: ${report.lastGenerated}</p>
            </div>
        `;
  });

  reportsListHtml += '</div></div>';
  cardContainer.innerHTML = headerHtml + reportsListHtml;
};

const renderQuickActions = () => {
  const container = document.getElementById('quick-actions');
  let html = '';

  const actions = [
    {
      icon: 'PieChart',
      title: 'Análisis Visual',
      description: 'Gráficos interactivos de tendencias',
      color: 'text-primary',
      buttonText: 'Ver Gráficos',
    },
    {
      icon: 'BarChart3',
      title: 'Reporte Personalizado',
      description: 'Crea reportes con filtros específicos',
      color: 'text-accent',
      buttonText: 'Crear Reporte',
    },
    {
      icon: 'Calendar',
      title: 'Reportes Automáticos',
      description: 'Programa reportes recurrentes',
      color: 'text-report-chart-3',
      buttonText: 'Configurar',
    },
  ];

  actions.forEach(action => {
    const iconSvg = getIconSvg(action.icon, `h-8 w-8 ${action.color} mx-auto mb-3`, 'currentColor');

    html += `
            <div class="card text-center p-6 hover:shadow-xl transition-shadow cursor-pointer flex flex-col justify-between items-center">
                <div class="w-full">
                    ${iconSvg}
                    <h3 class="font-bold text-lg mb-2 text-foreground">${action.title}</h3>
                    <p class="text-sm text-muted-foreground mb-4">${action.description}</p>
                </div>
                <button class="btn btn-outline text-sm w-full sm:w-auto">
                    ${action.buttonText}
                </button>
            </div>
        `;
  });

  container.innerHTML = html;
};

document.addEventListener('DOMContentLoaded', () => {
  renderKeyMetrics();
  renderAvailableReports();
  renderQuickActions();
});
