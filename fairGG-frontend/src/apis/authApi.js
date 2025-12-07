import { api } from '@/lib/axios';
import { storage } from '@/lib/storage';

export const authApi = {
  login: async (username, password) => {
    const response = await api.post('/api/auth/login', {
      username,
      password
    });
    return response.data;
  },

  signup: async (username, password, riotName, riotTag) => {
    const response = await api.post('/api/auth/signup', {
      username,
      password,
      riotName,
      riotTag
    });
    return response.data;
  },

  fetchMe: async () => {
    const token = storage.getToken();
    if (!token) {
      throw new Error('No token found');
    }
    
    const response = await api.get('/api/auth/me', {
      headers: {
        Authorization: `Bearer ${token}`
      }
    });
    return response.data;
  }
};