package com.uca.pncparcialfinalhotel.service;

import com.uca.pncparcialfinalhotel.dto.RoomRequest;
import com.uca.pncparcialfinalhotel.dto.RoomResponse;
import com.uca.pncparcialfinalhotel.entity.Room;
import java.util.List;

public interface RoomService {

    RoomResponse create(RoomRequest roomRequest);

    RoomResponse update(Long id, RoomRequest roomRequest);

    void delete(Long id);

    RoomResponse getById(Long id);

    List<RoomResponse> getAll();

    List<RoomResponse> getByStatus(String status);

    List<RoomResponse> getByRoomType(String roomType);

    Room getRoomEntityById(Long id);
}

