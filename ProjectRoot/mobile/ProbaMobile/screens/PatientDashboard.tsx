import React, { useEffect, useState } from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity, ActivityIndicator, Alert } from 'react-native';
import { LineChart } from 'react-native-chart-kit';
import { Dimensions } from 'react-native';
import patientService, { PatientData } from '../services/patientService';
import { useRoute, RouteProp } from '@react-navigation/native';

type AlertType = 'warning' | 'info';

type RootStackParamList = {
  PatientDashboard: { userId: number };
};

type PatientDashboardRouteProp = RouteProp<RootStackParamList, 'PatientDashboard'>;

const PatientDashboard: React.FC = () => {
  const [patientData, setPatientData] = useState<PatientData | null>(null);
  const [loading, setLoading] = useState(true);
  const route = useRoute<PatientDashboardRouteProp>();
  const screenWidth = Dimensions.get('window').width;

  useEffect(() => {
    const fetchPatientData = async () => {
      try {
        const userId = route.params.userId;
        console.log('Fetching patient data for user ID:', userId);
        const data = await patientService.getPatientData(userId);
        console.log('Received patient data:', data);
        setPatientData(data);
      } catch (error: any) {
        console.error('Error fetching patient data:', error);
        Alert.alert('Error', error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchPatientData();

    // Set up real-time updates
    const updateInterval = setInterval(async () => {
      try {
        const userId = route.params.userId;
        const vitalSigns = await patientService.getVitalSignsUpdates(userId);
        console.log('Received vital signs update:', vitalSigns);
        
        if (!vitalSigns) {
          console.log('No vital signs data received');
          return;
        }

        setPatientData(prevData => {
          if (!prevData) return null;

          const newData = { ...prevData };
          const timestamp = new Date(vitalSigns.dataInregistrare).toLocaleTimeString();
          
          // Update heart rate
          if (vitalSigns.puls !== null) {
            newData.vitalSigns.heartRate.data.push(vitalSigns.puls);
            newData.vitalSigns.heartRate.labels.push(timestamp);
            
            // Check heart rate alerts
            if (vitalSigns.puls > vitalSigns.valAlarmaPuls) {
              newData.alerts.push({
                id: Date.now(),
                type: 'warning',
                message: `High heart rate: ${vitalSigns.puls} bpm (max: ${vitalSigns.valAlarmaPuls})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaPulsMin !== null && vitalSigns.puls < vitalSigns.valAlarmaPulsMin) {
              newData.alerts.push({
                id: Date.now() + 1,
                type: 'warning',
                message: `Low heart rate: ${vitalSigns.puls} bpm (min: ${vitalSigns.valAlarmaPulsMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Update blood pressure
          if (vitalSigns.tensiuneArteriala !== null) {
            newData.vitalSigns.bloodPressure.systolic.push(vitalSigns.tensiuneArteriala);
            newData.vitalSigns.bloodPressure.diastolic.push(vitalSigns.tensiuneArteriala - 40);
            newData.vitalSigns.bloodPressure.labels.push(timestamp);
            
            // Check blood pressure alerts
            if (vitalSigns.tensiuneArteriala > vitalSigns.valAlarmaTensiune) {
              newData.alerts.push({
                id: Date.now() + 2,
                type: 'warning',
                message: `High blood pressure: ${vitalSigns.tensiuneArteriala} mmHg (max: ${vitalSigns.valAlarmaTensiune})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTensiuneMin !== null && vitalSigns.tensiuneArteriala < vitalSigns.valAlarmaTensiuneMin) {
              newData.alerts.push({
                id: Date.now() + 3,
                type: 'warning',
                message: `Low blood pressure: ${vitalSigns.tensiuneArteriala} mmHg (min: ${vitalSigns.valAlarmaTensiuneMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Update blood sugar
          if (vitalSigns.glicemie !== null) {
            newData.vitalSigns.bloodSugar.data.push(vitalSigns.glicemie);
            newData.vitalSigns.bloodSugar.labels.push(timestamp);
            
            // Check blood sugar alerts
            if (vitalSigns.glicemie > vitalSigns.valAlarmaGlicemie) {
              newData.alerts.push({
                id: Date.now() + 4,
                type: 'warning',
                message: `High blood sugar: ${vitalSigns.glicemie} mg/dL (max: ${vitalSigns.valAlarmaGlicemie})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaGlicemieMin !== null && vitalSigns.glicemie < vitalSigns.valAlarmaGlicemieMin) {
              newData.alerts.push({
                id: Date.now() + 5,
                type: 'warning',
                message: `Low blood sugar: ${vitalSigns.glicemie} mg/dL (min: ${vitalSigns.valAlarmaGlicemieMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Update temperature
          if (vitalSigns.temperaturaCorporala !== null) {
            newData.vitalSigns.temperature.data.push(vitalSigns.temperaturaCorporala);
            newData.vitalSigns.temperature.labels.push(timestamp);
            
            // Check body temperature alerts
            if (vitalSigns.temperaturaCorporala > vitalSigns.valAlarmaTemperatura) {
              newData.alerts.push({
                id: Date.now() + 6,
                type: 'warning',
                message: `High body temperature: ${vitalSigns.temperaturaCorporala}°C (max: ${vitalSigns.valAlarmaTemperatura})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTemperaturaMin !== null && vitalSigns.temperaturaCorporala < vitalSigns.valAlarmaTemperaturaMin) {
              newData.alerts.push({
                id: Date.now() + 7,
                type: 'warning',
                message: `Low body temperature: ${vitalSigns.temperaturaCorporala}°C (min: ${vitalSigns.valAlarmaTemperaturaMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Update weight
          if (vitalSigns.greutate !== null) {
            newData.vitalSigns.weight.data.push(vitalSigns.greutate);
            newData.vitalSigns.weight.labels.push(timestamp);
            
            // Check weight alerts
            if (vitalSigns.greutate > vitalSigns.valAlarmaGreutate) {
              newData.alerts.push({
                id: Date.now() + 8,
                type: 'warning',
                message: `High weight: ${vitalSigns.greutate} kg (max: ${vitalSigns.valAlarmaGreutate})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaGreutateMin !== null && vitalSigns.greutate < vitalSigns.valAlarmaGreutateMin) {
              newData.alerts.push({
                id: Date.now() + 9,
                type: 'warning',
                message: `Low weight: ${vitalSigns.greutate} kg (min: ${vitalSigns.valAlarmaGreutateMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Update ambient temperature
          if (vitalSigns.temperaturaAmbientala !== null) {
            newData.vitalSigns.ambientTemperature.data.push(vitalSigns.temperaturaAmbientala);
            newData.vitalSigns.ambientTemperature.labels.push(timestamp);
            
            // Check ambient temperature alerts
            if (vitalSigns.temperaturaAmbientala > vitalSigns.valAlarmaTemperaturaAmb) {
              newData.alerts.push({
                id: Date.now() + 10,
                type: 'warning',
                message: `High ambient temperature: ${vitalSigns.temperaturaAmbientala}°C (max: ${vitalSigns.valAlarmaTemperaturaAmb})`,
                date: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTemperaturaAmbMin !== null && vitalSigns.temperaturaAmbientala < vitalSigns.valAlarmaTemperaturaAmbMin) {
              newData.alerts.push({
                id: Date.now() + 11,
                type: 'warning',
                message: `Low ambient temperature: ${vitalSigns.temperaturaAmbientala}°C (min: ${vitalSigns.valAlarmaTemperaturaAmbMin})`,
                date: vitalSigns.dataInregistrare
              });
            }
          }

          // Keep only the last 10 alerts
          if (newData.alerts.length > 10) {
            newData.alerts = newData.alerts.slice(-10);
          }

          return newData;
        });
      } catch (error: any) {
        console.error('Error fetching vital signs update:', error);
      }
    }, 30000);

    return () => clearInterval(updateInterval);
  }, [route.params.userId]);

  const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 122, 255, ${opacity})`,
    strokeWidth: 2,
    barPercentage: 0.5,
    useShadowColorFromDataset: false
  };

  if (loading) {
    return (
      <View style={styles.loadingContainer}>
        <ActivityIndicator size="large" color="#007AFF" />
      </View>
    );
  }

  if (!patientData) {
    return (
      <View style={styles.errorContainer}>
        <Text style={styles.errorText}>No patient data available</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      {/* Personal Information */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Personal Information</Text>
        <View style={styles.card}>
          <Text style={styles.name}>{patientData.personalInfo.name}</Text>
          <Text style={styles.infoText}>Email: {patientData.personalInfo.email}</Text>
          <Text style={styles.infoText}>Age: {patientData.personalInfo.age} years</Text>
          <Text style={styles.infoText}>Gender: {patientData.personalInfo.gender}</Text>
          <Text style={styles.infoText}>Blood Type: {patientData.personalInfo.bloodType}</Text>
          
          <Text style={styles.subtitle}>Allergies:</Text>
          {patientData.personalInfo.allergies.map((allergy, index) => (
            <Text key={index} style={styles.infoText}>• {allergy}</Text>
          ))}
          
          <Text style={styles.subtitle}>Chronic Conditions:</Text>
          {patientData.personalInfo.chronicConditions.map((condition, index) => (
            <Text key={index} style={styles.infoText}>• {condition}</Text>
          ))}
          
          <Text style={styles.subtitle}>Emergency Contact:</Text>
          <Text style={styles.infoText}>Name: {patientData.personalInfo.emergencyContact.name}</Text>
          <Text style={styles.infoText}>Relationship: {patientData.personalInfo.emergencyContact.relationship}</Text>
          <Text style={styles.infoText}>Phone: {patientData.personalInfo.emergencyContact.phone}</Text>
        </View>
      </View>

      {/* Vital Signs */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Vital Signs</Text>
        
        {/* Heart Rate */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Heart Rate</Text>
          {patientData.vitalSigns.heartRate.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.heartRate.labels,
                datasets: [{
                  data: patientData.vitalSigns.heartRate.data
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No heart rate data available</Text>
          )}
        </View>

        {/* Blood Pressure */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Blood Pressure</Text>
          {patientData.vitalSigns.bloodPressure.systolic.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.bloodPressure.labels,
                datasets: [
                  {
                    data: patientData.vitalSigns.bloodPressure.systolic,
                    color: (opacity = 1) => `rgba(255, 0, 0, ${opacity})`,
                    strokeWidth: 2
                  },
                  {
                    data: patientData.vitalSigns.bloodPressure.diastolic,
                    color: (opacity = 1) => `rgba(0, 0, 255, ${opacity})`,
                    strokeWidth: 2
                  }
                ]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No blood pressure data available</Text>
          )}
        </View>

        {/* Blood Sugar */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Blood Sugar</Text>
          {patientData.vitalSigns.bloodSugar.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.bloodSugar.labels,
                datasets: [{
                  data: patientData.vitalSigns.bloodSugar.data
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No blood sugar data available</Text>
          )}
        </View>

        {/* Body Temperature */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Body Temperature</Text>
          {patientData.vitalSigns.temperature.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.temperature.labels,
                datasets: [{
                  data: patientData.vitalSigns.temperature.data
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No body temperature data available</Text>
          )}
        </View>

        {/* Weight */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Weight</Text>
          {patientData.vitalSigns.weight.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.weight.labels,
                datasets: [{
                  data: patientData.vitalSigns.weight.data
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No weight data available</Text>
          )}
        </View>

        {/* Ambient Temperature */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Ambient Temperature</Text>
          {patientData.vitalSigns.ambientTemperature.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.vitalSigns.ambientTemperature.labels,
                datasets: [{
                  data: patientData.vitalSigns.ambientTemperature.data
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={chartConfig}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>No ambient temperature data available</Text>
          )}
        </View>
      </View>

      {/* Medical Recommendations */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Medical Recommendations</Text>
        {patientData.medicalRecommendations.map((recommendation) => (
          <View key={recommendation.id} style={styles.card}>
            <Text style={styles.date}>{recommendation.date}</Text>
            <Text style={styles.doctor}>{recommendation.doctor}</Text>
            <Text style={styles.recommendation}>{recommendation.recommendation}</Text>
          </View>
        ))}
      </View>

      {/* Alerts */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Alerts</Text>
        {patientData.alerts.map((alert) => (
          <View key={alert.id} style={[styles.card, styles.warningAlert]}>
            <Text style={styles.alertDate}>{alert.date}</Text>
            <Text style={styles.alertMessage}>{alert.message}</Text>
          </View>
        ))}
      </View>

      {/* Contact Doctor Button */}
      <TouchableOpacity style={styles.contactButton}>
        <Text style={styles.contactButtonText}>Contact Doctor</Text>
      </TouchableOpacity>
    </ScrollView>
  );
};

const styles = StyleSheet.create({
  container: {
    flex: 1,
    backgroundColor: '#f5f5f5',
  },
  loadingContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  errorContainer: {
    flex: 1,
    justifyContent: 'center',
    alignItems: 'center',
  },
  errorText: {
    fontSize: 18,
    color: 'red',
  },
  section: {
    padding: 20,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 15,
    color: '#333',
  },
  card: {
    backgroundColor: 'white',
    borderRadius: 10,
    padding: 15,
    marginBottom: 15,
    elevation: 2,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.1,
    shadowRadius: 4,
  },
  name: {
    fontSize: 22,
    fontWeight: 'bold',
    marginBottom: 10,
    color: '#333',
  },
  subtitle: {
    fontSize: 18,
    fontWeight: '600',
    marginBottom: 10,
    color: '#444',
  },
  infoText: {
    fontSize: 16,
    color: '#666',
    marginBottom: 5,
  },
  chart: {
    marginVertical: 8,
    borderRadius: 16,
  },
  date: {
    fontSize: 14,
    color: '#666',
    marginBottom: 5,
  },
  doctor: {
    fontSize: 16,
    fontWeight: '600',
    color: '#333',
    marginBottom: 5,
  },
  recommendation: {
    fontSize: 16,
    color: '#444',
  },
  alertDate: {
    fontSize: 14,
    color: '#666',
    marginBottom: 5,
  },
  alertMessage: {
    fontSize: 16,
    color: '#333',
  },
  warningAlert: {
    borderLeftWidth: 4,
    borderLeftColor: '#ff9500',
  },
  contactButton: {
    backgroundColor: '#007AFF',
    borderRadius: 25,
    padding: 15,
    margin: 20,
    alignItems: 'center',
  },
  contactButtonText: {
    color: 'white',
    fontSize: 18,
    fontWeight: '600',
  },
  noDataText: {
    fontSize: 16,
    color: '#666',
    textAlign: 'center',
    padding: 20,
  },
});

export default PatientDashboard; 