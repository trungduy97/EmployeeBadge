import React, { Component } from "react";
import {
  Platform,
  StyleSheet,
  Text,
  View,
  TouchableOpacity,
  Dimensions,
} from "react-native";

import { Footer } from "./Footer";
import { SlideImage } from "./SlideImage";
import { Card } from "./Card";

var { height, width } = Dimensions.get("window");

export class Home extends Component {
  render() {
    return (
      <View style={styles.container}>
        <Footer />
        <SlideImage />
        <Card />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  container: {
    flex: 1,
    alignItems: "center",
    backgroundColor: "#80d6ff",
  },
  welcome: {
    fontSize: 20,
    textAlign: "center",
    margin: 10,
  },
  instructions: {
    textAlign: "center",
    color: "#333333",
    marginBottom: 5,
  },
});
