Vue.component("drives-from-organization-table", {
    template:`
    <div  v-if="loaded">
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Type</th>
                    <th>Capacity</th>
                    <th>Virtual Machine</th>
                </tr>
            </thead>
            <tr v-for="drive in drives">
                <td><a href="#" @click.prevent="viewDrive(drive.name)" data-toggle="modal" v-bind:data-target="'#' + viewModalId">{{ drive.name }}</a></td>
                <td>{{ drive.type }}</td>
                <td>{{ drive.capacity }}</td>
                <td>{{ drive.vm.name }}</td>
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
                drives: null,
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
                    this.loadDrives(this.currentUser.email);       
                });
            },
        methods: {
            loadDrives(email){
                axios
                .get('api/drives/organizations/' + email)
                .then(response =>{
                    this.drives = response.data;
                });
            },
            addDrive(drive) {
                this.drives.push(drive);
            },
            updateUser(drive) {
                var el = this.drives.find(function(element) {
                    return element.name === drives.name;
                });
                var idx = this.drive.indexOf(el);
                this.drives.splice(idx, 1);
                this.drives.splice(idx, 0, organization);
            },
            viewDrive(name) {
                this.$emit('viewDrive', name);
            }
       }

});