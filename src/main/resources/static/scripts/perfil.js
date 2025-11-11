const Swal = window.Swal

document.addEventListener("DOMContentLoaded", () => {
  const btnEditar = document.getElementById("btn-editar")
  const btnGuardar = document.getElementById("btn-guardar")
  const btnCancelar = document.getElementById("btn-cancelar")
  const editableFields = ["nombresApellidos", "dni", "fechaNacimiento", "telefono", "pais"]

  const originalValues = {}

  btnEditar.addEventListener("click", () => {
    enableEditMode()
  })

  btnGuardar.addEventListener("click", () => {
    saveProfileChanges()
  })

  btnCancelar.addEventListener("click", () => {
    cancelEdit()
  })

  function calculateAge(birthDate) {
    const today = new Date()
    const birth = new Date(birthDate)
    let age = today.getFullYear() - birth.getFullYear()
    const monthDiff = today.getMonth() - birth.getMonth()

    if (monthDiff < 0 || (monthDiff === 0 && today.getDate() < birth.getDate())) {
      age--
    }

    return age
  }

  function updateAgeCalculation() {
    const fechaNacimientoInput = document.getElementById("fechaNacimiento")
    const edadInput = document.getElementById("edad")

    if (fechaNacimientoInput.value) {
      const age = calculateAge(fechaNacimientoInput.value)
      edadInput.value = age
    }
  }

  document.getElementById("fechaNacimiento").addEventListener("change", updateAgeCalculation)

  function enableEditMode() {
    editableFields.forEach((fieldId) => {
      const input = document.getElementById(fieldId)
      originalValues[fieldId] = input.value
      input.disabled = false
    })

    btnEditar.classList.add("hidden")
    btnGuardar.classList.remove("hidden")
    btnCancelar.classList.remove("hidden")
  }

  function cancelEdit() {
    editableFields.forEach((fieldId) => {
      const input = document.getElementById(fieldId)
      input.value = originalValues[fieldId]
      input.disabled = true
    })

    updateAgeCalculation()

    btnEditar.classList.remove("hidden")
    btnGuardar.classList.add("hidden")
    btnCancelar.classList.add("hidden")
  }

  function saveProfileChanges() {
    let hasChanges = false
    const profileData = {}

    editableFields.forEach((fieldId) => {
      const input = document.getElementById(fieldId)
      if (input.value.trim() === "") {
        Swal.fire({
          icon: "warning",
          title: "Campo Requerido",
          text: `Por favor, completa el campo ${fieldId}`,
          confirmButtonColor: "var(--primary)",
          confirmButtonText: "OK",
          borderRadius: "12px",
        })
        return
      }
      if (input.value !== originalValues[fieldId]) {
        hasChanges = true
      }
      profileData[fieldId] = input.value
      input.disabled = true
    })

    if (!hasChanges) {
      Swal.fire({
        icon: "info",
        title: "Sin Cambios",
        text: "No realizaste ninguna modificación",
        confirmButtonColor: "var(--primary)",
        confirmButtonText: "OK",
        borderRadius: "12px",
      })
      btnEditar.classList.remove("hidden")
      btnGuardar.classList.add("hidden")
      btnCancelar.classList.add("hidden")
      return
    }

    Swal.fire({
      icon: "success",
      title: "¡Perfil Actualizado!",
      text: "Los cambios en tu perfil se han guardado correctamente",
      confirmButtonColor: "var(--primary)",
      confirmButtonText: "Aceptar",
      timer: 3000,
      timerProgressBar: true,
      borderRadius: "12px",
    })

    updateAgeCalculation()

    btnEditar.classList.remove("hidden")
    btnGuardar.classList.add("hidden")
    btnCancelar.classList.add("hidden")
  }
})
