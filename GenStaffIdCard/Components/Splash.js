import React, { Component } from "react";
import {
  Platform,
  StyleSheet,
  Text,
  View,
  Image,
  Animated,
  Dimensions,
} from "react-native";

var { height, width } = Dimensions.get("window");
export default class Splash extends Component {
  state = {
    logoOpacity: new Animated.Value(0),
    titleMaginTop: new Animated.Value(height / 2),
  };
  async componentDidMount() {
    Animated.sequence([
      Animated.timing(this.state.logoOpacity, {
        toValue: 1,
        duration: 1000,
      }),
      Animated.timing(this.state.titleMaginTop, {
        toValue: 10,
        duration: 700,
      }),
    ]).start(() => {});
  }

  render() {
    return (
      <View style={styles.container}>
        <Animated.Image
          style={{ ...styles.image, opacity: this.state.logoOpacity }}
          source={require("../img/logo.png")}
        />

        <Animated.Image
          style={{ ...styles.image2, marginTop: this.state.titleMaginTop }}
          source={require("../img/text.png")}
        />

        {/* <Animated.Text style={{...styles.title, marginTop: this.state.titleMaginTop}}>Fostering IT Innovation</Animated.Text> */}
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    backgroundColor: "#80d6ff",
    flex: 1,
    alignItems: "center",
    justifyContent: "center",
  },

  title: {
    fontWeight: "bold",
    fontSize: 24,
    color: "blue",
    textAlign: "center",
    width: 400,
  },

  image: {
    width: 200,
    height: 200,
  },
  image2: {
    width: 160,
    height: 25,
  },
});
