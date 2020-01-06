Vue.component("category-page",{
    template: `

        <base-layout v-bind:page-title="$route.meta.title">
            <div v-if="user.role ==='SUPER_ADMIN'">
                <super-admin-category-page></super-admin-category-page>
            </div>
            <div v-else-if="user.role ==='ADMIN'">
                <admin-category-page></admin-category-page>
            </div>
            <div v-else="user.role ==='USER'">
                <user-category-page></user-category-page>
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