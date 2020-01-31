Vue.component("category-page",{
    template: `

        <base-layout v-bind:page-title="$route.meta.title">
            <div v-if="$root.isSuperAdmin">
                <super-admin-category-page></super-admin-category-page>
            </div>
            <div v-else-if="$root.isAdmin">
                <admin-category-page></admin-category-page>
            </div>
            <div v-else>
                <user-category-page></user-category-page>
            </div>    
        </base-layout>
    `
});