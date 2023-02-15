package by.it_academy.jd2.MJD29522.web.controllers;

import by.it_academy.jd2.MJD29522.dto.SingerDTO;
import by.it_academy.jd2.MJD29522.dto.SingerDTOWithID;
import by.it_academy.jd2.MJD29522.service.api.ISingerService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/singer")
public class SingersController {

    private final ISingerService service;
    public SingersController(ISingerService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<SingerDTOWithID> getSingers() {
        return service.get();
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public SingerDTOWithID getCard(@PathVariable("id") Long id) {
        return service.getCard(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost(@RequestBody SingerDTO singer){
        service.add(singer);
    }

    @RequestMapping(path = "/{id}/version/{version}", method = RequestMethod.PUT)
    public void doUpdate(@PathVariable("id") Long singerId,
                         @PathVariable("version") Long version,
                       @RequestBody SingerDTO singer){
        service.update(singerId, version, singer);
    }

    @RequestMapping(path = "/{id}/version/{version}", method = RequestMethod.DELETE)
    public void doDelete(@PathVariable("id") Long id, @PathVariable("version") Long version){
        service.delete(id, version);
    }
}