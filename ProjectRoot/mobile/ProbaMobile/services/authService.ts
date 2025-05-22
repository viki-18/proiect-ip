import axios from 'axios';

const BASE_URL = 'http://192.168.100.79:8080'; // Schimbă cu IP-ul corect în producție

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterRequest {
  email: string;
}

export interface AuthResponse {
  token: string;
  user: {
    id: number;
    email: string;
    role: string;
  };
}

const authService = {
  login: async (credentials: LoginCredentials): Promise<AuthResponse> => {
    try {
      const response = await axios.post(`${BASE_URL}/api/auth/login`, credentials);
      return response.data;
    } catch (error: any) {
      if (error.response) {
        // Server responded with an error status
        if (error.response.status === 401) {
          throw new Error('Email sau parolă incorectă');
        } else if (error.response.status === 404) {
          throw new Error('Serverul nu a fost găsit. Verificați conexiunea la internet și încercați din nou.');
        } else {
          throw new Error(error.response.data?.message || 'A apărut o eroare la autentificare');
        }
      } else if (error.request) {
        // Request was made but no response received
        throw new Error('Nu s-a putut conecta la server. Verificați conexiunea la internet.');
      } else {
        // Something else happened
        throw new Error('A apărut o eroare neașteptată. Vă rugăm să încercați din nou.');
      }
    }
  },

  register: async (email: string): Promise<void> => {
    try {
      await axios.post(`${BASE_URL}/api/auth/register`, { email });
    } catch (error: any) {
      if (error.response) {
        throw new Error(error.response.data?.message || 'A apărut o eroare la înregistrare');
      } else if (error.request) {
        throw new Error('Nu s-a putut conecta la server. Verificați conexiunea la internet.');
      } else {
        throw new Error('A apărut o eroare neașteptată. Vă rugăm să încercați din nou.');
      }
    }
  },

  forgotPassword: async (email: string): Promise<void> => {
    try {
      await axios.post(`${BASE_URL}/api/auth/forgot-password`, { email });
    } catch (error: any) {
      if (error.response) {
        throw new Error(error.response.data?.message || 'A apărut o eroare la resetarea parolei');
      } else if (error.request) {
        throw new Error('Nu s-a putut conecta la server. Verificați conexiunea la internet.');
      } else {
        throw new Error('A apărut o eroare neașteptată. Vă rugăm să încercați din nou.');
      }
    }
  },
};

export default authService; 