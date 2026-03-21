package com.pm.patientservice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pm.patientservice.dto.PatientRequestDTO;
import com.pm.patientservice.dto.PatientResponseDTO;
import com.pm.patientservice.service.PatientService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;


@WebMvcTest(PatientController.class)
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatientService patientService;

    @InjectMocks
    private PatientController patientController;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void testGetPatientsEndpoint() throws Exception {

        PatientResponseDTO patientResponseDTO = new PatientResponseDTO();

        when(patientService.getPatients()).
                thenReturn(List.of(patientResponseDTO));

//       ResponseEntity<List<PatientResponseDTO>> getPatientApiResponse =
//               patientController.getPatients();

       mockMvc.perform(MockMvcRequestBuilders.get("/patients"))
               .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void testCreatePatient() throws Exception {

        PatientRequestDTO requestDTO = new PatientRequestDTO();
        requestDTO.setName("John Doe");
        requestDTO.setEmail("john@example.com");
        requestDTO.setAddress("New York");
        requestDTO.setDateOfBirth(LocalDate.of(1990, 1, 1).toString());
        requestDTO.setRegisteredDate(LocalDate.now().toString());

        PatientResponseDTO responseDTO = new PatientResponseDTO();

        when(patientService.createPatient(any(PatientRequestDTO.class)))
                .thenReturn(responseDTO);

        mockMvc.perform(MockMvcRequestBuilders.post("/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(requestDTO)))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }


//
//    @PostMapping
//    @Operation(summary = "Create a new Patient")
//    public ResponseEntity<PatientResponseDTO> createPatient(@Validated({Default.class, CreatePatientValidationGroup.class})
//                                                            @RequestBody PatientRequestDTO patientRequestDTO) {
//        return ResponseEntity.ok().body(patientService.createPatient(patientRequestDTO));
//    }
//



    List<PatientResponseDTO> getPatientList(){
        List<PatientResponseDTO> patientList = new ArrayList<>();

        PatientResponseDTO patientDTO = new PatientResponseDTO();
        patientDTO.setId(UUID.randomUUID().toString());
        patientDTO.setName("Ramesh");
        patientDTO.setAddress("Pune");
        patientDTO.setEmail("Ramesh@gmail.com");
        patientDTO.setDateOfBirth("01/01/1990");

        patientList.add(patientDTO);
        return patientList;



    }

}