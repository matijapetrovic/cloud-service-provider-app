Vue.component("main-form", {
    template: `
        <form
            v-bind:id="id"
            v-bind:method="method"
            v-on:submit="submitForm"
            v-on:delete="submitDelete"
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
                    class="btn btn-outline-secondary pull-right"
                    type="delete"
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
        checkDisable: function(){
            if(this.activeDelete){
                return false;
            }
            return true;
        },
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