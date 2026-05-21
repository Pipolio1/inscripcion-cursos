package com.educativa.inscripcion_cursos.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CursoRequestDTO {

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotBlank(message = "El instructor es obligatorio")
    private String instructor;

    @NotNull(message = "La duración es obligatoria")
    @Min(value = 1, message = "La duración debe ser al menos 1 hora")
    private Integer duracion;

    @NotNull(message = "El costo es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El costo debe ser mayor a 0")
    private BigDecimal costo;
}
