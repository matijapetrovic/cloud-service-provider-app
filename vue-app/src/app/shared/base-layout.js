Vue.component("base-layout", {
    template: `
    <div>
        <nav-bar></nav-bar>
        <div class="container-fluid">
            <div class="row">
                <side-nav></side-nav>
                <main-section v-bind:page-title="pageTitle"></main-section>
            </div>
        </div>
    </div>
    `,
    props: {
        pageTitle: String
    }
})