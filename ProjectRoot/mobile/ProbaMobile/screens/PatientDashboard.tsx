import React from 'react';
import { View, Text, StyleSheet, ScrollView, TouchableOpacity } from 'react-native';
import { LineChart } from 'react-native-chart-kit';
import { Dimensions } from 'react-native';

// Dummy data pentru pacient
const patientData = {
  personalInfo: {
    name: 'Ion Popescu',
    age: 75,
    gender: 'Masculin',
    bloodType: 'A+',
    allergies: ['Penicilină', 'Polen'],
    chronicConditions: ['Hipertensiune', 'Diabet tip 2'],
    emergencyContact: {
      name: 'Maria Popescu',
      relationship: 'Soție',
      phone: '0712 345 678'
    }
  },
  vitalSigns: {
    heartRate: {
      data: [72, 75, 68, 80, 74, 76, 70],
      labels: ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri', 'Sâmbătă', 'Duminică']
    },
    bloodPressure: {
      systolic: [130, 135, 128, 132, 130, 133, 129],
      diastolic: [85, 88, 82, 86, 84, 87, 83],
      labels: ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri', 'Sâmbătă', 'Duminică']
    },
    bloodSugar: {
      data: [120, 118, 125, 122, 119, 123, 121],
      labels: ['Luni', 'Marți', 'Miercuri', 'Joi', 'Vineri', 'Sâmbătă', 'Duminică']
    }
  },
  medicalRecommendations: [
    {
      id: 1,
      date: '2024-03-15',
      doctor: 'Dr. Maria Ionescu',
      recommendation: 'Să măsurați tensiunea de 3 ori pe zi și să păstrați un jurnal.'
    },
    {
      id: 2,
      date: '2024-03-14',
      doctor: 'Dr. Maria Ionescu',
      recommendation: 'Să luați medicamentele pentru tensiune la orele stabilite.'
    }
  ],
  alerts: [
    {
      id: 1,
      type: 'warning',
      message: 'Tensiunea arterială este peste valorile normale',
      date: '2024-03-15 14:30'
    },
    {
      id: 2,
      type: 'info',
      message: 'Următorul control programat: 20 Martie 2024',
      date: '2024-03-15 10:00'
    }
  ]
};

const PatientDashboard: React.FC = () => {
  const screenWidth = Dimensions.get('window').width;

  const chartConfig = {
    backgroundGradientFrom: '#ffffff',
    backgroundGradientTo: '#ffffff',
    color: (opacity = 1) => `rgba(0, 122, 255, ${opacity})`,
    strokeWidth: 2,
    barPercentage: 0.5,
    useShadowColorFromDataset: false
  };

  return (
    <ScrollView style={styles.container}>
      {/* Fișă personală */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Fișă Personală</Text>
        <View style={styles.card}>
          <Text style={styles.name}>{patientData.personalInfo.name}</Text>
          <Text style={styles.infoText}>Vârstă: {patientData.personalInfo.age} ani</Text>
          <Text style={styles.infoText}>Gen: {patientData.personalInfo.gender}</Text>
          <Text style={styles.infoText}>Grupa sanguină: {patientData.personalInfo.bloodType}</Text>
          
          <Text style={styles.subtitle}>Alergii:</Text>
          {patientData.personalInfo.allergies.map((allergy, index) => (
            <Text key={index} style={styles.infoText}>• {allergy}</Text>
          ))}
          
          <Text style={styles.subtitle}>Afecțiuni cronice:</Text>
          {patientData.personalInfo.chronicConditions.map((condition, index) => (
            <Text key={index} style={styles.infoText}>• {condition}</Text>
          ))}
          
          <Text style={styles.subtitle}>Contact de urgență:</Text>
          <Text style={styles.infoText}>Nume: {patientData.personalInfo.emergencyContact.name}</Text>
          <Text style={styles.infoText}>Relație: {patientData.personalInfo.emergencyContact.relationship}</Text>
          <Text style={styles.infoText}>Telefon: {patientData.personalInfo.emergencyContact.phone}</Text>
        </View>
      </View>

      {/* Valori fiziologice */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Valori Fiziologice</Text>
        
        <View style={styles.card}>
          <Text style={styles.subtitle}>Puls</Text>
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
        </View>

        <View style={styles.card}>
          <Text style={styles.subtitle}>Tensiune Arterială</Text>
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
        </View>

        <View style={styles.card}>
          <Text style={styles.subtitle}>Glicemie</Text>
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
        </View>
      </View>

      {/* Recomandări medicale */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Recomandări Medicale</Text>
        {patientData.medicalRecommendations.map((recommendation) => (
          <View key={recommendation.id} style={styles.card}>
            <Text style={styles.date}>{recommendation.date}</Text>
            <Text style={styles.doctor}>{recommendation.doctor}</Text>
            <Text style={styles.recommendation}>{recommendation.recommendation}</Text>
          </View>
        ))}
      </View>

      {/* Alerte și notificări */}
      <View style={styles.section}>
        <Text style={styles.sectionTitle}>Alerte și Notificări</Text>
        {patientData.alerts.map((alert) => (
          <View key={alert.id} style={[styles.card, styles[`${alert.type}Alert`]]}>
            <Text style={styles.alertDate}>{alert.date}</Text>
            <Text style={styles.alertMessage}>{alert.message}</Text>
          </View>
        ))}
      </View>

      {/* Buton de contact medic */}
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
    marginTop: 15,
    marginBottom: 5,
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
  warningAlert: {
    borderLeftWidth: 4,
    borderLeftColor: '#ff9800',
  },
  infoAlert: {
    borderLeftWidth: 4,
    borderLeftColor: '#2196f3',
  },
  alertDate: {
    fontSize: 12,
    color: '#666',
    marginBottom: 5,
  },
  alertMessage: {
    fontSize: 16,
    color: '#333',
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
});

export default PatientDashboard; 