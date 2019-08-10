import apiService from './apiService';

export default {
  findAll() {
    return apiService.get('streams', undefined);
  }
};