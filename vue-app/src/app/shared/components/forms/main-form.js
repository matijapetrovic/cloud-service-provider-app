Vue.component("main-form", {
    template: `
        <form
            v-bind:id="id"
            v-bind:method="method"
            v-on:submit="submitForm"
            novalidate="true"
            class="main-form card"
        >
            <div class="card-header">
                <h5>{{ headerText }}</h5>
            </div>
            <div class="card-body">
                <slot></slot>
                <button
                    class="btn btn-outline-primary"
                    type="submit"
                >
                    {{ buttonText }}
                </button>
            </div>
        </form>
    `,
    props: {
        id: String,
        method: String,
        headerText: String,
        buttonText: String
    },
    methods: {
        submitForm: function(e) {
            e.preventDefault();
            this.$emit('submit');
        }
    }
});