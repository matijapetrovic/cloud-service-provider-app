Vue.component("organizations-page", {
    template: `
    <div>
        <nav-bar></nav-bar>
        <h1>Organizations</h1>
        <organizations-table></organizations-table>
        <button type="button" class="btn btn-primary" data-toggle="modal" data-target="#addOrganizationModal">
                Add organization
        </button>
        <add-organization-modal></add-organization-modal>
    </div>
    `
    ,
    
    components: {
        'organizations-table': OrganizationsTable,
        'add-organization-modal': AddOrganizationModal
    }
});

var OrganizationsTable = {
    template: `
    <table border="1" class="table">
        <thead class="thead-dark">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Logo</th>
            </tr>
        </thead>
    
        <tr v-for="org in organizations">
            <td><a href="#">{{ org.name }}</a></td>
            <td>{{ org.description }}</td>
            <td>{{ org.logo }}</td>
        </tr>
    </table>
    `,
    data : function () {
        return {
            organizations: null
        }
    },
    mounted () {
        axios
            .get('/api/organizations')
            .then(response => {
                this.organizations = response.data
            })
    }
};

var AddOrganizationModal = {
    template: `
    <div class="modal fade" id="addOrganizationModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
        <div class="modal-dialog" role="document">
            <div class="modal-content">
            <div class="modal-header">
                <h5 class="modal-title" id="exampleModalLabel">Add organization</h5>
                <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                <span aria-hidden="true">&times;</span>
                </button>
            </div>
            <form action="http://localhost:8080/organizations" method="POST" id="addOrganizationForm">
                <div class="modal-body">
                    <label for="name">Name</label>
                    <div class="input-group"><input class="form-control" type="text" name="name" id=""></div>
                    <label for="description">Description</label>
                    <div class="input-group"><input class="form-control" type="text" name="description"></div>
                    <label for="logo">Logo</label>
                    <div class="input-group"><input class="form-control-file" type="file" name="logo" id=""></div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
                    <button type="button" class="btn btn-primary" type="submit">Add</button>
                </div>
            </form>
            </div>
        </div>
    </div>
    `
};

