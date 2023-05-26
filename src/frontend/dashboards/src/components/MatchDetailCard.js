import { React } from 'react';
import { Link } from 'react-router-dom';

export const MatchDetailCard = ({teamName, match}) => {

  
    if(!match) return null;
    const otherTeam = match.team1 === teamName ? match.team2: match.team1;
    const otherTeamRoute = `/teams/${otherTeam}`
    return (
    <div className="MatchDetailCard">
      <h2>Latest Match</h2>
      
      <h2>   
            vs   <Link to={otherTeamRoute}>{otherTeam} </Link> </h2>
      <h2>{match.date}</h2>
      <h2>at {match.venue}</h2>
      <h2>{match.matchWinner} won by {match.resultMargin} {match.result}</h2>
    </div>
  );
}


