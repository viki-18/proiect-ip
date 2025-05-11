import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';

type RootStackParamList = {
  Login: undefined;
};

type NavigationProp = NativeStackNavigationProp<RootStackParamList>;

const { height } = Dimensions.get('window');

const WelcomeScreen: React.FC = () => {
  const navigation = useNavigation<NavigationProp>();

  return (
    <View style={styles.container}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Bine ați venit la ElderCare+</Text>
        <Text style={styles.subtitle}>Sistemul dumneavoastră de teleasistență</Text>
        
        <TouchableOpacity 
          style={styles.button}
          onPress={() => navigation.navigate('Login')}
        >
          <Text style={styles.buttonText}>Continuă</Text>
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
    fontSize: 32,
    fontWeight: 'bold',
    color: '#222',
    marginTop: height * 0.18,
    marginBottom: 10,
    textAlign: 'center',
  },
  subtitle: {
    fontSize: 18,
    color: '#444',
    marginBottom: 40,
    textAlign: 'center',
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

export default WelcomeScreen;
