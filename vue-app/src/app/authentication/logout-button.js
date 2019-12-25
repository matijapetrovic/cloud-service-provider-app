Vue.component("logout-button", {
    template:`
    <button
        class="btn btn-outline-primary"
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
            this.$router.push('/login');
        }
    }
});