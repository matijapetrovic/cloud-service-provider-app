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
            <email-input
                name="name"
                v-model="drive.name"    
                required
            >
                Name
            </email-input>
            <password-input
                name="type"
                v-model="drive.type"
                required
            >
                Type
            </password-input>
            <text-input 
                name="capacity"
                v-model="drive.capacity"
                required
            >
                Capacity
            </text-input>
            <text-input
            name="vm"
            v-model="drive.vm"
            required
            >
                Virtual machine
                Moram imati neki select za vm
            </text-input>
            
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
                this.$emit('addedDrive', this.Drive);
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