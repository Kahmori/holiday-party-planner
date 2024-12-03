package com.ada.holiday_party_planning.service;

import com.ada.holiday_party_planning.dto.CreateEventDTO;
import com.ada.holiday_party_planning.exceptions.PartyOwnerNotFoundException;
import com.ada.holiday_party_planning.model.Event;
import com.ada.holiday_party_planning.model.PartyOwner;
import com.ada.holiday_party_planning.repository.EventRepository;
import com.ada.holiday_party_planning.repository.PartyOwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private PartyOwnerRepository partyOwnerRepository;

    @InjectMocks
    private EventService eventService;

    private PartyOwner partyOwner;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        partyOwner = new PartyOwner("Nome do Dono", "dono@exemplo.com", "senha");
        partyOwner.setOwnerId(UUID.randomUUID());
    }

    @Test
    public void deveCriarEventoComSucesso() {
        CreateEventDTO createEventDTO = new CreateEventDTO("Tema", "Título", null, "Local", "Descrição", false, null, null);
        when(partyOwnerRepository.findById(any(UUID.class))).thenReturn(Optional.of(partyOwner));

        eventService.createEvent(partyOwner.getOwnerId(), createEventDTO);

        ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
        verify(eventRepository, times(1)).save(eventCaptor.capture());
        Event eventoSalvo = eventCaptor.getValue();
        assertEquals("Título", eventoSalvo.getTitle());
        assertEquals("Tema", eventoSalvo.getTheme());
    }

    @Test
    public void deveLancarExcecaoQuandoDonoNaoEncontrado() {
        CreateEventDTO createEventDTO = new CreateEventDTO("Tema", "Título", null, "Local", "Descrição", false, null, null);
        when(partyOwnerRepository.findById(any(UUID.class))).thenReturn(Optional.empty());

        assertThrows(PartyOwnerNotFoundException.class, () -> {
            eventService.createEvent(UUID.randomUUID(), createEventDTO);
        });
    }
}