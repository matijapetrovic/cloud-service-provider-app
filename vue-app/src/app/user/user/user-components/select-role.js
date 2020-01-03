Vue.component('select-role',{
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
                v-bind:required="required"
                >
                    <option disabled  value="">Please select one</option>
                    <option>USER</option>
                    <option>ADMIN</option>
                </select>
            </div>
        </div>
    `,
    data : function () {
        return {
            name: "Role",
            value : '',
            required: {
                type: Boolean,
                default: false
            },
            
        }
    }
})