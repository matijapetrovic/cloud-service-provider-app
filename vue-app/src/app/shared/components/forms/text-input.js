Vue.component("text-input", {
    template: `
        <div class="form-group">
            <label v-bind:for="name">
                <slot></slot>
            </label>
            <div class="input-group">
                <input
                    class="form-control"
                    type="text"
                    v-bind:name="name"
                    v-bind:value="value"
                    v-on:input="$emit('input', $event.target.value)"
                >
            </div>
        </div>
    `,

    props: {
        name: String,
        value: String
    }
});