Vue.component("login-page", {
    template: `
    <form
        id="loginForm"
        method="POST"
        @submit="submitForm"
        class="form-signin"
        novalidate
    >
        <h1>Log in</h1>
        <email-input
            name="email"
            v-model="user.email"
            required
            autofocus
        >
            E-mail
        </email-input>
        <password-input
            name="password"
            v-model="user.password"
            required
        >
            Password
        </password-input>
        <button
            class="btn btn-primary btn-block"
            type="submit"
        >
            Log in
        </button> 
    </form>
    `,
    data: function() {
        return {
            user: {
                email: null,
                password: null
            }
        }
    },
    methods : {
        validateForm: function() {
            var form = document.getElementById('loginForm');
            form.classList.add('was-validated');
            return form.checkValidity();
        },
        submitForm : function(e) {
            e.preventDefault();
            if (this.validateForm()) {
                axios
                    .post('/api/login', 
                    {
                        email: this.user.email,
                        password: this.user.password
                    })
                    .then(response => {
                        const token = response.data.token;
                        // cuvamo u local storageu da bismo pristupili iz bilo koje komponente
                        // i da bi ostalo i kad se refreshuje
                        localStorage.setItem('user-token', token);
                        localStorage.setItem('user', response.data.user);
                        // stavljamo po defaultu u header da bi axios automatski
                        // slao zahteve sa nasim tokenom
                        axios.defaults.headers.common['Authorization'] = token;
                        this.$router.push('/');
                    })
                    .catch(err => {
                        localStorage.removeItem('user-token');
                        const status = err.response.status;
                        const msg = err.response.data;
                        alert('' + status + ': ' +  msg);
                    })
            }
        }
    }
});