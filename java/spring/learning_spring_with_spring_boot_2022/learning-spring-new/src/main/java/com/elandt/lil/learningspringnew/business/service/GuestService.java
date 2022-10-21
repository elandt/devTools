package com.elandt.lil.learningspringnew.business.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.elandt.lil.learningspringnew.data.entity.Guest;
import com.elandt.lil.learningspringnew.data.repository.GuestRepository;

@Service
public class GuestService {

    private final GuestRepository guestRepository;

    public GuestService(GuestRepository guestRepository) {
        this.guestRepository = guestRepository;
    }

    public List<Guest> getGuests() {
        List<Guest> guests = new ArrayList<>();
        this.guestRepository.findAll().forEach(guest -> {
            guests.add(guest);
        });
        return guests.stream()
                    .sorted(
                        Comparator.comparing(Guest::getLastName)
                        .thenComparing(Guest::getFirstName)
                    )
                    .collect(Collectors.toList());
    }

    public void addGuest(Guest guest) {
        if (null == guest) {
            throw new RuntimeException("Guest cannot be null");
        }
        this.guestRepository.save(guest);
    }
}
