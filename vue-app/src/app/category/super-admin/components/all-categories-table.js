Vue.component("all-categories-table",{
    template:`
    <div  v-if="loadedAll">
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
    `,
    props : {
        viewModalId : String
        
    },
    data: function(){
        return {
            categories: null,
            loadedAll: false
        }
    },
    mounted () {
        axios
            .get('api/categories')
            .then(response => {
                this.categories = response.data;
                this.loadedAll = true;
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            })
    },
    methods: {
        addCategory(Category) {
            this.categories.push(Category);
        },
        updateCategory(Category) {
            var el = this.categories.find(function(element) {
                return element.name === Category.name;
            });
            var idx = this.categories.indexOf(el);
            this.categories.splice(idx, 1);
            this.categories.splice(idx, 0, Category);
        },
        viewCategory(name) {
            this.$emit('viewCategory', name);
        },
        deleteCategory(Category){
            var el = this.categories.find(function(element) {
                return element.name === Category.name;
            });
            var idx = this.categories.indexOf(el);
            this.categories.splice(idx, 1);
        }
    }
})