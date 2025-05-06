// App.tsx
import React from 'react';
import { View, Text, StyleSheet } from 'react-native';

const App: React.FC = () => {
  return (
    <View style={styles.container}>
      <Text style={styles.text}>Hello, User!</Text>
    </View>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1 as const,
    justifyContent: 'center' as const,
    alignItems: 'center' as const,
  },
  text: {
    fontSize: 24,
  },
});

export default App;
