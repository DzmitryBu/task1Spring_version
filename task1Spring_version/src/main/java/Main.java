
import by.it_academy.jd2.MJD29522.service.GenreService;
import by.it_academy.jd2.MJD29522.service.SingerService;
import by.it_academy.jd2.MJD29522.service.StatisticService;
import by.it_academy.jd2.MJD29522.service.VoteService;
import by.it_academy.jd2.MJD29522.service.api.IGenreService;
import by.it_academy.jd2.MJD29522.service.api.ISingerService;
import by.it_academy.jd2.MJD29522.service.api.IStatisticService;
import by.it_academy.jd2.MJD29522.service.api.IVoteService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("main-context.xml");

        IGenreService genreService = context.getBean("genreService", GenreService.class);
        ISingerService singerService = context.getBean("singerService", SingerService.class);
        IVoteService voteService = context.getBean("voteService", VoteService.class);
        IStatisticService statisticService = context.getBean("statisticService", StatisticService.class);
        long [] array = {1, 2, 3};

        System.out.println(genreService.get());
        System.out.println(singerService.get());
        System.out.println(statisticService.getResult());

//        voteService.save(new VoteDTO(
//                1l,
//                new long[]{1, 2, 3},
//                "Hi",
//                "dm_bax@mail.ru",
//                LocalDateTime.now()));
//
//        System.out.println(statisticService.getResultGenre());
//        System.out.println(statisticService.getResultSinger());
//        System.out.println(statisticService.getResultMessage());
    }
}
