/**
 * LottieSplashScreen
 * from: https://hwangtaehyun.github.io
 * Author:TaehyunHwang
 * GitHub:https://github.com/HwangTaehyun
 * Email:eeht1717@gmail.com
 * @flow
 */
'use strict';

import React, {useEffect} from 'react';
import LottieSplashScreen from 'react-native-lottie-splash-screen';
import {StyleSheet, View, Text, TouchableOpacity, Linking} from 'react-native';

const App = () => {
  useEffect(() => {
    setTimeout(() => {
      LottieSplashScreen.hide(); // here
    }, 5_000);
  }, []);

  return (
    <TouchableOpacity
      style={styles.container}
      onPress={e => {
        Linking.openURL(
          'https://github.com/HwangTaehyun/react-native-lottie-splash-screen',
        );
      }}>
      <Text style={styles.item}>
        Please star for react-native-lottie-splash-screen!
      </Text>
      <Text style={styles.item}>Click this screen!</Text>
      <Text style={styles.item}>LottieSplashScreen</Text>
      <Text style={styles.item}>@：https://hwangtaehyun.github.io</Text>
      <Text style={styles.item}>GitHub: https://github.com/HwangTaehyun</Text>
      <Text style={styles.item}>Email: eeht1717@gmail.com</Text>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: 'white',
    justifyContent: 'center',
  },
  item: {
    fontSize: 20,
    marginBottom: 30,
    color: 'black',
  },
  line: {
    flex: 1,
    height: 0.3,
  },
});

export default App;
