Vue.component("organizations-page", {
    template: `
    <div>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrganizationModal" style="margin: 15px 0;">
                Add organization
        </button>
        <org-table></org-table>
        <org-modal></org-modal>
    </div>
    `
});