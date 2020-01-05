Vue.component("view-category-form", {
    template: `
        <main-form 
            id="viewCategoryForm"
            method="PUT"
            headerText="Category info"
            buttonText="Update"
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
                name="cpus"
                v-model="category.cpus"
            >
                CPUs
            </number-input>
            <number-input
            name="ram"
            v-model="category.ram"
            >
                RAM
            </number-input>
            <number-input
                name="gpus"
                v-model="category.gpus"
            >
                GPUs
            </number-input>

            <button
                class="btn btn-outline-secondary pull-right"
                id='deleteButton'
                @click="deleteCategory($event)"
                >
                    Delete
            </button>
        </main-form>
    `,
    data : function () {
        return {
            category : {
                name : null,
                cpus : null,
                ram : null,
                gpus : null,
            },
            id : null
        }
    },
    
    methods: {
        getCategory: function(name) {
            axios
                .get('/api/categories/' + name)
                .then(response => {
                    this.category = response.data;
                    this.id =  this.category.name;
                });
        },
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('updatedCategory', this.category);
                alert('Updating category successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        checkDeleteResponse: function(response) {
            if (response.status === 200) {
                this.$emit('updatedCategory', this.category);
                alert('Category with name '+ this.id + ' successfully!');
                this.$emit('submitDelete')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function(e) {
            axios
                .put('/api/categories/update/' + this.id, 
                {
                    "name": this.category.name,
                    "cpus": this.category.cpus,
                    "ram": this.category.ram,
                    "gpus": this.category.gpus,
                })
                .then(response => {
                    this.checkResponse(response);
                });
        },
        submitDelete(category){
            axios
                .delete('api/categories/delete/' + this.id)
                .then(response => {
                    this.checkDeleteResponse(response);
                });
        }
    }
});