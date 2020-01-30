Vue.component("view-drive-form", {
    template: `
        <main-form 
            id="viewDriveForm"
            method="PUT"
            headerText="Drive info"
            buttonText="Update"
            v-on:submit="submitForm($event)"

            activeDelete
            buttonTextDelete="Delete"
            @submitDelete="deleteDrive"
            ref="form"
        >
            <text-input
                name="name"
                v-model="drive.name"
                required
            >
                Name
            </text-input>
            <text-input
                name="type"
                v-model="drive.type"
            >
                Type
            </text-input>
            <number-input
                name="capacity"
                v-model="drive.capacity"
            >
                Capacity
            </number-input>
            <select-input
                v-if="$root.isSuperAdmin"
                name="organization"
                v-model="drive.organization"
                :options="organizations"
                required
            >
                Organization
            </select-input>
            <select-input
                name="virtualmachines"
                v-model="drive.vm"
                :options="vms"
                required
            >
                Virtual machines
            </select-input>

        </main-form>
    `,
    data : function () {
        return {
            drive : {
                name : null,
                type : null,
                capacity : null,
                vm : null,
                organization: null
            },
            organizations: [],
            vms: []
        }
    },
    mounted(){
        this.getVMS();
        if(this.$root.isSuperAdmin){
            this.getOrganization();

        }else {
            this.setDriveOrganization();
        }    
    },
    
    methods: {    
        setDriveOrganization: function(){
            axios
            .get('api/users/' + this.$root.currentUser.email)
            .then(response => {
                this.drive.organization = response.data.organization;
            }) 
        },
        getOrganization: function(){
            axios
                .get('/api/organizations')
                .then(response => {
                    this.organizations = response.data.map(organization => organization.name);
                });
        },
        getVMS: function(){
            axios
                .get('/api/virtualmachines')
                .then(response => {
                    this.vms = response.data.map(vm => vm.name);
                });
        },
        getDrive: function(name) {
            axios
                .get('/api/drives/' + name)
                .then(response => {
                    this.drive = response.data;
                });
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('updatedDrive', this.drive);
                alert('Updating drive successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        checkDeleteResponse: function(response) {
            if (response.status === 200) {
                this.$emit('deletedDrive', this.drive);
                alert('Drive with name '+ this.drive.name + ' successfully!');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function(e) {
            axios
                .put('/api/drives/update/' + this.drive.name, 
                {
                    "name": this.drive.name,
                    "type": this.drive.type,
                    "capacity": this.drive.capacity,
                    "vm":  this.drive.vm,
                    "organization": this.drive.organization
                })
                .then(response => {
                    this.checkResponse(response);
                });
        },
        deleteDrive(){
            axios
                .delete('api/drives/delete/' + this.drive.name)
                .then(response => {
                    this.checkDeleteResponse(response);
                });
        }
    }
});