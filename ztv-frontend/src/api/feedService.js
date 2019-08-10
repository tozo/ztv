import apiService from './apiService';

export default {
  findAll() {
    return apiService.get('feeds', undefined);
  },
  add(name, url, formData) {
    return apiService.post(`feeds?name=${name}&url=${url}`, formData);
  },
  delete(id) {
    return apiService.delete(`feeds/${id}`);
  }
};