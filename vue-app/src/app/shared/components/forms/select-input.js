Vue.component('multiselect', window.VueMultiselect.default);

Vue.component('select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
               <slot></slot>
            </label>  
            <div class="input-group">
                <multiselect
                    v-model="selectedVal"
                    :name="name"
                    :options="options"
                    :searchable="false"
                    :close-on-select="true"
                    :allow-empty="required === false"
                    :show-labels="false"
                    :disabled="disabled===true"
                    :preselect-first="required === true"
                >
                </multiselect>
                <div class="invalid-feedback" :style="{display: display}">
                    Please provide a valid {{ name }}
                </div>
            </div>
        </div>
    `,
    data : function() {
        return {
            display: 'none'
        }
    },
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
        disabled: {
            type: Boolean,
            default: false
        }
    },
    computed: {
        selectedVal: {
            get() {
                return this.value;
            },
            set(val) {
                this.$emit('input', val);
            }
        }
    },
    methods: {
        validate() {
            this.display = 'block';
        }
    }
});