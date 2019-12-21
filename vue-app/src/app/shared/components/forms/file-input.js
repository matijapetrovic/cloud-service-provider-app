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
                >
            </div>
        </div>
    `,

    props: {
        name: String,
        value: File
    },

    methods: {
        handleFileChange(e) {
            this.$emit('input', e.target.files[0]);
        }
    }
});