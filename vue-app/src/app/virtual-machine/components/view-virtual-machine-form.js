Vue.component("view-vm-form", {
    template: `
    <main-form 
        id="viewVirtualMachineForm"
        method="POST"
        headerText="Virtual machine info"
        buttonText="Update"
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
            v-model="virtualMachine.category.name"
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
                category: { name: ""},
                drives: [],
                organization: null
            },
            categories: [],
            drives: [],
            organizations: []
        }
    },
    methods: {
        getVirtualMachine: function(name) {
            axios
                .get('/api/virtualmachines/' + name)
                .then(response => {
                    this.virtualMachine = response.data;
                    this.getCategories();
                    this.getDrives();
                    this.getOrganizations();
                });
        },
        getCategories: function() {
            axios
            .get('/api/categories')
            .then(response => {
                this.categories = response.data.map(cat => cat.name);
            });
        },
        getDrives: function() {
            axios
            .get('api/drives?organization=' + this.virtualMachine.organization)
            .then(response => {
                this.drives = response.data.map(drive => drive.name);
            });
        },
        getOrganizations: function() {
            axios
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data.map(organization => organization.name);
            });
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedVirtualMachine', this.virtualMachine);
                alert('Updating virtual machine successful');
                this.$emit('submit')
            }
            // useless
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .put('/api/virtualmachines/update/' + this.virtualMachine.name, 
                {
                    "name": this.virtualMachine.name,
                    "category": this.virtualMachine.category,
                    "drives": this.virtualMachine.drives instanceof Array ? this.virtualMachine.drives : [ this.virtualMachine.drives ],
                    "organization": this.virtualMachine.organization
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});