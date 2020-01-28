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
    
});