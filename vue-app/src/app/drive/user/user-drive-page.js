Vue.component("user-drive-page",{
    template: `
    <div>
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
                    @updatedDrive="updateDrive($event)"
                    ref="viewForm"
                    >
                    </view-drive-form>
            </full-modal>    
    </div>
    `,
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
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.viewModalId).modal('hide');
        },
        addDrive(drive) {
            this.$refs.table.addDrive(drive);
        },
        updateDrive(drive) {
            this.$refs.table.updateDrive(drive);
        },
        viewDrive(name) {
            this.$refs.viewForm.getDrive(name);
        }
    }
});