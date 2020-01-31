Vue.component('select-type',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                Type
            </label>  
            <div> 
                <select
                class="selectpicker" 
                v-bind:value="value" 
                v-on:input="$emit('input', $event.target.value)"
                v-bind:required="required"
                >   
                    <option>SSD</option>
                    <option>HDD</option>
                </select>
                <div class="invalid-feedback">
                    Please provide a Role
                </div>
            </div>
        </div>
    `,
    props: {
        name: "Drive type",
        value: String,
        required: {
            type: Boolean,
            default: false
        }
    }
})