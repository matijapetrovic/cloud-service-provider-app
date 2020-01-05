Vue.component('select-organization',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                Organizations
            </label>  
            <div> 
                <select 
                class="selectpicker" 
                v-bind:value="value"
                v-on:input="$emit('input', $event.target.value)"
                required="required"
                >
                    <option v-for="item in organizations" :value="JSON.stringify(item)">{{item.name}}</option>
                </select>   
            </div>
        </div>
    `,
    props: {
        name: "Organizations",
            value : '',
            required: {
                type: Boolean,
                default: false
            }, 
    },
    data : function () {
        return {
            organizations : null,     
        }
    },
    mounted () {
        axios
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data;
            })
    },
})