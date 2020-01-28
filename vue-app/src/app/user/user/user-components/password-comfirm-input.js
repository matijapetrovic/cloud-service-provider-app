Vue.component('password-confirm-input', {
    template: `
        <main-form 
            id="viewUserForm"
            method="PUT"
            headerText="Change password"
            buttonText="Change password"
            v-on:submit="submitForm($event)"
            ref="form"
        >
            <text-input
                name="password"
                v-model="password"
                required
            >
                Password
            </text-input>
            <text-input
                name="confirm"
                v-model="confirm"
                required
            >
                Confirm password
            </text-input>
        </main-form>
    `
    ,
    mounted(){
        axios
        .get('api/users/' + this.$root.currentUser.email)
        .then(response => {
            this.user = response.data;
        });
    },
    data() {
        return {
            user : {
                email: null,
                password : null,
                name : null,
                surname : null,
                organizaion : {
                    name: null
                },
            },
            errors: [],
            password: null,
            confirm: null
        }
    },
    props: {
        value: null,
        required: {
            type: Boolean,
            default: false
        }
    },
    methods: {
        passwordConfirmed: function(){
           if(this.password !== this.confirm){
               return false;
           }
           return true;
        },
        submitForm: function(e) {
            if(this.password !== this.confirm){
                alert("Password is not confirmed!");
            }else{
                this.user.password = this.password;
                axios
                .put('/api/users/update/' + this.user.email, 
                {
                    "email": this.user.email,
                    "password": this.user.password,
                    "name": this.user.name,
                    "surname": this.user.surname,
                    "role" : this.user.role,
                    "organization" : this.user.organization
                })
                .then(response => {
                    this.checkResponse(response);
                });
            }  
        },
        checkResponse: function (response) {
            if (response.status === 200) {
                this.$emit('updatedUser', this.user);
                alert('Updating user successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
            return false;
        }
    },
    

})