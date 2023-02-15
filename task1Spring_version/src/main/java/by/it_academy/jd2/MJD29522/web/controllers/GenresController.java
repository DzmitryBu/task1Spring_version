package by.it_academy.jd2.MJD29522.web.controllers;

import by.it_academy.jd2.MJD29522.dto.GenreDTO;
import by.it_academy.jd2.MJD29522.dto.GenreDTOWithID;
import by.it_academy.jd2.MJD29522.service.api.IGenreService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/genre")
public class GenresController {

    private final IGenreService service;
    public GenresController(IGenreService service){
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<GenreDTOWithID> getGenres()  {
        return service.get();
    }
    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public GenreDTOWithID getCard(@PathVariable("id") Long id)  {
        return service.getCard(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    public void doPost(@RequestBody GenreDTO genre) {
        service.add(genre);
    }

    @RequestMapping(path = "/{id}/version/{version}", method = RequestMethod.PUT)
    public void doUpdate(@PathVariable("id") Long genreId,
                         @PathVariable("version") Long version,
                       @RequestBody GenreDTO genre){
        service.update(genreId,version, genre);
    }

    @RequestMapping(path = "/{id}/version/{version}", method = RequestMethod.DELETE)
    public void doDelete(@PathVariable ("id") Long id, @PathVariable ("version") Long version){
        service.delete(id, version);
    }
}