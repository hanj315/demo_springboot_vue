import axios from 'axios'
import token from './accessToken'
import router from '../router'
import storage from './storage'
import {Message} from 'element-ui'

// 配置axios
let instance = axios.create({
  baseURL: process.env.API_HOST,
  timeout: 5000
});

instance.defaults.headers.common['token'] = token.getAccessToken();
instance.defaults.headers.post['Content-Type'] = 'application/x-www-form-urlencoded';

// 添加请求拦截器
instance.interceptors.request.use(function (config) {
  return config;
}, function (error) {
  return Promise.reject(error);
});

// 添加响应拦截器
instance.interceptors.response.use(function (response) {
  if (!response.status === 200) {
    // 请求失败
    Message.error({
      message: '服务器繁忙，请重试'
    });
  }
  if (response.data && response.data.success != 1) {
    if ('100000' === response.data.errorCode) {
      // 登录凭证失效
      Message.error({
        message: '登录已过期'
      });
      storage.set('history_url', router.history.current.path);
      router.push('/login');
    } else {
      Message.error({
        message: response.data.errorMessage
      });
    }
  }
  return response;
}, function (error) {
  return Promise.reject(error);
});

// 封装分页
function buildPaginationAndSort(url, pagination, sort) {
  let pageURI = 'pageNumber=' + pagination.pageNumber + '&pageSize=' + pagination.pageSize;
  let sortURI = 'sort=' + sort.sort + '&desc=' + sort.desc;
  return url + (url.indexOf('?') >= 0 ? '&' : '?') + pageURI + '&' + sortURI;
}

// 获取错误信息（errorMessage = responseErrorMessage返回接口的错误信息）
function getErrorMessage(responseDTO, errorMessage, defaultMessage) {
  if (errorMessage) {
    let msg = '';
    if (errorMessage == 'responseErrorMessage') {
      if (responseDTO && responseDTO.errorMessage) {
        msg = responseDTO.errorMessage;
      } else {
        msg = defaultMessage;
      }
    } else if (typeof errorMessage === 'string') {
      msg = errorMessage;
    } else {
      msg = defaultMessage;
    }
    return msg;
  }
}

// 操作接口请求成功处理
function operateReqSuccessProcess(response, successCallback, errorCallback, successMessage, errorMessage) {
  if (response.data) {
    let successFlag = response.data.success;
    // message
    if (successMessage && successFlag == 1) {
      Message.success({
        message: typeof successMessage === 'string' ? successMessage : '操作成功'
      });
    } else if (errorMessage && successFlag != 1) {
      Message.error({
        message: getErrorMessage(response.data, errorMessage, '操作失败')
      });
    }
    // callback
    if (typeof successCallback === 'function' && successFlag == 1) {
      successCallback(response.data.data);
    } else if (typeof errorCallback === 'function' && successFlag != 1) {
      errorCallback(response.data.data);
    }
  }
}

// 操作接口请求失败处理
function operateReqErrorProcess(error, errorCallback, errorMessage) {
  // message
  if (errorMessage) {
    Message.error({
      message: getErrorMessage(error.response.data, errorMessage, '操作失败')
    });
  }
  // callback
  if (typeof errorCallback === 'function' && error.response && error.response.data) {
    errorCallback(error.response.data.data);
  }
}

export default {
  /**
   * GET请求
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param params 请求地址
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数
   * @param errorMessage 失败信息(responseErrorMessage: 响应的errorMessage)
   */
  get(needToken, url, params, successCallback, errorCallback, errorMessage) {
    if (!url) {
      return;
    }
    let config = {
      params: params
    };
    instance.get(url, config).then(function (response) {
      if (response.data) {
        let successFlag = response.data.success;
        // message
        if (errorMessage && successFlag != 1) {
          Message.error({
            message: getErrorMessage(response.data, errorMessage, '请求失败')
          });
        }
        // callback
        if (typeof successCallback === 'function' && successFlag == 1) {
          successCallback(response.data.data);
        } else if (typeof errorCallback === 'function' && successFlag != 1) {
          errorCallback(response.data.data);
        }
      }
    }).catch(function (error) {
      // message
      if (errorMessage) {
        Message.error({
          message: getErrorMessage(error.response.data, errorMessage, '请求失败')
        });
      }
      // callback
      if (typeof errorCallback === 'function' && error.response && error.response.data) {
        errorCallback(error.response.data.data);
      }
      Promise.reject(error);
    });
  },

  /**
   * POST请求
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param params 请求参数
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数
   * @param successMessage 成功信息
   * @param errorMessage 失败信息(responseErrorMessage: 响应的errorMessage)
   */
  post(needToken, url, params, successCallback, errorCallback, successMessage, errorMessage) {
    if (!url) {
      return;
    }
    instance.post(url, params).then(function (response) {
      operateReqSuccessProcess(response, successCallback, errorCallback, successMessage, errorMessage);
    }).catch(function (error) {
      operateReqErrorProcess(error, errorCallback, errorMessage);
      Promise.reject(error);
    });
  },

  /**
   * PUT请求
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param params 请求参数
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数
   * @param successMessage 成功信息
   * @param errorMessage 失败信息(responseErrorMessage: 响应的errorMessage)
   */
  put(needToken, url, params, successCallback, errorCallback, successMessage, errorMessage) {
    if (!url) {
      return;
    }
    instance.put(url, params).then(function (response) {
      operateReqSuccessProcess(response, successCallback, errorCallback, successMessage, errorMessage);
    }).catch(function (error) {
      operateReqErrorProcess(error, errorCallback, errorMessage);
      Promise.reject(error);
    });
  },

  /**
   * DELETE请求
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param params 请求地址
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数
   * @param successMessage 成功信息
   * @param errorMessage 失败信息(responseErrorMessage: 响应的errorMessage)
   */
  delete(needToken, url, params, successCallback, errorCallback, successMessage, errorMessage) {
    if (!url) {
      return;
    }
    let config = {
      params: params
    };
    instance.delete(url, config).then(function (response) {
      operateReqSuccessProcess(response, successCallback, errorCallback, successMessage, errorMessage);
    }).catch(function (error) {
      operateReqErrorProcess(error, errorCallback, errorMessage);
      Promise.reject(error);
    });
  },


  /**
   * 分页
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param param 分页参数
   * @param pagination 请求参数
   * @param sort 排序参数
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数(responseErrorMessage: 响应的errorMessage)
   */
  page(needToken, url, param, pagination, sort, successCallback, errorCallback) {
    this.get(needToken, buildPaginationAndSort(url, pagination, sort), param, successCallback, errorCallback);
  },

  /**
   * 文件上传
   * @param needToken 是否需要凭证
   * @param url 请求地址
   * @param formData 表单数据
   * @param successCallback 成功回调函数
   * @param errorCallback 错误回调函数
   * @param successMessage 成功信息
   * @param errorMessage 失败信息(responseErrorMessage: 响应的errorMessage)
   */
  upload(needToken, url, formData, successCallback, errorCallback, successMessage, errorMessage) {
    if (!url) {
      return;
    }
    instance.post(url, formData, {
      headers: {
        'Content-Type': 'multiple/form-data'
      }
    }).then(function (response) {
      if (response.data) {
        let successFlag = response.data.success;
        // message
        if (successMessage && successFlag == 1) {
          Message.success({
            message: typeof successMessage === 'string' ? successMessage : '上传成功'
          });
        } else if (errorMessage && successFlag != 1) {
          Message.error({
            message: getErrorMessage(response.data, errorMessage, '上传失败')
          });
        }
        // callback
        if (typeof successCallback === 'function' && successFlag == 1) {
          successCallback(response.data.data);
        } else if (typeof errorCallback === 'function' && successFlag != 1) {
          errorCallback(response.data.data);
        }
      }
    }).catch(function (error) {
      // message
      if (errorMessage) {
        Message.error({
          message: getErrorMessage(error.response.data, errorMessage, '上传失败')
        });
      }
      // callback
      if (typeof errorCallback === 'function' && error.response && error.response.data) {
        errorCallback(error.response.data.data);
      }
      Promise.reject(error);
    });
  }
}
