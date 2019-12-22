Vue.component('super-admin-page',{

    template: `
        <div>
            <button
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + addModalId"
            style="margin: 15px 0;"
            >
                Add user
            </button>
            <all-users-table
            @viewUSer="viewUser($event)"
            v-bind:view-modal-id="viewModalId"
            ref="table"
            >
            </all-users-table>
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
        viewUser(name) {
            this.$refs.viewForm.getUser(name);
        }
    }

})