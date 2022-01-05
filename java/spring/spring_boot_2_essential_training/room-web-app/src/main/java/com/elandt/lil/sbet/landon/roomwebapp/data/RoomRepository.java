package com.elandt.lil.sbet.landon.roomwebapp.data;

import com.elandt.lil.sbet.landon.roomwebapp.models.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoomRepository extends JpaRepository<Room, Long> {
    
}
