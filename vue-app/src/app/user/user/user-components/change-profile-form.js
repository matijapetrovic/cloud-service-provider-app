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
        }
    },
    mounted(){
        axios
        .get('api/users/currentUser')
        .then(response =>(
            this.user = response.data,
            this.email = this.user.email
        ))  
    },
methods:{
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