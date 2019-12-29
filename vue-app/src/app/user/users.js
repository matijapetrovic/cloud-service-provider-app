Vue.component("users-page",{
    template: `

        <base-layout v-bind:page-title="$route.meta.title">
            <super-admin-page></super-admin-page>
        </base-layout>
    `
    ,
    data: function(){
        return {
            users : null,
            user : null
        }
    },
});