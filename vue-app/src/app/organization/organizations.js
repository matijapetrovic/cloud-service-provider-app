Vue.component("organizations-page", {
    template: `
    <base-layout :page-title="$route.meta.title">
        <button
            type="button"
            class="btn btn-outline-primary"

            data-toggle="modal"
            :data-target="'#' + addModalId"
            >
                Add organization
        </button>

        <org-table
            @viewOrganization="viewOrganization($event)"
            :view-modal-id="viewModalId"
            ref="table"
            >
        </org-table>

        <full-modal
            @close="removeAddValidation"
            :modal-id="addModalId"
            modal-title="Add organization"
            >
                <organization-form
                    id="addOrganizationForm"
                    headerText="Organization info"
                    buttonText="Add"
                    @submit="addOrganization($event)"
                    ref="addForm"
                >
                </organization-form>
        </full-modal>

        <full-modal
            @close="removeViewValidation"
            :modal-id="viewModalId"
            modal-title="View organization"
            >
                <organization-form
                    id="viewOrganizationForm"
                    headerText="Organization info"
                    buttonText="Update"
                    @submit="updateOrganization($event)"
                    ref="viewForm"
                >
                </organization-form>
        </full-modal>
    </base-layout>
    `,
    data : function() {
        return {
            addModalId: 'addOrganizationModal',
            viewModalId: 'viewOrganizationModal'
        }
    },
    methods: {
        addOrganization(organization) {
            axios
                .post('/api/organizations', 
                {
                    "name": organization.name,
                    "description": organization.description,
                    "logo": organization.logo,
                    "users": [],
                    "resources": []
                })
                .then(response => {
                    this.$refs.table.addOrganization(response.data); 
                    alert('Adding organization successful');
                });
            
        },
        updateOrganization(organization) {
            axios
                .put('/api/organizations/' + organization.name, 
                {
                    "name": organization.name,
                    "description": organization.description,
                    "logo": organization.logo,
                    "users": [],
                    "resources": []
                })
                .then(response => {
                    this.$refs.table.updateOrganization(response.data);
                    alert('Updating organization successful');
                });
        },
        removeViewValidation() {
            this.$refs.viewForm.$refs.form.removeValidation();
        },
        removeAddValidation () {
            this.$refs.addForm.$refs.form.removeValidation();
        },
        closeViewModal() {
            this.removeViewValidation();
            $('#' + this.viewModalId).modal('hide');
        },
        closeAddModal() {
            this.removeAddValidation();
            $('#' + this.addModalId).modal('hide');
        },
        viewOrganization(name) {
            this.$refs.viewForm.getOrganization(name);
        }
    }
});