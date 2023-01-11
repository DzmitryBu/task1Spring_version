package by.it_academy.jd2.MJD29522.web;

import by.it_academy.jd2.MJD29522.dto.GenreDTO;
import by.it_academy.jd2.MJD29522.service.api.IGenreService;
import by.it_academy.jd2.MJD29522.service.fabrics.GenreServiceSingleton;
import org.apache.commons.lang3.math.NumberUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.Map;

@WebServlet(name = "GenresServlet", urlPatterns = "/genre")
public class GenresServlet extends HttpServlet {

    private final String DELETE_ID = "deleteId";
    private final String ADD = "add";
    private final String UPDATE_ID = "updateId";
    private final String UPDATE_NEW_NAME = "newName";
    private final IGenreService service;
    public GenresServlet(){
        this.service = GenreServiceSingleton.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        PrintWriter writer = resp.getWriter();

//        List<GenreID> genreIDS = service.get();
//        for (GenreID genreID : genreIDS) {
//            writer.write("<p>" + genreID.getId() + ". " + genreID.getGenreDTO().getName() + "</p>");
//        }
        List<GenreDTO> genres = service.get();
        for (GenreDTO genre : genres) {
            writer.write("<p>" + (genres.indexOf(genre) + 1) + ". " + genre.getName() + "</p>");
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException{
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html; charset=UTF-8");

        Map<String, String[]> mapParametrs = req.getParameterMap();

        List<GenreDTO> genres = service.get();

        PrintWriter writer = resp.getWriter();

        if(mapParametrs.containsKey(DELETE_ID)){
            String[] deleteGenre = mapParametrs.get(DELETE_ID);
            if(deleteGenre == null) {
                throw new IllegalArgumentException("id жанра для удаления не передан");
            }
            if(deleteGenre.length > 1){
                throw new IllegalArgumentException("Жанры можно удалять только по одному");
            }
            if(deleteGenre[0] == null || deleteGenre[0].isBlank() || !NumberUtils.isDigits(deleteGenre[0])){
                throw new IllegalArgumentException("Необходимо ввести число, для удаления жанра по id");
            }
            int idForDelete = Integer.parseInt(deleteGenre[0]);

            if(service.exist(idForDelete)){
                String genreForDelete = genres.get(idForDelete - 1).getName();
                service.delete(idForDelete);
                writer.write("<p>Вы удалили жанр " + genreForDelete + " по id: " + idForDelete + "</p>");
            }
        }
        if(mapParametrs.containsKey(ADD)){
            String[] addGenre = mapParametrs.get(ADD);
            if(addGenre == null) {
                throw new IllegalArgumentException("Название нового жанра для добавления не передано");
            }
            if(addGenre.length > 1){
                throw new IllegalArgumentException("Жанры можно добавлять только по одному");
            }
            if(addGenre[0] == null || addGenre[0].isBlank()){
                throw new IllegalArgumentException("Необходимо ввести название жанра для добавления");
            }
            String nameForAdd = addGenre[0];
            for (GenreDTO genre : genres) {
                if(nameForAdd.equals(genre.getName())){
                    throw new IllegalArgumentException("Жанр с именем " + nameForAdd + " уже есть в списке жанров");
                }
            }
            service.add(nameForAdd);
            writer.write("<p>Вы добавили жанр " + nameForAdd + " по id: " + genres.size() + "</p>");
        }
    }
}