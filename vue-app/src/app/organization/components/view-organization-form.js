Vue.component("view-org-form", {
    template: `
        <main-form 
            id="viewOrganizationForm"
            method="PUT"
            headerText="Organization info"
            buttonText="Update"
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
            errors: [],
            organization : {
                name: null,
                description: null,
                logo: null,
            }
        }
    },
    // nece ici u mounted nego on click
    mounted () {
        axios
        // placeholder name
            .get('/api/organizations/Doktori')
            .then(response => {
                this.organization = response.data
            })
    },
    methods: {
        checkResponse: function(response) {
            if (response.status === 200) {
                alert('Updating organization successful');
                this.$emit('submit')
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        submitForm: function(e) {
            axios
                .post('/api/organizations/update/' + this.organization.name, 
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