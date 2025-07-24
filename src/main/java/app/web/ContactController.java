package app.web;

import app.model.Contact;
import app.serivce.ContactService;
import app.web.dto.ContactRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.UUID;

@Controller
public class ContactController {

    @Autowired
    private ContactService contactService;

    @GetMapping("/")
    public ModelAndView getHomeView() {

        ModelAndView modelAndView = new ModelAndView();

        List<Contact> contacts = contactService.getAllContacts();

        modelAndView.setViewName("index");
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @GetMapping("/new")
    public ModelAndView getCreateView() {

        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("create-contact");
        modelAndView.addObject("contactRequest", new ContactRequest());

        return modelAndView;
    }

    @PostMapping("/new")
    public ModelAndView addNewContact(@Valid ContactRequest contactRequest, BindingResult bindingResult) {

        ModelAndView modelAndView = new ModelAndView();

        if (bindingResult.hasErrors()) {
            return new ModelAndView("create-contact");
        }

        modelAndView.addObject(contactRequest);
        contactService.addNewContact(contactRequest);

        return new ModelAndView("redirect:/");
    }

    @GetMapping("/edit/{id}")
    public ModelAndView getEditContactView(@PathVariable UUID id) {

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.setViewName("edit-contact");
        modelAndView.addObject("contactRequest", new ContactRequest());

        return modelAndView;
    }

    @DeleteMapping("/remove/{id}")
    public ModelAndView removeContact(@PathVariable UUID id) {

        contactService.deleteContact(id);
        return new ModelAndView("redirect:/");
    }

    @PutMapping("/switch/{id}")
    public ModelAndView switchContactActivity(@PathVariable UUID id) {

        contactService.changeContactActivity(id);
        return new ModelAndView("redirect:/");
    }

}
