function togglePasswordVisibility(button) {
  const targetId = button.getAttribute("data-target")
  const passwordInput = document.getElementById(targetId)
  const eyeIcon = button.querySelector(".eye-icon")

  if (!passwordInput || !eyeIcon) {
    console.error("Error: No se encontraron los elementos necesarios para el toggle de contraseña.")
    return
  }

  const isPassword = passwordInput.type === "password"
  passwordInput.type = isPassword ? "text" : "password"

  const eyeOpen = `<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>`
  const eyeClosed = `<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.542-7A10.05 10.05 0 0112 5c.577 0 1.144.041 1.701.123m.394 6.643a3 3 0 11-4.243-4.243m4.243 4.243L21 21"></path>`

  eyeIcon.innerHTML = isPassword ? eyeClosed : eyeOpen
}

function openPasswordResetModal() {
  const modal = document.getElementById("passwordResetModal")
  if (modal) {
    modal.classList.remove("hidden")
    modal.classList.remove("modal-hidden")
    // Forzar reflow para que la transición funcione
    modal.offsetHeight
    modal.classList.add("modal-visible")
    document.body.style.overflow = "hidden"
  }
}

function closePasswordResetModal() {
  const modal = document.getElementById("passwordResetModal")
  if (modal) {
    modal.classList.remove("modal-visible")
    modal.classList.add("modal-hidden")

    // Esperar a que termine la transición antes de ocultar
    setTimeout(() => {
      modal.classList.add("hidden")
      document.body.style.overflow = ""
      // Limpiar el formulario
      const form = document.getElementById("passwordResetForm")
      if (form) form.reset()
    }, 300) // Mismo tiempo que la transición CSS
  }
}

document.addEventListener("DOMContentLoaded", () => {
  // Toggle de contraseña para el campo de login
  const toggleButton = document.getElementById("togglePassword")
  if (toggleButton) {
    toggleButton.addEventListener("click", () => {
      const passwordInput = document.getElementById("password")
      const eyeIcon = document.getElementById("eye-icon")

      if (!passwordInput || !eyeIcon) return

      const isPassword = passwordInput.type === "password"
      passwordInput.type = isPassword ? "text" : "password"

      const eyeOpen = `<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"></path><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M2.458 12C3.732 7.943 7.523 5 12 5c4.478 0 8.268 2.943 9.542 7-1.274 4.057-5.064 7-9.542 7-4.477 0-8.268-2.943-9.542-7z"></path>`
      const eyeClosed = `<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M13.875 18.825A10.05 10.05 0 0112 19c-4.478 0-8.268-2.943-9.542-7A10.05 10.05 0 0112 5c.577 0 1.144.041 1.701.123m.394 6.643a3 3 0 11-4.243-4.243m4.243 4.243L21 21"></path>`

      eyeIcon.innerHTML = isPassword ? eyeClosed : eyeOpen
    })
  }

  const openModalButton = document.getElementById("openPasswordResetModal")
  if (openModalButton) {
    openModalButton.addEventListener("click", openPasswordResetModal)
  }

  const closeModalButton = document.getElementById("closeModal")
  if (closeModalButton) {
    closeModalButton.addEventListener("click", closePasswordResetModal)
  }

  const cancelButton = document.getElementById("cancelButton")
  if (cancelButton) {
    cancelButton.addEventListener("click", closePasswordResetModal)
  }

  // Cerrar modal al hacer click fuera de él
  const modal = document.getElementById("passwordResetModal")
  if (modal) {
    modal.addEventListener("click", (event) => {
      if (event.target === modal) {
        closePasswordResetModal()
      }
    })
  }

  const togglePasswordButtons = document.querySelectorAll(".toggle-password")
  togglePasswordButtons.forEach((button) => {
    button.addEventListener("click", function () {
      togglePasswordVisibility(this)
    })
  })
  /*
    const passwordResetForm = document.getElementById("passwordResetForm")
    if (passwordResetForm) {
      passwordResetForm.addEventListener("submit", (event) => {
        event.preventDefault()
        // El backend manejará el cambio de contraseña
      })
    }
  */

  // Cerrar modal con tecla ESC
  document.addEventListener("keydown", (event) => {
    if (event.key === "Escape") {
      closePasswordResetModal()
    }
  })
})
