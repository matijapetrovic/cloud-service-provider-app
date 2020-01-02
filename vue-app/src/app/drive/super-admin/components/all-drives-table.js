Vue.component("all-drives-table",{
    template:`
    <div >
        <table border="1" class="table">
            <thead class="thead-dark">
                <tr>
                    <th>Name</th>
                    <th>Capacity</th>
                    <th>Virtual Machine</th>
                </tr>
            </thead>
            <tr v-for="drive in drives">
                <td><a href="#" @click.prevent="viewDrive(drive.name )" data-toggle="modal" v-bind:data-target="'#' + viewModalId">{{ drive.name }}</a></td>
                <td>{{ drive.capacity }}</td>
                <td>{{ drive.vm }}</td>
            </tr>
        </table>
    </div>
    `,
    props : {
        viewModalId : String
        
    },
    data: function(){
        return {
            drives: null,
        }
    },
    mounted () {
        axios
            .get('api/drives')
            .then(response => {
                this.drives = response.data;
            }),
        axios
    },
    methods: {
        addDrive(drive) {
            this.drives.push(drive);
        },
        updateDrive(drive) {
            var el = this.drives.find(function(element) {
                return element.email === drive.email;
            });
            var idx = this.drives.indexOf(el);
            this.drives.splice(idx, 1);
            this.drives.splice(idx, 0, drive);
        },
        viewDrive(email) {
            this.$emit('viewDrive', email);
        },
        deleteDrive(drive){
            var el = this.drives.find(function(element) {
                return element.email === drive.email;
            });
            var idx = this.drives.indexOf(el);
            this.drives.splice(idx, 1);
        }
    }
})