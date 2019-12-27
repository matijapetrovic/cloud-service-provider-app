Vue.component("virtual-machines-page", {
    template: `
        <base-layout v-bind:page-title="$route.meta.title">
            <button
                type="button"
                class="btn btn-outline-primary"
                data-toggle="modal"
                v-bind:data-target="'#' + addModalId"
                style="margin: 15px 0;"
            >
                Add virtual machine
            </button>
            <vm-table
                @viewVirtualMachine="viewVirtualMachine($event)"
                v-bind:view-modal-id="viewModalId"
                ref="table"
            >
            </vm-table>
            <full-modal
                @close="removeAddValidation"
                v-bind:modal-id="addModalId"
                modal-title="Add virtual machine"
            >
                <add-vm-form
                    @submit="closeAddModal"
                    @addedVirtualMachine="addVirtualMachine($event)"
                    ref="addForm"
                    >
                </add-vm-form>
            </full-modal>
            <full-modal
                @close="removeViewValidation"
                v-bind:modal-id="viewModalId"
                modal-title="View virtual machine"
            >
                <view-vm-form
                    @submit="closeViewModal"
                    @updateVirtualMachine="updateVirtualMachine($event)"
                    ref="viewForm"
                >
                </view-vm-form>
            </full-modal>
        </base-layout>
    `,

    data: function() {
        return {
            addModalId: 'addVirtualMachineModal',
            viewModalId: 'viewVirtualMachineModal'
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
        addOrganization(organization) {
            this.$refs.table.addOrganization(organization);
        },
        updateOrganization(organization) {
            this.$refs.table.updateOrganization(organization);
        },
        viewOrganization(name) {
            this.$refs.viewForm.getOrganization(name);
        }
    }
});