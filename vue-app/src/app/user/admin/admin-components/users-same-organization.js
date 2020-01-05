Vue.component("users-from-organization-table", {
    template:`
    <div>
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th>Organization</th>

                </tr>
            </thead>
            <tr v-for="u in users" v-if="user.email !== currentUser.email">
                <td><a href="#">{{ u.email }}</a></td>
                <td>{{ u.name }}</td>
                <td>{{ u.surname }}</td>
                <td>{{ u.organization }}</td> 
            </tr>   
        </table>
    </div>
        `,
    props : {
        viewModalId : String
    },
    data: function(){
        return {
            users: null,
            currentUser: null,
            loaded : false,
        }
    },
    mounted () {
        this.currentUser = JSON.parse(localStorage.getItem('user'));
        this.loadUsers(this.currentUser.email);       
    },
    methods: {
        loadUsers(email){
            axios
            .get('api/users/organizations/' + email)
            .then(response =>{
                this.users = response.data;
            });
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
            this.$emit('viewUser', email);
        }
   }

});