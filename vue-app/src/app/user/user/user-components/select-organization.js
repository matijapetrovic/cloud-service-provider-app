Vue.component('select-organization',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
                Organizations
            </label>  
            <div> 
                <select 
                v-model="orgVal"
                required="required"
                >
                    <option disabled value="">Please select one</option>
                    <option v-for="item in organizations" :value="item">{{item.name}}</option>
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
            organizations : null,
            orgVal: null
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