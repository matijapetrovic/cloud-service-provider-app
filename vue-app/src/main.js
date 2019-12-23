const OrganizationsPage = { template: '<organizations-page></organizations-page>' }
const UsersPage = { template : '<users-page></users-page>' }
const LoginPage = { template: '<login-page></login-page>' }

// kad se refreshuje stranica da pokupi token
const token = localStorage.getItem('user-token')
if (token) {
  axios.defaults.headers.common['Authorization'] = token
}

const router = new VueRouter({
    mode : 'history',
    routes : [
        { path: '/', component: UsersPage, meta: {title: 'Home', requiresAuth: true}},
        { path: '/login', component: LoginPage, meta: { title: 'Login', requiresAuth: false}},
        { path: '/organizations', component: OrganizationsPage, meta: { title: 'Organizations', requiresAuth: true} },
        { path: '/users', component: UsersPage, meta: { title: 'Users', requiresAuth: true }}
    ]
});

router.beforeEach((to, from, next) => {
    
    if (to.matched.some(record => record.meta.requiresAuth)) {
        if (localStorage.getItem('user-token') == null) {
            next({
                path: '/login',
                params: { nextUrl: to.fullPath }
            });
        } else {
            next();
        }
    } else {
        if (localStorage.getItem('user-token') != null) {
            next({
                path: '/'
            });
        } else {
            next();
        }
    }
});

var app = new Vue({
    template: `
        <router-view></router-view>
    `,
    router,
    el: '#app',
    data: {
        message: 'Kabib'
    }
});