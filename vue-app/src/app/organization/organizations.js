Vue.component("org-page", {
    template: `
    <div>
        <nav-bar></nav-bar>
        <h1>Organizations</h1>
        <org-table></org-table>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrganizationModal">
                Add organization
        </button>
        <org-modal></org-modal>
    </div>
    `
});

