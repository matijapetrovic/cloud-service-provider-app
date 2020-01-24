Vue.component("all-users-table",{
    template:`
    <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Email</th>
                    <th>Name</th>
                    <th>Surname</th>
                    <th v-if="currentUserRole !== 'ADMIN'">Organization</th>
                </tr>
            </thead>
            <!-- Check printing super_admin in table -->
            <tr v-for="user in users" v-if="user.role !== 'SUPER_ADMIN'">
                <td>
                <a
                 href="#" 
                 @click.prevent="viewUser(user.email)" 
                 data-toggle="modal" 
                 v-bind:data-target="'#' + viewModalId">
                 {{ user.email }}
                 </a></td>
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td v-if="currentUserRole !== 'ADMIN'">{{user.organization}}</td>
            </tr>
        </table>

    `,
    props : {
        viewModalId : String
        
    },
    data: function(){
        return {
            users: null,   
            currentUserRole : null     
        }
    },
    mounted () {
        axios
            .get('api/users')
            .then(response => {
                this.users = response.data;
                let currentUser = JSON.parse(localStorage.getItem("user"));
                this.currentUserRole = currentUser.role;
            })
    },
    methods: {
        addUser(user) {
            this.users.push(user);
        },
        updateUser(user) {
            var el = this.users.find(function(element) {
                return element.email === user.email;
            });
            var idx = this.users.indexOf(el);
            this.users.splice(idx, 1);
            this.users.splice(idx, 0, user);
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
})