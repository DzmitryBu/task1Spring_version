package by.it_academy.jd2.MJD29522.web.controllers;

import by.it_academy.jd2.MJD29522.dto.VoteDTO;
import by.it_academy.jd2.MJD29522.service.api.IVoteService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/vote")
public class PostController {

    private final IVoteService service;
    public PostController(IVoteService service) {
        this.service = service;
    }

    @RequestMapping(method = RequestMethod.POST)
    protected void doPost(@RequestBody VoteDTO vote)  {
        service.save(vote);
//            String path = req.getContextPath() + "/result";
//            resp.sendRedirect(path);
    }
}
