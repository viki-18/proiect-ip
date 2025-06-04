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

const BASE_URL = 'http://192.168.185.237:8080';

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
        Alert.alert('Eroare', 'Vă rugăm să introduceți cel puțin un semn vital');
        return;
      }

      // Create data object with only non-null values
      const data: any = {};
      
      if (vitalSigns.tensiuneArteriala) {
        data.tensiuneArteriala = parseFloat(vitalSigns.tensiuneArteriala);
      }
      
      if (vitalSigns.temperaturaCorporala) {
        data.temperaturaCorporala = parseFloat(vitalSigns.temperaturaCorporala);
      }
      
      if (vitalSigns.greutate) {
        data.greutate = parseFloat(vitalSigns.greutate);
      }
      
      if (vitalSigns.glicemie) {
        data.glicemie = parseFloat(vitalSigns.glicemie);
      }

      console.log('Submitting vital signs:', JSON.stringify(data, null, 2));
      
      const response = await axios.post(`${BASE_URL}/semne-vitale/pacient/1/values`, data, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });

      console.log('Response status:', response.status);
      console.log('Response data:', JSON.stringify(response.data, null, 2));

      if (response.status === 200) {
        Alert.alert('Succes', 'Semnele vitale au fost actualizate cu succes');
        
        // Clear form
        setVitalSigns({
          glicemie: '',
          tensiuneArteriala: '',
          greutate: '',
          temperaturaCorporala: ''
        });
      } else {
        throw new Error('Actualizarea semnelor vitale a eșuat');
      }
    } catch (error: any) {
      console.error('Error submitting vital signs:', {
        message: error.message,
        response: error.response ? {
          status: error.response.status,
          data: error.response.data,
          headers: error.response.headers
        } : 'No response',
        request: error.request ? 'Request was made but no response received' : 'No request was made'
      });
      Alert.alert(
        'Eroare',
        error.response?.data?.message || 'Actualizarea semnelor vitale a eșuat'
      );
    } finally {
      setLoading(false);
    }
  };

  return (
    <ScrollView style={styles.container}>
      <View style={styles.header}>
        <Text style={styles.title}>Panou Îngrijitor</Text>
        <Text style={styles.subtitle}>Actualizare Semne Vitale Pacient</Text>
      </View>

      <View style={styles.form}>
        <View style={styles.inputGroup}>
          <Text style={styles.label}>Glicemie (mg/dL)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.glicemie}
            onChangeText={(value) => handleInputChange('glicemie', value)}
            placeholder="Introduceți valoarea glicemiei"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Tensiune Arterială (mmHg)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.tensiuneArteriala}
            onChangeText={(value) => handleInputChange('tensiuneArteriala', value)}
            placeholder="Introduceți tensiunea arterială"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Temperatură Corporală (°C)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.temperaturaCorporala}
            onChangeText={(value) => handleInputChange('temperaturaCorporala', value)}
            placeholder="Introduceți temperatura corporală"
            keyboardType="numeric"
          />
        </View>

        <View style={styles.inputGroup}>
          <Text style={styles.label}>Greutate (kg)</Text>
          <TextInput
            style={styles.input}
            value={vitalSigns.greutate}
            onChangeText={(value) => handleInputChange('greutate', value)}
            placeholder="Introduceți greutatea"
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
            <Text style={styles.submitButtonText}>Actualizare Semne Vitale</Text>
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
    color: '#007AFF',
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
    color: '#007AFF',
    marginBottom: 8,
  },
  input: {
    backgroundColor: '#fff',
    borderWidth: 1,
    borderColor: '#007AFF',
    borderRadius: 8,
    padding: 12,
    fontSize: 16,
    color: '#333',
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 1 },
    shadowOpacity: 0.1,
    shadowRadius: 2,
  },
  submitButton: {
    backgroundColor: '#007AFF',
    borderRadius: 8,
    padding: 15,
    alignItems: 'center',
    marginTop: 20,
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.15,
    shadowRadius: 6,
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