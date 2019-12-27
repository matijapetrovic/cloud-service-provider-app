Vue.component("view-user-form", {
    template: `
    <main-form 
        id="viewUserForm"
        method="PUT"
        headerText="User info"
        buttonText="Update"
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
        <file-input
            name="password"
            v-model="user.password"
        >
            Password
        </file-input>
        <file-input
            name="confirm-password"
            v-model="user.password"
        >
            Confirm password
        </file-input>
        <text-input
            name="name"
            v-model="user.name"
        >
            Name
        </text-input>
        <file-input
            name="surname"
            v-model="user.surname"
        >
            Surname
        </file-input>
    </main-form>
        `,
    data : function () {
        return {
            errors: [],
            user : {
                name : null,
                surname: null,
                email  : null,
                password : null,
            }
        }
    },
    methods: {
        getUser: function(name) {
            axios
                .get('/api/users/' + user.name)
                .then(response => {
                    this.organization = response.data
                });
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('updatedUser', this.organization);
                alert('Updating user successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function(e) {
            axios
                .put('/api/users/update/' + this.user.name, 
                {
                    "email": this.user.email,
                    "password": this.user.password,
                    "name": this.user.name,
                    "users":
                })
                .then(response => {
                    this.checkResponse(response);   
                });
        }
    }
});