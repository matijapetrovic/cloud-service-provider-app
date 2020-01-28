Vue.component("drives-from-organization-table", {
    template:`
    <div>
        <div class="container">
            <div class="row">   
                <div class="col-8">
                    <table border="1" class="table">
                        <thead class="thead-dark">
                            <tr>
                                <th>Name</th>
                                <th>Capacity</th>
                                <th>Type</th>
                                <th>Virtual Machine</th>
                            </tr>
                        </thead>
                        <tr v-for="drive in drives">
                            <td><a href="#" @click.prevent="viewDrive(drive.name)" data-toggle="modal" v-bind:data-target="'#' + viewModalId">{{ drive.name }}</a></td>
                            <td>{{ drive.capacity }}</td>
                            <td>{{ drive.type }}</td>
                            <td>{{ drive.vm }}</td>
                        </tr>
                    </table>
                </div>

                <aside class="col-md-4">
                    <filter-side-bar
                    @emitFilter=sendFilterRequest($event)
                    @reset="getAllDrives"    
                    ref="filterForm"
                    >
                    </filter-side-bar>
                </aside>
            </div>
        </div>
    </div>
    `,
    props : {
        viewModalId : String  
    },
    data: function(){
        return {
            loaded : null,
            drives : null,
            name : '',
            type: '',
            capacityFrom: '',
            capacityTo: ''
        }
    },
    mounted () {
        this.getAllDrives()
    },
    methods: {
        getAllDrives(){
            axios
            .get('api/drives')
            .then(response => {
                this.drives = response.data;
                this.loaded = true;
            })
        },
        sendFilterRequest(data){
            this.name = data.name;
            this.type = data.type;
            this.capacityFrom = data.from;
            this.capacityTo = data.to;
            axios
                .get('api/drives/filter?name=' + this.name +'&type='+ this.type +'&from='+ this.capacityFrom + '&to=' + this.capacityTo)
                .then(response => {
                    this.drives = response.data;
                })
        },
        sendSearchRequest(){
            axios
                .get('api/drives?name='+ this.parameter)
                .then(response => {
                    this.drives = response.data;
                })
        },
        addDrive(drive) {
            this.drives.push(drive);
        },
        updateDrive(drive) {
            var el = this.drives.find(function(element) {
                return element.name === drive.name;
            });
            var idx = this.drives.indexOf(el);
            this.drives.splice(idx, 1);
            this.drives.splice(idx, 0, drive);
        },
        viewDrive(name) {
            this.$emit('viewDrive', name);
        },
        deleteDrive(drive){
            var el = this.drives.find(function(element) {
                return element.name === drive.name;
            });
            var idx = this.drives.indexOf(el);
            this.drives.splice(idx, 1);
        }
    }
})