Vue.component('multiselect', window.VueMultiselect.default);

Vue.component('select-input',{
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
                    :close-on-select="true"
                    :allow-empty="required === false"
                    :show-labels="false"
                    :disabled="disabled===true"
                >
                </multiselect>

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