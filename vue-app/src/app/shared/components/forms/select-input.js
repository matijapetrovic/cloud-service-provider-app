Vue.component('select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                <slot></slot>
            </label>  
            <div> 
                <select
                class="selectpicker"  
                v-bind:name="name"
                v-bind:value="value"
                v-bind:options=options
                v-on:input="$emit('input', $event.target.value)"
                v-bind:required="required"
                    >
                    <option disabled value="">Please select one</option>
                    <div v-for="opt in options">
                        <option>opt.name</option>
                    </div>
                </select>
            </div>
        </div>
    `,
    data : function () {
        return {
            name: String,
            options: [],
            value : String,
            required: {
                type: Boolean,
                default: false
            },
            
        }
    },



})