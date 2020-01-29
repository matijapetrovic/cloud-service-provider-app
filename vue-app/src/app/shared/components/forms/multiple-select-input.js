Vue.component('multiselect', window.VueMultiselect.default);

Vue.component('multiple-select-input',{
    template: `
        <div  class="form-group">   
            <label v-bind:for="name">
               <slot></slot>
            </label>  
            <div>
                <multiselect
                    v-model="selectedVal"
                    :options="options"
                    :searchable="false"
                    :allow-empty="required === false"
                    :multiple="true"
                    :close-on-select="false"
                    :clear-on-select="false"
                >
                </multiselect>

            </div>
        </div>
    `,
    props: {
        name: String,
        value: Array,
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
    }
});