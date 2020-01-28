Vue.component("virtual-machines-page", {
    template: `
        <base-layout :page-title="$route.meta.title">
            <button
                v-if="!$root.isDefaultUser"
                @click="prepareAdd"
                
                type="button"
                class="btn btn-outline-primary"
                
                data-toggle="modal"
                :data-target="'#' + addModalId"
            >
                Add virtual machine
            </button>

            <search-bar>
            </search-bar>
            
            <vm-table
                @viewVirtualMachine="viewVirtualMachine($event)"
                :view-modal-id="viewModalId"
                ref="table"
            >
            </vm-table>
            
            <full-modal
                @close="removeAddValidation"
                :modal-id="addModalId"
                modal-title="Add virtual machine"
            >
                <vm-form
                    id="addVirtualMachineForm"
                    headerText="Virtual machine info"
                    buttonText="Add"
                    @submit="addVirtualMachine($event)"
                    ref="addForm"
                >
                </vm-form>
            </full-modal>
            
            <full-modal
                @close="removeViewValidation"
                :modal-id="viewModalId"
                modal-title="View virtual machine"
            >
                <vm-form
                    id="viewVirtualMachineForm"
                    headerText="Virtual machine info"
                    buttonText="Update"
                    @submit="updateVirtualMachine($event)"
                    ref="viewForm"
                >
                </vm-form>
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
        addVirtualMachine(virtualMachine) {
            axios
                .post('/api/virtualmachines', 
                {
                    "name": virtualMachine.name,
                    "category": {"name": virtualMachine.category },
                    "drives": virtualMachine.drives instanceof Array ? this.virtualMachine.drives : [ this.virtualMachine.drives ],
                    "organization": virtualMachine.organization
                })
                .then(response => {
                    this.$refs.table.addVirtualMachine(response.data);
                    alert('Adding virtual machine successful');
                    this.closeAddModal();
                });
        },
        updateVirtualMachine(virtualMachine) {
            axios
                .put('/api/virtualmachines/' + virtualMachine.name, 
                {
                    "name": virtualMachine.name,
                    "category": virtualMachine.category,
                    "drives": virtualMachine.drives instanceof Array ? this.virtualMachine.drives : [ this.virtualMachine.drives ],
                    "organization": virtualMachine.organization
                })
                .then(response => {
                    this.$refs.table.updateVirtualMachine(response.data);
                    alert('Updating virtual machine successful');
                    this.closeViewModal();
                });
        },
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
        prepareAdd() {
            this.$refs.addForm.getSelectInfo();
        },
        viewVirtualMachine(name) {
            this.$refs.viewForm.getVirtualMachine(name);
        }
    }
});