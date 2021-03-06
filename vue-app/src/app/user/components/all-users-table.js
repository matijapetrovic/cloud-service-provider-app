Vue.component("all-users-table",{
    template:`
    <div class="row">
        <table border="1" class="table">
                <thead class="thead-dark">
                    <tr>
                        <th>Email</th>
                        <th>Name</th>
                        <th>Surname</th>
                        <th v-if="$root.isSuperAdmin">Organization</th>
                    </tr>
                </thead>

                <tr v-for="user in users">
                    <td>
                        <a
                        href="#" 
                        @click.prevent="viewUser(user.email)" 
                        data-toggle="modal" 
                        v-bind:data-target="'#' + viewModalId"
                        >
                            {{ user.email }}
                        </a>
                    </td>
                    <td>{{ user.name }}</td>
                    <td>{{ user.surname }}</td>
                    <td v-if="$root.isSuperAdmin">{{user.organization}}</td>
                </tr>
            </table>
    </div>

    `,
    props : {
        viewModalId : String
        
    },
    data: function(){
        return {
            users: null   
        }
    },
    mounted () {
        axios
            .get('api/users')
            .then(response => {
                this.users = response.data;
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            });
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