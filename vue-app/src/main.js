const ProfilePage = {template: '<profile></profile>'}
const UsersPage = { template : '<users-page></users-page>' }
const LoginPage = { template: '<login-page></login-page>' }
const OrganizationsPage = { template: '<organizations-page></organizations-page>' }
const VMPage = { template: '<virtual-machines-page></virtual-machines-page>'}
const DrivePage = {template: '<drive-page></drive-page>'}
const CategoryPage = {template: '<category-page></category-page>'}
const Report = {template: '<report-page></report-page>'}

// kad se refreshuje stranica da pokupi token
const token = localStorage.getItem('user-token');
if (token) {
  axios.defaults.headers.common['Authorization'] = token;
}

const router = new VueRouter({
    mode : 'hash',
    routes : [
        { path: '/', component: VMPage, meta: {title: 'Home', requiresAuth: true, authorize: ["SUPER_ADMIN", "ADMIN", "USER"]}},
        { path: '/login', component: LoginPage, meta: { title: 'Login', requiresAuth: false}},
        { path: '/organizations', component: OrganizationsPage, meta: { title: 'Organizations', requiresAuth: true, authorize: ["SUPER_ADMIN"]} },
        { path: '/users', component: UsersPage, meta: { title: 'Users', requiresAuth: true, authorize: ["SUPER_ADMIN", "ADMIN"] }},
        { path: '/virtualmachines', component: VMPage, meta: { title:'Virtual Machines', requiresAuth: true, authorize: ["SUPER_ADMIN", "ADMIN", "USER"]}},
        { path: '/profile', component: ProfilePage, meta: { title:'Profile', requiresAuth: true, authorize: ["SUPER_ADMIN", "ADMIN", "USER"]}},
        { path: '/drives', component: DrivePage, meta: { title:'Drives', requiresAuth: true, authorize: ["SUPER_ADMIN", "ADMIN", "USER"]}},
        { path: '/categories', component: CategoryPage, meta: { title:'Categories', requiresAuth: true, authorize: ["SUPER_ADMIN"]}},
        { path: '/report', component: Report, meta: { title:'Report', requiresAuth: true, authorize: ["ADMIN"]}}
    ]
});

router.beforeEach((to, from, next) => {
    let currentUser = JSON.parse(localStorage.getItem("user"));

    let userIsLoggedIn = function() {
        return currentUser != null;
    };
    let routeRequiresAuthentication = function() {
        return to.matched.some(record => record.meta.requiresAuth);
    };
    let userIsAuthorized = function() {
        return to.matched.some(record => record.meta.authorize.includes(currentUser.role));
    };
    let redirectHome = function() {
        next({
            path: '/'
        });
    };
    let redirectLogin = function() {
        next({
            path: '/login',
            params: { nextUrl: to.fullPath }
        });
    };
    let forward = function() {
        next();
    };

    if (routeRequiresAuthentication()) {
        if (userIsLoggedIn())
            userIsAuthorized() ? forward() : redirectHome();
        else
            redirectLogin();
    } 
    else
        userIsLoggedIn() ? redirectHome() : forward();
});

var app = new Vue({
    template: `
        <router-view></router-view>
    `,
    router,
    el: '#app',
    data: {
        currentUser: null
    },
    computed: {
        isSuperAdmin() {
            return this.currentUser && this.currentUser.role === "SUPER_ADMIN";
        },
        isAdmin() {
            return this.currentUser && this.currentUser.role === "ADMIN";
        },
        isDefaultUser() {
            return this.currentUser && this.currentUser.role === "USER";
        }
    },
    methods: {
        updateCurrentUser() {
            this.currentUser = JSON.parse(localStorage.getItem("user"));
        },
        initParticles() {
            particlesJS('particles-js', {
                "particles": {
                  "number": {
                    "value": 80,
                    "density": {
                      "enable": true,
                      "value_area": 800
                    }
                  },
                  "color": {
                    "value": "#27AAE1"
                  },
                  "shape": {
                    "type": "circle",
                    "stroke": {
                      "width": 0,
                      "color": "#000000"
                    },
                    "polygon": {
                      "nb_sides": 5
                    },
                    "image": {
                      "src": "img/github.svg",
                      "width": 100,
                      "height": 100
                    }
                  },
                  "opacity": {
                    "value": 0.5,
                    "random": false,
                    "anim": {
                      "enable": false,
                      "speed": 1,
                      "opacity_min": 0.1,
                      "sync": false
                    }
                  },
                  "size": {
                    "value": 3,
                    "random": true,
                    "anim": {
                      "enable": false,
                      "speed": 40,
                      "size_min": 0.5,
                      "sync": false
                    }
                  },
                  "line_linked": {
                    "enable": true,
                    "distance": 160,
                    "color": "#27AAE1",
                    "opacity": 0.5,
                    "width": 0.8
                  },
                  "move": {
                    "enable": true,
                    "speed": 3,
                    "direction": "none",
                    "random": false,
                    "straight": false,
                    "out_mode": "bounce",
                    "bounce": false,
                    "attract": {
                      "enable": true,
                      "rotateX": 600,
                      "rotateY": 1200
                    }
                  }
                },
                "interactivity": {
                  "detect_on": "window",
                  "events": {
                    "onhover": {
                      "enable": true,
                      "mode": "grab"
                    },
                    "onclick": {
                      "enable": true,
                      "mode": "bubble"
                    },
                    "resize": true
                  },
                  "modes": {
                    "grab": {
                      "distance": 200,
                      "line_linked": {
                        "opacity": 1
                      }
                    },
                    "bubble": {
                      "distance": 200,
                      "size": 3,
                      "duration": 0.3,
                      "opacity": 1,
                      "speed": 3
                    },
                    "repulse": {
                      "distance": 200,
                      "duration": 0.4
                    },
                    "push": {
                      "particles_nb": 4
                    },
                    "remove": {
                      "particles_nb": 2
                    }
                  }
                },
                "retina_detect": true
              });
        }
    },
    beforeMount() {
        this.updateCurrentUser();
    }
});