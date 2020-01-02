Vue.component("drive-page",{
    template: `

        <base-layout v-bind:page-title="$route.meta.title">
            <div v-if="user.role ==='SUPER_ADMIN'">
                <super-admin-drive-page></super-admin-drive-page>
            </div>
            <div v-else-if="user.role ==='ADMIN'">
                <admin-drive-page></admin-drive-page>
            </div>
            <div v-else="user.role ==='USER'">
                <user-drive-page></user-drive-page>
            </div>    
        </base-layout>
    `
    ,
    data: function(){
        return {
            user : {
                email : null,
                password : null,
                name : null,
                surname : null,
                role : null,
                oganization : null
            }
        }
    },
    mounted(){
        axios
        .get('api/users/currentUser')
        .then(response => {
            this.user = response.data;
        });
    }
});