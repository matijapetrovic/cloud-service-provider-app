Vue.component("organizations-page", {
    template: `
    <div>
        <button type="button" class="btn btn-outline-primary" data-toggle="modal" v-bind:data-target="'#' + addModalId" style="margin: 15px 0;">
                Add organization
        </button>
        <org-table v-bind:view-modal-id="viewModalId"></org-table>
        <org-modal v-bind:modal-id="addModalId" modal-title="Add Organization"></org-modal>
        <org-modal v-bind:modal-id="viewModalId" modal-title="View Organization"></org-modal>
    </div>
    `,
    data : function() {
        return {
            addModalId: 'addOrganizationModal',
            viewModalId: 'viewOrganizationModal'
        }
    } 
});