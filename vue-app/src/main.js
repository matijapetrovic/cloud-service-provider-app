const OrganizationsPage = { template: '<org-page></org-page>' }
const UsersPage = { template : '<users-page></users-page>' }

const router = new VueRouter({
    mode : 'hash',
    routes : [
        { path: '/', component: UsersPage},
        { path: '/organizations', component: OrganizationsPage },
        { path: '/users', component: UsersPage }
    ]
})

var app = new Vue({
    router,
    el: '#app',
    data: {
        message : 'Kabib'
    }
})