const $ = window.$
const Swal = window.Swal

$(document).ready(() => {
  // Inicializar DataTable
  $("#tablaUsuarios").DataTable({
    language: {
      url: "//cdn.datatables.net/plug-ins/1.13.7/i18n/es-ES.json"
    },
    responsive: true,
    paging: true,
    pageLength: 10,
    lengthMenu: [5, 10, 20, 50],
    ordering: true,
    searching: true,
    columnDefs: [{ orderable: false, targets: 4 }]
  })

  setupModalListeners()
  setupTableListeners()
})

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

function setupTableListeners() {
  document.querySelectorAll(".btn-cambiar-estado").forEach((btn) => {
    btn.addEventListener("click", function (e) {
      e.preventDefault()
      const row = this.closest("tr")
      handleChangeStatus(row)
    })
  })
}

function handleChangeStatus(row) {
  const userName = row.querySelector("td:first-child").textContent.trim()

  // ✅ Seleccionamos el badge de la columna de estado (4ª celda)
  const badgeCell = row.querySelector("td:nth-child(4)")
  const badge = badgeCell.querySelector(".badge")

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
      // Cambiar texto y estilos del badge
      badge.textContent = newStatus
      badge.className = newStatus === "Activo"
        ? "badge badge-success"
        : "badge badge-destructive"

      // Cambiar icono del botón
      const btnIcon = row.querySelector(".btn-cambiar-estado i")
      btnIcon.className = newStatus === "Activo"
        ? "fas fa-toggle-on"
        : "fas fa-toggle-off"

      Swal.fire({
        title: "Estado actualizado",
        text: `El estado de ${userName} ha sido cambiado a ${newStatus}.`,
        icon: "success",
        confirmButtonColor: "#3085d6",
      })

      // ✅ Ejecutar la llamada al backend para guardar el cambio real
      const userId = row.querySelector(".btn-cambiar-estado").getAttribute("data-id")
      fetch(`/auth/usuarios/estado/${userId}`)
        .then(() => console.log("Estado actualizado en servidor"))
        .catch((err) => console.error("Error actualizando estado:", err))
    }
  })
}
