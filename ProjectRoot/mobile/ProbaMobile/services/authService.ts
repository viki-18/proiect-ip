import axios from 'axios';

const BASE_URL = 'http://192.168.185.237:8080'; // Updated with your laptop's IP

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
  testServerConnection: async (): Promise<boolean> => {
    try {
      console.log('Testing server connection to:', BASE_URL);
      const response = await axios.get(`${BASE_URL}/api/health`);
      console.log('Server response:', response.status);
      return true;
    } catch (error: any) {
      console.error('Server connection test failed:', {
        message: error.message,
        code: error.code,
        response: error.response ? {
          status: error.response.status,
          data: error.response.data
        } : 'No response'
      });
      return false;
    }
  },

  login: async (credentials: LoginCredentials): Promise<AuthResponse> => {
    try {
      const requestBody = {
        email: credentials.email,
        password: credentials.password
      };

      console.log('=== Login Request Details ===');
      console.log('URL:', `${BASE_URL}/api/auth/login`);
      console.log('Method:', 'POST');
      console.log('Headers:', {
        'Content-Type': 'application/json',
        'Accept': 'application/json'
      });
      console.log('Body:', JSON.stringify(requestBody, null, 2));
      
      const response = await axios.post(`${BASE_URL}/api/auth/login`, requestBody, {
        headers: {
          'Content-Type': 'application/json',
          'Accept': 'application/json'
        }
      });
      
      console.log('=== Login Response Details ===');
      console.log('Status:', response.status);
      console.log('Headers:', response.headers);
      console.log('Data:', JSON.stringify(response.data, null, 2));
      
      if (response.data && response.data.token && response.data.user) {
        // Ensure user.id is a number
        const userData = {
          ...response.data.user,
          id: Number(response.data.user.id)
        };
        
        const authResponse = {
          token: response.data.token,
          user: userData
        };
        
        console.log('Processed auth response:', JSON.stringify(authResponse, null, 2));
        
        // Store the token in memory
        axios.defaults.headers.common['Authorization'] = `Bearer ${response.data.token}`;
        return authResponse;
      } else {
        console.error('Invalid response format:', JSON.stringify(response.data, null, 2));
        throw new Error('Invalid response format from server');
      }
    } catch (error: any) {
      console.error('=== Login Error Details ===');
      console.error('Error message:', error.message);
      
      if (error.response) {
        console.error('Response status:', error.response.status);
        console.error('Response headers:', error.response.headers);
        console.error('Response data:', JSON.stringify(error.response.data, null, 2));
        
        if (error.response.status === 400) {
          throw new Error('Invalid request format. Please check your credentials.');
        } else if (error.response.status === 401) {
          throw new Error('Invalid email or password');
        } else if (error.response.status === 404) {
          throw new Error('Server not found. Please check your internet connection.');
        } else {
          throw new Error(error.response.data?.message || 'Authentication error occurred');
        }
      } else if (error.request) {
        console.error('Request was made but no response received');
        console.error('Request details:', error.request);
        throw new Error('Could not connect to server. Please check if the server is running and accessible.');
      } else {
        console.error('Unexpected error:', error.message);
        throw new Error('An unexpected error occurred. Please try again.');
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