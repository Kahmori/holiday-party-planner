package com.ada.holiday_party_planning.controller;

import com.ada.holiday_party_planning.dto.*;
import com.ada.holiday_party_planning.service.PartyOwnerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/party-owners")
public class PartyOwnerController {

    private final PartyOwnerService partyOwnerService;

    public PartyOwnerController(PartyOwnerService partyOwnerService) {
        this.partyOwnerService = partyOwnerService;
    }

    @PostMapping ("/register")
    public ResponseEntity<PartyOwnerDTO> createPartyOwner (@RequestBody CreatePartyOwnerDTO createPartyOwnerDTO) {

        PartyOwnerDTO partyOwnerDTO = partyOwnerService.createPartyOwner(createPartyOwnerDTO);

        return ResponseEntity
                .status(201)
                .body(partyOwnerDTO);
    }

    @PostMapping("/login")
    public ResponseEntity<PartyOwnerLoginResponseDTO> login(@RequestBody PartyOwnerLoginDTO partyOwnerLoginDTO) {
        PartyOwnerLoginResponseDTO partyOwnerLoginResponseDTO = partyOwnerService.login(partyOwnerLoginDTO);

        return ResponseEntity
                .ok(partyOwnerLoginResponseDTO);
    }


    @GetMapping("/all")
    public ResponseEntity<List<PartyOwnerDTO>> getAllPartyOwners() {

        List<PartyOwnerDTO> allPartyOwners = partyOwnerService.getAllPartyOwners();

        return ResponseEntity.ok(allPartyOwners);
    }

    @PutMapping("/update/{ownerId}")
    public ResponseEntity<PartyOwnerDTO> updatePartyOwner(@RequestBody PartyOwnerDTO partyOwnerDTO, @PathVariable UUID ownerId) {

        Optional<PartyOwnerDTO> newPartyOwner = partyOwnerService.updatePartyOwner(ownerId, partyOwnerDTO);

        if(newPartyOwner.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            return ResponseEntity.ok(newPartyOwner.get());
        }
    }

}
