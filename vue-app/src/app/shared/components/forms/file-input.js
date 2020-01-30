Vue.component("file-input", {
    template: `
        <div class="form-group">
            <label v-bind:for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control-file"
                    type="file"
                    v-bind:name="name"
                    @change="handleFileChange"
                    v-bind:required="required"
                >
                <div class="invalid-feedback">
                    Please provide a valid {{ name }} file
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
        }
    },

    methods: {
        handleFileChange(e) {
            this.$emit('input', e.target.files[0]);
        }
    }
});