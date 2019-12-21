Vue.component("main-form", {
    template: `
        <form
            v-bind:id="id"
            v-bind:method="method"
            v-on:submit="submitForm"
            class="main-form card"
            novalidate
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
        checkForm: function() {
            var form = document.getElementById(this.id);
            form.classList.add('was-validated');
            return form.checkValidity();
        },
        submitForm: function(e) {
            e.preventDefault();
            if (this.checkForm()) {
                this.$emit('submit');
            }
        }
    }
});