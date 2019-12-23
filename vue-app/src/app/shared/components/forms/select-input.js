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
                v-bind:options=options
                v-on:input="$emit('input', $event.target.value)"
                    >
                    <option disabled value="">Please select one</option>
                    <div v-for="opt in options">
                        <option><slot></slot></option>
                    </div>
                </select>
            </div>
        </div>
    `,
    data : function () {
        return {
            name: String,
            selected: null,
            options: [],
            required: {
                type: Boolean,
                default: false
            },
            
        }
    },



})