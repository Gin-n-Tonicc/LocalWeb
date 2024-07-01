package com.localWeb.localWeb.services.impl;

import com.localWeb.localWeb.models.dto.request.EventRequest;
import com.localWeb.localWeb.models.dto.response.EventResponse;
import com.localWeb.localWeb.services.EventService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EventServiceImpl implements EventService {

    @Override
    public List<EventResponse> getAllEvents() {
        return List.of();
    }

    @Override
    public EventResponse getEventById(UUID id) {
        return null;
    }

    @Override
    public EventResponse createEvent(EventRequest eventDTO) {
        return null;
    }

    @Override
    public EventResponse updateEvent(UUID id, EventRequest eventDTO) {
        return null;
    }

    @Override
    public void deleteEvent(UUID id) {

    }
}
