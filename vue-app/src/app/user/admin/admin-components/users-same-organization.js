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
        props : {
            viewModalId : String

        },
        data: function(){
            return {
                users: null,
                user :{
                    "email" : null,
                    "password": null,
                    "name": null,
                    "surname": null,
                    "organization": null
                }
            }
        },
        mounted () {
        axios
            .get('api/users/currentUser')
            .then(response => {
                this.user = response.data
            })
        },
        methods: {
        getOrganizationUsers(){
            axios
            .get('api/users/organizations/' + user.email)
        },
        addUser(user) {
            this.users.push(user);
        },
        updateUser(user) {
            var el = this.users.find(function(element) {
                return element.email === users.email;
            });
            var idx = this.user.indexOf(el);
            this.users.splice(idx, 1);
            this.users.splice(idx, 0, organization);
        },
        viewOrganization(email) {
            this.$emit('viewUserA', email);
        }
   }

});