import React, { useEffect, useState } from 'react';
import { MatchDetailCard } from '../components/MatchDetailCard';
import { MatchSmallCard } from '../components/MatchSmallCard';

export const TeamPage = () => {
  const [team, setTeam] = useState({ matches: [] });

  useEffect(() => {
    const fetchMatches = async () => {
      try {
        const response = await fetch('http://localhost:8080/team/Rajasthan%20Royals');
        const data = await response.json();
        console.log(data);
        setTeam(data);
      } catch (error) {
        console.error('Error fetching team data:', error);
      }
    };

    fetchMatches();
  }, []);

  return (
    <div className="TeamPage">
      <h1>{team.teamName}</h1>

      {team.matches.length > 0 && <MatchDetailCard match={team.matches[0]} />}
      {team.matches.slice(1).map((match) => (
        <MatchSmallCard key={match.id} match={match} />
      ))}
    </div>
  );
};
