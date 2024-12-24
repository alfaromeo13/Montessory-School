package com.example.dedis.services;

import com.example.dedis.dto.EventDTO;
import com.example.dedis.entities.Event;
import com.example.dedis.mappers.EventMapper;
import com.example.dedis.projections.EventProjection;
import com.example.dedis.repositories.EventRepository;
import com.example.dedis.repositories.ParentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventService {

    private final MailService mailService;
    private final EventMapper eventMapper;
    private final ImageService imageService;
    private final EventRepository eventRepository;
    private final ParentRepository parentRepository;

    public List<EventProjection> getAllEvents(){
        return eventRepository.getAllEvents();
    }

    @Cacheable(cacheNames="event", key="#id", unless = "#result == null")
    public Optional<Event> getSpecificEvent(Long id){
        return eventRepository.findById(id);
    }

    @Transactional
    @CachePut(cacheNames = "event", key = "#result.id")
    public Event createEvent(EventDTO eventDTO, MultipartFile[] images) {

        String eventTitle = (String) eventDTO.getContent().get("title");

        List<Map<String, Object>> contentBlocks =
                (List<Map<String, Object>>) eventDTO.getContent().get("contentBlocks");

        int imageIndex = 0;

        // Now we replace placeholders in contentBlocks with actual image URLs
        for (Map<String, Object> block : contentBlocks) {
            if(imageIndex == images.length) break;
            if ("image".equals(block.get("type"))) { // Match the type 'image'
                List<String> imageURLs = new ArrayList<>();
                int expectedImageCount = (int) block.get("imageCount");
                for(int i=0; i<expectedImageCount; i++){
                    MultipartFile image = images[imageIndex];
                    String url = imageService.uploadFile(image);
                    imageURLs.add(url);
                    imageIndex++;
                }
                block.put("values", imageURLs);
            }
        }

        mailService.sendNotificationToAllParents(eventTitle,parentRepository.findAll());
        return eventRepository.save(eventMapper.toEntity(eventDTO));
    }

    @Transactional
    @CachePut(cacheNames = "event", key = "#id")
    public Event updateEvent(Long id, EventDTO eventDTO, MultipartFile[] images){
        eventDTO.setId(id);
        return createEvent(eventDTO,images);
    }

    @Transactional
    @CacheEvict(cacheNames = "event", key = "#id")
    public void cancelEvent(Long id){
        if(!eventRepository.existsById(id)) return;
        String eventTitle = eventRepository.eventTitle(id);
        imageService.deleteFiles(eventRepository.imageURLs(id));
        eventRepository.deleteById(id);
        mailService.sendCancelEventMessageToParents(eventTitle,parentRepository.findAll());
    }
}