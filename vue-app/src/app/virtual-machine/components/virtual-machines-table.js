Vue.component("vm-table", {
    template: `
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>CPUs</th>
                    <th>RAM</th>
                    <th>GPUs</th>
                    <th v-if="$root.isSuperAdmin">Organization</th>
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
            </tr>
        </table>
    `,
    props: {
        viewModalId: String
    },
    data : function() {
        return {
            virtualMachines: null
        }
    },
    mounted() {
        axios
            .get('api/virtualmachines')
            .then(response => {
                this.virtualMachines = response.data;
            });
    },
    methods: {
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
        viewVirtualMachine(name) {
            this.$emit('viewVirtualMachine', name)
        }
    }
})