Vue.component("add-category-form", {
    template: `
        <main-form 
            id="addCategoryForm"
            method="POST"
            headerText="Category info"
            buttonText="Add"
            v-on:submit="submitForm($event)"
            ref="form"
        >
            <text-input
                name="name"
                v-model="category.name"    
                required
            >
                Name
            </text-input>
            <number-input
                name="CPUs"
                v-model="category.cpus"
                required
            >
                CPUs
            </number-input>
            <number-input 
                name="RAM"
                v-model=category.ram
                required
            >
                RAM
            </number-input>
            <number-input 
                name="GPUs"
                v-model=category.gpus
                required
            >
                GPUs
            </number-input>
            
        </main-form>
    `,
    data : function () {
        return {        
            category : {
                name : null,
                cpus: null,
                ram: null,
                gpus: null,
            }
        }
    },
    methods: {
        
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedCategory', this.category);
                alert('Adding category successful');
                this.$emit('submit');
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function() {
            axios
                .post('/api/categories/add', 
                {
                    "name": this.category.name,
                    "cpus": this.category.cpus,
                    "ram": this.category.ram,
                    "gpus": this.category.gpus,
                })
                .then(response => {
                    this.checkResponse(response);
                });
        }
    }
});