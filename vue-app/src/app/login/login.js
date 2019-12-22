Vue.component("login-page", {
    template: `
        <main-form
            id="loginForm"
            method="POST"
            headerText="Login form"
            buttonText="Login"
            v-on:submit="submitForm($event)"
        >
            <email-input
                name="email"
                v-model="user.email"
                required
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
        </main-form>
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
        submitForm : function() {
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
});