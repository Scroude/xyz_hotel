package com.example.xyz_hotel.application;

import com.example.xyz_hotel.database.ReservationRepository;
import com.example.xyz_hotel.database.RoomRepository;
import com.example.xyz_hotel.domain.Room;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Controller
@RequestMapping(path = "/api/room")

public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/all")
    public @ResponseBody List<Room> getRooms() {
        return roomRepository.findAll();
    }

    @PostMapping(path = "/add")
    public @ResponseBody String addRoom(@RequestBody RoomRequest roomRequest){
        Room room = new Room();
        room.setBathtub(roomRequest.getBathtub());
        room.setBed(roomRequest.getBed());
        room.setAirConditionner(roomRequest.getAirConditionner());
        room.setMinibar(roomRequest.getMinibar());
        room.setTerrace(roomRequest.getTerrace());
        room.setTv(roomRequest.getTv());
        room.setWifi(roomRequest.getWifi());
        room.setPrice(roomRequest.getPrice());
        roomRepository.save(room);
        return "Room Added";
    }
}
