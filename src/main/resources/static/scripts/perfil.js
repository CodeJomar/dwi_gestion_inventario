const Swal = window.Swal;

document.addEventListener("DOMContentLoaded", () => {
    const form = document.querySelector(".profile-form");

    const fields = ["Nombre-Completo", "DNI", "Fecha-Nacimiento", "Teléfono", "pais"];

    const fechaNacimientoInput = document.getElementById("Fecha-Nacimiento");
    const edadInput = document.getElementById("edad");

    fechaNacimientoInput.addEventListener("change", updateAgeCalculation);

    function calculateAge(dateString) {
        const today = new Date();
        const birth = new Date(dateString);

        if (isNaN(birth.getTime())) return "";

        let age = today.getFullYear() - birth.getFullYear();
        const m = today.getMonth() - birth.getMonth();

        if (m < 0 || (m === 0 && today.getDate() < birth.getDate())) {
            age--;
        }
        return age;
    }

    function updateAgeCalculation() {
        edadInput.value = calculateAge(fechaNacimientoInput.value);
    }

    form.addEventListener("submit", (e) => {

        for (let id of fields) {
            const input = document.getElementById(id);
            if (input.value.trim() === "") {
                e.preventDefault();
                Swal.fire({
                    icon: "warning",
                    title: "Campo requerido",
                    text: `Por favor completa el campo: ${id}`,
                    confirmButtonColor: "var(--primary)"
                });
                return;
            }
        }

        e.preventDefault();
        Swal.fire({
            icon: "question",
            title: "¿Guardar cambios?",
            text: "Tu información será actualizada.",
            showCancelButton: true,
            confirmButtonColor: "var(--primary)",
            confirmButtonText: "Sí, guardar",
            cancelButtonText: "Cancelar"
        }).then(result => {
            if (result.isConfirmed) {
                form.submit();
            }
        });

    });
});
