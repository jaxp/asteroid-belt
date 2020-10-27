export const Api = Object.freeze({
  auth: {
    getKey: '/api/anon/user/auth/getKey',
    login: '/api/anon/user/auth/login',
    logout: '/api/user/auth/logout',
    getUser: '/api/user/auth/getUser',
    getMenus: '/api/user/auth/getMenus',
    getAuthorities: '/api/user/auth/getAuthorities'
  },
  menu: {
    getMenus: '/api/user/menu/getMenus'
  }
});
