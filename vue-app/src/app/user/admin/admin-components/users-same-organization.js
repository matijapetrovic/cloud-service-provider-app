Vue.component("users-from-organization-table", {
    template:`
    <table border="1" class="table">
        <thead class="thead-dark">
            <tr>
                <th>Email</th>
                <th>Name</th>
                <th>Surname</th>
                <th>Organization</th>

            </tr>
        </thead>
        <tr v-for="u in getOrganizationUsers(user)">
            <div v-for=" in folder.checks">
                <td><a href="#">{{ u.email }}</a></td>
                <td>{{ u.name }}</td>
                <td>{{ u.surname }}</td>
                <td>{{ u.organization }}</td>
            </div> 
        </tr>
    </table>
`,
data: function(){
    return {
        user : null,
        organizationUsers : null
    }
},
mounted () {
    axios
        .get('api/users')
        .then(response => {
            this.users = response.data
        })

        .post('api/login')
            .then(response => {
                this.user =  response.data
            })

},
methods :{
    getOrganizationUsers(user){
        return this.user.organization;
    }

}
});