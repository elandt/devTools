package com.elandt.lil.learningspringnew.data.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.elandt.lil.learningspringnew.data.entity.Room;

@Repository
public interface RoomRepository extends CrudRepository<Room, Long> {
    
}
