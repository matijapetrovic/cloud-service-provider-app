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
            <select-input
                v-if="$root.isSuperAdmin"
                name="organization"
                v-model="user.organization"
                :options="allOrganizations"
                ref="organizationInput"
                required
            >
            Organization
            </select-input>
            <select-input
                name="role"
                v-model="user.role"
                :options="roles"
                required
            >
            Role
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
                role : null
            },
            allOrganizations: [],
            roles: ["ADMIN", "USER"]
        }
    },
    mounted () { 
        if (this.$root.isAdmin){
            this.getCurrentUserOrganization();
        }else if (this.$root.isSuperAdmin) {
            this.getOrganizations();
        }
        
    },
    methods: {     
        getCurrentUserOrganization(){
            axios
                .get('api/users/'+ this.$root.currentUser.email)
                .then(response => {
                    this.user.organization = response.data.organization;
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                })
        },
        getOrganizations: function(){
                axios
                .get('api/organizations')
                .then(response => {
                    this.allOrganizations = response.data.map(organization => organization.name);
                    this.user.organization = this.allOrganizations[0];
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                })
        },
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
            if (!this.user.organization)
                this.$refs.organizationInput.validate();
            else
                axios
                    .post('/api/users/add', 
                    {
                        "email": this.user.email,
                        "password": this.user.password,
                        "name": this.user.name,
                        "surname": this.user.surname,
                        "organization": this.user.organization,
                        "role": this.user.role
                    })
                    .then(response => {
                        this.checkResponse(response);
                    })
                    .catch(err => {
                        const status = err.response.status;
                        const msg = err.response.data;
                        alert('' + status + ': ' +  msg);
                    });
        }
    }
});