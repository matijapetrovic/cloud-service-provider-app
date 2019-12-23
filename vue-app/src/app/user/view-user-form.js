Vue.component("view-user-form", {
    template: `
        <main-form 
            id="viewUser"
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
            <text-input
                name="password"
                v-model="user.password"
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
        </main-form>
    `,
    data : function () {
        return {
            errors: [],
            user : {
                email: null,
                password : null,
                name : null,
                surname : null,
                organizaion : null,
            }
        }
    },
    
    methods: {
        getUser: function(email) {
            axios
                .get('/api/users/' + email)
                .then(response => {
                    this.user = response.data
                });
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('updatedUser', this.user);
                alert('Updating user successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function(e) {
            axios
                .put('/api/users/update/' + this.user.email, 
                {
                    "email": this.user.email,
                    "password": this.user.password,
                    "name": this.user.name,
                    "surname": this.user.surname,
                    "organizaion": null,
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});