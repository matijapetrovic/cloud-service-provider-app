Vue.component("login-page", {
    template: `
    <form
        id="#loginForm"
        action="http://localhost:8080/login"
        method="POST"
        v-on:submit="submitForm"
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
        checkResponse: function(response) {
            if (response.status === 200) {
                localStorage.setItem('jwt', response.data.token);               
                if (localStorage.getItem('jwt') != null) {
                    alert('Login successful');
                    this.$emit('loggedIn');
                    if (this.$route.params.nextUrl != null) {
                        this.$router.push(this.$route.params.nextUrl);
                    } else {
                        this.$router.push('');
                    }
                }
            }
            else {
                alert('Error: ' + response.data);
            }
        },
        validateForm: function() {
            var form = document.getElementById('loginForm');
            form.classList.add('was-validated');
            return form.checkValidity();
        },
        submitForm : function() {
            e.preventDefault();
            if (this.validateForm()) {
                axios
                    .post('/api/login', {
                        email: this.email,
                        password: this.password
                    })
                    .then(response => {
                        this.checkResponse(response);
                    })
            }
        }
    }
});