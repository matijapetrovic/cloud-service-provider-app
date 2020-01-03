Vue.component('select-vms',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                {{this.name}}
            </label>  
            <div> 
                <select 
                class="selectpicker" 
                v-bind:value="value"
                v-on:input="$emit('input', $event.target.value)"
                required="required"
                >
                    <option v-for="vm in vms" :value="JSON.stringify(vm)">{{vm.name}}</option>
                </select>   
            </div>
        </div>
    `,
    data : function () {
        return {
            vms : null,
            name: "Virtual machines",
            value : '',
            required: {
                type: Boolean,
                default: false
            },   
        }
    },
    mounted () {
        axios
            .get('api/virtualmachines')
            .then(response => {
                this.vms = response.data;
            })
    },
})