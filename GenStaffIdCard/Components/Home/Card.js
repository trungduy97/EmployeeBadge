import React, { Component } from 'react'
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Dimensions,
  Button,
  Image,
  TouchableOpacity
} from 'react-native'
import ImagePicker from 'react-native-image-picker'

var { height, width } = Dimensions.get('window')

const options = {
  title: 'Select Avatar',
  customButtons: [{ name: 'fb', title: 'Choose Photo from Facebook' }],
  storageOptions: {
    skipBackup: true,
    path: 'images'
  }
}

export class Card extends Component {
  render () {
    return (
      <View style={styles.card}>
        <TouchableOpacity
          style={{
            flex: 1,
            height: height / 2,
            width: width / 2,
            backgroundColor: '#d4e157'
          }}
          onPress={this.show.bind(this)}
        >
          <Image
            style={styles.image}
            source={require('../../img/iconTakePicture.png')}
          />
          <Text style={styles.text}>PICTURE</Text>
        </TouchableOpacity>

        <TouchableOpacity
          style={{
            flex: 1,
            height: height / 2,
            width: width / 2,
            backgroundColor: '#ffca28'
          }}
        >
           <Image
            style={styles.image}
            source={require('../../img/nameCard.png')}
          />
          <Text style={styles.text}>NAME CARD</Text>
        </TouchableOpacity>
      </View>
    )
  }

  show () {
    ImagePicker.showImagePicker(options, response => {
      console.log('Response = ', response)

      if (response.didCancel) {
        console.log('User cancelled image picker')
      } else if (response.error) {
        console.log('ImagePicker Error: ', response.error)
      } else if (response.customButton) {
        console.log('User tapped custom button: ', response.customButton)
      } else {
        const source = { uri: response.uri }

        // You can also display the image using data:
        // const source = { uri: 'data:image/jpeg;base64,' + response.data };

        this.setState({
          avatarSource: source
        })
      }
    })
  }
}

const styles = StyleSheet.create({
  card: {
    flex: 1,
    flexDirection: 'row',
    width: width,
    justifyContent: 'flex-start',
    alignItems: 'center',
    backgroundColor: '#9c27b0'
  },
  text: {
    width: 100,
    marginTop: 10,
    marginLeft: (width / 2 - 100) / 2,
    fontSize: 23,
    justifyContent: 'center',
    alignItems: 'center',
    color: 'black',
    textAlign: 'center',
    fontWeight: 'bold'
  },
  image: {
    width: 150,
    height: 150,
    marginLeft: (width / 2 - 150) / 2,
    marginTop: 50,
    justifyContent: 'center',
    alignItems: 'center'
  }
})
