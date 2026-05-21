package com.educativa.inscripcion_cursos.service;

import com.educativa.inscripcion_cursos.dto.CursoRequestDTO;
import com.educativa.inscripcion_cursos.dto.CursoResponseDTO;
import com.educativa.inscripcion_cursos.entity.Curso;
import com.educativa.inscripcion_cursos.repository.CursoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;

    @Transactional(readOnly = true)
    public List<CursoResponseDTO> listarCursosDisponibles() {
        return cursoRepository.findByDisponibleTrue().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public CursoResponseDTO crearCurso(CursoRequestDTO requestDTO) {
        Curso curso = new Curso();
        curso.setNombre(requestDTO.getNombre());
        curso.setInstructor(requestDTO.getInstructor());
        curso.setDuracion(requestDTO.getDuracion());
        curso.setCosto(requestDTO.getCosto());
        curso.setDisponible(true);

        Curso guardado = cursoRepository.save(curso);
        return mapToResponseDTO(guardado);
    }

    private CursoResponseDTO mapToResponseDTO(Curso curso) {
        return new CursoResponseDTO(
                curso.getId(),
                curso.getNombre(),
                curso.getInstructor(),
                curso.getDuracion(),
                curso.getCosto()
        );
    }
}
