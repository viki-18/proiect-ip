import React from 'react';
import { View, Text, Image, StyleSheet, TouchableOpacity, Dimensions, TextInput } from 'react-native';
import { Formik } from 'formik';
import * as Yup from 'yup';

const { height } = Dimensions.get('window');

const registerSchema = Yup.object().shape({
  email: Yup.string()
    .email('Email invalid')
    .required('Email-ul este obligatoriu'),
  username: Yup.string()
    .min(3, 'Username-ul trebuie să aibă cel puțin 3 caractere')
    .required('Username-ul este obligatoriu'),
  password: Yup.string()
    .min(6, 'Parola trebuie să aibă cel puțin 6 caractere')
    .required('Parola este obligatorie'),
  confirmPassword: Yup.string()
    .oneOf([Yup.ref('password')], 'Parolele nu coincid')
    .required('Confirmarea parolei este obligatorie'),
});

const RegisterScreen: React.FC = () => {
  const handleRegister = (values: {
    email: string;
    username: string;
    password: string;
    confirmPassword: string;
  }) => {
    console.log('Register attempt:', values);
    // Aici vom adăuga logica de navigare către login sau dashboard
  };

  return (
    <View style={styles.container}>
      <Image source={require('../assets/images/fundal_welcome.png')} style={styles.background} resizeMode="cover" />
      <View style={styles.overlay}>
        <Text style={styles.title}>Înregistrare</Text>
        
        <Formik
          initialValues={{ email: '', username: '', password: '', confirmPassword: '' }}
          validationSchema={registerSchema}
          onSubmit={handleRegister}
        >
          {({ handleChange, handleBlur, handleSubmit, values, errors, touched }) => (
            <View style={styles.formContainer}>
              <View style={styles.inputContainer}>
                <TextInput
                  style={styles.input}
                  placeholder="Email"
                  onChangeText={handleChange('email')}
                  onBlur={handleBlur('email')}
                  value={values.email}
                  keyboardType="email-address"
                  autoCapitalize="none"
                  placeholderTextColor="#666"
                />
                {touched.email && errors.email && (
                  <Text style={styles.errorText}>{errors.email}</Text>
                )}
              </View>

              <View style={styles.inputContainer}>
                <TextInput
                  style={styles.input}
                  placeholder="Username"
                  onChangeText={handleChange('username')}
                  onBlur={handleBlur('username')}
                  value={values.username}
                  autoCapitalize="none"
                  placeholderTextColor="#666"
                />
                {touched.username && errors.username && (
                  <Text style={styles.errorText}>{errors.username}</Text>
                )}
              </View>

              <View style={styles.inputContainer}>
                <TextInput
                  style={styles.input}
                  placeholder="Parolă"
                  onChangeText={handleChange('password')}
                  onBlur={handleBlur('password')}
                  value={values.password}
                  secureTextEntry
                  placeholderTextColor="#666"
                />
                {touched.password && errors.password && (
                  <Text style={styles.errorText}>{errors.password}</Text>
                )}
              </View>

              <View style={styles.inputContainer}>
                <TextInput
                  style={styles.input}
                  placeholder="Confirmă parola"
                  onChangeText={handleChange('confirmPassword')}
                  onBlur={handleBlur('confirmPassword')}
                  value={values.confirmPassword}
                  secureTextEntry
                  placeholderTextColor="#666"
                />
                {touched.confirmPassword && errors.confirmPassword && (
                  <Text style={styles.errorText}>{errors.confirmPassword}</Text>
                )}
              </View>

              <TouchableOpacity 
                style={styles.button}
                onPress={() => handleSubmit()}
              >
                <Text style={styles.buttonText}>Înregistrare</Text>
              </TouchableOpacity>

              <TouchableOpacity style={styles.button}>
                <Text style={styles.buttonText}>Înapoi la Login</Text>
              </TouchableOpacity>
            </View>
          )}
        </Formik>

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
    marginTop: height * 0.1,
    marginBottom: 30,
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

export default RegisterScreen; 