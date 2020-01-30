Vue.component("number-input", {
    template: `
        <div class="form-group">
            <label :for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    type="number"
                    min=1
                    :name="name"
                    :value="value"
                    @input="$emit('input', Number($event.target.value))"
                    :required="required"
                    :disabled="disabled"
                    @keydown="validate"
                >
                <div class="invalid-feedback">
                    Please provide a valid {{ name }}
                </div>
            </div>
        </div>
    `,

    props: {
        name: String,
        value: Number,
        required: {
            type: Boolean,
            default: false
        },
        disabled : {
            type: Boolean,
            default: false
        }
    },
    methods: {
        validate(event) {
            if (event.keyCode == 189 || event.keyCode == 190) {
                event.preventDefault();
            }
        }
    }
});