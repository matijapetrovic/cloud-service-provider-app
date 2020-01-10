Vue.component("categories-from-organization-table", {
    template:`
    <div  v-if="loaded">
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>CPUs</th>
                    <th>RAM</th>
                    <th>GPUs</th>
                </tr>
            </thead>
            <tr v-for="category in categories">
                <td><a href="#" @click.prevent="viewCategory(category.name)" data-toggle="modal" v-bind:data-target="'#' + viewModalId">{{ category.name }}</a></td>
                <td>{{ category.cpus }}</td>
                <td>{{ category.ram }}</td>
                <td>{{ category.gpus }}</td>
            </tr>
        </table>
    </div>
        `
         ,
        props : {
            viewModalId : String
        },
        data: function(){
            return {
                categories: null,
                currentUser: null,
                loaded : false,
            }
        },
        mounted () {
            axios
                .get('api/users/currentUser')
                .then(response => {
                    this.currentUser = response.data
                    this.loaded = true;
                    if(this.currentUser.role !== 'SUPER_ADMIN'){
                        this.loadCategories(this.currentUser.email);  
                    }     
                });
            },
        methods: {
            loadCategories(email){
                axios
                .get('api/categories/organizations/' + email)
                .then(response =>{
                    this.categories = response.data;
                });
            },
            addCategory(category) {
                this.categories.push(category);
            },
            updateUser(category) {
                var el = this.categories.find(function(element) {
                    return element.name === categories.name;
                });
                var idx = this.category.indexOf(el);
                this.categories.splice(idx, 1);
                this.categories.splice(idx, 0, organization);
            },
            deleteCategory(category){
                var el = this.categories.find(function(element) {
                    return element.name === category.name;
                });
                var idx = this.categories.indexOf(el);
                this.categories.splice(idx, 1);
            },
            viewCategory(name) {
                this.$emit('viewCategory', name);
            }
            
       }

});