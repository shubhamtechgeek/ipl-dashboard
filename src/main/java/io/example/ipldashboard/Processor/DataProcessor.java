package io.example.ipldashboard.Processor;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import io.example.ipldashboard.Entity.MatchInput;
import io.example.ipldashboard.Model.Match;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.item.ItemProcessor;

public class DataProcessor implements ItemProcessor<MatchInput, Match> {

    private static final Logger log = LoggerFactory.getLogger(DataProcessor.class);

    @Override
    public Match process(final MatchInput matchInput) throws Exception {
        Match match = new Match();

        match.setId(Long.parseLong(matchInput.getId()));
        match.setCity(matchInput.getCity());
        String date1 = matchInput.getDate();
        DateTimeFormatter df = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        match.setDate(LocalDate.parse(matchInput.getDate(), df));
        match.setPlayerOfMatch(matchInput.getPlayer_Of_match());
        match.setVenue(matchInput.getVenue());

        //Setting teams based on innings order
        String firstInningTeam, secondInningTeam;

        if("bat".equals(matchInput.getToss_decision())){
            firstInningTeam = matchInput.getToss_winner();
            secondInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                ?  matchInput.getTeam2() : matchInput.getTeam1();
        }else{
            secondInningTeam = matchInput.getToss_winner();
            firstInningTeam = matchInput.getToss_winner().equals(matchInput.getTeam1())
                ?  matchInput.getTeam2() : matchInput.getTeam1();
        }


        match.setTeam1(firstInningTeam);
        match.setTeam2(secondInningTeam);
        
        match.setTossWinner(matchInput.getToss_winner());
        match.setTossDecision(matchInput.getToss_decision());
        match.setMatchWinner(matchInput.getWinner());
        match.setResult(matchInput.getResult());
        match.setResultMargin(matchInput.getResult_margin());
        match.setUmpire1(matchInput.getUmpire1());
        match.setUmpire2(matchInput.getUmpire2());

        return match;

    }
}
