Vue.component("logout-button", {
    template:`
    <button
        class="btn btn-outline-light"
        @click="logout"
        type="submit"
    >
        Log out
    </button>
    `,
    methods: {
        logout () {
            localStorage.removeItem('user-token');
            localStorage.removeItem('user');
            delete axios.defaults.headers.common['Authorization'];
            this.$root.updateCurrentUser();
            this.$router.push('/login');
        }
    }
});