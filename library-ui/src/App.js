import React, {Component} from 'react';
import './css/App.css';
import Home from './Home';
import {BrowserRouter as Router, Route, Switch} from 'react-router-dom';
import GroupList from './BookList';
import GroupEdit from './BookEdit';
import {CookiesProvider} from 'react-cookie';

class App extends Component {
  render() {
    return (
        <CookiesProvider>
          <Router>
            <Switch>
              <Route path='/' exact={true} render={() => <Home/>}/>
              <Route path='/books' exact={true} render={(props) => <GroupList {...props}/>}/>
              <Route path='/book/:id' exact={true} render={() => <GroupEdit/>}/>
            </Switch>
          </Router>
        </CookiesProvider>
    )
  }
}

export default App;