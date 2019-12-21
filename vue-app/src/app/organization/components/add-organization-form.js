Vue.component("add-org-form", {
    template: `
        <main-form 
            id="addOrganizationForm"
            method="POST"
            headerText="Organization info"
            buttonText="Add"
            v-on:submit="submitForm($event)"
            ref="form"
        >
            <text-input
                name="name"
                v-model="organization.name"
                required
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
        checkResponse: function(response) {
            if (response.status === 200) {
                this.$emit('addedOrganization', this.organization);
                alert('Adding organization successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
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
                    this.checkResponse(response);
                });
        }
    }
});