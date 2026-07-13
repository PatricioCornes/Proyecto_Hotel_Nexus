package com.nexushospitality.staff.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;

import com.nexushospitality.staff.model.Personal;
import com.nexushospitality.staff.repository.PersonalRepository;
import com.nexushospitality.staff.dto.PersonalDTO;

@Transactional
@Service
public class PersonalService {

    @Autowired
    private PersonalRepository personalRepository;
    
    public List<Personal> findAll() {
        return personalRepository.findAll();
    }

    public Personal findById(Long id) {
        return personalRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Personal no encontrado"));
    }

    public Personal save(PersonalDTO dto) {
        personalRepository.findByRun(dto.getRun()).ifPresent(p -> {
            throw new IllegalStateException("Ya existe un empleado con el mismo RUN");
        });
        Personal personal = new Personal();
        personal.setRun(dto.getRun());
        personal.setNombre(dto.getNombre());
        personal.setApellido(dto.getApellido());
        personal.setFechaNacimiento(dto.getFechaNacimiento());
        personal.setCorreo(dto.getCorreo());
        personal.setTelefono(dto.getTelefono());
        personal.setCargo(dto.getCargo());
        personal.setTurno(dto.getTurno());
        personal.setDisponible(true);
        
        return personalRepository.save(personal);
    }

    public Personal update(Long id, PersonalDTO dto) {
        Personal personal = findById(id); 
        
        personal.setNombre(dto.getNombre());
        personal.setApellido(dto.getApellido());
        personal.setCorreo(dto.getCorreo());
        personal.setTelefono(dto.getTelefono());
        personal.setCargo(dto.getCargo());
        personal.setTurno(dto.getTurno());
     

        return personalRepository.save(personal);
    }

    public void delete(Long id) {
        findById(id);
        personalRepository.deleteById(id);
    }

    public Personal asignarDisponible(String cargo) {
        Personal personal = personalRepository.findFirstByCargoIgnoreCaseAndDisponibleTrueOrderByIdAsc(cargo)
                .orElseThrow(() -> new IllegalStateException("No hay empleados disponibles para " + cargo));
        personal.setDisponible(false);
        return personalRepository.save(personal);
    }

    public void liberar(Long id) {
        Personal personal = findById(id);
        personal.setDisponible(true);
        personalRepository.save(personal);
    }

}
