const Profile = {template: '<profile></profile>'}
const UsersPage = { template : '<users-page></users-page>' }
const LoginPage = { template: '<login-page></login-page>' }
const OrganizationsPage = { template: '<organizations-page></organizations-page>' }
const VMPage = { template: '<virtual-machines-page></virtual-machines-page>'}
const Drive = {template: '<drive-page></drive-page>'}

// kad se refreshuje stranica da pokupi token
const token = localStorage.getItem('user-token')
if (token) {
  axios.defaults.headers.common['Authorization'] = token
}

const router = new VueRouter({
    mode : 'hash',
    routes : [
        { path: '/', component: UsersPage, meta: {title: 'Home', requiresAuth: true}},
        { path: '/login', component: LoginPage, meta: { title: 'Login', requiresAuth: false}},
        { path: '/organizations', component: OrganizationsPage, meta: { title: 'Organizations', requiresAuth: true} },
        { path: '/users', component: UsersPage, meta: { title: 'Users', requiresAuth: true }},
        { path: '/virtualmachines', component: VMPage, meta: { title:'Virtual Machines', requiresAuth: true}},
        { path: '/profile', component: Profile, meta: { title:'Profile', requiresAuth: true}},
        { path: '/drives', component: Drive, meta: { title:'Drives', requiresAuth: true}}
        
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