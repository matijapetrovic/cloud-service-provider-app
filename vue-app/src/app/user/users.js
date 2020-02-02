Vue.component('users-page',{
    template: `
        <base-layout :page-title="$route.meta.title">
            <div class="row">
                <button
                type="button"
                class="btn btn-outline-primary"
                data-toggle="modal"
                :data-target="'#' + addModalId"
                style="margin: 15px 0;"
                >
                    Add user
                </button>
            </div>          
            <all-users-table
                @viewUser="viewUser($event)"
                :view-modal-id="viewModalId"
                ref="table"
            >
            </all-users-table>

            <full-modal
            @close="removeViewValidation"
            :modal-id="viewModalId"
            modal-title="View user"
            >
                <view-user-form
                    @submit="closeViewModal"
                    @submitDelete="closeViewModal"
                    @updatedUser="updateUser($event)"
                    @deletedUser="deleteUser($event)"
                    ref="viewForm"
                    >
                    </view-user-form>
            </full-modal>

            <full-modal
                @close="removeAddValidation"
                :modal-id="addModalId"
                modal-title="Add user"
                >
                    <add-user-form
                        @submit="closeAddModal"
                        @addedUser="addUser($event)"
                        ref="addForm"
                        >
                        </add-user-form>
            </full-modal>
        </base-layout>
    `,
    data : function() {
        return {
            addModalId: 'addUserModal',
            viewModalId: 'viewUserModal'
        }
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
        updateUser(user) {
            this.$refs.table.updateUser(user);
        },
        deleteUser(user){
            this.$refs.table.deleteUser(user);
        },
        viewUser(email) {
            this.$refs.viewForm.getUser(email);
        }
    }
})