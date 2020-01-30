Vue.component("vm-form", {
    template:`
    <main-form 
        :id="id"
        :headerText="headerText"
        :buttonText="buttonText"
        @submit="emitSubmit"

        activeDelete
        buttonTextDelete="Delete"
        @submitDelete="emitDelete"
        :disableButtons="$root.isDefaultUser"
        ref="form"
    >
        <text-input
            name="name"
            v-model="virtualMachine.name"
            :disabled="$root.isDefaultUser"
            required
        >
            Name
        </text-input>
        
        <select-input
            v-model="virtualMachine.category.name"
            :options="categories"
            :disabled="$root.isDefaultUser"
            name="category"
            required
        >
            Category
        </select-input>

        <multiple-select-input
            name="drives"
            v-model="virtualMachine.drives"
            :options="drives"
            :disabled="$root.isDefaultUser"
        >
            Drives
        </multiple-select-input>

        <select-input
            v-if="$root.isSuperAdmin"
            v-model="virtualMachine.organization"

            :options="organizations"
            :disabled="disableOrganizationSelect === true"
            name="organization"
            required
        >
            Organization
        </select-input>

        <vm-activity-list
            v-if="activityList"
            v-model="activityItem"
            :options="virtualMachine.activity"
        >
        </vm-activity-list>

        <switch-button
            v-if="toggleButton && !$root.isDefaultUser"
            v-model="virtualMachine.turnedOn"
            @toggle="emitToggled"
        >
            Turn {{ virtualMachine.turnedOn ? 'off' : 'on' }}
        </switch-button>
    </main-form>
    `,
    props: {
        id: String,
        headerText: String,
        buttonText: String,
        disableOrganizationSelect: {
            type: Boolean,
            default: false
        },
        toggleButton: {
            type: Boolean,
            default: false
        },
        activityList: {
            type: Boolean,
            default: false
        }
    },
    data: function() {
        return {
            virtualMachine: {
                name: null,
                category: { name: ""},
                drives: [],
                organization: null,
                turnedOn: false,
                activity: []
            },
            categories: [],
            drives: [],
            organizations: [],
            activityItem: null
        }
    },
    methods: {
        getVirtualMachine: function(name) {
            axios
                .get('/api/virtualmachines/' + name)
                .then(response => {
                    this.virtualMachine = response.data;
                    this.getSelectInfo();
                });
        },
        getSelectInfo: function() {
            this.getCategories();
            this.getDrives();
            if (this.$root.isSuperAdmin)
                this.getOrganizations();
            else
                this.virtualMachine.organization = this.$root.currentUser.organization;

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
                this.drives = this.drives.concat(this.virtualMachine.drives);
            });
        },
        getOrganizations: function() {
            axios
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data.map(organization => organization.name);
            });
        },
        emitSubmit() {
            this.$emit('submit', this.virtualMachine);
        },
        emitDelete() {
            this.$emit('delete', this.virtualMachine);
        },
        emitToggled() {
            this.$emit('toggled', this.virtualMachine);
        },
        resetVirtualMachine() {
            this.virtualMachine.name = null;
            this.virtualMachine.category = {name: ""};
            this.virtualMachine.drives = [];
            this.virtualMachine.organization = null;
            this.virtualMachine.turnedOn = false;
            this.virtualMachine.activity = [];
        }
    },
    watch: {
        'virtualMachine.organization': function (val, oldVal) {
            this.getDrives();
        }
    }
});