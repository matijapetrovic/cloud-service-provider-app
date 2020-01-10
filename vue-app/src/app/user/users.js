Vue.component("users-page",{
    template: `
    <base-layout v-bind:page-title="$route.meta.title">
        <div v-if="role==='SUPER_ADMIN'">
            <super-admin-page></super-admin-page>
        </div>
        <div v-else-if="role==='ADMIN'">
            <super-admin-page></super-admin-page>
        </div>
        <div v-else="role==='USER'">
            <admin-page></admin-page>
        </div>    
    </base-layout>
    `
    ,
    data: function(){
        return {
            role: null
        }
    },
    mounted(){
        let user = JSON.parse(localStorage.getItem("user"));
        this.role = user.role;
    }
});