Vue.component('select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
               <slot></slot>
            </label>  
            <div> 
                <select 
                v-model="orgVal"
                v-bind:options="options"
                required="required"
                >
                    <option disabled value="">Please select one</option>
                    <option v-for="item in options" :value="item">{{item.name}}</option>
                </select>   
            </div>
        </div>
    `,
    props: {
        name: String,
        value: '',
        required: {
            type: Boolean,
            default: false
        }
    },
    data : function () {
        return {
            options : null,
            orgVal: null
        }
    }
})