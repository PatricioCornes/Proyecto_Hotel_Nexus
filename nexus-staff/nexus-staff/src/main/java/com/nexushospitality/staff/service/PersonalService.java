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
        return personalRepository.findById(id).orElseThrow(() -> new RuntimeException("Personal no encontrado")); 
    }

    public Personal save(PersonalDTO dto) {
        Personal personal = new Personal();
        personal.setRun(dto.getRun());
        personal.setNombre(dto.getNombre());
        personal.setApellido(dto.getApellido());
        personal.setFechaNacimiento(dto.getFechaNacimiento());
        personal.setCorreo(dto.getCorreo());
        personal.setTelefono(dto.getTelefono());
        personal.setCargo(dto.getCargo());
        personal.setTurno(dto.getTurno());
        
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
        personalRepository.deleteById(id);
    }

}
