Vue.component("all-users-table",{

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
            <tr v-for="user in users">
                <td><a href="#" @click.prevent="viewUser(user.email)" data-toggle="modal" v-bind:data-target="'#' + viewModalId">{{ user.email }}</a></td>
                <td>{{ user.name }}</td>
                <td>{{ user.surname }}</td>
                <td>{{ user.organization }}</td>
            </tr>
        </table>
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
                this.users = response.data
            })
    },
    methods: {
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

    
})