Vue.component("organizations-page", {
    template: `
    <div>
        <button type="button" class="btn btn-outline-primary" data-toggle="modal" v-bind:data-target="'#' + addModalId" style="margin: 15px 0;">
                Add organization
        </button>
        <org-table v-bind:view-modal-id="viewModalId"></org-table>
        <full-modal v-bind:modal-id="addModalId" modal-title="Add organization">
            <add-org-form></add-org-form>
        </full-modal>
        <full-modal v-bind:modal-id="viewModalId" modal-title="View organization">
            <view-org-form></view-org-form>
        </full-modal>
    </div>
    `,
    data : function() {
        return {
            addModalId: 'addOrganizationModal',
            viewModalId: 'viewOrganizationModal'
        }
    } 
});