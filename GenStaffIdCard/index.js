/**
 * @format
 */
import React, { Component } from 'react';
import {AppRegistry} from 'react-native';
import App from './App';
import Splash from './Components/Splash'
import {name as appName} from './app.json';
import TabNavigator from './Components/Navigation/App'

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {currentScreen : 'Splash'};
        setTimeout(()=>{
            this.setState({currentScreen : 'App'})
        }, 5000)
    }

    render(){
        const { currentScreen } = this.state
        let mainScreen = currentScreen === 'Splash' ? <Splash/> : <TabNavigator/>
        return mainScreen;
    }
}

AppRegistry.registerComponent(appName, () => Main);
