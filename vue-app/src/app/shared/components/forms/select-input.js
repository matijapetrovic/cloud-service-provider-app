Vue.component('select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
               <slot></slot>
            </label>  
            <div> 
                <select
                    class="selectpicker"
                    :name="name"
                    :required="required"
                    :multiple="multiple"
                >
                    <option v-if="required" disabled value="">Please select one</option>
                    <option v-for="item in options" :value="item">{{item}}</option>
                </select>
            </div>
        </div>
    `,
    props: {
        name: String,
        value: '',
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