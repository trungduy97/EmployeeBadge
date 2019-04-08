import React, { Component } from 'react';
import {
  Text, View, Image, TouchableHighlight
} from 'react-native';
import HeaderComponent from './HeaderComponent';

export default class Demo1 extends Component {
  static navigationOptions = ({ navigation }) => {
    let drawerLabel = 'Withdrawal';
    let drawerIcon = () => (
      <Image
        source={require('../../img/iconhome.png')}
        style={{ width: 26, height: 26, tintColor: '#e97600' }}
      />
    );
    return { drawerLabel, drawerIcon };
  }
  
  render() {
    return (
      <View style={{flex: 1, flexDirection: 'column' }}>
        <HeaderComponent {...this.props} />
        <View style={{
          flex: 1,
          backgroundColor: '#e97600',
          alignItems: 'center',
          justifyContent: 'center'
        }}>
          <Text style={{ fontWeight: 'bold', fontSize: 22, color: 'white' }}>
            This is Withdrawal Screen
          </Text>
        </View>
      </View>
    );
  }
}