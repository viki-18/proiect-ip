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
    const fetchData = async () => {
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

    fetchData();

    // Set up real-time updates
    const fetchVitalSigns = async () => {
      try {
        const vitalSigns = await patientService.getVitalSignsUpdates(1);
        const timestamp = new Date(vitalSigns.dataInregistrare).toLocaleTimeString();

        setPatientData(prevData => {
          if (!prevData) return null;

          const newData = { ...prevData };
          
          // Update heart rate
          if (vitalSigns.puls !== null && vitalSigns.puls !== 0) {
            newData.semneVitale.ritmCardiac.data.push(vitalSigns.puls);
            newData.semneVitale.ritmCardiac.labels.push(timestamp);
          }
          
          // Update blood pressure
          if (vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala !== 0) {
            newData.semneVitale.tensiuneArteriala.sistolica.push(vitalSigns.tensiuneArteriala);
            newData.semneVitale.tensiuneArteriala.diastolica.push(vitalSigns.tensiuneArteriala - 40);
            newData.semneVitale.tensiuneArteriala.labels.push(timestamp);
          }
          
          // Update blood sugar
          if (vitalSigns.glicemie !== null && vitalSigns.glicemie !== 0) {
            newData.semneVitale.glicemie.data.push(vitalSigns.glicemie);
            newData.semneVitale.glicemie.labels.push(timestamp);
          }
          
          // Update temperature
          if (vitalSigns.temperaturaCorporala !== null && vitalSigns.temperaturaCorporala !== 0) {
            newData.semneVitale.temperaturaCorporala.data.push(vitalSigns.temperaturaCorporala);
            newData.semneVitale.temperaturaCorporala.labels.push(timestamp);
          }
          
          // Update weight
          if (vitalSigns.greutate !== null && vitalSigns.greutate !== 0) {
            newData.semneVitale.greutate.data.push(vitalSigns.greutate);
            newData.semneVitale.greutate.labels.push(timestamp);
          }
          
          // Update ambient temperature
          if (vitalSigns.temperaturaAmbientala !== null && vitalSigns.temperaturaAmbientala !== 0) {
            newData.semneVitale.temperaturaAmbientala.data.push(vitalSigns.temperaturaAmbientala);
            newData.semneVitale.temperaturaAmbientala.labels.push(timestamp);
          }

          // Keep only the last 10 data points for each vital sign
          newData.semneVitale.ritmCardiac.data = newData.semneVitale.ritmCardiac.data.slice(-10);
          newData.semneVitale.ritmCardiac.labels = newData.semneVitale.ritmCardiac.labels.slice(-10);
          
          newData.semneVitale.tensiuneArteriala.sistolica = newData.semneVitale.tensiuneArteriala.sistolica.slice(-10);
          newData.semneVitale.tensiuneArteriala.diastolica = newData.semneVitale.tensiuneArteriala.diastolica.slice(-10);
          newData.semneVitale.tensiuneArteriala.labels = newData.semneVitale.tensiuneArteriala.labels.slice(-10);
          
          newData.semneVitale.glicemie.data = newData.semneVitale.glicemie.data.slice(-10);
          newData.semneVitale.glicemie.labels = newData.semneVitale.glicemie.labels.slice(-10);
          
          newData.semneVitale.temperaturaCorporala.data = newData.semneVitale.temperaturaCorporala.data.slice(-10);
          newData.semneVitale.temperaturaCorporala.labels = newData.semneVitale.temperaturaCorporala.labels.slice(-10);
          
          newData.semneVitale.greutate.data = newData.semneVitale.greutate.data.slice(-10);
          newData.semneVitale.greutate.labels = newData.semneVitale.greutate.labels.slice(-10);
          
          newData.semneVitale.temperaturaAmbientala.data = newData.semneVitale.temperaturaAmbientala.data.slice(-10);
          newData.semneVitale.temperaturaAmbientala.labels = newData.semneVitale.temperaturaAmbientala.labels.slice(-10);

          return newData;
        });
      } catch (error) {
        console.error('Error fetching vital signs:', error);
      }
    };

    fetchVitalSigns();
    const interval = setInterval(fetchVitalSigns, 5000);
    return () => clearInterval(interval);
  }, [route.params.userId]);

  const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 122, 255, ${opacity})`,
    strokeWidth: 3,
    barPercentage: 0.5,
    useShadowColorFromDataset: false,
    labelColor: (opacity = 1) => `rgba(0, 0, 0, ${opacity})`,
    propsForLabels: {
      fontSize: 12
    },
    propsForDots: {
      r: '6',
      strokeWidth: '2',
    },
    formatYLabel: (value: string) => {
      const numValue = parseFloat(value);
      if (isNaN(numValue)) return value;
      return numValue.toFixed(1);
    },
    formatXLabel: (value: string) => {
      return value;
    },
    decimalPlaces: 1,
    count: 5,
    yAxisSuffix: '',
    yAxisInterval: 1,
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
        <Text style={styles.errorText}>Nu există date disponibile pentru pacient</Text>
      </View>
    );
  }

  return (
    <ScrollView style={styles.container}>
      {/* Personal Information */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Informații Personale</Text>
        <View style={styles.card}>
          <Text style={styles.name}>{patientData.personalInfo.nume}</Text>
          <Text style={styles.infoText}>Email: {patientData.personalInfo.email}</Text>
          <Text style={styles.infoText}>Vârstă: {patientData.personalInfo.varsta} ani</Text>
          <Text style={styles.infoText}>Gen: {patientData.personalInfo.gen}</Text>
          <Text style={styles.infoText}>Grupa Sanguină: {patientData.personalInfo.grupaSanguina}</Text>
          
          <Text style={styles.subtitle}>Alergii:</Text>
          {patientData.personalInfo.alergii.map((allergy, index) => (
            <Text key={index} style={styles.infoText}>• {allergy}</Text>
          ))}
          
          <Text style={styles.subtitle}>Condiții Cronice:</Text>
          {patientData.personalInfo.conditiiCronice.map((condition, index) => (
            <Text key={index} style={styles.infoText}>• {condition}</Text>
          ))}
          
          <Text style={styles.subtitle}>Contact Urgent:</Text>
          <Text style={styles.infoText}>Nume: {patientData.personalInfo.contactUrgent.nume}</Text>
          <Text style={styles.infoText}>Relație: {patientData.personalInfo.contactUrgent.relatie}</Text>
          <Text style={styles.infoText}>Telefon: {patientData.personalInfo.contactUrgent.telefon}</Text>
        </View>
      </View>

      {/* Vital Signs */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Semne Vitale</Text>
        
        {/* Heart Rate */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Ritm Cardiac</Text>
          {patientData.semneVitale.ritmCardiac.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.ritmCardiac.labels,
                datasets: [{
                  data: patientData.semneVitale.ritmCardiac.data,
                  color: (opacity = 1) => `rgba(255, 99, 132, ${opacity})`,
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' bpm'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru ritmul cardiac</Text>
          )}
        </View>

        {/* Blood Pressure */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Tensiune Arterială</Text>
          {patientData.semneVitale.tensiuneArteriala.sistolica.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.tensiuneArteriala.labels,
                datasets: [
                  {
                    data: patientData.semneVitale.tensiuneArteriala.sistolica,
                    color: (opacity = 1) => `rgba(255, 99, 132, ${opacity})`,
                    strokeWidth: 2
                  },
                  {
                    data: patientData.semneVitale.tensiuneArteriala.diastolica,
                    color: (opacity = 1) => `rgba(54, 162, 235, ${opacity})`,
                    strokeWidth: 2
                  }
                ]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' mmHg'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru tensiune arterială</Text>
          )}
        </View>

        {/* Blood Sugar */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Glicemie</Text>
          {patientData.semneVitale.glicemie.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.glicemie.labels,
                datasets: [{
                  data: patientData.semneVitale.glicemie.data,
                  color: (opacity = 1) => `rgba(75, 192, 192, ${opacity})`,
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' mg/dL'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru glicemie</Text>
          )}
        </View>

        {/* Body Temperature */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Temperatură Corporală</Text>
          {patientData.semneVitale.temperaturaCorporala.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.temperaturaCorporala.labels,
                datasets: [{
                  data: patientData.semneVitale.temperaturaCorporala.data,
                  color: (opacity = 1) => `rgba(255, 159, 64, ${opacity})`,
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' °C'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru temperatură corporală</Text>
          )}
        </View>

        {/* Weight */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Greutate</Text>
          {patientData.semneVitale.greutate.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.greutate.labels,
                datasets: [{
                  data: patientData.semneVitale.greutate.data,
                  color: (opacity = 1) => `rgba(153, 102, 255, ${opacity})`,
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' kg'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru greutate</Text>
          )}
        </View>

        {/* Ambient Temperature */}
        <View style={styles.card}>
          <Text style={styles.subtitle}>Temperatură Ambientală</Text>
          {patientData.semneVitale.temperaturaAmbientala.data.length > 0 ? (
            <LineChart
              data={{
                labels: patientData.semneVitale.temperaturaAmbientala.labels,
                datasets: [{
                  data: patientData.semneVitale.temperaturaAmbientala.data,
                  color: (opacity = 1) => `rgba(201, 203, 207, ${opacity})`,
                }]
              }}
              width={screenWidth - 40}
              height={220}
              chartConfig={{
                ...chartConfig,
                yAxisSuffix: ' °C'
              }}
              bezier
              style={styles.chart}
            />
          ) : (
            <Text style={styles.noDataText}>Nu există date pentru temperatură ambientală</Text>
          )}
        </View>
      </View>

      {/* Medical Recommendations */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Recomandări Medicale</Text>
        {patientData.recomandariMedicale.map((recommendation) => (
          <View key={recommendation.id} style={styles.card}>
            <Text style={styles.date}>{recommendation.data}</Text>
            <Text style={styles.doctor}>{recommendation.medic}</Text>
            <Text style={styles.recommendation}>{recommendation.recomandare}</Text>
          </View>
        ))}
      </View>

      {/* Alerts */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Alerte</Text>
        {patientData.alerte.map((alert) => (
          <View key={alert.id} style={[styles.card, styles.warningAlert]}>
            <Text style={styles.alertDate}>{alert.data}</Text>
            <Text style={styles.alertMessage}>{alert.mesaj}</Text>
          </View>
        ))}
      </View>

      {/* Contact Doctor Button */}
      <TouchableOpacity style={styles.contactButton}>
        <Text style={styles.contactButtonText}>Contactează Medicul</Text>
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
    color: '#FF3B30',
  },
  section: {
    padding: 20,
  },
  sectionTitle: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 15,
    color: '#007AFF',
  },
  card: {
    backgroundColor: 'white',
    borderRadius: 15,
    padding: 20,
    marginBottom: 15,
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.15,
    shadowRadius: 6,
  },
  name: {
    fontSize: 24,
    fontWeight: 'bold',
    marginBottom: 12,
    color: '#007AFF',
  },
  subtitle: {
    fontSize: 20,
    fontWeight: '600',
    marginBottom: 12,
    color: '#007AFF',
  },
  infoText: {
    fontSize: 16,
    color: '#333',
    marginBottom: 8,
  },
  chart: {
    marginVertical: 8,
    borderRadius: 16,
    padding: 10,
    backgroundColor: '#fff',
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
    borderLeftColor: '#FF9500',
  },
  contactButton: {
    backgroundColor: '#007AFF',
    borderRadius: 25,
    padding: 15,
    margin: 20,
    alignItems: 'center',
    elevation: 3,
    shadowColor: '#000',
    shadowOffset: { width: 0, height: 2 },
    shadowOpacity: 0.15,
    shadowRadius: 6,
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
    fontStyle: 'italic',
  },
});

export default PatientDashboard; 