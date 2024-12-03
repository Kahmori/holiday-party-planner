package com.ada.holiday_party_planning.service;

import com.ada.holiday_party_planning.dto.CreateGuestDTO;
import com.ada.holiday_party_planning.exceptions.EmailAlreadyExistsException;
import com.ada.holiday_party_planning.model.Event;
import com.ada.holiday_party_planning.model.Guest;
import com.ada.holiday_party_planning.repository.EventRepository;
import com.ada.holiday_party_planning.repository.GuestRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class GuestServiceTest {

    @Mock
    private GuestRepository guestRepository;

    @Mock
    private EventRepository eventRepository;

    @InjectMocks
    private GuestService guestService;

    private Event event;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        event = new Event("Tema", "Título", null, "Local", "Descrição", false, null, null);
        event.setEventId(UUID.randomUUID());
    }

    @Test
    public void deveCriarConvidadoComSucesso() {
        CreateGuestDTO createGuestDTO = new CreateGuestDTO("Convidado", "convidado@exemplo.com", null, event, false);
        when(guestRepository.findByEmail(createGuestDTO.getEmail())).thenReturn(Optional.empty());

        Guest createdGuest = guestService.createGuest(createGuestDTO);

        assertEquals("Convidado", createdGuest.getName());
        assertEquals("convidado@exemplo.com", createdGuest.getEmail());
        assertEquals(event, createdGuest.getEvent());
        verify(guestRepository, times(1)).save(any(Guest.class));
    }

    @Test
    public void deveLancarExcecaoAoCriarConvidadoComEmailExistente() {
        CreateGuestDTO createGuestDTO = new CreateGuestDTO("Convidado", "convidado@exemplo.com", null, event, false);
        Guest existingGuest = new Guest("Outro Convidado", "convidado@exemplo.com", event);
        
        when(guestRepository.findByEmail(createGuestDTO.getEmail())).thenReturn(Optional.of(existingGuest));

        assertThrows(EmailAlreadyExistsException.class, () -> {
            guestService.createGuest(createGuestDTO);
        });
    }
}