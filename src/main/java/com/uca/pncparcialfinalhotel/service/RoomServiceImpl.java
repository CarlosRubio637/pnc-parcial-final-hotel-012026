package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.RoomRequest;
import com.uca.pncparcialfinalhotel.dto.RoomResponse;
import com.uca.pncparcialfinalhotel.entity.Room;
import com.uca.pncparcialfinalhotel.repository.RoomRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;

    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public RoomResponse create(RoomRequest roomRequest) {
        if (roomRepository.existsByRoomNumber(roomRequest.getRoomNumber())) {
            throw new IllegalArgumentException("La habitación con número " + roomRequest.getRoomNumber() + " ya existe");
        }

        Room room = Room.builder()
                .roomNumber(roomRequest.getRoomNumber())
                .roomType(roomRequest.getRoomType())
                .capacity(roomRequest.getCapacity())
                .pricePerNight(roomRequest.getPricePerNight())
                .status(roomRequest.getStatus())
                .build();

        Room savedRoom = roomRepository.save(room);
        log.info("Habitación creada: {}", savedRoom.getRoomNumber());
        return mapToResponse(savedRoom);
    }

    @Override
    public RoomResponse update(Long id, RoomRequest roomRequest) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con ID: " + id));

        if (!room.getRoomNumber().equals(roomRequest.getRoomNumber()) &&
            roomRepository.existsByRoomNumber(roomRequest.getRoomNumber())) {
            throw new IllegalArgumentException("El número de habitación " + roomRequest.getRoomNumber() + " ya existe");
        }

        room.setRoomNumber(roomRequest.getRoomNumber());
        room.setRoomType(roomRequest.getRoomType());
        room.setCapacity(roomRequest.getCapacity());
        room.setPricePerNight(roomRequest.getPricePerNight());
        room.setStatus(roomRequest.getStatus());

        Room updatedRoom = roomRepository.save(room);
        log.info("Habitación actualizada: {}", updatedRoom.getRoomNumber());
        return mapToResponse(updatedRoom);
    }

    @Override
    public void delete(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con ID: " + id));

        roomRepository.delete(room);
        log.info("Habitación eliminada: {}", room.getRoomNumber());
    }

    @Override
    public RoomResponse getById(Long id) {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con ID: " + id));
        return mapToResponse(room);
    }

    @Override
    public List<RoomResponse> getAll() {
        return roomRepository.findAll().stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getByStatus(String status) {
        return roomRepository.findByStatus(status).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<RoomResponse> getByRoomType(String roomType) {
        return roomRepository.findByRoomType(roomType).stream()
                .map(this::mapToResponse)
                .collect(Collectors.toList());
    }

    @Override
    public Room getRoomEntityById(Long id) {
        return roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Habitación no encontrada con ID: " + id));
    }

    private RoomResponse mapToResponse(Room room) {
        return new RoomResponse(
                room.getId(),
                room.getRoomNumber(),
                room.getRoomType(),
                room.getCapacity(),
                room.getPricePerNight(),
                room.getStatus(),
                room.getCreatedAt(),
                room.getUpdatedAt()
        );
    }
}

