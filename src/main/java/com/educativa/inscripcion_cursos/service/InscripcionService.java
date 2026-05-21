package com.educativa.inscripcion_cursos.service;

import com.educativa.inscripcion_cursos.dto.CursoInscripcionDTO;
import com.educativa.inscripcion_cursos.dto.InscripcionRequestDTO;
import com.educativa.inscripcion_cursos.dto.InscripcionResponseDTO;
import com.educativa.inscripcion_cursos.entity.Curso;
import com.educativa.inscripcion_cursos.entity.Estudiante;
import com.educativa.inscripcion_cursos.entity.Inscripcion;
import com.educativa.inscripcion_cursos.entity.InscripcionDetalle;
import com.educativa.inscripcion_cursos.repository.CursoRepository;
import com.educativa.inscripcion_cursos.repository.EstudianteRepository;
import com.educativa.inscripcion_cursos.repository.InscripcionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InscripcionService {

    private final InscripcionRepository inscripcionRepository;
    private final EstudianteRepository estudianteRepository;
    private final CursoRepository cursoRepository;

    @Transactional
    public InscripcionResponseDTO inscribirEstudiante(InscripcionRequestDTO requestDTO) {
        Estudiante estudiante = estudianteRepository.findByEmail(requestDTO.getEmailEstudiante())
                .orElseGet(() -> {
                    Estudiante nuevo = new Estudiante();
                    nuevo.setNombre(requestDTO.getNombreEstudiante());
                    nuevo.setEmail(requestDTO.getEmailEstudiante());
                    return estudianteRepository.save(nuevo);
                });

        List<Curso> cursos = cursoRepository.findAllById(requestDTO.getCursoIds());

        if (cursos.isEmpty()) {
            throw new IllegalArgumentException("No se encontraron cursos con los IDs proporcionados");
        }

        if (cursos.size() != requestDTO.getCursoIds().size()) {
            throw new IllegalArgumentException("Algunos cursos no fueron encontrados");
        }

        for (Curso curso : cursos) {
            if (!Boolean.TRUE.equals(curso.getDisponible())) {
                throw new IllegalArgumentException("El curso " + curso.getNombre() + " no está disponible");
            }
        }

        Inscripcion inscripcion = new Inscripcion();
        inscripcion.setEstudiante(estudiante);
        inscripcion.setFechaInscripcion(LocalDateTime.now());

        List<InscripcionDetalle> detalles = new ArrayList<>();
        BigDecimal total = BigDecimal.ZERO;

        for (Curso curso : cursos) {
            InscripcionDetalle detalle = new InscripcionDetalle();
            detalle.setInscripcion(inscripcion);
            detalle.setCurso(curso);
            detalle.setCostoAplicado(curso.getCosto());
            detalles.add(detalle);
            total = total.add(curso.getCosto());
        }

        inscripcion.setDetalles(detalles);
        inscripcion.setTotal(total);

        Inscripcion guardada = inscripcionRepository.save(inscripcion);

        return mapToResponseDTO(guardada);
    }

    private InscripcionResponseDTO mapToResponseDTO(Inscripcion inscripcion) {
        List<CursoInscripcionDTO> cursosDTO = inscripcion.getDetalles().stream()
                .map(detalle -> new CursoInscripcionDTO(
                        detalle.getCurso().getId(),
                        detalle.getCurso().getNombre(),
                        detalle.getCurso().getInstructor(),
                        detalle.getCostoAplicado()
                ))
                .collect(Collectors.toList());

        return new InscripcionResponseDTO(
                inscripcion.getId(),
                inscripcion.getEstudiante().getNombre(),
                inscripcion.getEstudiante().getEmail(),
                inscripcion.getFechaInscripcion(),
                cursosDTO,
                inscripcion.getTotal()
        );
    }
}
