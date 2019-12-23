Vue.component("add-user-form", {
    template: `
        <main-form 
            id="addOrganizationForm"
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
                Emal
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
            user : {
                email: null,
                password: null,
                name: null,
                surname : null,
                organizaton : null,
            }
        }
    },
    methods: {
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedUser', this.user);
                alert('Adding user successful');
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
                    "organizaton": this.user.organizaton
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});