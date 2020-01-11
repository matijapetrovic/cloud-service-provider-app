Vue.component('select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
               <slot></slot>
            </label>  
            <div> 
                <select
                    :name="name"
                    :required="required"
                    :multiple="multiple"
                    @input="$emit('input', $event.target.value)"
                >
                    <option v-if="required" disabled value="" selected>Please select one</option>
                    <option v-for="item in options" :value="item">{{item}}</option>
                </select>
            </div>
        </div>
    `,
    props: {
        name: String,
        value: String,
        options: {
            type: Array
        },
        required: {
            type: Boolean,
            default: false
        },
        multiple: {
            type: Boolean,
            default: false
        }
    }
})