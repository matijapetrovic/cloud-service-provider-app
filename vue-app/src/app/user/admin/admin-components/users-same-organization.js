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
            <tr v-for="u in userOrganization.users" v-if="user.email !== currentUser.email">
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
            loaded : false,
            currentUser: null,
            loaded : false,
            userOrganization :{
                users: []
            }
        }
    },
    mounted () {
        this.currentUser = JSON.parse(localStorage.getItem('user'));
        this.loadUsers();    
    },
    methods: {
        loadUsers(){
            this.userOrganization = this.currentUser.organization;
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
        viewUser(email) {
            this.$emit('viewUser', email);
        },
        deleteUser(user){
            var el = this.users.find(function(element) {
                return element.email === user.email;
            });
            var idx = this.users.indexOf(el);
            this.users.splice(idx, 1);
        }
   }
});