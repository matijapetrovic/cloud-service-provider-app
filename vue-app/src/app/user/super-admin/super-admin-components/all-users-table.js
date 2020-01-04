Vue.component("all-users-table",{
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
            <!-- Check printing currentUser in table -->
            <tr v-for="user in users" v-if="user.email !== currentUser.email">
                <td>
                <a
                 href="#" 
                 @click.prevent="viewUser(user.email )" 
                 data-toggle="modal" 
                 v-bind:data-target="'#' + viewModalId">
                 {{ user.email }}
                 </a></td>
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td>{{ user.organization.name }}</td>
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
            currentUser : null,
            loaded : false,
        }
    },
    mounted () {
        axios
            .get('api/users')
            .then(response => {
                this.users = response.data;
            }),
        axios
        .get('api/users/currentUser')
        .then(response => {
            this.currentUser = response.data;
            this.loaded = true;
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