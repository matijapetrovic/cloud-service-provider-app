Vue.component("add-org-form", {
    template: `
        <main-form 
            id="addOrganizationForm"
            method="POST"
            headerText="Organization info"
            buttonText="Add"
            v-on:submit="submitForm($event)"
        >
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
        </main-form>
    `,
    data : function () {
        return {
            organization : {
                name: null,
                description: null,
                logo: null,
            }
        }
    },
    methods: {
        submitForm: function() {
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