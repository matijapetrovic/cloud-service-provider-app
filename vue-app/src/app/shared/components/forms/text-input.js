Vue.component("text-input", {
    template: `
        <div class="form-group">
            <label :for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    type="text"
                    :name="name"
                    :value="value"
                    :required="required"
                    :disabled="disabled"
                    @input="$emit('input', $event.target.value)"
                >
                <div class="invalid-feedback">
                    Please provide a valid {{ name }}
                </div>
            </div>
        </div>
    `,

    props: {
        name: String,
        value: String,
        required: {
            type: Boolean,
            default: false
        },
        disabled: {
            type: Boolean,
            default: false
        }
    }
});