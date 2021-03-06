Vue.component('select-vms',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                Virtual machines
            </label>  
            <div> 
                <select 
                class="selectpicker" 
                v-bind:value="value"
                v-on:input="$emit('input', $event.target.value)"
                required="required"
                >
                    <option disabled selected value="">Please select one</option>
                    <option v-for="vm in vms" :value="JSON.stringify(vm)">{{vm}}</option>
                </select>   
                <div class="invalid-feedback">
                    Please provide a valid Virtual machine
                </div>
            </div>
        </div>
    `,
    props: {
        value : '',
        required: {
            type: Boolean,
            default: false
        }
    },
    data : function () {
        return {
            vms : null,
            name: "Virtual machines" 
        }
    },
    mounted () {
        axios
            .get('api/virtualmachines')
            .then(response => {
                this.vms = response.data;
            })
            .catch(err => {
                const status = err.response.status;
                const msg = err.response.data;
                alert('' + status + ': ' +  msg);
            })
    },
})