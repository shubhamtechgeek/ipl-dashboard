package io.example.ipldashboard.Repository;

import io.example.ipldashboard.Controller.TeamController;
import io.example.ipldashboard.Model.Team;
import org.springframework.data.repository.CrudRepository;

public interface TeamRepository extends CrudRepository<Team, Long> {

    Team findByTeamName(String teamName);


}
