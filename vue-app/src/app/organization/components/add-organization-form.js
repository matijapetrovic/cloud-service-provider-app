Vue.component("add-org-form", {
    template: `
    <form
        id="addOrganizationForm"
        @submit="submitForm"
        method="POST"
        novalidate="true"
    >
        <div class="modal-body">
            <p v-if="errors.length">
                <b>Please correct the following error(s):</b>
                <ul>
                    <li v-for="error in errors">{{ error }}</li>
                </ul>
            </p>
            <label for="name">Name</label>
            <div class="input-group">
                <input
                    class="form-control"
                    v-model="organization.name"
                    type="text"
                    name="name"                            
                >
            </div>
            <label for="description">Description</label>
            <div class="input-group">
                <input
                    class="form-control"
                    v-model="organization.description"
                    type="text"
                    name="description"
                >
            </div>
            <label for="logo">Logo</label>
            <div class="input-group">
                <input
                    class="form-control-file"
                    @change="processFile"
                    type="file"
                    name="logo"
                >
            </div>
        </div>
        <div class="modal-footer">
            <button
                class="btn btn-secondary"
                type="button"
                data-dismiss="modal"
            >
                Close
            </button>
            <button
                class="btn btn-outline-primary"
                type="submit"
            >
                Add
            </button>
        </div>
    </form>
    `,
    data : function () {
        return {
            errors: [],
            organization : {
                name: null,
                description: null,
                logo: null,
            }
        }
    },
    methods: {
        checkForm: function() {
            if (this.name) {
                return true;
            }

            this.errors = [];
            if (!this.name) {
                this.errors.push('Name required');
            }
            return false;
        },
        submitForm: function(e) {
            e.preventDefault();
            if (!this.checkForm()) {
                return;
            }
            axios
                .post('/api/organizations/add', 
                {
                    "name": this.organization.name,
                    "description": this.organization.description,
                    "logo": this.organization.logo,
                    "users": [],
                    "resources": []
                })
                .then(response => {
                    alert(response);
                });
        },
        processFile(e) {
            this.organization.logo = e.target.files[0];
        }
    }
});