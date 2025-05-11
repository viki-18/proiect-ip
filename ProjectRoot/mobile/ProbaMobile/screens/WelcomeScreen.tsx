import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';
import { useNavigation } from '@react-navigation/native';

const { height } = Dimensions.get('window');

const WelcomeScreen: React.FC = () => {
  const navigation = useNavigation();

  const handlePress = () => {
    navigation.navigate('Login');
  };

  return (
    <TouchableOpacity style={styles.container} activeOpacity={1} onPress={handlePress}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Bine ați venit!</Text>
        <Image source={require('../assets/images/logo_ElderCare_lung.png')} style={styles.logo} resizeMode="contain" />
      </View>
    </TouchableOpacity>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
  },
  background: {
    ...StyleSheet.absoluteFillObject,
    width: '100%',
    height: '100%',
  },
  overlay: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  title: {
    fontSize: 32,
    fontWeight: 'bold',
    color: '#222',
    marginTop: height * 0.18,
    marginBottom: 'auto',
  },
  logo: {
    width: 220,
    height: 80,
    position: 'absolute',
    bottom: 40,
    alignSelf: 'center',
  },
});

export default WelcomeScreen;
