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
            
        </main-form>
    `,
    data : function () {
        return {        
            drive : {
                name : null,
                type: null,
                capacity: null,
                vm: null,
            },
            organizations : null,
            vms: [],
            types: ["SSD", "HDD"]
        }
    },
    mounted () {
        this.loadVMs();
    },
    methods: {
        loadVMs: function(){
            axios
            .get('api/virtualmachines')
            .then(response => {
                this.vms = response.data.map(vm => vm.name);
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
                    "vm": {"name": this.drive.vm},
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});