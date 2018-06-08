import http from '../util/http'

export default {
  // 分页查询用户
  searchUser (params, pagination, sort, successCallback) {
    http.page(true, '/api/user', params, pagination, sort, successCallback);
  },

  // 根据用户id获取用户
  selectUserById (id, successCallback) {
    http.get(true, '/api/user/' + id, null, successCallback);
  },

  // 新建用户
  insertUser (params, successCallback, errorCallback, successMessage, errorMessage) {
    http.post(true, '/api/user', params, successCallback, errorCallback, successMessage, errorMessage);
  },

  // 根据用户id更新用户
  updateUserById (params, successCallback, errorCallback, successMessage, errorMessage) {
    http.put(true, '/api/user', params, successCallback, errorCallback, successMessage, errorMessage);
  },

  // 根据用户id删除用户
  deleteUserById (params, successCallback, errorCallback, successMessage, errorMessage) {
    http.delete(true, '/api/user', params, successCallback, errorCallback, successMessage, errorMessage);
  },
}
