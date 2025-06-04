import axios from 'axios';

const BASE_URL = 'http://192.168.185.237:8080';

export interface VitalSigns {
  ritmCardiac: {
    data: number[];
    labels: string[];
  };
  tensiuneArteriala: {
    sistolica: number[];
    diastolica: number[];
    labels: string[];
  };
  glicemie: {
    data: number[];
    labels: string[];
  };
  temperaturaCorporala: {
    data: number[];
    labels: string[];
  };
  greutate: {
    data: number[];
    labels: string[];
  };
  temperaturaAmbientala: {
    data: number[];
    labels: string[];
  };
}

export interface PatientData {
  id: number;
  personalInfo: {
    nume: string;
    email: string;
    varsta: number;
    gen: string;
    grupaSanguina: string;
    alergii: string[];
    conditiiCronice: string[];
    contactUrgent: {
      nume: string;
      relatie: string;
      telefon: string;
    };
  };
  semneVitale: VitalSigns;
  recomandariMedicale: Array<{
    id: number;
    data: string;
    medic: string;
    recomandare: string;
  }>;
  alerte: Array<{
    id: number;
    tip: string;
    mesaj: string;
    data: string;
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
          nume: `${user.nume || ''} ${user.prenume || ''}`.trim() || 'N/A',
          email: user.email || 'N/A',
          varsta: user.varsta || 0,
          gen: user.gen || "N/A",
          grupaSanguina: "N/A", // Blood type not available in the response
          alergii: [],
          conditiiCronice: [],
          contactUrgent: {
            nume: "N/A",
            relatie: "N/A",
            telefon: "N/A"
          }
        },
        semneVitale: {
          ritmCardiac: {
            data: [],
            labels: []
          },
          tensiuneArteriala: {
            sistolica: [],
            diastolica: [],
            labels: []
          },
          glicemie: {
            data: [],
            labels: []
          },
          temperaturaCorporala: {
            data: [],
            labels: []
          },
          greutate: {
            data: [],
            labels: []
          },
          temperaturaAmbientala: {
            data: [],
            labels: []
          }
        },
        recomandariMedicale: patient.recomandariDeLaMedic ? [{
          id: 1,
          data: new Date().toISOString(),
          medic: "Medic",
          recomandare: patient.recomandariDeLaMedic
        }] : [],
        alerte: []
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
          patientData.semneVitale = {
            ritmCardiac: {
              data: vitalSigns.puls !== null && vitalSigns.puls !== 0 ? [vitalSigns.puls] : [],
              labels: vitalSigns.puls !== null && vitalSigns.puls !== 0 ? [timestamp] : []
            },
            tensiuneArteriala: {
              sistolica: vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala !== 0 ? [vitalSigns.tensiuneArteriala] : [],
              diastolica: vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala !== 0 ? [vitalSigns.tensiuneArteriala - 40] : [], // Approximate diastolic
              labels: vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala !== 0 ? [timestamp] : []
            },
            glicemie: {
              data: vitalSigns.glicemie !== null && vitalSigns.glicemie !== 0 ? [vitalSigns.glicemie] : [],
              labels: vitalSigns.glicemie !== null && vitalSigns.glicemie !== 0 ? [timestamp] : []
            },
            temperaturaCorporala: {
              data: vitalSigns.temperaturaCorporala !== null && vitalSigns.temperaturaCorporala !== 0 ? [vitalSigns.temperaturaCorporala] : [],
              labels: vitalSigns.temperaturaCorporala !== null && vitalSigns.temperaturaCorporala !== 0 ? [timestamp] : []
            },
            greutate: {
              data: vitalSigns.greutate !== null && vitalSigns.greutate !== 0 ? [vitalSigns.greutate] : [],
              labels: vitalSigns.greutate !== null && vitalSigns.greutate !== 0 ? [timestamp] : []
            },
            temperaturaAmbientala: {
              data: vitalSigns.temperaturaAmbientala !== null && vitalSigns.temperaturaAmbientala !== 0 ? [vitalSigns.temperaturaAmbientala] : [],
              labels: vitalSigns.temperaturaAmbientala !== null && vitalSigns.temperaturaAmbientala !== 0 ? [timestamp] : []
            }
          };

          // Add alerts for values outside normal ranges
          const alerts: Array<{
            id: number;
            tip: string;
            mesaj: string;
            data: string;
          }> = [];
          
          // Heart rate alerts
          if (vitalSigns.puls !== null && vitalSigns.puls !== 0) {
            if (vitalSigns.puls > vitalSigns.valAlarmaPuls) {
              alerts.push({
                id: 1,
                tip: 'warning',
                mesaj: `Ritmul cardiac ridicat: ${vitalSigns.puls} bpm (max: ${vitalSigns.valAlarmaPuls})`,
                data: vitalSigns.dataInregistrare
              });
            }
            // Check for low heart rate (below 80)
            if (vitalSigns.puls < 80) {
              alerts.push({
                id: 7,
                tip: 'warning',
                mesaj: `Ritmul cardiac scăzut: ${vitalSigns.puls} bpm (min: 80)`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Blood pressure alerts
          if (vitalSigns.tensiuneArteriala !== null && vitalSigns.tensiuneArteriala !== 0) {
            if (vitalSigns.tensiuneArteriala > vitalSigns.valAlarmaTensiune) {
              alerts.push({
                id: 2,
                tip: 'warning',
                mesaj: `Tensiune arterială ridicată: ${vitalSigns.tensiuneArteriala} mmHg (max: ${vitalSigns.valAlarmaTensiune})`,
                data: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTensiuneMin !== null && vitalSigns.tensiuneArteriala < vitalSigns.valAlarmaTensiuneMin) {
              alerts.push({
                id: 8,
                tip: 'warning',
                mesaj: `Tensiune arterială scăzută: ${vitalSigns.tensiuneArteriala} mmHg (min: ${vitalSigns.valAlarmaTensiuneMin})`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Body temperature alerts
          if (vitalSigns.temperaturaCorporala !== null && vitalSigns.temperaturaCorporala !== 0) {
            if (vitalSigns.temperaturaCorporala > vitalSigns.valAlarmaTemperatura) {
              alerts.push({
                id: 3,
                tip: 'warning',
                mesaj: `Temperatură corporală ridicată: ${vitalSigns.temperaturaCorporala}°C (max: ${vitalSigns.valAlarmaTemperatura})`,
                data: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTemperaturaMin !== null && vitalSigns.temperaturaCorporala < vitalSigns.valAlarmaTemperaturaMin) {
              alerts.push({
                id: 9,
                tip: 'warning',
                mesaj: `Temperatură corporală scăzută: ${vitalSigns.temperaturaCorporala}°C (min: ${vitalSigns.valAlarmaTemperaturaMin})`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Blood sugar alerts
          if (vitalSigns.glicemie !== null && vitalSigns.glicemie !== 0) {
            if (vitalSigns.glicemie > vitalSigns.valAlarmaGlicemie) {
              alerts.push({
                id: 4,
                tip: 'warning',
                mesaj: `Glicemie ridicată: ${vitalSigns.glicemie} mg/dL (max: ${vitalSigns.valAlarmaGlicemie})`,
                data: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaGlicemieMin !== null && vitalSigns.glicemie < vitalSigns.valAlarmaGlicemieMin) {
              alerts.push({
                id: 10,
                tip: 'warning',
                mesaj: `Glicemie scăzută: ${vitalSigns.glicemie} mg/dL (min: ${vitalSigns.valAlarmaGlicemieMin})`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Weight alerts
          if (vitalSigns.greutate !== null && vitalSigns.greutate !== 0) {
            if (vitalSigns.greutate > vitalSigns.valAlarmaGreutate) {
              alerts.push({
                id: 5,
                tip: 'warning',
                mesaj: `Greutate ridicată: ${vitalSigns.greutate} kg (max: ${vitalSigns.valAlarmaGreutate})`,
                data: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaGreutateMin !== null && vitalSigns.greutate < vitalSigns.valAlarmaGreutateMin) {
              alerts.push({
                id: 11,
                tip: 'warning',
                mesaj: `Greutate scăzută: ${vitalSigns.greutate} kg (min: ${vitalSigns.valAlarmaGreutateMin})`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Ambient temperature alerts
          if (vitalSigns.temperaturaAmbientala !== null && vitalSigns.temperaturaAmbientala !== 0) {
            if (vitalSigns.temperaturaAmbientala > vitalSigns.valAlarmaTemperaturaAmb) {
              alerts.push({
                id: 6,
                tip: 'warning',
                mesaj: `Temperatură ambientală ridicată: ${vitalSigns.temperaturaAmbientala}°C (max: ${vitalSigns.valAlarmaTemperaturaAmb})`,
                data: vitalSigns.dataInregistrare
              });
            }
            if (vitalSigns.valAlarmaTemperaturaAmbMin !== null && vitalSigns.temperaturaAmbientala < vitalSigns.valAlarmaTemperaturaAmbMin) {
              alerts.push({
                id: 12,
                tip: 'warning',
                mesaj: `Temperatură ambientală scăzută: ${vitalSigns.temperaturaAmbientala}°C (min: ${vitalSigns.valAlarmaTemperaturaAmbMin})`,
                data: vitalSigns.dataInregistrare
              });
            }
          }
          
          // Keep only the last 10 alerts
          patientData.alerte = alerts.slice(-10);
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