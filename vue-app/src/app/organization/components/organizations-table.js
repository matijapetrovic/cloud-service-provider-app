Vue.component("org-table", {
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
            <td><a href="#" data-toggle="modal" data-target="#viewOrganizationModal">{{ org.name }}</a></td>
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
});