import React, { Component } from "react";
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Dimensions,
  Slider,
} from "react-native";
import Gallery from "react-native-image-gallery";

var { height, width } = Dimensions.get("window");
var image = [
  "http://i.imgur.com/XP2BE7q.jpg",
  "http://i.imgur.com/5nltiUd.jpg",
];

export class SlideImage extends Component {
  constructor(props) {
    super(props);
    this.state = {
      index: true,
    };

    var actionImage = () => {
      this.setState(previousState => {
        return {
          index: !previousState.index,
        };
      });
    };

    const time = 2000;
    setInterval(actionImage, time);
  }

  render() {
    var index = true;
    var a = 0;
    if (this.state.index === true) {
      a = 0;
    } else {
      a = 1;
    }
    return (
      <Gallery
        style={styles.container}
        images={[{ source: { uri: image[a] } }]}
      />
    );
  }
}

const styles = StyleSheet.create({
  slide: {
    width: width,
    height: height / 2.5,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "#ffeb3b",
  },
  container: {
    flex: 1,
    flexDirection: "column",
    width: width,
    height: height / 2.5,
    justifyContent: "center",
    alignItems: "center",
  },
  text: {
    fontSize: 50,
    textAlign: "center",
  },
});
