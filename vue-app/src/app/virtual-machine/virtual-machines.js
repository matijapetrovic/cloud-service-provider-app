Vue.component("virtual-machines-page", {
    template: `
        <base-layout :page-title="$route.meta.title">
            <div class="row">
                <button
                    v-if="!$root.isDefaultUser"
                    @click="prepareAdd"
                    
                    type="button"
                    class="btn btn-outline-primary"
                    
                    data-toggle="modal"
                    :data-target="'#' + addModalId"
                    style="margin: 15px 0;"
                >
                    Add virtual machine
                </button>
            </div>
            
            <div class="row">
                <vm-table
                    @viewVirtualMachine="viewVirtualMachine($event)"
                    :view-modal-id="viewModalId"
                    ref="table"
                >
                </vm-table>

                <vm-filter-sidebar
                    @search="searchVirtualMachines($event)"
                >
                </vm-filter-sidebar>
            </div>
            
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
                    :disableOrganizationSelect="true"
                    :activityList="true"
                    :toggleButton="true"
                    @submit="updateVirtualMachine($event)"
                    @delete="deleteVirtualMachine($event)"
                    @toggled="toggleVirtualMachine($event)"
                    ref="viewForm"
                >
                </vm-form>
            </full-modal>
        </base-layout>
    `,

    data: function() {
        return {
            addModalId: 'addVirtualMachineModal',
            viewModalId: 'viewVirtualMachineModal',
            searchText: ""
        }
    },
    methods: {
        addVirtualMachine(virtualMachine) {
            axios
                .post('/api/virtualmachines', 
                {
                    "name": virtualMachine.name,
                    "category": virtualMachine.category,
                    "drives": virtualMachine.drives instanceof Array ? virtualMachine.drives : [ virtualMachine.drives ],
                    "organization": virtualMachine.organization,
                    "turnedOn": false,
                    "activity": []
                })
                .then(response => {
                    this.$refs.table.getVirtualMachines("");
                    alert('Adding virtual machine successful');
                    this.closeAddModal();
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        },
        updateVirtualMachine(virtualMachine) {
            axios
                .put('/api/virtualmachines/' + virtualMachine.oldName, 
                {
                    "name": virtualMachine.name,
                    "category": virtualMachine.category,
                    "drives": virtualMachine.drives instanceof Array ? virtualMachine.drives : [ virtualMachine.drives ],
                    "organization": virtualMachine.organization,
                    "activity": virtualMachine.activity
                })
                .then(response => {
                    this.$refs.table.getVirtualMachines("");
                    alert('Updating virtual machine successful');
                    this.closeViewModal();
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        },
        deleteVirtualMachine(virtualMachine) {
            axios
                .delete('/api/virtualmachines/' + virtualMachine.name)
                .then(response => {
                    this.$refs.table.getVirtualMachines("");
                    alert('Deleting virtual machine successful');
                    this.closeViewModal();
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        },
        toggleVirtualMachine(virtualMachine) {
            axios
                .post('/api/virtualmachines/toggle/' + virtualMachine.name)
                .then(response => {
                    this.$refs.table.getVirtualMachines("");
                    this.$refs.viewForm.getVirtualMachine(virtualMachine.name);
                    alert('Toggling virtual machine successful');
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        },
        removeViewValidation() {
            this.$refs.viewForm.$refs.form.removeValidation();
        },
        removeAddValidation () {
            this.$refs.addForm.$refs.form.removeValidation();
            this.$refs.addForm.resetVirtualMachine();
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
        },
        searchVirtualMachines(searchString) {
            this.$refs.table.getVirtualMachines(searchString);
        }
    }
});