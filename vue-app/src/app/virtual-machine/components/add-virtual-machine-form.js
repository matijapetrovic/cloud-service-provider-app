Vue.component("add-vm-form", {
    template: `
    <main-form 
        id="addVirtualMachineForm"
        method="POST"
        headerText="Virtual machine info"
        buttonText="Add"
        v-on:submit="submitForm($event)"
        ref="form"
    >
        <text-input
            name="name"
            v-model="virtualMachine.name"
            required
        >
            Name
        </text-input>

    </main-form>
    `,
    data: function() {
        return {
            virtualMachine: {
                name: null,
                category: null,
                drives: null
            }
        }
    },
    methods: {
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedVirtualMachine', this.virtualMachine);
                alert('Adding virtual machine successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .post('/api/virtualmachines/add', 
                {
                    "name": this.virtualMachine.name,
                    "category": this.category,
                    "drives": this.drives
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});