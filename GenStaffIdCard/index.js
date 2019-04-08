/**
 * @format
 */
import React, { Component } from 'react';
import {AppRegistry} from 'react-native';
import App from './App';
import {name as appName} from './app.json';
import ImagePickerHieu from './components/ImagePickerHieu'
import Splash from './components/Splash'
import DrawerNavigator from './components/Navigator/DrawerNavigator'

class Main extends Component {
    constructor(props) {
        super(props);
        this.state = {currentScreen : 'Splash'};
        setTimeout(()=>{
            this.setState({currentScreen : 'App'})
        }, 3000)
    }

    render(){
        const { currentScreen } = this.state
        let mainScreen = currentScreen === 'Splash' ? <Splash/> : <App/>
        return mainScreen;
    }
}

AppRegistry.registerComponent(appName, () => Main);
