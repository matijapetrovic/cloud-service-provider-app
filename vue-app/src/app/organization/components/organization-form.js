Vue.component("organization-form", {
    template: `
    <main-form
        :id="id"
        :headerText="headerText"
        :buttonText="buttonText"
        @submit="emitSubmit"
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
    props: {
        id: String,
        headerText: String,
        buttonText: String,
    },
    data : function () {
        return {
            organization : {
                name: null,
                oldName: null,
                description: null,
                logo: null,
            }
        }
    },
    methods: {
        getOrganization: function(name) {
            axios
                .get('/api/organizations/' + name)
                .then(response => {
                    this.organization = response.data,
                    this.organization.oldName = response.data.name;
                })
                .catch(err => {
                    const status = err.response.status;
                    const msg = err.response.data;
                    alert('' + status + ': ' +  msg);
                });
        },
        emitSubmit() {
            this.$emit('submit', this.organization);
        }
    }
});