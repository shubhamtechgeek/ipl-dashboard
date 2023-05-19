package io.example.ipldashboard.Processor;

import io.example.ipldashboard.Model.Team;
import jakarta.persistence.EntityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

  private static final Logger log = LoggerFactory.getLogger(JobCompletionNotificationListener.class);

//  private final JdbcTemplate jdbcTemplate;
    private final EntityManager em;
  
//  @Autowired
//  public JobCompletionNotificationListener(JdbcTemplate jdbcTemplate) {
//    this.jdbcTemplate = jdbcTemplate;
//  }

  @Autowired
  public JobCompletionNotificationListener(EntityManager em) {
    this.em = em;
  }

  @Override
  @Transactional
  public void afterJob(JobExecution jobExecution) {
    if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
      log.info("!!! JOB FINISHED! Time to verify the results");

//      jdbcTemplate.query("SELECT team1, team2, date FROM MATCH",
//              (rs, row) -> "Team 1 " + rs.getString(1) + " Team 2 " + rs.getString(2) + " Date " + rs.getString(3)
//      ).forEach(str -> System.out.println(str));

      Map<String, Team> teamData = new HashMap<>();

      em.createQuery("select m.team1, count(*) from Match m group by m.team1", Object[].class)
              .getResultList()
              .stream()
              .map(e ->  new Team((String)e[0], (Long)e[1]))
              .forEach(team -> teamData.put(team.getTeamName(), team));

      em.createQuery("select m.team2, count(*) from Match m group by m.team2", Object[].class)
              .getResultList()
              .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                team.setTotalMatches(team.getTotalMatches() + (Long) e[1]);
              });
      em.createQuery("select m.matchWinner, count(*) from Match m group by m.matchWinner", Object[].class)
              .getResultList()
              .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                if(team != null) team.setTotalWins((Long) e[1]);
              });

      teamData.values().forEach(team -> em.persist(team));

        teamData.values().forEach(team -> System.out.println(team));


    }
  }
}
