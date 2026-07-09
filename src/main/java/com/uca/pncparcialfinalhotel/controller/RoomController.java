package com.uca.pncparcialfinalhotel.controller;

import com.uca.pncparcialfinalhotel.dto.RoomRequest;
import com.uca.pncparcialfinalhotel.dto.RoomResponse;
import com.uca.pncparcialfinalhotel.service.RoomService;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;

    public RoomController(RoomService roomService) {
        this.roomService = roomService;
    }

    @PostMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<RoomResponse> create(@RequestBody RoomRequest roomRequest) {
        RoomResponse createdRoom = roomService.create(roomRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdRoom);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER')")
    public ResponseEntity<RoomResponse> update(@PathVariable Long id, @RequestBody RoomRequest roomRequest) {
        RoomResponse updatedRoom = roomService.update(id, roomRequest);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        roomService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    public ResponseEntity<RoomResponse> getById(@PathVariable Long id) {
        RoomResponse room = roomService.getById(id);
        return ResponseEntity.ok(room);
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    public ResponseEntity<List<RoomResponse>> getAll() {
        List<RoomResponse> rooms = roomService.getAll();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/filter/status")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    public ResponseEntity<List<RoomResponse>> getByStatus(@RequestParam String status) {
        List<RoomResponse> rooms = roomService.getByStatus(status);
        return ResponseEntity.ok(rooms);
    }

    @GetMapping("/filter/type")
    @PreAuthorize("hasRole('ADMIN') or hasRole('MANAGER') or hasRole('USER')")
    public ResponseEntity<List<RoomResponse>> getByRoomType(@RequestParam String roomType) {
        List<RoomResponse> rooms = roomService.getByRoomType(roomType);
        return ResponseEntity.ok(rooms);
    }
}

