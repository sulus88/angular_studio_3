package org.launchcode.codingevents.controllers;

import org.launchcode.codingevents.data.EventData;
import org.launchcode.codingevents.data.EventRepository;
import org.launchcode.codingevents.models.Event;
import org.launchcode.codingevents.models.EventType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Controller
@RequestMapping("events")
public class EventController {

    @Autowired
   private EventRepository eventRepository;

    @GetMapping
    public String displayAllEvents(Model model){
//model.addAttribute("events", EventData.getAll());
        model.addAttribute("events", eventRepository.findAll());
return "events/index";
    }
@GetMapping("create")
    public String renderCreateEventForm(Model model){
        model.addAttribute("title", "Create Event");
        model.addAttribute("event", new Event());
        model.addAttribute("types",(EventType.values()));
        return "events/create";
    }

    @PostMapping("create")
    public String processCreateEventForm(@ModelAttribute @Valid Event newEvent,
                                         Errors errors, Model model){
        if(errors.hasErrors()){
            model.addAttribute("title", "Create Event");
            model.addAttribute("errorMsg", "Bad Data!");
            return "events/create";
        }
        eventRepository.save(newEvent);
        //EventData.add(newEvent);
        return "redirect:/events";
    }

    @GetMapping("delete")
    public String displayDeleteEventForm(Model model){
        //model.addAttribute("title", "Delete Events");
        model.addAttribute("events", eventRepository.findAll());
        model.addAttribute("events", EventData.getAll());
        return "events/delete";
    }

    @PostMapping("delete")
    public String processDeleteEventsForm(@RequestParam (required=false) int [] eventIds){
        if(eventIds != null) {
            for (int id : eventIds) {
                eventRepository.deleteById(id);
                //EventData.remove(id);
            }
        }
        return "redirect:";
    }

}
