Vue.component("add-drive-form", {
    template: `
        <main-form 
            id="addDriveForm"
            method="POST"
            headerText="Drive info"
            buttonText="Add"
            v-on:submit="submitForm($event)"
            ref="form"
        >
            <text-input
                name="name"
                v-model="drive.name"    
                required
            >
                Name
            </text-input>
            <select-input
            name="type"
            v-model="drive.type"
            v-bind:options="types"
            required
            >
            Type
            </select-input>
            <number-input 
                name="Capacity"
                v-model=drive.capacity
                required
            >
            Capacity
            </number-input>
            
            <select-input
            name="virtualmachine"
            v-model="drive.vm"
            v-bind:options="vms"
            required
            >
            Virtual Machine
            </select-input>

            <select-input
            v-if="$root.isSuperAdmin"
            name="organization"
            v-model="drive.organization"
            v-bind:options="organizations"
            required
            >
            Organization
            </select-input>
            
        </main-form>
    `,
    data : function () {
        return {        
            drive : {
                name : null,
                type: null,
                capacity: null,
                vm: null,
                organization: null
            },
            organizations : [],
            vms: [],
            types: ["SSD", "HDD"]
        }
    },
    mounted () {
        this.loadVMs();
        if(this.$root.isSuperAdmin){
            this.loadOrganizations();
        }else {
            this.setUserOrganization();
        }
        
    },
    methods: {
        setUserOrganization: function(){
            axios
            .get('api/users/' + this.$root.currentUser.email)
            .then(response => {
                this.drive.organization = response.data.organization;
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            })
        },
        loadVMs: function(){
            axios
            .get('api/virtualmachines?organization='+ this.drive.organization)
            .then(response => {
                this.vms = response.data.map(vm => vm.name);
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            })
        },
        loadOrganizations: function(){
            axios
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data.map(organization => organization.name);
                this.drive.organization = this.organizations[0];
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            })
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedDrive', this.drive);
                alert('Adding drive successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .post('/api/drives/add', 
                {
                    "name": this.drive.name,
                    "type": this.drive.type,
                    "capacity": this.drive.capacity,
                    "vm":  this.drive.vm ,
                    "organization": this.drive.organization       
                })
                .then(response => {
                    this.checkResponse(response);
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        }        
    },
    watch: {
        'drive.organization': function (val, oldVal) {
            this.drive.vm = null;
            this.loadVMs();
        }
    }
});