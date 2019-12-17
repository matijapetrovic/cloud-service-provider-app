Vue.component("organizations-table", {
    template: `
    <table border="1">
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
    `
    ,
    data : function () {
        return {
            organizations: null
        }
    },
    mounted () {
        axios
            .get('/organizations')
            .then(response => {
                this.organizations = response.data
            })
    }
})