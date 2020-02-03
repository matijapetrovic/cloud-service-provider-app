Vue.component('profile',{
    template: `
        <div>
            <div id="particles-js"></div>
            <div style="height: 100vh;background-color: #595c5e; color: white;">
                <profile-info></profile-info>  
            </div>
        </div>
    `,
    mounted() {
        this.$root.initParticles();
    }
});