import './App.css';
import { MatchPage } from './pages/MatchPages';
import {TeamPage} from './pages/TeamPage.js';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom';

function App() {
  return (
    <div className="App">
      <Router>
      
      <Routes>
        
          <Route path="/teams/:teamName/matches/:year"  element = {<MatchPage />} />
          <Route path="/teams/:teamName"  element = {<TeamPage />} />
        
        
        
        
      </Routes>
      
      </Router>
   
    </div>
  );
}

export default App;
