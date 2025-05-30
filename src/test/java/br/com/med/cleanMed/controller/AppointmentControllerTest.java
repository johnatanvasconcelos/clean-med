package br.com.med.cleanMed.controller;

import br.com.med.cleanMed.domain.appointment.DataDetailsAppointmentDTO;
import br.com.med.cleanMed.domain.appointment.scheduling.AppointmentScheduling;
import br.com.med.cleanMed.domain.appointment.scheduling.DataScheduleAppointmentDTO;
import br.com.med.cleanMed.domain.doctor.Specialty;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class AppointmentControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private JacksonTester<DataScheduleAppointmentDTO> dataScheduleAppointmentDTOJson;

    @Autowired
    private JacksonTester<DataDetailsAppointmentDTO> dataDetailsAppointmentDTOJson;

    @MockitoBean
    private AppointmentScheduling appointmentScheduling;

    @Test
    @DisplayName("Deveria devolver codigo HTTP 400 quando informações estão inválidas")
    @WithMockUser
    void scheduleCase1() throws Exception {
        var response = mvc.perform(post("/appointments"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Deveria devolver codigo HTTP 200 quando informações estão válidas")
    @WithMockUser
    void scheduleCase2() throws Exception {
        var dateTime = LocalDateTime.now().plusHours(1);
        var specialty = Specialty.CARDIOLOGY;

        var dataDetails = new DataDetailsAppointmentDTO(null, 1L, 2L, dateTime);

        when(appointmentScheduling.toSchedule(any())).thenReturn(dataDetails);

        var response = mvc
                .perform(
                        post("/appointments")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(dataScheduleAppointmentDTOJson.write(
                                        new DataScheduleAppointmentDTO(1L,2L, dateTime, specialty)
                                ).getJson())
                )
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());

        var jsonWaited = dataDetailsAppointmentDTOJson.write(dataDetails).getJson();

        assertThat(response.getContentAsString()).isEqualTo(jsonWaited);
    }
}