const OrganizationsPage = { template: '<organizations-page></organizations-page>' }
const UsersPage = { template : '<users-page></users-page>' }

const router = new VueRouter({
    mode : 'hash',
    routes : [
        { path: '/', component: UsersPage},
        { path: '/organizations', component: OrganizationsPage, meta: { title: 'Organizations'} },
        { path: '/users', component: UsersPage, meta: { title: 'Users' }}
    ]
})

var app = new Vue({
    template: `
        <base-layout v-bind:page-title="$route.meta.title"></base-layout>
    `,
    router,
    el: '#app',
    data: {
        message: 'Kabib'
    }
})