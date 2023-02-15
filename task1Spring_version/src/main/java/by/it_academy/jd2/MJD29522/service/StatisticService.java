package by.it_academy.jd2.MJD29522.service;

import by.it_academy.jd2.MJD29522.dto.*;
import by.it_academy.jd2.MJD29522.service.api.IGenreService;
import by.it_academy.jd2.MJD29522.service.api.ISingerService;
import by.it_academy.jd2.MJD29522.service.api.IStatisticService;
import by.it_academy.jd2.MJD29522.service.api.IVoteService;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class  StatisticService implements IStatisticService {

    private final ISingerService singerService;
    private final IGenreService genreService;
    private final IVoteService voteService;

    public StatisticService(ISingerService singerService, IGenreService genreService, IVoteService voteService) {
        this.singerService = singerService;
        this.genreService = genreService;
        this.voteService = voteService;
    }


    @Override
    public List<StatisticDTOGenre> getResultGenre() {
        List<GenreDTOWithID> genres = genreService.get();
        List<VoteDTO> votes = voteService.getVote();
        List<StatisticDTOGenre> statisticGenre = new ArrayList<>();
        // в этом цикле происходит заполнение именами. т.к. я заполняю ид и имена, то сравнивать проще по ид, в след методах все однотипно
        for(GenreDTOWithID  genreID : genres){
            statisticGenre.add(new StatisticDTOGenre(genreID.getId(),genreID.getName()));
        }

        for(VoteDTO vote : votes){
            for(int i = 0;i<vote.getGenresID().length;i++){                         //берем каждый существующий жанр в голосе
                boolean notAddingCount = true;
                for(int j = 0;j<statisticGenre.size();j++){                         //ничего лучшего не придумал, как обратиться к элементу на прямую без цикла???
                        if(statisticGenre.get(j).getId()==vote.getGenresID()[i]){
                            statisticGenre.get(j).addCount();
                            notAddingCount = false;
                            break;
                        }
                }
                if(notAddingCount){
                    throw new ArrayStoreException("Такого голоса не существует в жанрах");
                }
            }
        }
        List<StatisticDTOGenre> sortList = statisticGenre.stream().sorted(Comparator.comparing(StatisticDTOGenre::getCount).reversed()).collect(Collectors.toList());
        return sortList;
    }

    @Override
    public List<StatisticDTOArtist> getResultSinger() {
        List<SingerDTOWithID> singers = singerService.get();
        List<VoteDTO> votes = voteService.getVote();
        List<StatisticDTOArtist> statisticSinger = new ArrayList<>();
        for(SingerDTOWithID singerID: singers){                                    // в этом цикле происходит заполнение именами
            statisticSinger.add(new StatisticDTOArtist(singerID.getId(),singerID.getName()));
        }
        for(VoteDTO vote : votes){
            boolean notAddingCount = true;
            for(int i = 0;i<statisticSinger.size();i++){
                if(vote.getSingerID()==statisticSinger.get(i).getId()){
                    statisticSinger.get(i).addCount();
                    notAddingCount = false;
                    break;
                }
            }
            if(notAddingCount){
                throw new ArrayStoreException("Такого исполнителя не существует в жанрах");
            }
        }
        List<StatisticDTOArtist> sortList = statisticSinger.stream().sorted(Comparator.comparing(StatisticDTOArtist::getCount).reversed()).collect(Collectors.toList());
        return sortList;
    }
    @Override
    public List<StatisticDTOMessage> getResultMessage() {
        List<VoteDTO> votes = voteService.getVote();
        List<StatisticDTOMessage> messages = new ArrayList<>();
        for(VoteDTO vote : votes){
            messages.add(new StatisticDTOMessage(vote.getLocalDate(),vote.getMessage()));
        }
        List<StatisticDTOMessage> sortList = messages.stream().sorted(Comparator.comparing(StatisticDTOMessage::getTime)).collect(Collectors.toList());
        return sortList;
    }

    @Override
    public StatisticDTO getResult() {
        StatisticDTO result = new StatisticDTO(getResultSinger(), getResultGenre(), getResultMessage());
        return result;
    }
}
