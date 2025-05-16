package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.doctor.DoctorDetailsDTO;
import br.com.med.cleanMed.domain.doctor.DoctorRegisterDTO;
import br.com.med.cleanMed.domain.doctor.DoctorResponseDTO;
import br.com.med.cleanMed.domain.doctor.DoctorUpdateDTO;
import br.com.med.cleanMed.domain.doctor.Doctor;
import br.com.med.cleanMed.domain.doctor.DoctorRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("/doctors")
public class DoctorController {

    @Autowired
    private DoctorRepository repository;

    @PostMapping
    @Transactional
    public ResponseEntity registerDoctor(@RequestBody @Valid DoctorRegisterDTO doctorDTO, UriComponentsBuilder uriBuilder){
        var doctor = new Doctor(doctorDTO);
        repository.save(doctor);
        var dto = new DoctorDetailsDTO(doctor);
        var uri = uriBuilder.path("/doctors/{id}").buildAndExpand(doctor.getId()).toUri();

        return ResponseEntity.created(uri).body(dto);
    }

    @GetMapping
    public ResponseEntity<Page<DoctorResponseDTO>> listDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable){
        var page = repository.findAllByActiveTrue(pageable).map(DoctorResponseDTO::new);

        return ResponseEntity.ok(page);
    }

    @PutMapping
    @Transactional
    public ResponseEntity update(@RequestBody @Valid DoctorUpdateDTO doctorUpdateDTO){
        var doctor = repository.getReferenceById(doctorUpdateDTO.id());
        doctor.updateData(doctorUpdateDTO);

        return ResponseEntity.ok(new DoctorDetailsDTO(doctor));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity delete(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        doctor.delete();

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity details(@PathVariable Long id){
        var doctor = repository.getReferenceById(id);
        return ResponseEntity.ok(new DoctorDetailsDTO(doctor));
    }

}
