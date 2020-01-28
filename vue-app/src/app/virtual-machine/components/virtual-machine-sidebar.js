Vue.component("vm-filter-sidebar", {
    template: `
        <aside class="col-md-4">
            <h5> Search and filter </h5>
            <text-input name="name" v-model="name">Search</text-input>
            <div class="filter-group">
                <number-input name="cpuFrom" v-model="cpuFrom">CPUs from</number-input>
                <number-input name="cpuTo" v-model="cpuTo">CPUs to</number-input>
            </div>
            <div class="filter-group">
                <number-input name="ramFrom" v-model="ramFrom">RAM from</number-input>
                <number-input name="ramTo" v-model="ramTo">RAM to</number-input>
            </div>
            <div class="filter-group">
                <number-input name="gpuFrom" v-model="gpuFrom">GPUs from</number-input>
                <number-input name="gpuTo" v-model="gpuTo">GPUs to</number-input>
            </div>

            <button
                class="btn btn-outline-primary"
                @click="emitSearch" 
            >
                Search
            </button>
            
            <button
                class="btn btn-danger"
                @click="resetSearch"
            >
                Reset
            </button>
        </aside>
    `,
    data: function() {
        return {
            name: null,
            cpuFrom: null,
            cpuTo: null,
            ramFrom: null,
            ramTo: null,
            gpuFrom: null,
            gpuTo: null
        }
    },
    methods: {
        emitSearch() {
            this.$emit('search', this.buildSearchString());
        },
        buildSearchString() {
            var searchString = '?';
            if (this.name)
                searchString += 'name=' + this.name;
            if (this.cpuFrom)
                searchString += '&cpuFrom=' + this.cpuFrom;
            if (this.cpuTo)
                searchString += '&cpuTo=' + this.cpuTo;
            if (this.ramFrom)
                searchString += '&ramFrom=' + this.ramFrom;
            if (this.ramTo)
                searchString += '&ramTo=' + this.ramTo;
            if (this.gpuFrom)
                searchString += '&gpuFrom=' + this.gpuFrom;
            if (this.gpuTo)
                searchString += '&gpuTo=' + this.gpuTo;
            return searchString;
        },
        resetSearch() {
            this.name = null;
            this.cpuFrom = null;
            this.cpuTo = null;
            this.ramFrom = null;
            this.ramTo = null;
            this.gpuFrom = null;
            this.gpuTo = null;
            this.$emit('search', "");
        }
    }
});