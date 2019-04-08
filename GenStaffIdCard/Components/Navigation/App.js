import React from 'react';
import { Text, View } from 'react-native';
import { createBottomTabNavigator, createAppContainer } from 'react-navigation';
import Home from '../Home/Home'


class SettingsScreen extends React.Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>Settings!</Text>
      </View>
    );
  }
}

class GmailScreen extends React.Component {
  render() {
    return (
      <View style={{ flex: 1, justifyContent: 'center', alignItems: 'center' }}>
        <Text>GmailScreen!</Text>
      </View>
    );
  }
}

const TabNavigator = createBottomTabNavigator({
  Home: Home,
  GmailScreen: GmailScreen,
  Settings: SettingsScreen,
});

export default createAppContainer(TabNavigator);
