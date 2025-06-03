import React, { useState } from 'react';
import {
  View,
  Text,
  StyleSheet,
  TextInput,
  TouchableOpacity,
  ScrollView,
  Alert,
  ActivityIndicator
} from 'react-native';
import axios from 'axios';

const BASE_URL = 'http://192.168.1.8:8080';

const CaregiverDashboard: React.FC = () => {
  const [loading, setLoading] = useState(false);
  const [vitalSigns, setVitalSigns] = useState({
    glicemie: '',
    tensiuneArteriala: '',
    greutate: '',
    temperaturaCorporala: ''
  });

  const handleInputChange = (field: string, value: string) => {
    setVitalSigns(prev => ({
      ...prev,
      [field]: value
    }));
  };

  const handleSubmit = async () => {
    try {
      setLoading(true);
      
      // Validate inputs
      if (!vitalSigns.glicemie && !vitalSigns.tensiuneArteriala && !vitalSigns.greutate && !vitalSigns.temperaturaCorporala) {
        Alert.alert('Error', 'Please enter at least one vital sign');
        return;
      }

      const data = {
        glicemie: vitalSigns.glicemie ? parseFloat(vitalSigns.glicemie) : null,
        tensiuneArteriala: vitalSigns.tensiuneArteriala ? parseFloat(vitalSigns.tensiuneArteriala) : null,
        greutate: vitalSigns.greutate ? parseFloat(vitalSigns.greutate) : null,
        temperaturaCorporala: vitalSigns.temperaturaCorporala ? parseFloat(vitalSigns.temperaturaCorporala) : null
      };

      console.log('Submitting vital signs:', data);
      
      const response = await axios.post(`${BASE_URL}/semne-vitale/pacient/1/values`, data, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });

      console.log('Response:', response.data);
      
      Alert.alert('Success', 'Vital signs updated successfully');
      
      // Clear form
      setVitalSigns({
        glicemie: '',
        tensiuneArteriala: '',
        greutate: '',
        temperaturaCorporala: ''
      });
    } catch (error: any) {
      console.error('Error submitting vital signs:', error);
      Alert.alert(
        'Error',
        error.response?.data?.message || 'Failed to update vital signs'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Caregiver Dashboard</Text>
        <Text style={styles.subtitle}>Update Patient Vital Signs</Text>
      </View>

      <View style={styles.form}>
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Blood Sugar (mg/dL)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.glicemie}
            onChangeText={(value) => handleInputChange('glicemie', value)}
            placeholder="Enter blood sugar value"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Blood Pressure (mmHg)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.tensiuneArteriala}
            onChangeText={(value) => handleInputChange('tensiuneArteriala', value)}
            placeholder="Enter blood pressure value"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Body Temperature (°C)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.temperaturaCorporala}
            onChangeText={(value) => handleInputChange('temperaturaCorporala', value)}
            placeholder="Enter body temperature value"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Weight (kg)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.greutate}
            onChangeText={(value) => handleInputChange('greutate', value)}
            placeholder="Enter weight value"
            keyboardType="numeric"
          />
        </View>

        <TouchableOpacity
          style={[styles.submitButton, loading && styles.submitButtonDisabled]}
          onPress={handleSubmit}
          disabled={loading}
        >
          {loading ? (
            <ActivityIndicator color="#fff" />
          ) : (
            <Text style={styles.submitButtonText}>Update Vital Signs</Text>
          )}
        </TouchableOpacity>
      </View>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  header: {
    padding: 20,
    backgroundColor: '#fff',
    borderBottomWidth: 1,
    borderBottomColor: '#e0e0e0',
  },
  title: {
    fontSize: 24,
    fontWeight: 'bold',
    color: '#333',
    marginBottom: 5,
  },
  subtitle: {
    fontSize: 16,
    color: '#666',
  },
  form: {
    padding: 20,
  },
  inputGroup: {
    marginBottom: 20,
  },
  label: {
    fontSize: 16,
    fontWeight: '600',
    color: '#333',
    marginBottom: 8,
  },
  input: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#ddd',
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
  },
  submitButton: {
    backgroundColor: '#007AFF',
    borderRadius: 8,
    padding: 15,
    alignItems: 'center',
    marginTop: 20,
  },
  submitButtonDisabled: {
    backgroundColor: '#ccc',
  },
  submitButtonText: {
    color: '#fff',
    fontSize: 18,
    fontWeight: '600',
  },
});

export default CaregiverDashboard; 