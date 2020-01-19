Vue.component("profile-info", {
    template: `
    <base-layout v-bind:page-title="$route.meta.title" class="profile">
        <div v-if="loaded">
        <div class="col-xs-12 col-sm-12 col-md-10 col-lg-10 col-xs-offset-0 col-sm-offset-0 col-md-offset-1 col-lg-offset-1">
        <div class="panel panel-primary">
            <div class="panel-heading">
                <h3 class="panel-title">User information</h3>
            </div>
            <div class="panel-body">
                <div class="row">
                    <div class="col-md-3 col-lg-3 hidden-xs hidden-sm">
                        <img class="img-circle" src="./assets/images/user.png" alt="User Pic">
                    </div>
                    <div class="col-xs-10 col-sm-10 hidden-md hidden-lg">
                        <strong></strong><br>
                        <dl>
                            <dt>Email</dt>
                            <dd>{{user.email}}</dd>
                            <dt>Password</dt>
                            <dd>{{user.password}}</dd>
                            <dt>Name</dt>
                            <dd>{{user.name}}</dd>
                            <dt>Surname</dt>
                            <dd>{{user.surname}}</dd>
                            <dt>Role</dt>
                            <dd>{{user.role}}</dd>
                            <div v-if="user.role!=='SUPER_ADMIN'">
                            <dt>Organization</dt>
                            <dd>{{user.organization.name}}</dd>
                            </div>
                        </dl>
                    </div>
                    <div v-if="(user.role!=='SUPER_ADMIN')">
                        <div class=" col-md-9 col-lg-9 hidden-xs hidden-sm">
                            <strong>Organization info</strong><br>
                            <table class="table table-user-information">
                                <tbody>
                                <tr>
                                    <th>Name</th>
                                    <th>Description</th>
                                    <th>Users number</th>
                                    <th>Virtual machines number</th>
                                    <th>Drives number</th>
                                </tr>
                                <tr>
                                    <td>{{user.organization.name}}</td>
                                    <td>{{user.organization.description}}</td>
                                    <td>{{user.organization.users.length}}</td>
                                    <td>{{user.organization.virtualMachines.length}}</td>
                                    <td>{{user.organization.drives.length}}</td>
                                </tr>
                                </tbody>
                            </table>
                        </div>
                    </div>
                </div>
            </div>
            <div class="panel-footer">
                <button class="btn btn-sm btn-primary" type="button" data-toggle="tooltip" data-original-title="Send message to user"><i class="glyphicon glyphicon-envelope"></i></button>
                <span class="pull-right">
                    <button class="btn btn-sm btn-warning" type="button" data-toggle="tooltip" data-original-title="Edit this user"><i class="glyphicon glyphicon-edit"></i></button>
                    <button class="btn btn-sm btn-danger" type="button" data-toggle="tooltip" data-original-title="Remove this user"><i class="glyphicon glyphicon-remove"></i></button>
                </span>
                </div>
            </div>
        </div>
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
                organizaion : {
                    name: null
                },
            },
            loaded: false,
            users : null
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
           this.user = user;
        }
    }
});