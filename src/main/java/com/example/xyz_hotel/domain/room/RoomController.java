package com.example.xyz_hotel.domain.room;

import com.example.xyz_hotel.database.RoomRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Controller
@RequestMapping(path = "/room")

public class RoomController {
    @Autowired
    private RoomRepository roomRepository;

    @GetMapping(path = "/all")
    public @ResponseBody List<Room> getRooms() {
        return roomRepository.findAll();
    }
}
