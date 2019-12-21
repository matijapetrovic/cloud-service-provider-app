Vue.component("add-org-form", {
    template: `
        <form
            id="addOrganizationForm"
            @submit="submitForm"
            method="POST"
            novalidate="true"
        >
                <p v-if="errors.length">
                    <b>Please correct the following error(s):</b>
                    <ul>
                        <li v-for="error in errors">{{ error }}</li>
                    </ul>
                </p>
                <text-input
                    name="name"
                    v-model="organization.name"
                >
                    Name
                </text-input>
                <text-input
                    name="description"
                    v-model="organization.description"
                >
                    Description
                </text-input>
                <file-input
                    name="logo"
                    v-model="organization.logo"
                >
                    Logo
                </file-input>
                <button
                    class="btn btn-outline-primary"
                    type="submit"
                >
                    Add
                </button>
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
        }
    }
});