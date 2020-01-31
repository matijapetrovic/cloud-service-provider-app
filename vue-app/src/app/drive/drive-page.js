Vue.component("drive-page",{
    template: `
        <base-layout v-bind:page-title="$route.meta.title">
            <div v-if="$root.isSuperAdmin">
                <super-admin-drive-page></super-admin-drive-page>
            </div>
            <div v-else-if="$root.isAdmin">
                <admin-drive-page></admin-drive-page>
            </div>
            <div v-else>
                <user-drive-page></user-drive-page>
            </div>
        </base-layout>
    `
});