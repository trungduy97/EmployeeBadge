import React, { Component } from "react";
import { Platform, StyleSheet, Text, View, Dimensions } from "react-native";
var { height, width } = Dimensions.get("window");

export class Footer extends Component {
  render() {
    return (
      <View style={styles.footer}>
        <Text style={styles.text}>Home</Text>
      </View>
    );
  }
}

const styles = StyleSheet.create({
  footer: {
    width: width,
    height: 35,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "#80d6ff",
  },
  text: {
    justifyContent: "center",
    alignItems: "center",
    color: "white",
    paddingTop: 10,
    fontSize: 20,
    fontWeight: "bold",
  },
});
