import React, { Component } from 'react'
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Dimensions,
  Image
} from 'react-native'
var { height, width } = Dimensions.get('window')

export class Footer extends Component {
  render () {
    return (
      <View style={styles.footer}>
        <Image
          style={styles.imageIcon}
          source={require('../../img/menu.png')}
        />
        <Text style={styles.text}>Home</Text>
      </View>
    )
  }
}

const styles = StyleSheet.create({
  footer: {
    flexDirection: 'row',
    width: width,
    height: 35,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#80d6ff'
  },
  text: {
    justifyContent: 'center',
    alignItems: 'center',
    color: 'white',
    paddingTop: 15,
    marginLeft: 120,
    fontSize: 20,
    fontWeight: 'bold'
  },
  imageIcon: {
    marginTop: 10,
    marginLeft: 15,
    width: 30,
    height: 30
  }
})
