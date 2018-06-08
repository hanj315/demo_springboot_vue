import storage from './storage'
import moment from 'moment'
import router from '../router'
import {Message} from 'element-ui'

export default {
  /**
   * 获取accessToken
   */
  getAccessToken () {
    // 从storage中查询token信息
    let token = storage.get('token');
    // TODO
    token = {
      accessToken: "111",
      refreshToken: "222",
      expired: "2222-2-2"
    };
    if (!token || !token.accessToken || !token.expired) {
      Message.error({
        message: '请登录',
        duration: 1000
      });
      router.push('/login');
      return false;
    } else if (token.expired <= moment().unix()) {
      // 已过期，置换accessToken
      Message.error({
        message: '凭证已过期',
        duration: 1000
      });
      router.push('/login');
      return false;
    }
    return token.accessToken
  },

  /**
   * 保存token信息
   *
   * @param {*} accessToken  访问凭证
   * @param {*} refreshToken 置换凭证
   * @param {*} expireTime 过期时间（秒）
   */
  refreshToken (accessToken, refreshToken, expireTime) {
    storage.set('token', {
      accessToken: accessToken, // 访问凭证
      refreshToken: refreshToken, // 置换凭证
      expired: moment().unix() + parseInt(expireTime) - 10 // 有效时间 = 当前时间戳 + 过期时间（秒）
    })
  }

}
