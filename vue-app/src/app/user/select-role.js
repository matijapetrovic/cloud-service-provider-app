Vue.component('select-role',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                <slot></slot>
            </label>  
            <div> 
                <select
                class="selectpicker"  
                v-on:input="$emit('input', $event.target.value)"
                    >
                    <option disabled value="">Please select one</option>
                    <div v-for="role in roles">
                        <option>role</option>
                    </div>
                </select>
            </div>
        </div>
    `,
    data : function () {
        return {
            name: String,
            key: String,
            roles: ["ADMIN","USER"],
            required: {
                type: Boolean,
                default: false
            },
            
        }
    },



})