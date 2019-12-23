Vue.component("add-user-form", {
    template: `
        <main-form 
            id="addUserForm"
            method="POST"
            headerText="User info"
            buttonText="Add"
            v-on:submit="submitForm($event)"
            ref="form"
        >
            <text-input
                name="email"
                v-model="user.email"    
                required
            >
                Email
            </text-input>
            <text-input
                name="password"
                v-model="user.password"
                required
            >
                Password
            </text-input>
            <text-input 
                name="name"
                v-model="user.name"
            >
                Name
            </text-input>
            <text-input
            name="surname"
            v-model="user.surname"
            >
                Surname
            </text-input>
            
            <select-input
            name="Organizations"
            options="organizations"
            required
            >
            Organizations
            </select-input>  
        </main-form>
    `,
    data : function () {
        return {
            user : {
                email: null,
                password: null,
                name: null,
                surname: null,
                organization: null,
            },
            organizations : null
        }
    },
    mounted () {
        axios  
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data
            })
    },
    methods: {
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedUser', this.User);
                alert('Adding User successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .post('/api/users/add', 
                {
                    "email": this.user.email,
                    "password": this.user.password,
                    "name": this.user.name,
                    "surname": this.user.surname,
                    "organization": this.user.organization
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});