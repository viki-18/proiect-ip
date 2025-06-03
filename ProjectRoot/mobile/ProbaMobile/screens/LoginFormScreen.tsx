import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions, TextInput, Alert } from 'react-native';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import authService from '../services/authService';

type RootStackParamList = {
  Login: undefined;
  ForgotPassword: undefined;
  Dashboard: undefined;
  PatientDashboard: { userId: number };
  CaregiverDashboard: undefined;
};

type NavigationProp = NativeStackNavigationProp<RootStackParamList>;

const { height } = Dimensions.get('window');

const loginSchema = Yup.object().shape({
  email: Yup.string()
    .email('Email invalid')
    .required('Email-ul este obligatoriu'),
  password: Yup.string()
    .required('Parola este obligatorie'),
});

const LoginFormScreen: React.FC = () => {
  const navigation = useNavigation<NavigationProp>();
  const [isLoading, setIsLoading] = useState(false);

  const handleLogin = async (values: { email: string; password: string }) => {
    try {
      setIsLoading(true);
      
      // Use patient id 1
      navigation.navigate('PatientDashboard', { userId: 1 });
      
    } catch (error: any) {
      console.error('Login error in screen:', error);
      let errorMessage = 'An error occurred during authentication';
      
      if (error.response) {
        errorMessage = `Server Error: ${error.response.status}\n${JSON.stringify(error.response.data, null, 2)}`;
      } else if (error.request) {
        errorMessage = 'Could not connect to server. Please check your internet connection.';
      } else if (error.message) {
        errorMessage = error.message;
      }
      
      Alert.alert(
        'Error',
        errorMessage,
        [
          {
            text: 'OK',
            onPress: () => console.log('OK Pressed')
          }
        ]
      );
    } finally {
      setIsLoading(false);
    }
  };

  const handleSkipAuth = (role: 'patient' | 'caregiver') => {
    if (role === 'patient') {
      // For testing, use a known patient's userId
      navigation.navigate('PatientDashboard', { userId: 1 }); // Using patient ID 1
    } else {
      navigation.navigate('CaregiverDashboard');
    }
  };

  return (
    <View style={styles.container}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Autentificare</Text>
        
        <Formik
          initialValues={{ email: '', password: '' }}
          validationSchema={loginSchema}
          onSubmit={handleLogin}
        >
          {({ handleChange, handleBlur, handleSubmit, values, errors, touched }) => (
            <View style={styles.formContainer}>
              <View style={styles.inputContainer}>
                <TextInput
                  style={[styles.input, styles.inputText]}
                  placeholder="Email"
                  onChangeText={handleChange('email')}
                  onBlur={handleBlur('email')}
                  value={values.email}
                  keyboardType="email-address"
                  autoCapitalize="none"
                  placeholderTextColor="#666"
                  editable={!isLoading}
                />
                {touched.email && errors.email && (
                  <Text style={styles.errorText}>{errors.email}</Text>
                )}
              </View>

              <View style={styles.inputContainer}>
                <TextInput
                  style={[styles.input, styles.inputText]}
                  placeholder="Parolă"
                  onChangeText={handleChange('password')}
                  onBlur={handleBlur('password')}
                  value={values.password}
                  secureTextEntry
                  placeholderTextColor="#666"
                  editable={!isLoading}
                />
                {touched.password && errors.password && (
                  <Text style={styles.errorText}>{errors.password}</Text>
                )}
              </View>

              <TouchableOpacity 
                style={styles.forgotPasswordLink}
                onPress={() => navigation.navigate('ForgotPassword')}
                disabled={isLoading}
              >
                <Text style={styles.forgotPasswordText}>Ți-ai uitat parola?</Text>
              </TouchableOpacity>

              <TouchableOpacity 
                style={[styles.button, isLoading && styles.buttonDisabled]}
                onPress={() => handleSubmit()}
                disabled={isLoading}
              >
                <Text style={styles.buttonText}>
                  {isLoading ? 'Se procesează...' : 'Autentificare'}
                </Text>
              </TouchableOpacity>

              <TouchableOpacity 
                style={[styles.button, isLoading && styles.buttonDisabled]}
                onPress={() => navigation.goBack()}
                disabled={isLoading}
              >
                <Text style={styles.buttonText}>Înapoi</Text>
              </TouchableOpacity>

              {/* Temporary skip auth buttons */}
              <View style={styles.skipAuthContainer}>
                <Text style={styles.skipAuthText}>Sau continuă ca:</Text>
                <TouchableOpacity 
                  style={[styles.skipButton, styles.patientButton]}
                  onPress={() => handleSkipAuth('patient')}
                >
                  <Text style={styles.skipButtonText}>Pacient</Text>
                </TouchableOpacity>
                <TouchableOpacity 
                  style={[styles.skipButton, styles.caregiverButton]}
                  onPress={() => handleSkipAuth('caregiver')}
                >
                  <Text style={styles.skipButtonText}>Îngrijitor</Text>
                </TouchableOpacity>
              </View>
            </View>
          )}
        </Formik>
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
  formContainer: {
    width: '80%',
    alignItems: 'center',
  },
  inputContainer: {
    width: '100%',
    marginBottom: 15,
  },
  input: {
    backgroundColor: 'white',
    borderRadius: 24,
    paddingVertical: 12,
    paddingHorizontal: 20,
    fontSize: 16,
    width: '100%',
    elevation: 2,
  },
  inputText: {
    color: '#222',
  },
  errorText: {
    color: 'red',
    fontSize: 12,
    marginTop: 5,
    marginLeft: 10,
  },
  forgotPasswordLink: {
    marginBottom: 20,
  },
  forgotPasswordText: {
    color: '#007AFF',
    fontSize: 16,
    textDecorationLine: 'underline',
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
  buttonDisabled: {
    opacity: 0.7,
  },
  buttonText: {
    color: '#222',
    fontSize: 18,
    fontWeight: '500',
  },
  skipAuthContainer: {
    marginTop: 20,
    alignItems: 'center',
  },
  skipAuthText: {
    color: '#666',
    fontSize: 16,
    marginBottom: 10,
  },
  skipButton: {
    borderRadius: 20,
    paddingVertical: 10,
    paddingHorizontal: 30,
    marginVertical: 5,
    minWidth: 150,
    alignItems: 'center',
  },
  patientButton: {
    backgroundColor: '#4CAF50',
  },
  caregiverButton: {
    backgroundColor: '#2196F3',
  },
  skipButtonText: {
    color: 'white',
    fontSize: 16,
    fontWeight: '500',
  },
});

export default LoginFormScreen; 