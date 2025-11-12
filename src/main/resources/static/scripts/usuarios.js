// Declare global variables for jQuery and Swal
const $ = window.$
const Swal = window.Swal

// Initialize DataTable
let table

$(document).ready(() => {
  table = $("#tablaUsuarios").DataTable({
    language: {
      sProcessing: "Procesando...",
      sLengthMenu: "Mostrar _MENU_ registros",
      sZeroRecords: "No se encontraron resultados",
      sEmptyTable: "Ningún dato disponible en esta tabla",
      sInfo: "Mostrando registros del _START_ al _END_ de un total de _TOTAL_ registros",
      sInfoEmpty: "Mostrando registros del 0 al 0 de un total de 0 registros",
      sInfoFiltered: "(filtrado de un total de _MAX_ registros)",
      sSearch: "Buscar:",
      oPaginate: {
        sFirst: "Primero",
        sPrevious: "Anterior",
        sNext: "Siguiente",
        sLast: "Último",
      },
    },
    paging: true,
    pageLength: 10,
    ordering: true,
    searching: true,
    columnDefs: [{ orderable: false, targets: 3 }],
    drawCallback: () => {
      // Reinitialize event listeners after DataTable redraws
      setupTableListeners()
    },
  })

  // Event listeners setup
  setupModalListeners()
  setupTableListeners()
})

// Configure modal listeners
function setupModalListeners() {
  const modalCrear = document.getElementById("modalCrearUsuario")
  const modalDetalles = document.getElementById("modalDetalles")
  const btnCrear = document.getElementById("btnCrearUsuario")
  const formCrear = document.getElementById("formCrearUsuario")

  // Open create modal
  btnCrear.addEventListener("click", (e) => {
    e.preventDefault()
    openModal(modalCrear)
  })

  // Close modals with X button
  document.querySelectorAll(".modal-close").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const modal = this.closest(".modal")
      closeModal(modal)
    })
  })

  // Close modals with Cancel/Close button
  document.querySelectorAll(".modal-close-btn").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const modal = this.closest(".modal")
      closeModal(modal)
    })
  })

  // Close modal when clicking outside
  document.querySelectorAll(".modal").forEach((modal) => {
    modal.addEventListener("click", (e) => {
      if (e.target === modal) {
        closeModal(modal)
      }
    })
  })

  // Close modal with ESC key
  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      document.querySelectorAll(".modal.show").forEach((modal) => {
        closeModal(modal)
      })
    }
  })

  // Submit create user form
  formCrear.addEventListener("submit", (e) => {
    e.preventDefault()
    handleCreateUser()
  })
}

// Configure table listeners
function setupTableListeners() {
  document.querySelectorAll(".btn-detalles").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const row = this.closest("tr")
      handleViewDetails(row)
    })
  })

  document.querySelectorAll(".btn-cambiar-estado").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const row = this.closest("tr")
      handleChangeStatus(row)
    })
  })
}

// Open modal
function openModal(modal) {
  modal.classList.add("show")
}

// Close modal
function closeModal(modal) {
  modal.classList.remove("show")
}

// Create user handler
function handleCreateUser() {
  const usuario = document.getElementById("nuevoUsuario").value
  const nombre = document.getElementById("nuevoNombre").value
  const correo = document.getElementById("nuevoCorreo").value
  const rol = document.getElementById("nuevoRol").value

  Swal.fire({
    title: "¿Crear usuario?",
    html: "<strong>" + nombre + "</strong> (" + usuario + ")<br><small>" + correo + "</small>",
    icon: "info",
    showCancelButton: true,
    confirmButtonText: "Crear",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
  }).then((result) => {
    if (result.isConfirmed) {
      Swal.fire({
        title: "¡Usuario creado!",
        text: "El usuario " + nombre + " ha sido creado exitosamente.",
        icon: "success",
        confirmButtonColor: "#3085d6",
      })
      closeModal(document.getElementById("modalCrearUsuario"))
      document.getElementById("formCrearUsuario").reset()
    }
  })
}

// View details handler
function handleViewDetails(row) {
  const cells = row.querySelectorAll("td")
  const userNameElement = cells[0].querySelector(".user-info span")
  const userName = userNameElement ? userNameElement.textContent.trim() : "Usuario"
  const userEmail = cells[1].textContent.trim()
  const userStatus = cells[2].querySelector(".badge").textContent.trim()

  // Example data (in production this would come from backend)
  document.getElementById("detalleUsuario").textContent = "usuario.ejemplo"
  document.getElementById("detalleNombre").textContent = userName
  document.getElementById("detalleCorreo").textContent = userEmail
  document.getElementById("detalleRol").textContent = "Administrador"
  document.getElementById("detalleEstado").textContent = userStatus
  document.getElementById("detalleFecha").textContent = "15/10/2024 10:30 AM"
  document.getElementById("detalleUltimoAcceso").textContent = "11/11/2025 02:45 PM"

  openModal(document.getElementById("modalDetalles"))
}

// Change status handler
function handleChangeStatus(row) {
  const userNameElement = row.querySelector(".user-info span")
  const userName = userNameElement ? userNameElement.textContent.trim() : "Usuario"
  const badge = row.querySelector(".badge")
  const currentStatus = badge.textContent.trim()
  const newStatus = currentStatus === "Activo" ? "Inactivo" : "Activo"

  Swal.fire({
    title: "Cambiar estado",
    text: "¿Cambiar el estado de " + userName + " a " + newStatus + "?",
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
  }).then((result) => {
    if (result.isConfirmed) {
      // Update badge
      badge.textContent = newStatus
      badge.style.backgroundColor = newStatus === "Activo" ? "var(--primary)" : "oklch(0.6 0.15 60)"
      badge.style.color = "var(--primary-foreground)"

      // Change icon
      const btn = row.querySelector(".btn-cambiar-estado i")
      btn.className = newStatus === "Activo" ? "fas fa-toggle-on" : "fas fa-toggle-off"

      Swal.fire({
        title: "Estado actualizado",
        text: "El estado de " + userName + " ha sido cambiado a " + newStatus + ".",
        icon: "success",
        confirmButtonColor: "#3085d6",
      })
    }
  })
}
