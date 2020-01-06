Vue.component('select-role',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                Role
            </label>  
            <div> 
                <select
                class="selectpicker" 
                v-bind:value="value" 
                v-on:input="$emit('input', $event.target.value)"
                v-bind:required="required"
                >
                    <option>USER</option>
                    <option>ADMIN</option>
                </select>
                <div class="invalid-feedback">
                    Please provide a Role
                </div>
            </div>
        </div>
    `,
    props: {
        name: "Role",
        value: String,
        required: {
            type: Boolean,
            default: false
        }
    }
})