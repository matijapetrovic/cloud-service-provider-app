const OrganizationsTable = { template: '<organizations-table></organizations-table>'}
const UsersTable = {template : '<users-table></users-table>'}

const router = new VueRouter({
    mode : '',
    routes : [
        { path: '/organizations', component: OrganizationsTable },
        {path: '/users', component: UsersTable}
    ]
})

var app = new Vue({
    router,
    el: '#app',
    data: {
        message : 'Kabib'
    }
})