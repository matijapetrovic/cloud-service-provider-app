Vue.component("organizations-page", {
    template: `
    <div>
        <button
            type="button"
            class="btn btn-outline-primary"
            data-toggle="modal"
            v-bind:data-target="'#' + addModalId"
            style="margin: 15px 0;"
            >
                Add organization
        </button>
        <org-table
            v-bind:view-modal-id="viewModalId"
            ref="table"
            >
            </org-table>
        <full-modal
            @close="removeAddValidation"
            v-bind:modal-id="addModalId"
            modal-title="Add organization"
            >
                <add-org-form
                    @submit="closeAddModal"
                    @addedOrganization="addOrganization($event)"
                    ref="addForm"
                    >
                    </add-org-form>
        </full-modal>
        <full-modal
            @close="removeViewValidation"
            v-bind:modal-id="viewModalId"
            modal-title="View organization"
            >
                <view-org-form
                    @submit="closeViewModal"
                    ref="viewForm"
                    >
                    </view-org-form>
        </full-modal>
    </div>
    `,
    data : function() {
        return {
            addModalId: 'addOrganizationModal',
            viewModalId: 'viewOrganizationModal'
        }
    },
    methods: {
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
        addOrganization(organization) {
            this.$refs.table.addOrganization(organization);
        }
    }
});