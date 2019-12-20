Vue.component("org-modal", {
    template: `
        <full-modal modal-id="addOrganizationModal">
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
                        id="name"
                        class="form-control"
                        v-model="name"
                        type="text"
                        name="name"                            
                    >
                </div>
                <label for="description">Description</label>
                <div class="input-group">
                    <input
                        id="description"
                        class="form-control"
                        v-model="description"
                        type="text"
                        name="description"
                    >
                </div>
                <label for="logo">Logo</label>
                <div class="input-group">
                    <input
                        id="logo"
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
                    class="btn btn-primary"
                    type="submit"
                >
                    Add
                </button>
            </div>
        </form>
        </full-modal>

    `,
    data : function () {
        return {
            errors: [],
            name: null,
            description: null,
            logo: null
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
                    "name": this.name,
                    "description": this.description,
                    "logo": this.logo,
                    "users": [],
                    "resources": []
                })
                .then(response => {
                    alert(response);
                });
        },
        processFile(e) {
            this.logo = e.target.files[0];
        }
    }
});