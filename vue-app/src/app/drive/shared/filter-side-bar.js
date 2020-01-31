Vue.component("filter-side-bar", {
    template: `
    <div>
            <h5> Search and filter </h5>
            <text-input name="name" v-model="name">Search</text-input>
            <div class="filter-group">
                <number-input name="capacityFrom" v-model="capacityFrom">Capacity from</number-input>
                <number-input name="capacityTo" v-model="capacityTo">Capacity to</number-input>
            </div>
            <div class="filter-group">
                <select-input
                name="type"
                v-model="type"
                v-bind:options="types"
                >
                Type
                </select-input>
            </div>

            <button
                class="btn btn-outline-primary"
                @click="submitForm($event)" 
            >
                Search
            </button>
            
            <button
                class="btn btn-danger"
                @click="resetSearch"
            >
                Reset
            </button>
        </div>
    
    `,
    data : function () {
        return {        
           type: null,
           capacityFrom: null,
           capacityTo: null,
           name: null,
           types: ["SSD", "HDD"]
        }
    },
    methods: {
        submitForm: function() {
            this.$emit('emitFilter',{'name': this.name,'type': this.type, 'from': this.capacityFrom,'to': this.capacityTo } );
            this.$emit('apply')
        },
        resetForm: function(){
            this.type =  null,
            this.capacityFrom = null,
            this.capacityTo = null,
            this.name =  null
        },
        resetSearch: function() {
            this.resetForm();
            this.$emit('reset');
        }
    }
});