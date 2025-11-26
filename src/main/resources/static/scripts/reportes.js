const Swal = require("sweetalert2") // Import Swal or declare it before using it

document.addEventListener("DOMContentLoaded", () => {

  const downloadButtons = document.querySelectorAll(".download-btn")
  downloadButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
      e.preventDefault()

      const reportName = button.dataset.report
      const route = button.dataset.route

      const reportLabels = {
        productos: "Reporte de Productos",
        ordenes: "Reporte de Órdenes",
        usuarios: "Reporte de Usuarios",
      }

      Swal.fire({
        title: "¿Descargar reporte?",
        text: `Se descargará: ${reportLabels[reportName]}`,
        icon: "info",
        showCancelButton: true,
        confirmButtonText: "Sí, descargar",
        cancelButtonText: "Cancelar",
        confirmButtonColor: "#008B8B",
        cancelButtonColor: "#6B7280",
      }).then((result) => {
        if (result.isConfirmed) {
          window.location.href = route

          setTimeout(() => {
            Swal.fire({
              title: "¡Descarga iniciada!",
              text: `${reportLabels[reportName]} se está descargando...`,
              icon: "success",
              timer: 2000,
              showConfirmButton: false,
            })
          }, 500)
        }
      })
    })
  })
})
