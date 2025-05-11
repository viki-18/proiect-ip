// App.tsx
import React from 'react';
import { NavigationContainer } from '@react-navigation/native';
import { createNativeStackNavigator } from '@react-navigation/native-stack';

import WelcomeScreen from './screens/WelcomeScreen';
import LoginScreen from './screens/LoginScreen';
import LoginFormScreen from './screens/LoginFormScreen';
import RegisterFormScreen from './screens/RegisterFormScreen';

export type RootStackParamList = {
  Welcome: undefined;
  Login: undefined;
  LoginForm: undefined;
  RegisterForm: undefined;
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
      </Stack.Navigator>
    </NavigationContainer>
  );
};

export default App;
