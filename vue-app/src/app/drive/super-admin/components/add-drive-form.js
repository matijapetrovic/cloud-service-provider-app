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
            <text-input
                name="type"
                v-model="drive.type"
                required
            >
                Type
            </text-input>
            <number-input 
                name="Capacity"
                v-model=drive.capacity
                required
            >
            </number-input>
            
            <select-vms
            v-model="drive.vm"
            required
            >
            </select-vms>
            
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
        }
    },
    methods: {
        
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
                    "vm": JSON.parse(this.drive.vm),
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});