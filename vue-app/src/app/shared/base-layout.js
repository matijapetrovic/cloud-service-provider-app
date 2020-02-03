Vue.component("base-layout", {
    template: `
    <div>
        <nav-bar></nav-bar>
        <div id="particles-js"></div>
        <div class="container-fluid">
            <div class="row">
                <side-nav></side-nav>
                <main role="main" class="col-md-9 ml-sm-auto col-lg-10 px-4">
                <div class="d-flex justify-content-between flex-wrap flex-md-nowrap align-items-center pt-3 pb-2 mb-3 border-bottom">
                    <h1 class="h2">{{ pageTitle }}</h1>
                </div>
                <div class="container">
                    <slot></slot>
                </div>
            </main>
            </div>
        </div>
    </div>
    `,
    props: {
        pageTitle: String
    },
    mounted() {
        this.$root.initParticles();
    }
})