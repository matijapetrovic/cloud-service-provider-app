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
            <td>
                <a 
                href="#" 
                @click.prevent="viewOrganization(org.name)" 
                data-toggle="modal" 
                :data-target="'#' + viewModalId"
                >
                    {{ org.name }}
                </a>
            </td>
            <td>{{ org.description }}</td>
            <td><img style="height:2em;" :src="'/' + org.logo"></td>
        </tr>
    </table>
    `,
    props : {
        viewModalId : String
    },
    data : function () {
        return {
            organizations: null
        }
    },
    mounted () {
        this.getOrganizations();
    },
    methods: {
        getOrganizations() {
            axios
            .get('api/organizations')
            .then(response => {
                this.organizations = response.data;
            });
        },
        addOrganization(organization) {
            this.organizations.push(organization);
        },
        updateOrganization(organization) {
            var el = this.organizations.find(function(element) {
                return element.name === organization.name;
            });
            var idx = this.organizations.indexOf(el);
            this.organizations.splice(idx, 1);
            this.organizations.splice(idx, 0, organization);
        },
        viewOrganization(name) {
            this.$emit('viewOrganization', name);
        }
    }
});