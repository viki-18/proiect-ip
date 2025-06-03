// App.tsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import WelcomeScreen from './screens/WelcomeScreen';
import LoginScreen from './screens/LoginScreen';
import LoginFormScreen from './screens/LoginFormScreen';
import RegisterFormScreen from './screens/RegisterFormScreen';
import ForgotPasswordScreen from './screens/ForgotPasswordScreen';
import PatientDashboard from './screens/PatientDashboard';
import CaregiverDashboard from './screens/CaregiverDashboard';

export type RootStackParamList = {
  Welcome: undefined;
  Login: undefined;
  LoginForm: undefined;
  RegisterForm: undefined;
  ForgotPassword: undefined;
  PatientDashboard: { userId: number };
  CaregiverDashboard: undefined;
};

const Stack = createNativeStackNavigator<RootStackParamList>();

const App = () => {
  return (
    <NavigationContainer>
      <Stack.Navigator
        initialRouteName="Welcome"
        screenOptions={{
          headerShown: false,
        }}
      >
        <Stack.Screen name="Welcome" component={WelcomeScreen} />
        <Stack.Screen name="Login" component={LoginScreen} />
        <Stack.Screen name="LoginForm" component={LoginFormScreen} />
        <Stack.Screen name="RegisterForm" component={RegisterFormScreen} />
        <Stack.Screen name="ForgotPassword" component={ForgotPasswordScreen} />
        <Stack.Screen 
          name="PatientDashboard" 
          component={PatientDashboard}
          options={{
            headerShown: true,
            title: 'Dashboard Pacient',
            headerStyle: {
              backgroundColor: '#007AFF',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },
          }}
        />
        <Stack.Screen 
          name="CaregiverDashboard" 
          component={CaregiverDashboard}
          options={{
            headerShown: true,
            title: 'Dashboard Ingrijitor',
            headerStyle: {
              backgroundColor: '#007AFF',
            },
            headerTintColor: '#fff',
            headerTitleStyle: {
              fontWeight: 'bold',
            },
          }}
        />
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
