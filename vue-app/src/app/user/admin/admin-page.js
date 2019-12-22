Vue.component('admin-page',{

    template: `
        
        <users-from-organization-table></users-from-organization-table>
    `,

    mounted () {
        axios
            .get('api/users')
            .then(response => {
                this.users = response.data
            })
    }

})