import axios from 'axios';

const BASE_URL = 'http://192.168.1.8:8080';

export interface VitalSigns {
  heartRate: {
    data: number[];
    labels: string[];
  };
  bloodPressure: {
    systolic: number[];
    diastolic: number[];
    labels: string[];
  };
  bloodSugar: {
    data: number[];
    labels: string[];
  };
  temperature: {
    data: number[];
    labels: string[];
  };
  weight: {
    data: number[];
    labels: string[];
  };
  ambientTemperature: {
    data: number[];
    labels: string[];
  };
}

export interface PatientData {
  id: number;
  personalInfo: {
    name: string;
    email: string;
    age: number;
    gender: string;
    bloodType: string;
    allergies: string[];
    chronicConditions: string[];
    emergencyContact: {
      name: string;
      relationship: string;
      phone: string;
    };
  };
  vitalSigns: VitalSigns;
  medicalRecommendations: Array<{
    id: number;
    date: string;
    doctor: string;
    recommendation: string;
  }>;
  alerts: Array<{
    id: number;
    type: string;
    message: string;
    date: string;
  }>;
}

interface Patient {
  id: number;
  utilizatorId: number;
  profesie: string;
  nr: string;
  CNP: string;
  idIngrijitor: number;
  idSmartphone: number;
  recomandariDeLaMedic: string | null;
  idSupraveghetor: number;
  pathRapoarte: string | null;
  idMedic: number;
  strada: string;
  localitate: string;
}

interface User {
  id: number;
  email: string;
  nume: string;
  prenume: string;
  rol: string;
  varsta: number | null;
  gen: string | null;
}

interface VitalSignsResponse {
  id: number;
  idPacient: number;
  pacient: {
    idPacient: number;
    utilizatorId: number;
    utilizator: {
      id: number;
      email: string;
      nrTelefon: string;
      tipUtilizator: string;
      parola: string;
      nume: string | null;
      prenume: string | null;
      varsta: number | null;
      gen: string | null;
      specializare: string | null;
    };
    localitate: string;
    strada: string;
    nr: string;
    profesie: string;
    idMedic: number;
    medic: {
      idMedic: number;
      utilizatorId: number;
      utilizator: {
        id: number;
        email: string;
        nrTelefon: string;
        tipUtilizator: string;
        parola: string;
        nume: string | null;
        prenume: string | null;
        varsta: number | null;
        gen: string | null;
        specializare: string | null;
      };
    };
    recomandariDeLaMedic: string | null;
    idSupraveghetor: number;
    supraveghetor: {
      idSupraveghetor: number;
      utilizatorId: number;
      utilizator: {
        id: number;
        email: string;
        nrTelefon: string;
        tipUtilizator: string;
        parola: string;
        nume: string | null;
        prenume: string | null;
        varsta: number | null;
        gen: string | null;
        specializare: string | null;
      };
    };
    idIngrijitor: number;
    ingrijitor: {
      idIngrijitor: number;
      utilizatorId: number;
      utilizator: {
        id: number;
        email: string;
        nrTelefon: string;
        tipUtilizator: string;
        parola: string;
        nume: string | null;
        prenume: string | null;
        varsta: number | null;
        gen: string | null;
        specializare: string | null;
      };
      nume: string | null;
      prenume: string | null;
      varsta: number | null;
      gen: string | null;
      specializare: string | null;
    };
    idSmartphone: number;
    pathRapoarte: string | null;
    cnp: string;
  };
  puls: number | null;
  valAlarmaPuls: number;
  valAlarmaPulsMin: number | null;
  tensiuneArteriala: number | null;
  valAlarmaTensiune: number;
  valAlarmaTensiuneMin: number | null;
  temperaturaCorporala: number | null;
  valAlarmaTemperatura: number;
  valAlarmaTemperaturaMin: number | null;
  greutate: number | null;
  valAlarmaGreutate: number;
  valAlarmaGreutateMin: number | null;
  glicemie: number | null;
  valAlarmaGlicemie: number;
  valAlarmaGlicemieMin: number | null;
  lumina: number | null;
  valAlarmaLumina: boolean;
  temperaturaAmbientala: number | null;
  valAlarmaTemperaturaAmb: number;
  valAlarmaTemperaturaAmbMin: number | null;
  gaz: number | null;
  valAlarmaGaz: boolean;
  umiditate: number | null;
  valAlarmaUmiditate: boolean;
  proximitate: number | null;
  valAlarmaProximitate: boolean;
  dataInregistrare: string;
}

const patientService = {
  getPatientData: async (userId: number): Promise<PatientData> => {
    try {
      // Always fetch patient data for ID 1
      console.log('Fetching patient info from:', `${BASE_URL}/pacienti/1`);
      const patientResponse = await axios.get(`${BASE_URL}/pacienti/1`, {
        headers: {
          'Accept': 'application/json'
        }
      });
      
      const patient = patientResponse.data;
      console.log('Patient data:', patient);

      // Then get the user data using utilizatorId
      console.log('Fetching user info for utilizatorId:', patient.utilizatorId);
      const userResponse = await axios.get(`${BASE_URL}/utilizatori/${patient.utilizatorId}`, {
        headers: {
          'Accept': 'application/json'
        }
      });
      
      const user = userResponse.data;
      console.log('User data:', user);
      
      // Transform the data into the expected format
      const patientData: PatientData = {
        id: patient.id,
        personalInfo: {
          name: `${user.nume} ${user.prenume}`,
          email: user.email,
          age: user.varsta || 0,
          gender: user.gen || "N/A",
          bloodType: "N/A", // Blood type not available in the response
          allergies: [],
          chronicConditions: [],
          emergencyContact: {
            name: "N/A",
            relationship: "N/A",
            phone: "N/A"
          }
        },
        vitalSigns: {
          heartRate: {
            data: [],
            labels: []
          },
          bloodPressure: {
            systolic: [],
            diastolic: [],
            labels: []
          },
          bloodSugar: {
            data: [],
            labels: []
          },
          temperature: {
            data: [],
            labels: []
          },
          weight: {
            data: [],
            labels: []
          },
          ambientTemperature: {
            data: [],
            labels: []
          }
        },
        medicalRecommendations: patient.recomandariDeLaMedic ? [{
          id: 1,
          date: new Date().toISOString(),
          doctor: "Medic",
          recommendation: patient.recomandariDeLaMedic
        }] : [],
        alerts: []
      };

      // Get vital signs for patient ID 1
      try {
        console.log('Fetching vital signs from:', `${BASE_URL}/semne-vitale/pacient/1`);
        const vitalSignsResponse = await axios.get(`${BASE_URL}/semne-vitale/pacient/1`, {
          headers: {
            'Accept': 'application/json'
          }
        });
        console.log('Vital signs response:', JSON.stringify(vitalSignsResponse.data, null, 2));
        
        if (vitalSignsResponse.data) {
          const vitalSigns = vitalSignsResponse.data as VitalSignsResponse;
          const timestamp = new Date(vitalSigns.dataInregistrare).toLocaleTimeString();
          
          // Update vital signs with the new data
          patientData.vitalSigns = {
            heartRate: {
              data: vitalSigns.puls !== null ? [vitalSigns.puls] : [],
              labels: vitalSigns.puls !== null ? [timestamp] : []
            },
            bloodPressure: {
              systolic: vitalSigns.tensiuneArteriala !== null ? [vitalSigns.tensiuneArteriala] : [],
              diastolic: vitalSigns.tensiuneArteriala !== null ? [vitalSigns.tensiuneArteriala - 40] : [], // Approximate diastolic
              labels: vitalSigns.tensiuneArteriala !== null ? [timestamp] : []
            },
            bloodSugar: {
              data: vitalSigns.glicemie !== null ? [vitalSigns.glicemie] : [],
              labels: vitalSigns.glicemie !== null ? [timestamp] : []
            },
            temperature: {
              data: vitalSigns.temperaturaCorporala !== null ? [vitalSigns.temperaturaCorporala] : [],
              labels: vitalSigns.temperaturaCorporala !== null ? [timestamp] : []
            },
            weight: {
              data: vitalSigns.greutate !== null ? [vitalSigns.greutate] : [],
              labels: vitalSigns.greutate !== null ? [timestamp] : []
            },
            ambientTemperature: {
              data: vitalSigns.temperaturaAmbientala !== null ? [vitalSigns.temperaturaAmbientala] : [],
              labels: vitalSigns.temperaturaAmbientala !== null ? [timestamp] : []
            }
          };

          // Add alerts for values outside normal ranges
          const alerts: Array<{
            id: number;
            type: string;
            message: string;
            date: string;
          }> = [];
          
          if (vitalSigns.puls !== null && vitalSigns.puls > vitalSigns.valAlarmaPuls) {
            alerts.push({
              id: 1,
              type: 'warning',
              message: `High heart rate: ${vitalSigns.puls} bpm (max: ${vitalSigns.valAlarmaPuls})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          if (vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala > vitalSigns.valAlarmaTensiune) {
            alerts.push({
              id: 2,
              type: 'warning',
              message: `High blood pressure: ${vitalSigns.tensiuneArteriala} mmHg (max: ${vitalSigns.valAlarmaTensiune})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          if (vitalSigns.temperaturaCorporala !== null && vitalSigns.temperaturaCorporala > vitalSigns.valAlarmaTemperatura) {
            alerts.push({
              id: 3,
              type: 'warning',
              message: `High body temperature: ${vitalSigns.temperaturaCorporala}°C (max: ${vitalSigns.valAlarmaTemperatura})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          if (vitalSigns.glicemie !== null && vitalSigns.glicemie > vitalSigns.valAlarmaGlicemie) {
            alerts.push({
              id: 4,
              type: 'warning',
              message: `High blood sugar: ${vitalSigns.glicemie} mg/dL (max: ${vitalSigns.valAlarmaGlicemie})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          if (vitalSigns.greutate !== null && vitalSigns.greutate > vitalSigns.valAlarmaGreutate) {
            alerts.push({
              id: 5,
              type: 'warning',
              message: `High weight: ${vitalSigns.greutate} kg (max: ${vitalSigns.valAlarmaGreutate})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          if (vitalSigns.temperaturaAmbientala !== null && vitalSigns.temperaturaAmbientala > vitalSigns.valAlarmaTemperaturaAmb) {
            alerts.push({
              id: 6,
              type: 'warning',
              message: `High ambient temperature: ${vitalSigns.temperaturaAmbientala}°C (max: ${vitalSigns.valAlarmaTemperaturaAmb})`,
              date: vitalSigns.dataInregistrare
            });
          }
          
          patientData.alerts = alerts;
        }
      } catch (vitalSignsError) {
        console.warn('Could not fetch vital signs:', vitalSignsError);
        // Continue without vital signs
      }

      console.log('Final patient data:', JSON.stringify(patientData, null, 2));
      return patientData;
    } catch (error: any) {
      console.error('Error in getPatientData:', {
        message: error.message,
        response: error.response ? {
          status: error.response.status,
          data: error.response.data,
          headers: error.response.headers
        } : 'No response',
        request: error.request ? 'Request was made but no response received' : 'No request was made'
      });

      if (error.response) {
        if (error.response.status === 404) {
          throw new Error('Patient not found');
        } else {
          throw new Error(`Error fetching patient data: ${error.response.data?.message || error.message}`);
        }
      } else if (error.request) {
        throw new Error('Could not connect to server. Please check your internet connection.');
      } else {
        throw new Error(`An unexpected error occurred: ${error.message}`);
      }
    }
  },

  // Add a function to fetch vital signs updates
  getVitalSignsUpdates: async (userId: number): Promise<VitalSignsResponse> => {
    try {
      // Always fetch vital signs for patient ID 1
      console.log('Fetching vital signs from:', `${BASE_URL}/semne-vitale/pacient/1`);
      const response = await axios.get(`${BASE_URL}/semne-vitale/pacient/1`, {
        headers: {
          'Accept': 'application/json'
        }
      });
      
      if (!response.data) {
        throw new Error('No data received from vital signs endpoint');
      }

      console.log('Vital signs response:', JSON.stringify(response.data, null, 2));
      return response.data;
    } catch (error: any) {
      console.error('Error in getVitalSignsUpdates:', {
        message: error.message,
        response: error.response ? {
          status: error.response.status,
          data: error.response.data,
          headers: error.response.headers
        } : 'No response',
        request: error.request ? 'Request was made but no response received' : 'No request was made'
      });

      if (error.response) {
        if (error.response.status === 404) {
          throw new Error('Vital signs not found');
        } else if (error.response.status === 405) {
          throw new Error('Invalid request method. Please check the API documentation.');
        } else {
          throw new Error(`Error fetching vital signs: ${error.response.data?.message || error.message}`);
        }
      } else if (error.request) {
        throw new Error('Could not connect to server. Please check your internet connection.');
      } else {
        throw new Error(`An unexpected error occurred: ${error.message}`);
      }
    }
  }
};

export default patientService; 