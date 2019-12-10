const OrganizationsTable = { template: '<organizations-table></organizations-table>'}

const router = new VueRouter({
    mode : '',
    routes : [
        { path: '/organizations', component: OrganizationsTable }
    ]
})

var app = new Vue({
    el: '#app',
    data: {
        message : 'Kabib'
    }
})