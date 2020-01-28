Vue.component("filter-drive-form", {
    template: `
        <main-form 
            id="filterFormId"
            method="GET"
            headerText="Drive filter"
            buttonText="Apply"
            v-on:submit="submitForm($event)"
            ref="form"
        >
        <select-input
        name="type"
        v-model="type"
        v-bind:options="types"
        >
        Type
        </select-input>

        <number-input
        v-model="from">
        Capacity above
        </number-input>

        <number-input
        v-model="to"
        >
        Capacity below
        </number-input>

    </main-form>
    `,
    data : function () {
        return {        
           type: null,
           from: null,
           to: null,
           types: ["SSD", "HDD"]
        }
    },
    methods: {
        submitForm: function() {
            this.$emit('emitFilter',{'type': this.type, 'from': this.from,'to': this.to } );
            this.$emit('apply')
        }
    }
});