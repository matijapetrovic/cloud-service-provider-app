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
                v-bind:key="key"
                v-bind:options=options
                v-on:input="$emit('input', $event.target.value)"
                    >
                    <option disabled value="">Please select one</option>
                    <div v-for="opt in options">
                        <option>opt.key</option>
                    </div>
                </select>
            </div>
        </div>
    `,
    data : function () {
        return {
            name: String,
            key: String,
            options: [],
            required: {
                type: Boolean,
                default: false
            },
            
        }
    },



})