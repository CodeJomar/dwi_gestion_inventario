// === Variables globales ===
const $ = window.$
const Swal = window.Swal

$(document).ready(() => {
  // Inicializar DataTable
  $("#tablaUsuarios").DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.13.7/i18n/es-ES.json"
    },
    paging: true,
    pageLength: 10,
    ordering: true,
    searching: true,
    columnDefs: [{ orderable: false, targets: 4 }]
  })

  setupModalListeners()
  setupTableListeners()
})

// === Modales ===
function setupModalListeners() {
  const modalCrear = document.getElementById("modalCrearUsuario")
  const btnCrear = document.getElementById("btnCrearUsuario")

  if (btnCrear) {
    btnCrear.addEventListener("click", (e) => {
      e.preventDefault()
      openModal(modalCrear)
    })
  }

  document.querySelectorAll(".modal-close, .modal-close-btn").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const modal = this.closest(".modal")
      closeModal(modal)
    })
  })

  document.querySelectorAll(".modal").forEach((modal) => {
    modal.addEventListener("click", (e) => {
      if (e.target === modal) closeModal(modal)
    })
  })

  document.addEventListener("keydown", (e) => {
    if (e.key === "Escape") {
      document.querySelectorAll(".modal.show").forEach((modal) => closeModal(modal))
    }
  })
}

function openModal(modal) {
  if (!modal) return
  modal.classList.add("show")
  document.body.style.overflow = "hidden"
}

function closeModal(modal) {
  if (!modal) return
  modal.classList.remove("show")
  document.body.style.overflow = ""
}

// === Acciones de tabla ===
function setupTableListeners() {
  document.querySelectorAll(".btn-cambiar-estado").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const row = this.closest("tr")
      handleChangeStatus(row)
    })
  })
}

// === Cambiar estado ===
function handleChangeStatus(row) {
  const userName = row.querySelector("td:first-child").textContent.trim()
  const badge = row.querySelector(".badge")
  const currentStatus = badge.textContent.trim()
  const newStatus = currentStatus === "Activo" ? "Inactivo" : "Activo"

  Swal.fire({
    title: "Cambiar estado",
    text: `¿Cambiar el estado de ${userName} a ${newStatus}?`,
    icon: "warning",
    showCancelButton: true,
    confirmButtonText: "Confirmar",
    cancelButtonText: "Cancelar",
    confirmButtonColor: "#3085d6",
  }).then((result) => {
    if (result.isConfirmed) {
      badge.textContent = newStatus
      badge.style.backgroundColor =
        newStatus === "Activo" ? "var(--primary)" : "oklch(0.6 0.15 60)"
      badge.style.color = "var(--primary-foreground)"

      const btn = row.querySelector(".btn-cambiar-estado i")
      btn.className = newStatus === "Activo" ? "fas fa-toggle-on" : "fas fa-toggle-off"

      Swal.fire({
        title: "Estado actualizado",
        text: `El estado de ${userName} ha sido cambiado a ${newStatus}.`,
        icon: "success",
        confirmButtonColor: "#3085d6",
      })
    }
  })
}
