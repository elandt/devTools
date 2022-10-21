package com.elandt.lil.learningspringnew.business.service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.elandt.lil.learningspringnew.business.dto.RoomReservation;
import com.elandt.lil.learningspringnew.data.entity.Guest;
import com.elandt.lil.learningspringnew.data.entity.Reservation;
import com.elandt.lil.learningspringnew.data.entity.Room;
import com.elandt.lil.learningspringnew.data.repository.GuestRepository;
import com.elandt.lil.learningspringnew.data.repository.ReservationRepository;
import com.elandt.lil.learningspringnew.data.repository.RoomRepository;

@Service
public class ReservationService {
    
    private final RoomRepository roomRepository;
    private final GuestRepository guestRepository;
    private final ReservationRepository reservationRepository;

    // Because there is only one constructor, the @Autowired annotation is not required
    @Autowired
    public ReservationService(RoomRepository roomRepository, GuestRepository guestRepository,
            ReservationRepository reservationRepository) {
        this.roomRepository = roomRepository;
        this.guestRepository = guestRepository;
        this.reservationRepository = reservationRepository;
    }

    public List<RoomReservation> getRoomReservationsForDate(Date date) {
        Iterable<Room> rooms = this.roomRepository.findAll();
        Map<Long, RoomReservation> roomReservationMap = new HashMap<>();
        rooms.forEach(room -> {
            RoomReservation roomReservation = new RoomReservation();
            roomReservation.setRoomId(room.getId());
            roomReservation.setRoomName(room.getName());
            roomReservation.setRoomNumber(room.getRoomNumber());
            roomReservationMap.put(room.getId(), roomReservation);
        });

        Iterable<Reservation> resevations = this.reservationRepository.findReservationByReservationDate(new java.sql.Date(date.getTime()));
        resevations.forEach(resevation -> {
            RoomReservation roomReservation = roomReservationMap.get(resevation.getRoomId());
            roomReservation.setDate(date);
            Guest guest = this.guestRepository.findById(resevation.getGuestId()).get();
            roomReservation.setFirstName(guest.getFirstName());
            roomReservation.setLastName(guest.getLastName());
            roomReservation.setGuestId(guest.getGuestId());
        });

        List<RoomReservation> roomReservations = new ArrayList<>();
        for(Long id : roomReservationMap.keySet()){
            roomReservations.add(roomReservationMap.get(id));
        }

        roomReservations.sort(new Comparator<RoomReservation>() {
            @Override
            public int compare(RoomReservation o1, RoomReservation o2) {
                if(o1.getRoomName().equals(o2.getRoomName())) {
                    return o1.getRoomNumber().compareTo(o2.getRoomNumber());
                }
                return o1.getRoomName().compareTo(o2.getRoomName());
            }
        });

        return roomReservations;
    }

    public List<Room> getRooms() {
        List<Room> rooms = new ArrayList<>();
        this.roomRepository.findAll().forEach(room -> {
            rooms.add(room);
        });
        return rooms.stream()
                    .sorted(
                        Comparator.comparing(Room::getName)
                        .thenComparing(Room::getRoomNumber)
                    )
                    .collect(Collectors.toList());
    }
}
