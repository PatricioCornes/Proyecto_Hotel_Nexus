package com.nexushospitality.staff.dto;

import java.time.LocalDate;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.AssertTrue;
import com.nexushospitality.staff.validation.RunValido;
import lombok.Data;

@Data
public class PersonalDTO {

    @NotBlank(message = "El RUN es obligatorio")
    @Size(min = 8, max = 12, message = "Formato de RUN inválido")
    @RunValido
    private String run;

    @NotBlank(message = "El nombre es obligatorio")
    @Size(max = 80)
    private String nombre;

    @NotBlank(message = "El apellido es obligatorio")
    @Size(max = 80)
    private String apellido;

    @NotNull(message = "La fecha de nacimiento es obligatoria")
    @Past(message = "La fecha de nacimiento debe ser en el pasado")
    private LocalDate fechaNacimiento;

    @NotBlank(message = "El correo es obligatorio")
    @Email(message = "Debe ser un formato de correo válido")
    private String correo;

    @NotBlank(message = "El teléfono es obligatorio")
    @Pattern(regexp = "\\+?[0-9 ]{8,15}", message = "Formato de telefono invalido")
    private String telefono;

    @NotBlank(message = "El turno es obligatorio")
    @Pattern(regexp = "(?i)MANANA|MAÑANA|TARDE|NOCHE|ROTATIVO", message = "Turno no valido")
    private String turno;

    @NotBlank(message = "El cargo es obligatorio")
    @Size(max = 60)
    private String cargo;

    @AssertTrue(message = "El empleado debe tener al menos 18 anos")
    public boolean isMayorDeEdad() {
        return fechaNacimiento == null || !fechaNacimiento.plusYears(18).isAfter(LocalDate.now());
    }

}
