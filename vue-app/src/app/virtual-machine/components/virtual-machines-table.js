Vue.component("vm-table", {
    template: `
        <table border="1" class="table col-md-8">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>CPUs</th>
                    <th>RAM</th>
                    <th>GPUs</th>
                    <th v-if="$root.isSuperAdmin">Organization</th>
                    <th>Turned on</th>
                </tr>
            </thead>

            <tr v-for="virtualMachine in virtualMachines">
                <td>
                    <a 
                        href="#" 
                        @click.prevent="viewVirtualMachine(virtualMachine.name)"
                        data-toggle="modal"
                        v-bind:data-target="'#' + viewModalId"
                    >
                        {{ virtualMachine.name }}
                    </a>
                </td>
                <td>{{ virtualMachine.category.cpus }}</td>
                <td>{{ virtualMachine.category.ram }}</td>
                <td>{{ virtualMachine.category.gpus }}</td>
                <td v-if="$root.isSuperAdmin">{{ virtualMachine.organization}}</td>
                <td>{{ virtualMachine.turnedOn ? 'Yes' : 'No' }}</td>
            </tr>
        </table>
    `,
    props: {
        viewModalId: String
    },
    data : function() {
        return {
            virtualMachines: []
        }
    },
    mounted() {
        this.getVirtualMachines("");
    },
    methods: {
        getVirtualMachines(searchString) {
            axios
            .get('api/virtualmachines' + searchString)
            .then(response => {
                this.virtualMachines = response.data;
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            });
        },
        addVirtualMachine(vm) {
            this.virtualMachines.push(vm);
        },
        updateVirtualMachine(vm) {
            var el = this.virtualMachines.find(function(element) {
                return element.name === vm.name;
            });
            var idx = this.virtualMachines.indexOf(el);
            this.virtualMachines.splice(idx, 1);
            this.virtualMachines.splice(idx, 0, vm);
        },
        deleteVirtualMachine(vm) {
            var el = this.virtualMachines.find(function(element) {
                return element.name === vm.name;
            });
            var idx = this.virtualMachines.indexOf(el);
            this.virtualMachines.splice(idx, 1);
        },
        viewVirtualMachine(name) {
            this.$emit('viewVirtualMachine', name)
        }
    }
})