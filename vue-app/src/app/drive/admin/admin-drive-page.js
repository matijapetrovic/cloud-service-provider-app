Vue.component("admin-drive-page",{
    template: `
    <div>
        <button
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + addModalId"
            style="margin: 15px 0;"
        >
            Add drive
        </button>
        <drives-from-organization-table
            @viewDrive="viewDrive($event)"
            v-bind:view-modal-id="viewModalId"
            ref="table"
        >
        </drives-from-organization-table>

        <full-modal
            @close="removeViewValidation"
            v-bind:modal-id="viewModalId"
            modal-title="View drive"
        >
            <view-drive-form
                    @submit="closeViewModal"
                    @deletedDrive="deleteDrive"
                    @updatedDrive="updateDrive($event)"
                    ref="viewForm"
                >
                </view-drive-form>
        </full-modal>

        <full-modal
            @close="removeAddValidation"
            v-bind:modal-id="addModalId"
            modal-title="Add drive"
        >
            <add-drive-form
                    @submit="closeAddModal"
                    @addedDrive="addDrive($event)"
                    ref="addForm"
                >
                </add-drive-form>
        </full-modal>        
    </div>
`
,
data : function() {
    return {
        addModalId: 'addDriveModal',
        viewModalId: 'viewDriveModal'
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
    addDrive(drive) {
        this.$refs.table.addDrive(drive);
    },
    updateDrive(drive) {
        this.$refs.table.getAllDrives();
    },
    deleteDrive(){
            this.$refs.table.getAllDrives();
        },
    viewDrive(name) {
        this.$refs.viewForm.getDrive(name);
    }
}
});