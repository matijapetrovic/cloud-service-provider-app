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
            style="margin: 15px 0;"
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
                    @submit="closeViewModal"
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
                oganization : null
            }
        }
    },
    mounted(){
        axios
        .get('api/users/' + this.$root.currentUser.email)
        .then(response =>(
            this.user = response.data
        ))  
    },
    methods:{
        updateUser(user) {
            this.user = user;
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
        submitForm: function(e) {
            axios
                .put('/api/users/update/' + this.email, 
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
                });
            }
        }
})