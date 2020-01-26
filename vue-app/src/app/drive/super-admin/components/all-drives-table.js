Vue.component("all-drives-table",{
    template:`
    <div  v-if="loaded" class="row">   
        <span class="row h-25"> 
        <search-bar
        class="col"
        v-model="parameter"
        @search="sendSearchRequest"
        >
        </search-bar>
        <div class="col-4"></div> 
        
        <span class="inline-block">
        <button
        class="btn btn-outline-primary col"
        data-toggle="modal"
        v-bind:data-target="'#' + modalId"
        >
            Filter
        </button>

        <button
        class="btn btn btn-danger col"
        @click="getAllDrives"
        >
        Reset
        </button>
        </span>

        </span>
        
        <full-modal
        @close="removeFilterValidation"
        v-bind:modal-id="modalId"
        modal-title="Filter"
        >
            <filter-drive-form
            @apply="closeFilterModal"
            @emitFilter=sendFilterRequest($event)    
            ref="filterForm"
            >
            </filter-drive-form>
        </full-modal>

        

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
    `,
    props : {
        viewModalId : String
        
    },
    data: function(){
        return {
            modalId: 'filterModalId',
            drives: null,
            parameter: '',
            type: '',
            capacityFrom: '',
            capacityTo: '',
            loaded: false
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
        removeFilterValidation() {
            this.$refs.filterForm.$refs.form.removeValidation();
        },
        closeFilterModal() {
            this.removeFilterValidation();
            $('#' + this.modalId).modal('hide');
        },
        sendFilterRequest(data){
            this.type = data.type;
            this.capacityFrom = data.from;
            this.capacityTo = data.to;
            axios
                .get('api/drives/filter?type='+ this.type +'&from='+ this.capacityFrom + '&to=' + this.capacityTo)
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