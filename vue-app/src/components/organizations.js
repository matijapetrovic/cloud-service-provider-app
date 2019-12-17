Vue.component("organizations-page", {
    template: `
    <div>
        <nav-bar></nav-bar>
        <table border="1" class="table table-dark">
            <tr>
                <th>Name</th>
                <th>Description</th>
                <th>Logo</th>
            </tr>
        
            <tr v-for="org in organizations">
                <td><a href="#">{{ org.name }}</a></td>
                <td>{{ org.description }}</td>
                <td>{{ org.logo }}</td>
            </tr>
        </table>
    </div>
    `
    ,
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
})