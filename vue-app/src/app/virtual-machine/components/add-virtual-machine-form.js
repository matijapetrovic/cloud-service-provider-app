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
        <select-input
            name="category"
            v-model="virtualMachine.category"
            v-bind:options="categories"
            required
        >
            Category
        </select-input>

        <select-input
            name="drives"
            v-model="virtualMachine.drives"
            v-bind:options="drives"
            multiple
        >
            Drives
        </select-input>
        <select-input
            name="organization"
            v-model="virtualMachine.organization"
            v-bind:options="organizations"
            required
        >
            Organization
        </select-input>
    </main-form>
    `,
    data: function() {
        return {
            virtualMachine: {
                name: null,
                category: null,
                drives: [],
                organization: null
            },
            categories: [],
            drives: [],
            organizations: []
        }
    },
    methods: {
        checkResponse: function(response) {
            if (response.status === 201) {
                this.$emit('addedVirtualMachine', this.virtualMachine);
                alert('Adding virtual machine successful');
                this.$emit('submit')
            }
            // useless
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .post('/api/virtualmachines/add', 
                {
                    "name": this.virtualMachine.name,
                    "category": {"name": this.virtualMachine.category },
                    "drives": this.virtualMachine.drives instanceof Array ? this.virtualMachine.drives : [ this.virtualMachine.drives ],
                    "organization": this.virtualMachine.organization
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    },
    mounted() {
        axios
        .get('/api/categories')
        .then(response => {
            this.categories = response.data.map(cat => cat.name);
        });
        axios
        .get('api/drives?organization=' + this.virtualMachine.organization)
        .then(response => {
            this.drives = response.data.map(drive => drive.name);
        });
        axios
        .get('api/organizations')
        .then(response => {
            this.organizations = response.data.map(organization => organization.name);
        });
    }
});