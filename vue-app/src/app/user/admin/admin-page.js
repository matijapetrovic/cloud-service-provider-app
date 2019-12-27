Vue.component('admin-page',{

    template: `
        <div>    
            <users-from-organization-table
            @viewUSer="viewUser($event)"
            v-bind:view-modal-id="viewModalId"
            ref="table"
            >
            </users-from-organization-table>

            <button
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + addModalId"
            style="margin: 15px 0;"
            >
                Add user
            </button>

            <full-modal
            @close="removeViewValidation"
            v-bind:modal-id="viewModalId"
            modal-title="View user"
            >
                <view-user-form
                    @submit="closeViewModal"
                    @updatedOrganization="updateOrganization($event)"
                    ref="viewForm"
                    >
                    </view-user-form>
            </full-modal>

            <full-modal
            @close="removeAddValidation"
            v-bind:modal-id="addModalId"
            modal-title="Add user"
            >
                <add-user-form
                    @submit="   "
                    @addedUser="addUser($event)"
                    ref="addForm"
                    >
                    </add-user-form>
            </full-modal>
        </div>
        
    `,
    data : function() {
        return {
            addModalId: 'addUserModal',
            viewModalId: 'viewUserModal'
        }
    },
    mounted () {
        axios
            .get('api/users')
            .then(response => {
                this.users = response.data
            })
    },
    methods: {
        removeViewValidation() {
            this.$refs.viewForm.$refs.form.removeValidation();
        },
        removeAddValidation () {
            this.$refs.addForm.$refs.form.removeValidation();
        },
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.viewModalId).modal('hide');
        },
        closeAddModal() {
            this.removeAddValidation();
            $('#' + this.addModalId).modal('hide');
        },
        addUser(user) {
            this.$refs.table.addUser(user);
        },
        updateuser(user) {
            this.$refs.table.updateUser(user);
        },
        viewUser(email) {
            this.$refs.viewForm.getUser(email);
        }
    }

})