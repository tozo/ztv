import apiService from './apiService';

export default {
  retrieve() {
    return apiService.get('status', undefined);
  }
};