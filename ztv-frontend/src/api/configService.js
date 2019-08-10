import apiService from './apiService';

export default {
  find(name) {
    return apiService.get(`configs/${name}`, undefined);
  },
  add(formData) {
    return apiService.post(`configs`, formData);
  }
};