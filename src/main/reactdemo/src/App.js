import React, { Component } from 'react';
import {BrowserRouter as Router,Route,Redirect,Switch} from 'react-router-dom'
import Records from './components/Records'
import test from './router/test'
import login from './router/login'
import user from './router/user'
import home from './router/home'
import page404 from './router/page404'

class App extends Component {
  render() {
    const CONTEXT = "/demo";
    return (
        <Router>
            <div>
                <Switch>
                    <Route exact path={CONTEXT} component={Records} />
                    <Route path={`${CONTEXT}/test`} component={test} />
                    <Route path={`${CONTEXT}/user`} component={user} />
                    <Route path={`${CONTEXT}/login`} component={login} />
                    <Route path={`${CONTEXT}/404`} component={page404} />
                    <Redirect to={`${CONTEXT}/404`} />
                </Switch>
            </div>
        </Router>
    );
  }
}

export default App;
