Vue.component("view-organization-page", {
    template: `
    <div>
        
    </div>
    `,

    data: function() {
        return {
            name: null,
            organization: null
        }
    },
    mounted() {
        axios
            .get('/api/organizations/' + this.name + '/')
            .then(response => {
                this.organization = response.data
            })
    }
});