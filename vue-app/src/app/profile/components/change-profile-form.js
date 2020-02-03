Vue.component('change-profile-form',{
    template: `
    <main-form 
        id="changeProfileForm"
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

        <div class="input-group">
            <text-input
            name="surname"
            v-model="user.password"
            required
            disabled
            >
                Password
            </text-input>
            <button class="btn btn-light h-25" 
                type="button"
                data-toggle="modal"
                v-bind:data-target="'#' + changePasswordModalId"
                style="margin: 29px 0;"
            >
                Change password
            </button>
        </div>

        <full-modal
            @close="removeViewValidation"
            v-bind:modal-id="changePasswordModalId"
            modal-title="Change password"
            
            >
                <password-confirm-input
                    @submit="passwordChanged"
                    @updatedUser="updateUser($event)"
                    ref="viewForm"
                >
                </password-confirm-input>
            </full-modal>
    
    </main-form>
    
    `,
    data: function(){
        return {
            changePasswordModalId: 'changePasswordModalId',
            errors: [],
            user : {
                email : null,
                password : null,
                name : null,
                surname : null,
                role : null,
                organization : null
            }
        }
    },
    mounted(){
        axios
        .get('api/users/' + this.$root.currentUser.email)
        .then(response =>(
            this.user = response.data
        ))
        .catch(err => {
            const status = err.response.status;
            const msg = err.response.data;
            alert('' + status + ': ' +  msg);
        })
    },
    methods:{
        updateUser(user) {
            this.user = user;
        },
        passwordChanged() {
            this.$emit("updatedUser", this.user);
            this.closeViewModal();
        },
        removeViewValidation() {
            this.$refs.viewForm.$refs.form.removeValidation();
        },
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.changePasswordModalId).modal('hide');
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
        setCurrentUser : function() {
            axios
                .post('/api/login', 
                {
                    email: this.user.email,
                    password: this.user.password
                })
                .then(response => {
                    const token = response.data.token;
                    // cuvamo u local storageu da bismo pristupili iz bilo koje komponente
                    // i da bi ostalo i kad se refreshuje
                    localStorage.setItem('user-token', token);
                    localStorage.setItem('user', JSON.stringify(response.data.user));
                    // stavljamo po defaultu u header da bi axios automatski
                    // slao zahteve sa nasim tokenom
                    axios.defaults.headers.common['Authorization'] = token;
                    this.$root.updateCurrentUser();
                    this.$router.push('/profile');
                })
                .catch(err => {
                    localStorage.removeItem('user-token');
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                })
        
        },
        submitForm: function(e) {
            axios
                .put('/api/users/update/' + this.$root.currentUser.email, 
                {
                    "email": this.user.email,
                    "password": this.user.password,
                    "name": this.user.name,
                    "surname": this.user.surname,
                    "organization": this.user.organization,
                    "role" : this.user.role
                })
                .then(response => {
                    this.checkResponse(response);
                    this.setCurrentUser();
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
            }
        },
})