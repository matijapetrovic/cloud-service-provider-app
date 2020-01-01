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
            <email-input
                name="email"
                v-model="user.email"    
                required
            >
                Email
            </email-input>
            <password-input
                name="password"
                v-model="user.password"
                required
            >
                Password
            </password-input>
            <text-input 
                name="name"
                v-model="user.name"
                required
            >
                Name
            </text-input>
            <text-input
            name="surname"
            v-model="user.surname"
            required
            >
                Surname
            </text-input>
            
            <select-role
            v-model="user.role"
            required
            >
            </select-role>

            <select-organization
            v-model="user.organization"
            required
            >
            {{user.organization}}
            </select-organization>  
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
                role : null
            },
            organizations : null,
        }
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
                    "organization": JSON.parse(this.user.organization),
                    "role": this.user.role
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});