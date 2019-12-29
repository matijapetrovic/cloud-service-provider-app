Vue.component("profile-info", {
    template: `
        <base-layout v-bind:page-title="$route.meta.title">
               <div>
                    <h3> <b>Email: </b> {{user.email}} </h3>
                </div>
                <div>
                    <h3><b> Password: </b> {{user.password}}</h3> 
                </div>
                <div>
                    <h3><b> Name: </b> {{user.name}}</h3> 
                </div>
                <div>
                <h3><b> Surname: </b> {{user.surname}}</h3>
                </div>
                <div>
                    <h3><b> Organization: </b> {{user.organization}} </h3>   
                </div>
        </base-layout>
    `,
    data : function () {
        return {
            user : {
                email: null,
                password : null,
                name : null,
                surname : null,
                organizaion : null,
            },
        }
    },
    mounted(){
        axios
        .get('api/users/currentUser')
        .then(response => {
            this.user = {
                "email" : this.user.email,
                "password" : this.user.password,
                "name" : this.user.name,
                "surname" : this.user.surname,
                "organization" : this.user.organizaion,
            }
        });
    },
});