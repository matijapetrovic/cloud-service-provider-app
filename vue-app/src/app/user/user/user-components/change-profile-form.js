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
        <password-input
            name="password"
            v-model="this.p1"     
        >
            Password
        </password-input>
        <password-input
            name="role"
            v-model="this.p2"
        >
            Confirm password
        </password-input>
    </main-form>
    
    `,
    data: function(){
        return {
            errors: [],
            user : {
                email : null,
                password : null,
                name : null,
                surname : null,
                role : null,
                oganization : null
            },
            email : null,
            p1 : null,
            p2 : null
        }
    },
    mounted(){
        axios
        .get('api/users/currentUser')
        .then(response =>(
            this.user = response.data,
            this.p1 = this.user.password,
            this.p2 = this.user.password,
            this.email = this.user.email
        ))  
    },
methods:{
    checkPasswordConfirmation: function(){
        if(this.p1 === this.p2){
            return true;
        }
        if (!this.p1 && this.p2) {
            this.errors.push("Password is empty!");
            }
        else if(!this.p2 && this.p1){
            this.errors.push("Password is not confirmed!"); 
        }
        else if(this.p1 !== this.p2){
            this.errors.push("Your password and confirmation password do not match.");
        }

        e.preventDefault();
        return false;
    },
    checkResponse: function(response) {
        console.log(this.p1);
        console.log(this.p2);
        if(this.checkPasswordConfirmation){
            if (response.status === 200) {
                this.$emit('updatedUser', this.user);
                alert('Updating user successful');
                this.$emit('submit')
            }
            else {  
                alert('Error: ' + response.data);
            }
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