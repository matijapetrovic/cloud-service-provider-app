Vue.component("profile-info", {
    template: `
        <base-layout v-bind:page-title="$route.meta.title">
            <div>
               <nav class="navbar navbar-expand navbar-dark bg-secondary">
                <h4><b>Email:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <h4><b>{{user.email}}</b></h4>  
                </form>
                </nav>
            </div>
            <div>
               <nav class="navbar navbar-expand navbar-dark color:#fff">
                <h4><b>Password:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <h4><b>{{user.password}}</b></h4>  
                </form>
                </nav>
            </div>
            <div>
               <nav class="navbar navbar-expand navbar-dark bg-secondary">
                <h4><b>Name:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <div v-if="loaded">   
                    <h4><b>{{user.name}}</b></h4>  
                </div> 
                </form>
                </nav>
            </div>
            <div>
               <nav class="navbar navbar-expand navbar-dark color:#fff">
                <h4><b>Surname:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <h4><b>{{user.surname}}</b></h4>  
                </form>
                </nav>
            </div>
            <div>
               <nav class="navbar navbar-expand navbar-dark bg-secondary">
                <h4><b>Organization:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <div v-if="loaded">
                    <h4><b>{{user.organization.name}}</b></h4>  
                </div>
                </form>
                </nav>
            </div>
            <div>
               <nav class="navbar navbar-expand navbar-dark color:#fff">
                <h4><b>Role:</b></h4>
                <div class="collapse navbar-collapse" id="navBarForInfo">
                </div>
                <form class="form-inline my-2 my-md-0">
                <h4><b>{{user.role}}</b></h4>  
                </form>
                </nav>
            </div>
            <button
            
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + viewProfileId"
            style="margin: 15px 0;"
            >
                Change profile
            </button>

            <full-modal
            v-bind:modal-id="viewProfileId"
            modal-title="View profile"
            >
                <change-profile-form
                @submit="closeViewModal"
                @updatedUser="updateUser($event)" 
                ref="viewForm"
                >
                </change-profile-form>
            </full-modal>
            
        </base-layout>
    `,
    data : function () {
        return {
            viewProfileId : "viewUserModal",
            errors: [],
            user : {
                email: null,
                password : null,
                name : null,
                surname : null,
                organizaion : {name:null},
            },
            loaded: false

        }
    },
    mounted(){
        axios
        .get('api/users/currentUser')
        .then(response => {
            this.user = response.data; 
            this.loaded = true;    
        });
    },
    methods:{
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.viewProfileId).modal('hide');
        },
        removeViewValidation() {
            this.$refs.viewForm.$refs.form.removeValidation();
        },
        updateUser(user) {
            var el = this.users.find(function(element) {
                return element.email === users.email;
            });
            var idx = this.user.indexOf(el);    
            this.users.splice(idx, 1);
            this.users.splice(idx, 0, user);
        },
    }
});