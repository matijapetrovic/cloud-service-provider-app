Vue.component("profile-info", {
    template: `
        <main-form>
               <div>
                    <h3> <b>Email: </b> user.email </h3>
                </div>
                <div>
                    <h3><b> Password: </b> user.password</h3> 
                </div>
                <div>
                    <h3><b> Name: </b> user.name</h3> 
                </div>
                <div>
                <h3><b> Surname: </b> user.surname</h3>
                </div>
                <div>
                    <h3><b> Organization: </b> user.organization</h3>   
                </div>
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
                .get('/api/currentUser' + email)
                .then(response => {
                    this.user = response.data
                });
        },
    }
});