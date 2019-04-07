import React, { Component } from "react";
import { Platform, StyleSheet, Text, View, Dimensions } from "react-native";
var { height, width } = Dimensions.get("window");

export class Card extends Component {
  render() {
    return (
      <View style={styles.card}>
        <View
          style={{
            flex: 1,
            height: height / 2,
            width: width / 2,
            backgroundColor: "green",
          }}
        />

        <View
          style={{
            flex: 1,
            height: height / 2,
            width: width / 2,
            backgroundColor: "gray",
          }}
        />
      </View>
    );
  }
}

const styles = StyleSheet.create({
  card: {
    flex: 1,
    flexDirection: "row",
    width: width,
    justifyContent: "flex-start",
    alignItems: "center",
    backgroundColor: "#9c27b0",
  },
});
