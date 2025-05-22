import React, { useState } from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions, TextInput, Alert } from 'react-native';
import { Formik } from 'formik';
import * as Yup from 'yup';
import { useNavigation } from '@react-navigation/native';
import { NativeStackNavigationProp } from '@react-navigation/native-stack';
import authService from '../services/authService';

type RootStackParamList = {
  LoginForm: undefined;
};

type NavigationProp = NativeStackNavigationProp<RootStackParamList>;

const { height } = Dimensions.get('window');

const forgotPasswordSchema = Yup.object().shape({
  email: Yup.string()
    .email('Email invalid')
    .required('Email-ul este obligatoriu'),
});

const ForgotPasswordScreen: React.FC = () => {
  const navigation = useNavigation<NavigationProp>();
  const [isLoading, setIsLoading] = useState(false);

  const handleSubmit = async (values: { email: string }) => {
    try {
      setIsLoading(true);
      await authService.forgotPassword(values.email);
      Alert.alert(
        'Succes',
        'Link-ul de resetare a parolei a fost trimis pe email.'
      );
      navigation.navigate('LoginForm');
    } catch (error: any) {
      Alert.alert(
        'Eroare',
        error.response?.data?.message || 'A apărut o eroare la trimiterea link-ului de resetare'
      );
    } finally {
      setIsLoading(false);
    }
  };

  return (
    <View style={styles.container}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Resetare Parolă</Text>
        <Text style={styles.subtitle}>Introdu adresa de email pentru a primi link-ul de resetare</Text>
        
        <Formik
          initialValues={{ email: '' }}
          validationSchema={forgotPasswordSchema}
          onSubmit={handleSubmit}
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

              <TouchableOpacity 
                style={[styles.button, isLoading && styles.buttonDisabled]}
                onPress={() => handleSubmit()}
                disabled={isLoading}
              >
                <Text style={styles.buttonText}>
                  {isLoading ? 'Se procesează...' : 'Trimite Link'}
                </Text>
              </TouchableOpacity>

              <TouchableOpacity 
                style={[styles.button, isLoading && styles.buttonDisabled]}
                onPress={() => navigation.goBack()}
                disabled={isLoading}
              >
                <Text style={styles.buttonText}>Înapoi</Text>
              </TouchableOpacity>
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
    marginBottom: 10,
  },
  subtitle: {
    fontSize: 16,
    color: '#444',
    textAlign: 'center',
    marginBottom: 30,
    paddingHorizontal: 20,
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
});

export default ForgotPasswordScreen; 