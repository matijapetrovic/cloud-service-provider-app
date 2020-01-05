Vue.component("main-form", {
    template: `
        <form
            v-bind:id="id"
            v-bind:method="method"
            v-on:submit="submitForm"
            v-on:submitDelete="submitDelete"
            class="main-form card"
            novalidate
            activeDelete
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
        
                <button
                    v-show="this.activeDelete"
                    v-on:click="submitDelete"
                    class="btn btn-outline-secondary pull-right"
                    type="submit"
                >
                    {{ buttonTextDelete }}
                </button>
            </div>
        </form>
    `,
    props: {
        id: String,
        method: String,
        headerText: String,
        buttonText: String,
        buttonTextDelete: String,
        activeDelete : Boolean,
    },
    methods: {
        removeValidation: function() {
            var form = document.getElementById(this.id);
            form.classList.remove('was-validated');
        },
        validateForm: function() {
            var form = document.getElementById(this.id);
            form.classList.add('was-validated');
            return form.checkValidity();
        },
        submitForm: function(e) {
            e.preventDefault();
            if (this.validateForm()) {
                this.$emit('submit');
            }
        },
        submitDelete: function(e) {
            e.preventDefault();
            if (this.validateForm()) {
                this.$emit('submitDelete');
            }
        }
    }
});