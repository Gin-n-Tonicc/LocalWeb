package com.localWeb.localWeb.service;

import com.localWeb.localWeb.models.dto.request.EventRequest;
import com.localWeb.localWeb.models.dto.response.EventResponse;

import java.util.List;
import java.util.UUID;

public interface EventService {

    List<EventResponse> getAllEvents();

    EventResponse getEventById(UUID id);

    EventResponse createEvent(EventRequest eventDTO);

    EventResponse updateEvent(UUID id, EventRequest eventDTO);

    void deleteEvent(UUID id);
}
