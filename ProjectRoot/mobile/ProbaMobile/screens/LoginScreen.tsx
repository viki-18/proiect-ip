import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';

const { height } = Dimensions.get('window');

const LoginScreen: React.FC = () => {
  return (
    <View style={styles.container}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Autentificare</Text>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Autentificare</Text>
        </TouchableOpacity>
        <TouchableOpacity style={styles.button}>
          <Text style={styles.buttonText}>Înregistrare</Text>
        </TouchableOpacity>
        <Image source={require('../assets/images/logo_ElderCare_lung.png')} style={styles.logo} resizeMode="contain" />
      </View>
    </View>
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
    fontSize: 28,
    fontWeight: 'bold',
    color: '#222',
    marginTop: height * 0.18,
    marginBottom: 40,
  },
  button: {
    backgroundColor: 'white',
    borderRadius: 24,
    paddingVertical: 12,
    paddingHorizontal: 36,
    marginVertical: 10,
    elevation: 2,
    minWidth: 180,
    alignItems: 'center',
  },
  buttonText: {
    color: '#222',
    fontSize: 18,
    fontWeight: '500',
  },
  logo: {
    width: 220,
    height: 80,
    position: 'absolute',
    bottom: 40,
    alignSelf: 'center',
  },
});

export default LoginScreen;
