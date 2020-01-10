Vue.component("number-input", {
    template: `
        <div class="form-group">
            <label v-bind:for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    type="number"
                    min=0
                    v-bind:name="name"
                    v-bind:value="value"
                    v-on:input="$emit('input', Number($event.target.value))"
                    v-bind:required="required"
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
        }
    }
});