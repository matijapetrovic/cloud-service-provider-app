Vue.component("main-form", {
    template: `
        <form
            :id="id"
            :method="method"
            @submit="submitForm"
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
                    :disabled="disableButtons"
                >
                    {{ buttonText }}
                </button>
                
                <button
                    v-show="this.activeDelete"
                    @click="submitDelete"
                    class="btn btn-outline-secondary pull-right"
                    type="submit"
                    :disabled="disableButtons"
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
        disableButtons: {
            type: Boolean,
            default: false
        }
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
            this.$emit('submitDelete');
        }
    }
});